package info.vams.zktecoedu.reception.Retrofit.Helpers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import info.vams.zktecoedu.reception.Model.ParkingModels.GetVisitor.GetParkVisitorReq;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetVisitor.GetParkVisitorResp;
import info.vams.zktecoedu.reception.Model.ParkingModels.ParkCheckIn.ParkCheckInReq;
import info.vams.zktecoedu.reception.Model.ParkingModels.ParkCheckIn.ParkCheckInResp;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostParkCheckinHelper {


    public static void callMethod(final Context context, final IGetResponse iGetResponse, ParkCheckInReq parkCheckInReq) {
        Utilities.showprogressDialogue(context.getString(R.string.checking_in ), context.getString(R.string.please_wait), context, true);
        Call<ResponseBody> call = ApiUtils.getAPIService().postParkingCheckIn(Utilities.getToken(context),
                parkCheckInReq );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Tag", "Response Code For Masters =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        try {
                            String responseStr = response.body().string().toString();
                           ParkCheckInResp lstParkVisitors = new Gson().fromJson(responseStr,
                                    new TypeToken<ParkCheckInResp>() {
                                    }.getType());
                            Utilities.hideProgress();
                            iGetResponse.isSuccess(true, lstParkVisitors);
                        } catch (Exception e) {
                            //write log method
                            Utilities.hideProgress();
                            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                            e.printStackTrace();
                        }
                    } else {
                        Utilities.showPopup(context, "", "Response is null");
                        Utilities.hideProgress();
                    }
                } else {
                    Utilities.hideProgress();
                    try {
                        if (response.errorBody() != null) {
                            String errorMsg = response.errorBody().string().toString();
                            String error = "";
                            JSONArray jsonArray = new JSONArray(errorMsg);
                            if (jsonArray != null) {
                                JSONObject object = jsonArray.getJSONObject(0);
                                error = object.getString("message");
                                Utilities.showPopup(context, "", "" + error);
                            } else {
                                Utilities.showPopup(context, "", "" + errorMsg);
                            }
                        } else {
                            Utilities.showPopup(context, "", context.getString(R.string.technical_error));
                        }
                    } catch (IOException e) {
                        //write log method
                        Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
//                        Utilities.showPopup(context, "", "" + error);
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                Utilities.hideProgress();
                Utilities.showPopup(context, "", context.getResources().getString(R.string.no_internet_msg_text));
                //write log method
                Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                        "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                e.printStackTrace();
            }
        });
    }
}
