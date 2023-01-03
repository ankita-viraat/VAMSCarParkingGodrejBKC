package info.vams.zktecoedu.reception.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

//import com.commercial.vams.viraat.Fragment.CreateAppointmentFragment;

@SuppressLint("SdCardPath")
public class Imageutils {
    static Context context;
    private Activity current_activity;
    private Fragment current_fragment;

    private ImageAttachmentListener imageAttachment_callBack;

    private String selected_path = "";
    private Uri imageUri;
    private File path = null;

    private int from = 0;
    private boolean isFragment = false;

    public Imageutils(Activity act) {

        this.context = act;
        this.current_activity = act;
        imageAttachment_callBack = (ImageAttachmentListener) context;
    }

    public Imageutils(Activity act, Fragment fragment, boolean isFragment) {

        this.context = act;
        this.current_activity = act;
        imageAttachment_callBack = (ImageAttachmentListener) fragment;
        if (isFragment) {
            this.isFragment = true;
            current_fragment = fragment;
        }

    }

    /**
     * Get file name from path
     *
     * @param path
     * @return
     */

    public String getfilename_from_path(String path) {
        return path.substring(path.lastIndexOf('/') + 1, path.length());

    }

    /**
     * Get Image URI from Bitmap
     *
     * @param context
     * @param photo
     * @return
     */

    public Uri getImageUri(Context context, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 80, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }

