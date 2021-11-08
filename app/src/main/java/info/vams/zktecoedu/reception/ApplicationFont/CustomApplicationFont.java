package info.vams.zktecoedu.reception.ApplicationFont;

import android.app.Application;
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
        // initalize Calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Montserrat-Regular.ttf").build());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Barlow-Regular.ttf").build());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Montserrat-Light.ttf").build());
    }

}
