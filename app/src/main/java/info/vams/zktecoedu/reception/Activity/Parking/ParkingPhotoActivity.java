package info.vams.zktecoedu.reception.Activity.Parking;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Activity.BaseActivity;
import info.vams.zktecoedu.reception.Activity.CaptureActivity;
import info.vams.zktecoedu.reception.Activity.LoginActivity;
import info.vams.zktecoedu.reception.Helper.FileTransferHelper;
import info.vams.zktecoedu.reception.Model.ParkingModels.ParkCheckIn.ParkCheckInResp;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.Helpers.IGetResponse;
import info.vams.zktecoedu.reception.Retrofit.Helpers.PostParkCheckinHelper;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Imageutils;
import info.vams.zktecoedu.reception.Util.Utilities;

public class ParkingPhotoActivity extends BaseActivity implements Imageutils.ImageAttachmentListener {
    private static final String TAG = "ParkingPhotoActivity";
    CircleImageView civCaptureimage;
    ImageView imgvCapture1, imgvCapture2, imgvCapture3, imgvCapture4, imgvCapture5, imgvCapture6, ivBack,ivLogo_visitorEntryActivity;
    Context context;
    private Uri fileUri = null;
    private static int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_DOCUMENT = 20;
    private static final int PICK_IMAGE_ID = 100;
    private static final int CAPTURE_ID = 200;
    private static final int CAMERA_REQ = 300;
    Imageutils imageUtils;
    Button btnCheckIn, btnSkip;
    HashMap<String, Uri> lstParkingUri = new HashMap<>();

