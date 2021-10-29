package info.vams.zktecoedu.reception.Retrofit;


import java.util.HashMap;

import info.vams.zktecoedu.reception.Model.BuildingLevelLogoutRequest;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetEmployee.GetParkEmployeeReq;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetVisitor.GetParkVisitorReq;
import info.vams.zktecoedu.reception.Model.ParkingModels.ParkCheckIn.ParkCheckInReq;
import info.vams.zktecoedu.reception.Model.ParkingModels.ParkCheckout.ParkCheckOutReq;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;

import info.vams.zktecoedu.reception.Model.VisitorEntryModel;
import info.vams.zktecoedu.reception.Model.VisitorPrintPassRequestMobileViewModel;
import info.vams.zktecoedu.reception.Model.WebRequest;
import info.vams.zktecoedu.reception.Util.AppConfig;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @FormUrlEncoded
    @POST(AppConfig.TOKEN)
    Call<ResponseBody> getToken(@Field("grant_type") String grant_type,
                                @Field("username") String username,
                                @Field("password") String password);


    @POST(AppConfig.MASTERS)
    Call<ResponseBody> getMasters(@Header("Authorization") String Authorization,
                                  @Body RequestClientDetails requestClientDetails);


    @POST(AppConfig.POST_PARKING_CHECK_IN_VISITOR)
    Call<ResponseBody> postParkingCheckIn(@Header("Authorization") String Authorization,
                                  @Body ParkCheckInReq parkCheckInReq);

    @POST(AppConfig.POST_PARKING_CHECK_OUT_VISITOR)
    Call<ResponseBody> postParkingCheckOut(@Header("Authorization") String Authorization,
                                  @Body ParkCheckOutReq requestClientDetails);

    @POST(AppConfig.GET_PARKING_VISITOR)
    Call<ResponseBody> getParkingVisitor(@Header("Authorization") String Authorization,
                                  @Body GetParkVisitorReq requestClientDetails);


    @POST(AppConfig.GET_PARKING_EMPLOYEE)
    Call<ResponseBody> getParkingEmployee(@Header("Authorization") String Authorization,
                                         @Body GetParkEmployeeReq parkEmployeeReq);



    @POST(AppConfig.MY_PROFILE)
    Call<ResponseBody> getProfile(@Header("Authorization") String Authorization,
                                  @Body RequestClientDetails requestClientDetails);

    @POST(AppConfig.TENANT_LIST)
    Call<ResponseBody> getTenantList(@Header("Authorization") String Authorization,
                                  @Body RequestClientDetails requestClientDetails);

    @POST(AppConfig.GET_PARKING_LOCATION_LIST)
    Call<ResponseBody> getParkingLocationList(@Header("Authorization") String Authorization,
                                     @Body RequestClientDetails requestClientDetails);

    @POST(AppConfig.EMPLOYEE_LIST)
    Call<ResponseBody> getEmployeeList(@Header("Authorization") String Authorization,
                                       @Body RequestClientDetails requestClientDetails);

    @POST(AppConfig.EMPLOYEE_LIST_BY_TENANT_ID)
    Call<ResponseBody> getEmployeeListByTenantId(@Header("Authorization") String Authorization,
                                                 @Body WebRequest webRequest);

    @POST(AppConfig.GET_REPEATED_VISITOR)
    Call<ResponseBody> getRepeatedvisitor(@Header("Authorization") String Authorization,
                                                 @Body HashMap<String,Object> map);


    @POST(AppConfig.REPETED_VISITOR_APPOINTMENT)
    Call<ResponseBody> getRepeatedvisitorAppointment(@Header("Authorization") String Authorization,
                                                 @Body HashMap<String,Object> map);




    @POST(AppConfig.VISITOR_ENTRY)
    Call<ResponseBody> visitorEntry(@Header("Authorization") String Authorization,
                                       @Body VisitorEntryModel visitorEntryModel);


    @POST(AppConfig.PRE_APPOINTMENT)
    Call<ResponseBody> getPreAppointment(@Header("Authorization") String Authorization,
                                       @Body HashMap<String,Object> map);


    @POST(AppConfig.VISITOR_LOGOUT)
    Call<ResponseBody> getVisitorLogout(@Header("Authorization") String Authorization,
                                         @Body HashMap<String,Object> map);


    @POST(AppConfig.PRINT_PASS)
    Call<ResponseBody> getPrintPassData(@Header("Authorization") String Authorization,
                                        @Body VisitorPrintPassRequestMobileViewModel model);


    @POST(AppConfig.CONTRACTOR_EMPLOYEE_NEW__LOGIN)
    Call<ResponseBody> getContractVisitorLogout(@Header("Authorization") String Authorization,
                                                @Body WebRequest webRequest);

    @POST(AppConfig.CONTRACTOR_EMPLOYEE_NEW_CHECKINOUT)
    Call<ResponseBody> getContractorEmplyeeCheckInOut(@Header("Authorization") String Authorization,
                                                      @Body HashMap<String,Object> map);

    @POST(AppConfig.CONTRACTOR_EMPLOYEE_NEW_CANCEL)
    Call<ResponseBody> getContractorEmployeeCancel(@Header("Authorization") String Authorization,
                                                   @Body HashMap<String,Object> map);

    @POST(AppConfig.FORGOT_PASSWORD)
    Call<ResponseBody> getForgotPassword(@Body WebRequest webRequest);

    @POST(AppConfig.RESEND_OTP)
    Call<ResponseBody> callReSendOtp(@Header("Authorization") String Authorization,
                                     @Body HashMap<String,Object> map);

    @POST(AppConfig.SEND_REAUTHENTICATION_OTP)
    Call<ResponseBody> callSendReAuthenticationOtp(@Header("Authorization") String Authorization,
                                     @Body HashMap<String,Object> map);

    @POST(AppConfig.VALIDATE_OTP)
    Call<ResponseBody> callValidateOtp(@Header("Authorization") String Authorization,
                                     @Body HashMap<String,Object> map);



    @POST(AppConfig.BYPASS_LOGIN)
    Call<ResponseBody> callByPassLogin(@Header("Authorization") String Authorization,
                                        @Body HashMap<String,Object> map);

    @POST(AppConfig.SETTINGS)
    Call<ResponseBody> getSettings(@Header("Authorization") String Authorization,
                                   @Body RequestClientDetails requestClientDetails);

    @POST(AppConfig.LOGOUT_LIST)
    Call<ResponseBody> getLogoutList(@Header("Authorization") String Authorization,
                                   @Body RequestClientDetails requestClientDetails);

    @POST(AppConfig.LOGOUT_VISITOR_BY_ID)
    Call<ResponseBody> getLogoutVIsitorById(@Header("Authorization") String Authorization,
                                            @Body HashMap<String,Object> map);


    @POST(AppConfig.RE_PRINT_PASS)
    Call<ResponseBody> callRePringPass(@Header("Authorization") String Authorization,
                                            @Body HashMap<String,Object> map);

    @POST(AppConfig.PRINT_PASS_NEW)
    Call<ResponseBody> callPrintPassNew(@Header("Authorization") String Authorization,
                                        @Body HashMap<String,Object> map);

    @POST(AppConfig.VISITOR_BADGE_LIST)
    Call<ResponseBody> callVisitorBadgeList(@Body HashMap<String,Object> map,
                                            @Header("Authorization") String Authorization);

    @POST(AppConfig.GETBUILDINGS)
    Call<ResponseBody> getBuildingList(@Header("Authorization") String Authorization,
                                       @Body RequestClientDetails requestClientDetails);

    @POST(AppConfig.GET_LOGGED_IN_VISITOR_AGAINST_BUILDINGS)
    Call<ResponseBody> getLoggedInVisitorAgainstBuildings(@Body BuildingLevelLogoutRequest buildingLevelLogoutRequest,
                                                          @Header("Authorization") String authHeader);

    @POST(AppConfig.UPDATE_BUILDING_VISITOR_LOG)
    Call<ResponseBody> updateBuildingVisitorLog(@Header("Authorization") String Authorization,
                                                @Body HashMap<String, Object> map);

    @POST(AppConfig.GET_PASSQUEUE_VISITOR_LIST)
    Call<ResponseBody> getPassQueueVisitorList(@Header("Authorization") String Authorization,
                                               @Body HashMap<String, Object> map);

    @POST(AppConfig.UPDATE_PASSQUEUE_VISITOR)
    Call<ResponseBody> updatePassQueueVisitor(@Header("Authorization") String Authorization,
                                              @Body HashMap<String, Object> map);

    @POST(AppConfig.SOFT_PASS)
    Call<ResponseBody> callsoftPass(@Header("Authorization") String Authorization,
                                              @Body HashMap<String, Object> map);


    @POST(AppConfig.STUDENT_LIST)
    Call<ResponseBody> getStudentList(@Header("Authorization") String Authorization,
                                      @Body RequestClientDetails requestClientDetails);

    @POST(AppConfig.CHILD_PICKUP_ENTRY)
    Call<ResponseBody> getChildPickupEntry(@Header("Authorization") String Authorization,
                                           @Body HashMap<String,Object> map);

    @POST(AppConfig.STUDENT_DETAILS_BY_STUDENT_ID)
    Call<ResponseBody> getStudentDetailsByStudentId(@Header("Authorization") String Authorization,
                                                    @Body HashMap<String,Object> map);

    @GET(AppConfig.SEXUAL_OFFENDER)
    Call<ResponseBody> getSexualOffendedList(@Query("visitorName") String visitorName,@Query("StateCode") String StateCode,
                                            @Query("City") String City,@Query("ZipCode") String ZipCode);

    @POST(AppConfig.GET_AUTHENTICATE_VISITOR)
    Call<ResponseBody> callGetAuthenticateVisitor(@Body HashMap<String,Object> map,
                                       @Header("Authorization") String Authorization);
    @POST(AppConfig.RE_AUTHENTICATE_VISITOR)
    Call<ResponseBody> callReAuthenticateVisitor(@Body HashMap<String,Object> map,@Header("Authorization") String Authorization);
}
