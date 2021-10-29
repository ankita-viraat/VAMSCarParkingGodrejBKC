package info.vams.zktecoedu.reception.ApplicationFont;

import android.app.Application;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
//import com.microblink.MicroblinkSDK;
//import com.microblink.intent.IntentDataTransferMode;
//import com.microblink.result.extract.ResultExtractorFactoryProvider;
//import com.microblink.result.extract.blinkid.BlinkIdResultExtractorFactory;
//import com.microblink.util.Log;
//import com.microblink.util.RecognizerCompatibility;
//import com.microblink.util.RecognizerCompatibilityStatus;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Nithin on 5/25/2018.
 */

public class CustomApplicationFont extends Application {
    private static boolean isRecognitionSupported = true;

    public static boolean isRecognitionSupported() {
        return isRecognitionSupported;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        // initalize Calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Montserrat-Regular.ttf").build());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Barlow-Regular.ttf").build());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Montserrat-Light.ttf").build());
//        try {
//            RecognizerCompatibilityStatus supportStatus = RecognizerCompatibility.getRecognizerCompatibilityStatus(this);
//            if (supportStatus == RecognizerCompatibilityStatus.RECOGNIZER_SUPPORTED) {
//                Log.i(this, "Recognition is supported!");
//            } else if (supportStatus == RecognizerCompatibilityStatus.NO_CAMERA) {
//                Toast.makeText(this, "Recognition is supported only in DirectAPI mode!", Toast.LENGTH_SHORT).show();
//                Log.w(this, "Recognition is supported only in DirectAPI mode!");
//            } else {
//                isRecognitionSupported = false;
//                Toast.makeText(this, "Recognition is not supported! Reason: " + supportStatus.name(), Toast.LENGTH_LONG).show();
//                Log.e(this, "Recognition is not supported! Reason: {}", supportStatus.name());
//            }
//
//            if (isRecognitionSupported) {
//                MicroblinkSDK.setLicenseFile("info.vams.zktecoedu.reception_blinkid_android_2020_03_15.mblic", this);
//                MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED);
//            }
//
//            ResultExtractorFactoryProvider.set(new BlinkIdResultExtractorFactory());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
