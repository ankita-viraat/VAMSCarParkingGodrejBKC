package info.vams.zktecoedu.reception.Util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppConfig {
    
    public static boolean isLogsWritingOn = false;
    public static final String LOG_DIR = "ZKTeco EDU Reception";
    public static final String LOG_FILE = "Log.txt";

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
            "MM/dd/yy hh:mm aa", Locale.ENGLISH);

    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
            "hh:mm aa", Locale.ENGLISH);

    public static final SimpleDateFormat DATE_TIME_FORMAT_FOR_SCHEDULE = new SimpleDateFormat(
            "dd MMM yyyy hh:mm:ss", Locale.ENGLISH);

    public static final SimpleDateFormat SERVER_DATE_TIME_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public static final SimpleDateFormat SERVER_DATE_TIME_ZONE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);

    public static final SimpleDateFormat DATE_TIME_FORMAT_DASHBOARD = new SimpleDateFormat(
            "EEE,dd MMM yyyy hh:mm aa", Locale.ENGLISH);

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "EEE,dd MMM yyyy", Locale.ENGLISH);


    public static final SimpleDateFormat DB_DATE_FORMAT = new SimpleDateFormat(
            "dd MMM yyyy", Locale.ENGLISH);

//    public static final String WEB_URL = "http://182.72.92.110:8082/VamsCommercialExcel400/";
    //public static final String WEB_URL = "https://www.vaspl.in/vamsgodrejbkc/";
  //  public static final String WEB_URL = " https://vamsglobal.in/VamsCommercialExcelVer402/";
    public static final String WEB_URL = "https://www.vtlpl.in/VAMSGodrejbkc/";
   // public static final String WEB_URL = "https://182.72.92.110:9081/VamsCommercialExcel400/";
 //   public static final String WEB_URL = "https://vamsglobal.in:9081/VamsCommercialExcelVer402/";
    //Web Methods
    public static final String TOKEN = "token";

    public static final String MASTERS = "api/master/masters";
    public static final String MY_PROFILE = "api/Account/MyProfile";
    public static final String TENANT_LIST = "api/Tenant/GetTenantList";
    public static final String EMPLOYEE_LIST = "api/Employee/GetEmployeeById";
    public static final String EMPLOYEE_LIST_BY_TENANT_ID = "api/Employee/GetTenantEmployee";
    public static final String GET_REPEATED_VISITOR = "api/Entry/GetRepeatedVisitor";
    public static final String REPETED_VISITOR_APPOINTMENT = "api/Entry/GetRepeatedVisitorAppointment";
    public static final String VISITOR_ENTRY = "api/entry/CreateVisitorEntryForAPI";
    public static final String PRE_APPOINTMENT = "api/Entry/PreAppointment";
    public static final String CONTRACTOR_VISITOR_LOGOUT = "api/ContractorEmployee/ContractorEmployeeLoginLogout";
    public static final String CONTRACTOR_EMPLOYEE_NEW__LOGIN = "api/ContractorEmployee/GetContractorEmployeeByBarcode";
    public static final String CONTRACTOR_EMPLOYEE_NEW_CHECKINOUT = "api/ContractorEmployee/ContractorEmployeeLoginLogout";
    public static final String CONTRACTOR_EMPLOYEE_NEW_CANCEL = "api/ContractorEmployee/ContractorEmployeeLoginLogoutCancel";
    public static final String FORGOT_PASSWORD = "api/Account/ForgotPassword";
    public static final String RESEND_OTP = "api/Entry/ResendOTP";
    public static final String SEND_REAUTHENTICATION_OTP = "api/Entry/SendReauthenticationKey";
    public static final String VALIDATE_OTP = "api/entry/ValidateOTP";
    public static final String BYPASS_LOGIN = "api/Account/ByPassLogin";
    public static final String SETTINGS = "api/Setting/GetSetting";
    public static final String PRINT_PASS = "api/Visitor/PrintPass";
    public static final String RE_PRINT_PASS = "api/Visitor/RePrintPass";
    public static final String PRINT_PASS_NEW = "api/Visitor/PrintPassHtml?";
    public static final String VISITOR_BADGE_LIST = "api/Visitor/VisitorBadgeList";
    public static final String LOGOUT_LIST = "api/Visitor/VisitorListLogout";
    public static final String LOGOUT_VISITOR_BY_ID = "api/Visitor/LogoutVisitorByVisitorId";
    public static final String GETBUILDINGS = "api/master/GetBuildings";
    public static final String GET_LOGGED_IN_VISITOR_AGAINST_BUILDINGS = "api/visitor/GetLoggedInVisitorAgainstBuildings";
    public static final String UPDATE_BUILDING_VISITOR_LOG = "api/visitor/UpdateBuildingVisitorLog";
    public static final String GET_PASSQUEUE_VISITOR_LIST = "api/visitor/GetPassQueueVisitorList";
    public static final String UPDATE_PASSQUEUE_VISITOR = "api/visitor/UpdatePassQueueVisitor";
    public static final String VISITOR_LOGOUT = "api/Visitor/LogoutVisitor";
    public static final String SOFT_PASS = "api/visitor/SendSoftPass";
    public static final String STUDENT_LIST = "api/ChildPickup/GetStudentList";
    public static final String STUDENT_DETAILS_BY_STUDENT_ID = "api/ChildPickup/GetStudentDetails";
    public static final String CHILD_PICKUP_ENTRY = "api/ChildPickup/Entry";
    public static final String SEXUAL_OFFENDER = "api/SexualOffender/GetOffendersNationalPub";
    public static final String GET_AUTHENTICATE_VISITOR = "api/entry/GetauthenticatedVisitor";
    public static final String RE_AUTHENTICATE_VISITOR = "api/entry/ReauthenticateVisitor";

    public static final String POST_PARKING_CHECK_IN_VISITOR = "api/Parking/CheckInVisitor";
    public static final String POST_PARKING_CHECK_OUT_VISITOR = "api/Parking/CheckOutVisitor";
    public static final String GET_PARKING_VISITOR = "api/Parking/GetVisitor";
    public static final String GET_PARKING_EMPLOYEE = "api/Parking/SearchEmployeeAndVisitor";
    public static final String GET_PARKING_LOCATION_LIST = "api/ParkingLocation/GetParKingLocationList";



    // Image handler
    public static final String IMAGE_UPLOAD = "handler/UploadVisitorPhoto.ashx";
    public static final String DOWNLOAD_LOGO = "handler/GetEmployeePhoto.ashx";
    public static final String HANDLER_PRINTPASS = "handler/GetPrintPassPDF.ashx";
    public static final String PARKING_VISITOR_IMAGE_UPLOAD = "Handler/UploadParkingVisitorPhoto.ashx";


    public static final String APP_DIR = "VAMS";
    public static final String APP_PROF_DIR = "vams";
    public static final String APP_DOWNLOAD_PROFILE = "upload";
    public static final boolean IsSexualOffender = true;
    public static final String BUNDLE_IS_FROM_ADDITIONAL ="bundle_is_additional";
    public static final String BUNDLE_IS_FROM_PREAPPOINTMENT ="bundle_is_preAppt";

}
