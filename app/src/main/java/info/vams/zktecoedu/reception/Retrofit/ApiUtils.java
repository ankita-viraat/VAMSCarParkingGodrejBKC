package info.vams.zktecoedu.reception.Retrofit;

import android.util.Log;

import info.vams.zktecoedu.reception.Util.AppConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Nithin on 7/20/2017.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = AppConfig.WEB_URL;

    public static APIService getAPIService() {
        Log.d("TAG","URL ="+BASE_URL);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIService.class);

        /*return RetrofitClient.getClient(BASE_URL)
                .create(APIService.class);*/
    }
}