    /**
     * Get Path from Image URI
     *
     * @param uri
     * @return
     */

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.context.getContentResolver().query(uri, projection, null, null, null);
        int column_index = 0;
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        } else
            return uri.getPath();
    }

    /**
     * Bitmap from String
     *
     * @param encodedString
     * @return
     */
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    /**
     * Get String from Bitmap
     *
     * @param bitmap
     * @return
     */

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    /**
     * Check Camera Availability
     *
     * @return
     */

    public boolean isDeviceSupportCamera() {
        if (this.context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    /**
     * Compress Imgae
     *
     * @param imageUri
     * @param height
     * @param width
     * @return
     */


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Bitmap compressImage(Uri imageUri, float height, float width) {

//        String filePath = getRealPathFromURI(imageUri);
        String filePath = getPath(context,imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        // by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        // you try the use the bitmap here, you will get null.
//        options.inSampleSize = 2;
        options.inJustDecodeBounds = true;
//
//        if(filePath.contains("/storage/emulated/0")) {
//            filePath= filePath.replace("/storage/emulated/0","/storage/emulated/10");
//        }



        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        // max Height and width values of the compressed image is taken as 816x612

        float maxHeight = height;
        float maxWidth = width;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        // width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        //  setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        //  inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

        // this options allow android to claim the bitmap memory if it runs low on memory

        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            //  load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
//        if(VisitorEntryActivity.isFrontCamera) {
//            scaleMatrix.postScale(1, -1, middleX, middleY);
//        }

//        Canvas canvas = new Canvas(scaledBitmap);
//        canvas.setMatrix(scaleMatrix);
//        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
//
//        // check the rotation of the image and display it properly
//        Matrix matrix = new Matrix();
//        ExifInterface exif;
//        try {
//         /*   if(!VisitorEntryActivity.isFrontCamera) {
//                exif = new ExifInterface(filePath);
//                int orientation = exif.getAttributeInt(
//                        ExifInterface.TAG_ORIENTATION, 0);
//                Log.d("EXIF", "Exif: " + orientation);
//
//                if (orientation == 6) {
//                    matrix.postRotate(-90);
//                    Log.d("EXIF", "Exif: " + orientation);
//                } else if (orientation == 3) {
//                    matrix.postRotate(-180);
//                    Log.d("EXIF", "Exif: " + orientation);
//                } else if (orientation == 8) {
//                    matrix.postRotate(-270);
//                    Log.d("EXIF", "Exif: " + orientation);
//                }
//            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,true);
//            }else {
//                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());
//            }*/
//
//            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,true);


        try {
            return Utilities.modifyOrientation(scaledBitmap,filePath,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        return null;
    }

    /**
     * Get RealPath from Content URI
     *
     * @param contentURI
     * @return
     */
    private static String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    private static String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * ImageSize Calculation
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    /**
     * Launch Camera
     *
     * @param from
     */

    public void launchCamera(int from) {
        this.from = from;

        if (Build.VERSION.SDK_INT >= 23) {
            if (isFragment)
                permission_check_fragment(1);
            else
                permission_check(1);
        } else {
            camera_call();
        }
    }

    /**
     * Launch Gallery
     *
     * @param from
     */

    public void launchGallery(int from) {

        this.from = from;

        if (Build.VERSION.SDK_INT >= 23) {
            if (isFragment)
                permission_check_fragment(2);
            else
                permission_check(2);
        } else {
            galley_call();
        }
    }

    /**
     * Show AlertDialog with the following options
     * <p>
     * Camera
     * Gallery
     *
     * @param from
     */

    public void imagepicker(final int from, boolean hasImage) {
        this.from = from;

        final CharSequence[] items;

        if (isDeviceSupportCamera()) {
            if (hasImage) {
                items = new CharSequence[3];
                items[0] = "Camera";
                items[1] = "Gallery";
                items[2] = "Remove Image";
            } else {
                items = new CharSequence[2];
                items[0] = "Camera";
                items[1] = "Gallery";
            }
        } else {
            if (hasImage) {
                items = new CharSequence[2];
                items[0] = "Gallery";
                items[1] = "Remove Image";
            } else {
                items = new CharSequence[1];
                items[0] = "Gallery";
            }

        }
        android.app.AlertDialog.Builder alertdialog = new android.app.AlertDialog.Builder(current_activity);
//        alertdialog.setTitle("Add Image From");
        alertdialog.setTitle(null);
        alertdialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {


                    launchCamera(from);
                } else if (items[item].equals("Gallery")) {
                    launchGallery(from);
                } else if (items[item].equals("Remove Image")) {
//                    if (MyProfileActivity.getDashboardFragmentInstance() != null) {
//                        MyProfileActivity.getDashboardFragmentInstance().showDefaultImage();
//                    }
//                    if (CreateAppointmentActivity.getInstance() != null) {
//                        CreateAppointmentActivity.getInstance().showDefaultImage();
//                    }
//
//                    if (SendInvitationActivity.getInstance() != null) {
//                        SendInvitationActivity.getInstance().showDefaultImage();
//                    }
                }
            }
        });
        alertdialog.show();

    }

    /**
     * Check permission
     *
     * @param code
     */

    public void permission_check(final int code) {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(current_activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(current_activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                showMessageOKCancel("For adding images , You need to provide permission to access your files",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ActivityCompat.requestPermissions(current_activity,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        code);
                            }
                        });
                return;
            }

            ActivityCompat.requestPermissions(current_activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    code);
            return;
        }

        if (code == 1)
            camera_call();
        else if (code == 2)
            galley_call();
    }


    /**
     * Check permission
     *
     * @param code
     */

    public void permission_check_fragment(final int code) {
        Log.d(TAG, "permission_check_fragment: " + code);
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(current_activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)

        {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(current_activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                showMessageOKCancel("For adding images , You need to provide permission to access your files",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                current_fragment.requestPermissions(
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        code);
                            }
                        });
                return;
            }

            current_fragment.requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    code);
            return;
        }

        if (code == 1)
            camera_call();
        else if (code == 2)
            galley_call();
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(current_activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    /**
     * Capture image from camera
     */

    public void camera_call() {
        try {
            ContentValues values = new ContentValues();
            imageUri = current_activity.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent1.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
            intent1.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            intent1.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                intent1.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
//            } else {
//                intent1.putExtra("android.intent.extras.CAMERA_FACING", 1);
//            }

            if (isFragment)
                current_fragment.startActivityForResult(intent1, 0);
            else
                current_activity.startActivityForResult(intent1, 0);
        } catch (ActivityNotFoundException e) {
//            Toast.makeText(context, context.getString(R.string.cameranotfound), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * pick image from Gallery
     */

    public void galley_call() {
        try {
            Log.d(TAG, "galley_call: ");

            Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent2.setType("image/*");
//            intent2.setComponent(DeviceAdminReceiver.getComponentName(context));
            if (isFragment)
                current_fragment.startActivityForResult(intent2, 1);
            else
                current_activity.startActivityForResult(intent2, 1);
        } catch (ActivityNotFoundException e) {
//            Toast.makeText(context, context.getString(R.string.gallerynotfound), Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Activity PermissionResult
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void request_permission_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    camera_call();
                } else {
                    Toast.makeText(current_activity, "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;

            case 2:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galley_call();
                } else {

                    Toast.makeText(current_activity, "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    /**
     * Intent ActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String file_name;
        Bitmap bitmap;

        Log.e(TAG, "onActivityResult: "+data.getDataString() );

        switch (requestCode) {
            case 200:
                if (resultCode == RESULT_OK) {
//                    Log.i("Camera Selected", String.valueOf(data.getParcelableExtra("imageUri")));
                    try {
                        selected_path = null;
                        imageUri = Uri.parse(data.getStringExtra("imageUri"));
                        selected_path = getPath(imageUri);
                        // Log.i("selected","path"+selected_path);
                        file_name = selected_path.substring(selected_path.lastIndexOf("/") + 1);
                        // Log.i("file","name"+file_name);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inJustDecodeBounds = true;


                        boolean frontCam = data.hasExtra("frontCamera");
                        Bitmap bmp = BitmapFactory.decodeFile(selected_path, options);
                        bitmap = Utilities.modifyOrientation(bmp,selected_path,frontCam);
//                        bitmap = compressImage(imageUri, 408, 306);
                        /*if(VisitorEntryActivity.isFrontCamera) {
                            rotateImage(new File(selected_path),VisitorEntryActivity.isFrontCamera);
                        }*/
                        imageAttachment_callBack.image_attachment(from, new File(selected_path), file_name, bitmap, imageUri);
                    } catch (Exception e) {
                        Toast.makeText(context, "Unable to process the image..!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Log.i("Gallery", "Photo");
                    Uri selectedImage = data.getData();
                    try {
                        selected_path = null;
                        selected_path = getPath(selectedImage);
                        file_name = selected_path.substring(selected_path.lastIndexOf("/") + 1);
                        bitmap = compressImage(selectedImage, 408, 306);
                        imageAttachment_callBack.image_attachment(from, new File(selected_path), file_name, bitmap, selectedImage);
                    } catch (Exception e) {
                        Toast.makeText(context, "Unable to process the image..!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                break;
        }


    }

    /**
     * Get image from Uri
     *
     * @param uri
     * @param height
     * @param width
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Bitmap getImage_FromUri(Uri uri, float height, float width) {
        Bitmap bitmap = null;

        try {
            bitmap = compressImage(uri, height, width);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * Get filename from URI
     *
     * @param uri
     * @return
     */
    public String getFileName_from_Uri(Uri uri) {
        String path = null, file_name = null;

        try {

            path = getRealPathFromURI(uri.getPath());
            file_name = path.substring(path.lastIndexOf("/") + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return file_name;

    }


    /**
     * Check Image Exist (or) Not
     *
     * @param file_name
     * @param file_path
     * @return
     */

    public boolean checkimage(String file_name, String file_path) {
        boolean flag;
        path = new File(file_path);

        File file = new File(path, file_name);
        if (file.exists()) {
            Log.i("file", "exists");
            flag = true;
        } else {
            Log.i("file", "not exist");
            flag = false;
        }

        return flag;
    }


    /**
     * Get Image from the given path
     *
     * @param file_name
     * @param file_path
     * @return
     */

    public Bitmap getImage(String file_name, String file_path) {

        path = new File(file_path);
        File file = new File(path, file_name);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 2;
        options.inTempStorage = new byte[16 * 1024];

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        if (bitmap != null)
            return bitmap;
        else
            return null;
    }

    /**
     * Create an image
     *
     * @param bitmap
     * @param file_name
     * @param filepath
     * @param file_replace
     */




    public void createImage(Bitmap bitmap, String file_name, String filepath, boolean file_replace) {

        path = new File(filepath);

        if (!path.exists()) {
            path.mkdirs();
        }

        File file = new File(path, file_name);

        if (file.exists()) {
            if (file_replace) {
                file.delete();
                file = new File(path, file_name);
                store_image(file, bitmap);
                Log.i("file", "replaced");
            }
        } else {
            store_image(file, bitmap);
        }

    }

    /**
     * @param file
     * @param bmp
     */
    public void store_image(File file, Bitmap bmp) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Image Attachment Callback

    public interface ImageAttachmentListener {
        public void image_attachment(int from, File fileloaction, String filename, Bitmap file, Uri uri);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    void rotateImage(File tempphoto,boolean isFrontCamera){
        String photopath = tempphoto.getPath().toString();
        Bitmap bmp = BitmapFactory.decodeFile(photopath);

        Matrix matrix = new Matrix();
        if(isFrontCamera) {
            matrix.postRotate(-90);
        }
        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(tempphoto);
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, fOut);
            fOut.flush();
            fOut.close();

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
