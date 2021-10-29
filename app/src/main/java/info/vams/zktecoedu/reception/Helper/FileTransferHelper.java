/**
 * Copyright (C) 2014 Viraat Technology Labs Pvt. Ltd.
 * http://www.viraat.info
 */
package info.vams.zktecoedu.reception.Helper;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Utilities;


/**
 * @author Rohit Chaskar
 * @Since Apr 30, 2014 9:59:29 AM
 */
public class FileTransferHelper {
    public static String fileuploadMessage = "";
    public static HttpURLConnection conn = null;
    static String filedownloadMessage = "";
    private static final int  MEGABYTE = 1024 * 1024;


    public static boolean uploadFile(Context context, long visitorid, long registeredVisitorId,
                                     long bypassedVisitorId, File file) {
        boolean fileUploaded = false;

        URL connectURL;
        FileInputStream fileInputStream = null;
        InputStream is = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag = "fSnd";
        DataOutputStream dos = null;
        disableSSLCertificateChecking();

        try {
            connectURL = new URL(AppConfig.WEB_URL + AppConfig.IMAGE_UPLOAD);
            fileInputStream = new FileInputStream(file);

            Log.e(Tag, "Starting Http File Sending to URL");

            // Open a HTTP connection to the URL

            conn = (HttpURLConnection) connectURL.openConnection();

            // Allow Inputs
            conn.setDoInput(true);

            // Allow Outputs
            conn.setDoOutput(true);

            // Don't use a cached copy.
            conn.setUseCaches(false);

            conn.setChunkedStreamingMode(0);

            // Use a post method.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Close");

            // Setting connection timeout
            conn.setConnectTimeout(120000);

            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());


            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"ApiKey\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Utilities.getApiKey());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"UuId\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Utilities.getUDIDNumber(context));
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            /*dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"FcmId\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Utilities.getFcmId());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);*/

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"DeviceType\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Utilities.getDeviceType());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"visitorid\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(String.valueOf(visitorid));
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"registeredVisitorId\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(String.valueOf(registeredVisitorId));
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"bypassedVisitorId\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(String.valueOf(bypassedVisitorId));
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);



            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                    + file + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            Log.e(Tag, "Headers are written");

            // create a buffer of maximum size
            int bytesAvailable = fileInputStream.available();

            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];

            // read file and write it into form...
            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                Log.e("Bytes Remains", Integer.toString(bytesAvailable));
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            dos.flush();

            Log.e(Tag,
                    "File Sent, Response: "
                            + String.valueOf(conn.getResponseCode()));
            Log.e(Tag,
                    "File Sent, Response message: "
                            + String.valueOf(conn.getResponseMessage()));

            is = conn.getInputStream();

            // retrieve the response from server
            int ch;

            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.i("Response", s);
            dos.close();

            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                    fileInputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (conn.getResponseCode() == 200) {
                fileUploaded = true;
                try {
                    file.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("File Deleted", "true");
            } else {
                fileUploaded = false;
                file.delete();
                Log.e("File uploading failed", "false");
            }

            fileuploadMessage = s;
        } catch (MalformedURLException ex) {
            Log.e(Tag, "URL error: " + ex.getMessage(), ex);
            fileuploadMessage = ex.getMessage();
        } catch (IOException ioe) {
            Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
            fileuploadMessage = ioe.getMessage();
        } catch (Exception e) {
            Log.e(Tag, "Exception : " + e.getMessage(), e);
            fileuploadMessage = e.getMessage();
        }

        // close streams
        try {
            if (dos != null) {
                dos.close();
                dos = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (is != null) {
                is.close();
                is = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileUploaded;

    }



    public static boolean uploadParkingVisitorPhoto(Context context, long visitorid, File file) {
        boolean fileUploaded = false;

        URL connectURL;
        FileInputStream fileInputStream = null;
        InputStream is = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag = "fSnd";
        DataOutputStream dos = null;
        disableSSLCertificateChecking();

        try {
            connectURL = new URL(AppConfig.WEB_URL + AppConfig.PARKING_VISITOR_IMAGE_UPLOAD);
            fileInputStream = new FileInputStream(file);

            Log.e(Tag, "Starting Http File Sending to URL");

            // Open a HTTP connection to the URL

            conn = (HttpURLConnection) connectURL.openConnection();

            // Allow Inputs
            conn.setDoInput(true);

            // Allow Outputs
            conn.setDoOutput(true);

            // Don't use a cached copy.
            conn.setUseCaches(false);

            conn.setChunkedStreamingMode(0);

            // Use a post method.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Close");

            // Setting connection timeout
            conn.setConnectTimeout(120000);

            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());


            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"ApiKey\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Utilities.getApiKey());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"UuId\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Utilities.getUDIDNumber(context));
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            /*dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"FcmId\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Utilities.getFcmId());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);*/

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"DeviceType\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Utilities.getDeviceType());
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"parkingVisitorId\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(String.valueOf(visitorid));
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"registeredVisitorId\""
//                    + lineEnd);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(String.valueOf(registeredVisitorId));
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//
//
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"bypassedVisitorId\""
//                    + lineEnd);
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(String.valueOf(bypassedVisitorId));
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + lineEnd);



            dos.writeBytes("Content-Disposition: form-data; name=\"photo\";filename=\""
                    + file + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            Log.e(Tag, "Headers are written");

            // create a buffer of maximum size
            int bytesAvailable = fileInputStream.available();

            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];

            // read file and write it into form...
            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                Log.e("Bytes Remains", Integer.toString(bytesAvailable));
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            dos.flush();

            Log.e(Tag,
                    "File Sent, Response: "
                            + String.valueOf(conn.getResponseCode()));
            Log.e(Tag,
                    "File Sent, Response message: "
                            + String.valueOf(conn.getResponseMessage()));

            is = conn.getInputStream();

            // retrieve the response from server
            int ch;

            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.i("Response", s);
            dos.close();

            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                    fileInputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (conn.getResponseCode() == 200) {
                fileUploaded = true;
                try {
                    file.delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("File Deleted", "true");
            } else {
                fileUploaded = false;
                file.delete();
                Log.e("File uploading failed", "false");
            }

            fileuploadMessage = s;
        } catch (MalformedURLException ex) {
            Log.e(Tag, "URL error: " + ex.getMessage(), ex);
            fileuploadMessage = ex.getMessage();
        } catch (IOException ioe) {
            Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
            fileuploadMessage = ioe.getMessage();
        } catch (Exception e) {
            Log.e(Tag, "Exception : " + e.getMessage(), e);
            fileuploadMessage = e.getMessage();
        }

        // close streams
        try {
            if (dos != null) {
                dos.close();
                dos = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (is != null) {
                is.close();
                is = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileUploaded;

    }

    private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("SSL");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static class NullHostNameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            Log.i("RestUtilImpl", "Approving certificate for " + hostname);
            return true;
        }

    }

    public static boolean downloadFile(String schoolAdmin,File directory) {
        boolean fileDownload = false;

        URL connectURL;

        String url = null;

        InputStream is = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag = "fRcv";
        DataOutputStream dos = null;
        disableSSLCertificateChecking();
        try {

            connectURL = new URL(Utilities.getLinkForDownloadLogoUrl(schoolAdmin));

            Log.e(Tag, "Starting Http File Sending to URL");

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) connectURL.openConnection();

            // Allow Inputs
            conn.setDoInput(true);

            // Allow Outputs
            conn.setDoOutput(true);

            // Don't use a cached copy.
            conn.setUseCaches(false);

            // Use a post method.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"UserId \""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(schoolAdmin);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            Log.e(Tag, "Headers are written");

            dos.flush();

            is = conn.getInputStream();
            is = new BufferedInputStream(conn.getInputStream());

            // download the file
            OutputStream output = new FileOutputStream(directory.getAbsolutePath());

            byte data[] = new byte[1024];
            int count;
            while ((count = is.read(data)) != -1) {
                 output.write(data, 0, count);
            }

            output.flush();
            output.close();

            int resCode = conn.getResponseCode();
            String resMsg = conn.getResponseMessage();

            Log.e(Integer.toString(resCode), resMsg);

            // long lastModifiedDateTime = outFile.lastModified();
            //Date lastModified = new Date(lastModifiedDateTime);
            /*Log.e("Last Modified date",
                    AppConfig.DISPLAY_DATE_TIME_FORMAT.format(lastModified));*/


            fileDownload = true;





        } catch (MalformedURLException ex) {
            Log.e(Tag, "URL error: " + ex.getMessage(), ex);
            filedownloadMessage = ex.getMessage();
        } catch (IOException ioe) {
            Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
            filedownloadMessage = ioe.getMessage();
        } catch (Exception e) {
            Log.e(Tag, "Exception: " + e.getMessage(), e);
            filedownloadMessage = e.getMessage();
        }

        // close streams
        try {
            dos.close();
            dos = null;
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            is.close();
            is = null;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            conn.disconnect();
            conn = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileDownload;
    }

    public static void downloadFileForPDF(String fileUrl, File directory){
        try {


            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            disableSSLCertificateChecking();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);

            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