    final String IMAGE_1 = "image1";
    final String IMAGE_2 = "image2";
    final String IMAGE_3 = "image3";
    final String IMAGE_4 = "image4";
    final String IMAGE_5 = "image5";
    final String IMAGE_6 = "image6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_photo);
        init();
    }

    void init() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        imageUtils = new Imageutils(this);
        context = ParkingPhotoActivity.this;
        civCaptureimage = findViewById(R.id.civCaptureimage);
        imgvCapture1 = findViewById(R.id.imgvCapture1);
        imgvCapture2 = findViewById(R.id.imgvCapture2);
        imgvCapture3 = findViewById(R.id.imgvCapture3);
        imgvCapture4 = findViewById(R.id.imgvCapture4);
        imgvCapture5 = findViewById(R.id.imgvCapture5 );
        imgvCapture6 = findViewById(R.id.imgvCapture6);
        ivBack = findViewById(R.id.ivBack);
        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnSkip = findViewById(R.id.btnSkip);
        ivLogo_visitorEntryActivity= findViewById(R.id.ivLogo_visitorEntryActivity);

        Utilities.setUserLogo(context, ivLogo_visitorEntryActivity);

        ivLogo_visitorEntryActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.redirectToHome(context);
            }
        });

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostParkCheckinHelper.callMethod(ParkingPhotoActivity.this, new IGetResponse() {
                    @Override
                    public void isSuccess(boolean val, Object obj) {
                        afterCheckInSuccess((ParkCheckInResp) obj, false);
                    }
                }, ParkingCheckInActivity.parkCheckInReq);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostParkCheckinHelper.callMethod(ParkingPhotoActivity.this, new IGetResponse() {
                    @Override
                    public void isSuccess(boolean val, Object obj) {
                        afterCheckInSuccess((ParkCheckInResp) obj, true);
                    }
                }, ParkingCheckInActivity.parkCheckInReq);
            }
        });


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    for (Map.Entry me : lstParkingUri.entrySet()) {
                        System.out.println("Key: " + me.getKey() + " & Value: " + me.getValue());
                        new File(lstParkingUri.get(me.getKey()).getPath()).delete();
                    }
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        imgvCapture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeImage(IMAGE_1);
            }
        });

        imgvCapture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeImage(IMAGE_2);
            }
        });

        imgvCapture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeImage(IMAGE_3);
            }
        });

        imgvCapture4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeImage(IMAGE_4);
            }
        });

        imgvCapture5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeImage(IMAGE_5);
            }
        });

        imgvCapture6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeImage(IMAGE_6);
            }
        });

        civCaptureimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ParkingPhotoActivity.this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, CAMERA_REQ);

                } else {
                    captureImage();
                }
            }
        });
    }

    void afterCheckInSuccess(ParkCheckInResp ParkCheckInResp, boolean isSkip) {
        if (isSkip) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    finish();
                    startActivity(new Intent(ParkingPhotoActivity.this, ParkingCheckInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            };
            Utilities.showPopuprunnable(context, "Vehicle Check In Successfully." , false, runnable);
        } else {
            UploadImageTask task = new UploadImageTask(ParkCheckInResp);
            task.execute();
        }
//        Toast.makeText(ParkingPhotoActivity.this,"Park Check in Success!",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void removeImage(final String key) {
        // setup the alert builder
        if (!lstParkingUri.isEmpty() && lstParkingUri.containsKey(key)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(null);
            builder.setMessage("Are You Sure You Want To Delete This Image?");

            // add a button
            builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (key == IMAGE_1) {
                        imgvCapture1.setImageBitmap(null);
                        imgvCapture1.setBackgroundColor(getResources().getColor(R.color.white));
                    } else if (key == IMAGE_2) {
                        imgvCapture2.setImageBitmap(null);
                        imgvCapture2.setBackgroundColor(getResources().getColor(R.color.white));
                    } else if (key == IMAGE_3) {
                        imgvCapture3.setImageBitmap(null);
                        imgvCapture3.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    else if (key == IMAGE_4) {
                        imgvCapture4.setImageBitmap(null);
                        imgvCapture4.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    else if (key == IMAGE_5) {
                        imgvCapture5.setImageBitmap(null);
                        imgvCapture5.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    else if (key == IMAGE_6) {
                        imgvCapture6.setImageBitmap(null);
                        imgvCapture6.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    try {
                        if (new File(lstParkingUri.get(key).getPath()).delete()) {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    lstParkingUri.remove(key);
                }
            });

            builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    void captureImage() {
        if (lstParkingUri.size() >= 6) {
            Toast.makeText(ParkingPhotoActivity.this, "Max Image limit reached", Toast.LENGTH_LONG).show();
        } else {
            if (Utilities.getDevice().equalsIgnoreCase("samsung")) {
                //captureVisitorImageCamera2();
                captureDocumentImage();

            } else {
                captureDocumentImage();
            }
        }
    }

    private void captureVisitorImageCamera2() {
        startActivityForResult(new Intent(ParkingPhotoActivity.this, CaptureActivity.class), CAPTURE_ID);
    }

    private void captureDocumentImage() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            Log.e(TAG, "captureDocumentImage: " + fileUri.getPath());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (LoginActivity.isSelfeHeplKiosk) {
                intent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_FRONT);
            } else {
                intent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_BACK);
            }
            startActivityForResult(intent, PICK_IMAGE_ID);
        }catch (Exception e){
            Log.e(TAG, "captureDocumentImage: "+e.getMessage() );
            Toast.makeText(this,"something went wrong please try again",Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type, false));
//        return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", Uri.fromFile(getOutputMediaFile(type, false)).);
    }

    private  File getOutputMediaFile(int type, boolean isCopy) {
        String path = AppConfig.APP_PROF_DIR
                + File.separator
                + AppConfig.APP_DOWNLOAD_PROFILE
                + File.separator;

        // External sdcard location
        File mediaStorageDir ;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            mediaStorageDir = new File(Environment.getExternalStorageDirectory(), path);
        else
            mediaStorageDir = new File(this.getExternalFilesDir(null).getAbsolutePath(), path);

     /*   File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory(), path);*/

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(AppConfig.APP_DIR, "Oops! Failed create "
                        + AppConfig.APP_DIR + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            if (!isCopy) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
            } else {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_Copy" + timeStamp + ".jpg");
            }
            Log.e(TAG, "getOutputMediaFile: "+mediaFile.getPath() );
            return mediaFile;

        } else {
            return null;
        }
    }

    private Uri makePhotoCopy(Bitmap tempBitmap) {

        File file = getOutputMediaFile(1, true);
        try {
            FileOutputStream out = new FileOutputStream(file);
            tempBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();

            return Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void selectFileButtonOnClick() {

    }

    @Override
    public void printButtonOnClick() {

    }

    @Override
    public void image_attachment(int from, File fileloaction, String filename, Bitmap file, Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode != Activity.RESULT_CANCELED) {
                Log.e(TAG, "onActivityResult: "+fileUri.getPath() );

                switch (requestCode) {
                    case PICK_IMAGE_ID:
                        if (fileUri != null) {
                            try {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                                Bitmap tempBitmap = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    tempBitmap = Utilities.modifyOrientation(bitmap, fileUri.getPath(), false);
                                }

                                Uri uri = null;
                                // make a copy of file
                                if (tempBitmap != null) {
                                    uri = makePhotoCopy(tempBitmap);
                                }

                                if (fileUri != null) {
                                    if (new File(fileUri.getPath()).exists()) {
                                        new File(fileUri.getPath()).delete();
                                    }
                                }
                                if (lstParkingUri.size() == 0) {
                                    imgvCapture1.setImageBitmap(tempBitmap);
                                    imgvCapture1.setBackgroundColor(getResources().getColor(R.color.white));
                                    lstParkingUri.put(IMAGE_1, uri);
                                } else if (lstParkingUri.size() == 1) {
                                    imgvCapture2.setImageBitmap(tempBitmap);
                                    imgvCapture2.setBackgroundColor(getResources().getColor(R.color.white));
                                    lstParkingUri.put(IMAGE_2, uri);
                                } else if (lstParkingUri.size() == 2) {
                                    imgvCapture3.setImageBitmap(tempBitmap);
                                    imgvCapture3.setBackgroundColor(getResources().getColor(R.color.white));
                                    lstParkingUri.put(IMAGE_3, uri);
                                } else if (lstParkingUri.size() == 3) {
                                    imgvCapture4.setImageBitmap(tempBitmap);
                                    imgvCapture4.setBackgroundColor(getResources().getColor(R.color.white));
                                    lstParkingUri.put(IMAGE_4, uri);
                                } else if (lstParkingUri.size() == 4) {
                                    imgvCapture5.setImageBitmap(tempBitmap);
                                    imgvCapture5.setBackgroundColor(getResources().getColor(R.color.white));
                                    lstParkingUri.put(IMAGE_5, uri);
                                } else if (lstParkingUri.size() == 5) {
                                    imgvCapture6.setImageBitmap(tempBitmap);
                                    imgvCapture6.setBackgroundColor(getResources().getColor(R.color.white));
                                    lstParkingUri.put(IMAGE_6, uri);
                                }
                            } catch(IOException e){
                                e.printStackTrace();
                                Log.e("exception uri",e.getMessage());
                            }
                        }
                        else{
                            Toast.makeText(context, "Error saving image...please try again", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case CAPTURE_ID:
                        Log.e(TAG, "onActivityResult: CAPTURE_ID" );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            imageUtils.onActivityResult(requestCode, resultCode, data);
                        }
                        break;

                    case CAMERA_REQ:
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            captureImage();
                        }
                        break;
                    default:
                        super.onActivityResult(requestCode, resultCode, data);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class UploadImageTask extends AsyncTask<String, String, Boolean> {
        ParkCheckInResp parkCheckInResp;

        UploadImageTask(ParkCheckInResp parkCheckInResp) {
            this.parkCheckInResp = parkCheckInResp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utilities.showProgressForAsynk(context,getString(R.string.uploadingImage), getString(R.string.please_wait), this);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                for (Map.Entry me : lstParkingUri.entrySet()) {
                    System.out.println("Key: " + me.getKey() + " & Value: " + me.getValue());
                    boolean isUploaded = FileTransferHelper.uploadParkingVisitorPhoto(context, parkCheckInResp.getParkingVisitorId()
                            , new File(lstParkingUri.get(me.getKey()).getPath()));

                    if (isUploaded) {
                        new File(lstParkingUri.get(me.getKey()).getPath()).delete();
                    }
                    System.out.println("File : "+lstParkingUri.get(me.getKey()).getPath() +" status: "+ isUploaded);
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Utilities.hideProgress();
            if (aBoolean) {
                try {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(new Intent(ParkingPhotoActivity.this, ParkingCheckInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    };
                    Utilities.showPopuprunnable(context, "Vehicle check in successfully." , false, runnable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context,"Error While uploading images\nPlease contact service administrator.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
