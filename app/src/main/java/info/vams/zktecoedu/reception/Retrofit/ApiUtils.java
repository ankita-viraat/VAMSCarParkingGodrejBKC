package info.vams.zktecoedu.reception.Retrofit;

import android.util.Log;

import info.vams.zktecoedu.reception.Util.AppConfig;



/**
 * Created by Nithin on 7/20/2017.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = AppConfig.WEB_URL;

    public static APIService getAPIService() {
        Log.d("TAG","URL ="+BASE_URL);
        return RetrofitClient.getClient(BASE_URL)
                .create(APIService.class);
    }

}
