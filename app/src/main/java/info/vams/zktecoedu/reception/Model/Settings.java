package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nithin on 7/29/2018.
 */

public class Settings {

    @SerializedName("settingId")
    @Expose
    private Integer settingId;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("tenantId")
    @Expose
    private Object tenantId;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("toggleKeyboard")
    @Expose
    private Boolean toggleKeyboard;
    @SerializedName("useTogglekeyboardVstrEntry")
    @Expose
    private Boolean useTogglekeyboardVstrEntry;
    @SerializedName("useNameOfLoggedInUser")
    @Expose
    private Boolean useNameOfLoggedInUser;
    @SerializedName("useLastLoginDateAndTime")
    @Expose
    private Boolean useLastLoginDateAndTime;
    @SerializedName("useVstrMobileNo")
    @Expose
    private Boolean useVstrMobileNo;
    @SerializedName("vstrAuthenticatiOnEveryTime")
    @Expose
    private Boolean vstrAuthenticatiOnEveryTime;
    @SerializedName("multiVstrAuthenticatiOn")
    @Expose
    private Boolean multiVstrAuthenticatiOn;
    @SerializedName("overdueAlertToTenantFrOntdesk")
    @Expose
    private Boolean overdueAlertToTenantFrOntdesk;
    @SerializedName("overdueAlertToHostOrMultipleHosts")
    @Expose
    private Boolean overdueAlertToHostOrMultipleHosts;
    @SerializedName("isBypassedAllowed")
    @Expose
    private Boolean isBypassedAllowed;
    @SerializedName("useScanIdProofOption")
    @Expose
    private Boolean useScanIdProofOption;
    @SerializedName("useBrowseIdProofOption")
    @Expose
    private Boolean useBrowseIdProofOption;
    @SerializedName("useVstrSignatureOption")
    @Expose
    private Boolean useVstrSignatureOption;
    @SerializedName("allowMultiplePersonToMeet")
    @Expose
    private Boolean allowMultiplePersonToMeet;
    @SerializedName("printServerPhotoOrCurrentPhoto")
    @Expose
    private Boolean printServerPhotoOrCurrentPhoto;
    @SerializedName("vstrLogout")
    @Expose
    private Boolean vstrLogout;
    @SerializedName("fieldsOnVstrLogoutScreen")
    @Expose
    private Boolean fieldsOnVstrLogoutScreen;
    @SerializedName("allowChangeOfDateBeforeLoggingOutVstr")
    @Expose
    private Boolean allowChangeOfDateBeforeLoggingOutVstr;
    @SerializedName("allowChangeOfTimeBeforeLoggingOutVstr")
    @Expose
    private Boolean allowChangeOfTimeBeforeLoggingOutVstr;
    @SerializedName("maskVstrMobileNo")
    @Expose
    private Boolean maskVstrMobileNo;
    @SerializedName("sendApptLinkToVstrToFillDtls")
    @Expose
    private Boolean sendApptLinkToVstrToFillDtls;
    @SerializedName("allowMultipleDaysAppt")
    @Expose
    private Boolean allowMultipleDaysAppt;
    @SerializedName("allowMultipleVstrInASingleAppt")
    @Expose
    private Boolean allowMultipleVstrInASingleAppt;
    @SerializedName("maxVstrInSingleAppt")
    @Expose
    private Object maxVstrInSingleAppt;
    @SerializedName("allowToCreateApptOnBehalfOf")
    @Expose
    private Boolean allowToCreateApptOnBehalfOf;
    @SerializedName("authenticateVstrBy")
    @Expose
    private String authenticateVstrBy;
    @SerializedName("noAuthentication")
    @Expose
    private Boolean noAuthentication;
    @SerializedName("useNoOfRecordsOnASinglePage")
    @Expose
    private Object useNoOfRecordsOnASinglePage;
    @SerializedName("timeInterval")
    @Expose
    private Integer timeInterval;
    @SerializedName("apptIntervalTimeBetweenStartTimeAndEndTime")
    @Expose
    private Integer apptIntervalTimeBetweenStartTimeAndEndTime;
    @SerializedName("allowPreApptVstrEntryBeforeHoursOfStartTime")
    @Expose
    private Object allowPreApptVstrEntryBeforeHoursOfStartTime;
    @SerializedName("allowPreApptVstrEntryAfterHoursOfStartTime")
    @Expose
    private Object allowPreApptVstrEntryAfterHoursOfStartTime;
    @SerializedName("walkinVisitorApproval")
    @Expose
    private Boolean walkinVisitorApproval;
    @SerializedName("defaultIsdCode")
    @Expose
    private String defaultIsdCode;
    @SerializedName("sendApptUpdateToVisitorBy")
    @Expose
    private Object sendApptUpdateToVisitorBy;
    @SerializedName("dateTimeDisplayFormat")
    @Expose
    private String dateTimeDisplayFormat;
    @SerializedName("capturePhotoAllowed")
    @Expose
    private Boolean capturePhotoAllowed;
    @SerializedName("capturePhotoRequired")
    @Expose
    private Boolean capturePhotoRequired;
    @SerializedName("showTenantNameSecondScreen")
    @Expose
    private Boolean showTenantNameSecondScreen;
    @SerializedName("showBuildingNameSecondScreen")
    @Expose
    private Boolean showBuildingNameSecondScreen;
    @SerializedName("useIdScanner")
    @Expose
    private Boolean useIdScanner;
    @SerializedName("hostConfirmationMode")
    @Expose
    private String hostConfirmationMode;
    @SerializedName("tenantHostConfirmationMode")
    @Expose
    private String tenantHostConfirmationMode;
    @SerializedName("walkinVstrConfirmationMode")
    @Expose
    private String walkinVstrConfirmationMode;
    @SerializedName("vstrOverDueAlert")
    @Expose
    private Boolean vstrOverDueAlert;
    @SerializedName("vstrOverDueAlertAfterHoursOfVstrLoggedIn")
    @Expose
    private Integer vstrOverDueAlertAfterHoursOfVstrLoggedIn;
    @SerializedName("vstrWelcomeGreetings")
    @Expose
    private Boolean vstrWelcomeGreetings;
    @SerializedName("greetingsText")
    @Expose
    private Object greetingsText;
    @SerializedName("allowAutoVisitorLogoutAfterMidnightEveryDay")
    @Expose
    private Boolean allowAutoVisitorLogoutAfterMidnightEveryDay;
    @SerializedName("apptLinkExpiryMinutes")
    @Expose
    private Integer apptLinkExpiryMinutes;
    @SerializedName("singleApptCreationForMaxDays")
    @Expose
    private Integer singleApptCreationForMaxDays;
    @SerializedName("issuePassForMaxDays")
    @Expose
    private Integer issuePassForMaxDays;
    @SerializedName("emailAlertBeforeCardExpiry")
    @Expose
    private Object emailAlertBeforeCardExpiry;
    @SerializedName("sendEmailAlertOnCardExpiryDay")
    @Expose
    private Object sendEmailAlertOnCardExpiryDay;
    @SerializedName("contractorEmployeeRequestReminderDays")
    @Expose
    private Object contractorEmployeeRequestReminderDays;
    @SerializedName("dailyVstrReportHours")
    @Expose
    private String dailyVstrReportHours;
    @SerializedName("dailyContractorEmployeeReportHours")
    @Expose
    private String dailyContractorEmployeeReportHours;
    @SerializedName("dailyVstrOverstayAlertHours")
    @Expose
    private String dailyVstrOverstayAlertHours;
    @SerializedName("dailySMSReportHours")
    @Expose
    private String dailySMSReportHours;
    @SerializedName("vstrEntryAt")
    @Expose
    private String vstrEntryAt;
    @SerializedName("vstrArrivalNotificationToHostBy")
    @Expose
    private String vstrArrivalNotificationToHostBy;
    @SerializedName("blacklistedAlertToComplexAdminBy")
    @Expose
    private Object blacklistedAlertToComplexAdminBy;
    @SerializedName("contractorEmployeeInOutFunctionality")
    @Expose
    private String contractorEmployeeInOutFunctionality;
    @SerializedName("personToMeetRequierd")
    @Expose
    private Boolean personToMeetRequierd;
    @SerializedName("validityOfForgotPasswordLinkInHours")
    @Expose
    private Integer validityOfForgotPasswordLinkInHours;
    @SerializedName("timeGapBetweenScanInMinutes")
    @Expose
    private int timeGapBetweenScanInMinutes;
    @SerializedName("printPass")
    @Expose
    private Boolean printPass;
    @SerializedName("generateSoftPass")
    @Expose
    private Boolean GenerateSoftPass;
    @SerializedName("visitorCapturePhotoAllowed")
    @Expose
    private Boolean isVisitorCapturePhotoAllowed;
    @SerializedName("visitorCapturePhotoRequired")
    @Expose
    private Boolean isVisitorCapturePhotoRequired;






    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public Integer getComplexId() {
        return complexId;
    }

    public void setComplexId(Integer complexId) {
        this.complexId = complexId;
    }

    public Object getTenantId() {
        return tenantId;
    }

    public void setTenantId(Object tenantId) {
        this.tenantId = tenantId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getToggleKeyboard() {
        return toggleKeyboard;
    }

    public void setToggleKeyboard(Boolean toggleKeyboard) {
        this.toggleKeyboard = toggleKeyboard;
    }

    public Boolean getUseTogglekeyboardVstrEntry() {
        return useTogglekeyboardVstrEntry;
    }

    public void setUseTogglekeyboardVstrEntry(Boolean useTogglekeyboardVstrEntry) {
        this.useTogglekeyboardVstrEntry = useTogglekeyboardVstrEntry;
    }

    public Boolean getUseNameOfLoggedInUser() {
        return useNameOfLoggedInUser;
    }

    public void setUseNameOfLoggedInUser(Boolean useNameOfLoggedInUser) {
        this.useNameOfLoggedInUser = useNameOfLoggedInUser;
    }

    public Boolean getUseLastLoginDateAndTime() {
        return useLastLoginDateAndTime;
    }

    public void setUseLastLoginDateAndTime(Boolean useLastLoginDateAndTime) {
        this.useLastLoginDateAndTime = useLastLoginDateAndTime;
    }

    public Boolean getUseVstrMobileNo() {
        return useVstrMobileNo;
    }

    public void setUseVstrMobileNo(Boolean useVstrMobileNo) {
        this.useVstrMobileNo = useVstrMobileNo;
    }

    public Boolean getVstrAuthenticatiOnEveryTime() {
        return vstrAuthenticatiOnEveryTime;
    }

    public void setVstrAuthenticatiOnEveryTime(Boolean vstrAuthenticatiOnEveryTime) {
        this.vstrAuthenticatiOnEveryTime = vstrAuthenticatiOnEveryTime;
    }

    public Boolean getMultiVstrAuthenticatiOn() {
        return multiVstrAuthenticatiOn;
    }

    public void setMultiVstrAuthenticatiOn(Boolean multiVstrAuthenticatiOn) {
        this.multiVstrAuthenticatiOn = multiVstrAuthenticatiOn;
    }

    public Boolean getOverdueAlertToTenantFrOntdesk() {
        return overdueAlertToTenantFrOntdesk;
    }

    public void setOverdueAlertToTenantFrOntdesk(Boolean overdueAlertToTenantFrOntdesk) {
        this.overdueAlertToTenantFrOntdesk = overdueAlertToTenantFrOntdesk;
    }

    public Boolean getOverdueAlertToHostOrMultipleHosts() {
        return overdueAlertToHostOrMultipleHosts;
    }

    public void setOverdueAlertToHostOrMultipleHosts(Boolean overdueAlertToHostOrMultipleHosts) {
        this.overdueAlertToHostOrMultipleHosts = overdueAlertToHostOrMultipleHosts;
    }

    public Boolean getBypassedAllowed() {
        return isBypassedAllowed;
    }

    public void setBypassedAllowed(Boolean bypassedAllowed) {
        isBypassedAllowed = bypassedAllowed;
    }

    public Boolean getUseScanIdProofOption() {
        return useScanIdProofOption;
    }

    public void setUseScanIdProofOption(Boolean useScanIdProofOption) {
        this.useScanIdProofOption = useScanIdProofOption;
    }

    public Boolean getUseBrowseIdProofOption() {
        return useBrowseIdProofOption;
    }

    public void setUseBrowseIdProofOption(Boolean useBrowseIdProofOption) {
        this.useBrowseIdProofOption = useBrowseIdProofOption;
    }

    public Boolean getUseVstrSignatureOption() {
        return useVstrSignatureOption;
    }

    public void setUseVstrSignatureOption(Boolean useVstrSignatureOption) {
        this.useVstrSignatureOption = useVstrSignatureOption;
    }

    public Boolean getAllowMultiplePersonToMeet() {
        return allowMultiplePersonToMeet;
    }

    public void setAllowMultiplePersonToMeet(Boolean allowMultiplePersonToMeet) {
        this.allowMultiplePersonToMeet = allowMultiplePersonToMeet;
    }

    public Boolean getPrintServerPhotoOrCurrentPhoto() {
        return printServerPhotoOrCurrentPhoto;
    }

    public void setPrintServerPhotoOrCurrentPhoto(Boolean printServerPhotoOrCurrentPhoto) {
        this.printServerPhotoOrCurrentPhoto = printServerPhotoOrCurrentPhoto;
    }

    public Boolean getVstrLogout() {
        return vstrLogout;
    }

    public void setVstrLogout(Boolean vstrLogout) {
        this.vstrLogout = vstrLogout;
    }

    public Boolean getFieldsOnVstrLogoutScreen() {
        return fieldsOnVstrLogoutScreen;
    }

    public void setFieldsOnVstrLogoutScreen(Boolean fieldsOnVstrLogoutScreen) {
        this.fieldsOnVstrLogoutScreen = fieldsOnVstrLogoutScreen;
    }

    public Boolean getAllowChangeOfDateBeforeLoggingOutVstr() {
        return allowChangeOfDateBeforeLoggingOutVstr;
    }

    public void setAllowChangeOfDateBeforeLoggingOutVstr(Boolean allowChangeOfDateBeforeLoggingOutVstr) {
        this.allowChangeOfDateBeforeLoggingOutVstr = allowChangeOfDateBeforeLoggingOutVstr;
    }

    public Boolean getAllowChangeOfTimeBeforeLoggingOutVstr() {
        return allowChangeOfTimeBeforeLoggingOutVstr;
    }

    public void setAllowChangeOfTimeBeforeLoggingOutVstr(Boolean allowChangeOfTimeBeforeLoggingOutVstr) {
        this.allowChangeOfTimeBeforeLoggingOutVstr = allowChangeOfTimeBeforeLoggingOutVstr;
    }

    public Boolean getMaskVstrMobileNo() {
        return maskVstrMobileNo;
    }

    public void setMaskVstrMobileNo(Boolean maskVstrMobileNo) {
        this.maskVstrMobileNo = maskVstrMobileNo;
    }

    public Boolean getSendApptLinkToVstrToFillDtls() {
        return sendApptLinkToVstrToFillDtls;
    }

    public void setSendApptLinkToVstrToFillDtls(Boolean sendApptLinkToVstrToFillDtls) {
        this.sendApptLinkToVstrToFillDtls = sendApptLinkToVstrToFillDtls;
    }

    public Boolean getAllowMultipleDaysAppt() {
        return allowMultipleDaysAppt;
    }

    public void setAllowMultipleDaysAppt(Boolean allowMultipleDaysAppt) {
        this.allowMultipleDaysAppt = allowMultipleDaysAppt;
    }

    public Boolean getAllowMultipleVstrInASingleAppt() {
        return allowMultipleVstrInASingleAppt;
    }

    public void setAllowMultipleVstrInASingleAppt(Boolean allowMultipleVstrInASingleAppt) {
        this.allowMultipleVstrInASingleAppt = allowMultipleVstrInASingleAppt;
    }

    public Object getMaxVstrInSingleAppt() {
        return maxVstrInSingleAppt;
    }

    public void setMaxVstrInSingleAppt(Object maxVstrInSingleAppt) {
        this.maxVstrInSingleAppt = maxVstrInSingleAppt;
    }

    public Boolean getAllowToCreateApptOnBehalfOf() {
        return allowToCreateApptOnBehalfOf;
    }

    public void setAllowToCreateApptOnBehalfOf(Boolean allowToCreateApptOnBehalfOf) {
        this.allowToCreateApptOnBehalfOf = allowToCreateApptOnBehalfOf;
    }

    public String getAuthenticateVstrBy() {
        return authenticateVstrBy;
    }

    public void setAuthenticateVstrBy(String authenticateVstrBy) {
        this.authenticateVstrBy = authenticateVstrBy;
    }

    public Boolean getNoAuthentication() {
        return noAuthentication;
    }

    public void setNoAuthentication(Boolean noAuthentication) {
        this.noAuthentication = noAuthentication;
    }

    public Object getUseNoOfRecordsOnASinglePage() {
        return useNoOfRecordsOnASinglePage;
    }

    public void setUseNoOfRecordsOnASinglePage(Object useNoOfRecordsOnASinglePage) {
        this.useNoOfRecordsOnASinglePage = useNoOfRecordsOnASinglePage;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Integer getApptIntervalTimeBetweenStartTimeAndEndTime() {
        return apptIntervalTimeBetweenStartTimeAndEndTime;
    }

    public void setApptIntervalTimeBetweenStartTimeAndEndTime(Integer apptIntervalTimeBetweenStartTimeAndEndTime) {
        this.apptIntervalTimeBetweenStartTimeAndEndTime = apptIntervalTimeBetweenStartTimeAndEndTime;
    }

    public Object getAllowPreApptVstrEntryBeforeHoursOfStartTime() {
        return allowPreApptVstrEntryBeforeHoursOfStartTime;
    }

    public void setAllowPreApptVstrEntryBeforeHoursOfStartTime(Object allowPreApptVstrEntryBeforeHoursOfStartTime) {
        this.allowPreApptVstrEntryBeforeHoursOfStartTime = allowPreApptVstrEntryBeforeHoursOfStartTime;
    }

    public Object getAllowPreApptVstrEntryAfterHoursOfStartTime() {
        return allowPreApptVstrEntryAfterHoursOfStartTime;
    }

    public void setAllowPreApptVstrEntryAfterHoursOfStartTime(Object allowPreApptVstrEntryAfterHoursOfStartTime) {
        this.allowPreApptVstrEntryAfterHoursOfStartTime = allowPreApptVstrEntryAfterHoursOfStartTime;
    }

    public Boolean getWalkinVisitorApproval() {
        return walkinVisitorApproval;
    }

    public void setWalkinVisitorApproval(Boolean walkinVisitorApproval) {
        this.walkinVisitorApproval = walkinVisitorApproval;
    }

    public String getDefaultIsdCode() {
        return defaultIsdCode;
    }

    public void setDefaultIsdCode(String defaultIsdCode) {
        this.defaultIsdCode = defaultIsdCode;
    }

    public Object getSendApptUpdateToVisitorBy() {
        return sendApptUpdateToVisitorBy;
    }

    public void setSendApptUpdateToVisitorBy(Object sendApptUpdateToVisitorBy) {
        this.sendApptUpdateToVisitorBy = sendApptUpdateToVisitorBy;
    }

    public String getDateTimeDisplayFormat() {
        return dateTimeDisplayFormat;
    }

    public void setDateTimeDisplayFormat(String dateTimeDisplayFormat) {
        this.dateTimeDisplayFormat = dateTimeDisplayFormat;
    }

    public Boolean getCapturePhotoAllowed() {
        return capturePhotoAllowed;
    }

    public void setCapturePhotoAllowed(Boolean capturePhotoAllowed) {
        this.capturePhotoAllowed = capturePhotoAllowed;
    }

    public Boolean getCapturePhotoRequired() {
        return capturePhotoRequired;
    }

    public void setCapturePhotoRequired(Boolean capturePhotoRequired) {
        this.capturePhotoRequired = capturePhotoRequired;
    }

    public Boolean getShowTenantNameSecondScreen() {
        return showTenantNameSecondScreen;
    }

    public void setShowTenantNameSecondScreen(Boolean showTenantNameSecondScreen) {
        this.showTenantNameSecondScreen = showTenantNameSecondScreen;
    }

    public Boolean getShowBuildingNameSecondScreen() {
        return showBuildingNameSecondScreen;
    }

    public void setShowBuildingNameSecondScreen(Boolean showBuildingNameSecondScreen) {
        this.showBuildingNameSecondScreen = showBuildingNameSecondScreen;
    }

    public Boolean getUseIdScanner() {
        return useIdScanner;
    }

    public void setUseIdScanner(Boolean useIdScanner) {
        this.useIdScanner = useIdScanner;
    }

    public String getHostConfirmationMode() {
        return hostConfirmationMode;
    }

    public void setHostConfirmationMode(String hostConfirmationMode) {
        this.hostConfirmationMode = hostConfirmationMode;
    }

    public String getTenantHostConfirmationMode() {
        return tenantHostConfirmationMode;
    }

    public void setTenantHostConfirmationMode(String tenantHostConfirmationMode) {
        this.tenantHostConfirmationMode = tenantHostConfirmationMode;
    }

    public String getWalkinVstrConfirmationMode() {
        return walkinVstrConfirmationMode;
    }

    public void setWalkinVstrConfirmationMode(String walkinVstrConfirmationMode) {
        this.walkinVstrConfirmationMode = walkinVstrConfirmationMode;
    }

    public Boolean getVstrOverDueAlert() {
        return vstrOverDueAlert;
    }

    public void setVstrOverDueAlert(Boolean vstrOverDueAlert) {
        this.vstrOverDueAlert = vstrOverDueAlert;
    }

    public Integer getVstrOverDueAlertAfterHoursOfVstrLoggedIn() {
        return vstrOverDueAlertAfterHoursOfVstrLoggedIn;
    }

    public void setVstrOverDueAlertAfterHoursOfVstrLoggedIn(Integer vstrOverDueAlertAfterHoursOfVstrLoggedIn) {
        this.vstrOverDueAlertAfterHoursOfVstrLoggedIn = vstrOverDueAlertAfterHoursOfVstrLoggedIn;
    }

    public Boolean getVstrWelcomeGreetings() {
        return vstrWelcomeGreetings;
    }

    public void setVstrWelcomeGreetings(Boolean vstrWelcomeGreetings) {
        this.vstrWelcomeGreetings = vstrWelcomeGreetings;
    }

    public Object getGreetingsText() {
        return greetingsText;
    }

    public void setGreetingsText(Object greetingsText) {
        this.greetingsText = greetingsText;
    }

    public Boolean getAllowAutoVisitorLogoutAfterMidnightEveryDay() {
        return allowAutoVisitorLogoutAfterMidnightEveryDay;
    }

    public void setAllowAutoVisitorLogoutAfterMidnightEveryDay(Boolean allowAutoVisitorLogoutAfterMidnightEveryDay) {
        this.allowAutoVisitorLogoutAfterMidnightEveryDay = allowAutoVisitorLogoutAfterMidnightEveryDay;
    }

    public Integer getApptLinkExpiryMinutes() {
        return apptLinkExpiryMinutes;
    }

    public void setApptLinkExpiryMinutes(Integer apptLinkExpiryMinutes) {
        this.apptLinkExpiryMinutes = apptLinkExpiryMinutes;
    }

    public Integer getSingleApptCreationForMaxDays() {
        return singleApptCreationForMaxDays;
    }

    public void setSingleApptCreationForMaxDays(Integer singleApptCreationForMaxDays) {
        this.singleApptCreationForMaxDays = singleApptCreationForMaxDays;
    }

    public Integer getIssuePassForMaxDays() {
        return issuePassForMaxDays;
    }

    public void setIssuePassForMaxDays(Integer issuePassForMaxDays) {
        this.issuePassForMaxDays = issuePassForMaxDays;
    }

    public Object getEmailAlertBeforeCardExpiry() {
        return emailAlertBeforeCardExpiry;
    }

    public void setEmailAlertBeforeCardExpiry(Object emailAlertBeforeCardExpiry) {
        this.emailAlertBeforeCardExpiry = emailAlertBeforeCardExpiry;
    }

    public Object getSendEmailAlertOnCardExpiryDay() {
        return sendEmailAlertOnCardExpiryDay;
    }

    public void setSendEmailAlertOnCardExpiryDay(Object sendEmailAlertOnCardExpiryDay) {
        this.sendEmailAlertOnCardExpiryDay = sendEmailAlertOnCardExpiryDay;
    }

    public Object getContractorEmployeeRequestReminderDays() {
        return contractorEmployeeRequestReminderDays;
    }

    public void setContractorEmployeeRequestReminderDays(Object contractorEmployeeRequestReminderDays) {
        this.contractorEmployeeRequestReminderDays = contractorEmployeeRequestReminderDays;
    }

    public String getDailyVstrReportHours() {
        return dailyVstrReportHours;
    }

    public void setDailyVstrReportHours(String dailyVstrReportHours) {
        this.dailyVstrReportHours = dailyVstrReportHours;
    }

    public String getDailyContractorEmployeeReportHours() {
        return dailyContractorEmployeeReportHours;
    }

    public void setDailyContractorEmployeeReportHours(String dailyContractorEmployeeReportHours) {
        this.dailyContractorEmployeeReportHours = dailyContractorEmployeeReportHours;
    }

    public String getDailyVstrOverstayAlertHours() {
        return dailyVstrOverstayAlertHours;
    }

    public void setDailyVstrOverstayAlertHours(String dailyVstrOverstayAlertHours) {
        this.dailyVstrOverstayAlertHours = dailyVstrOverstayAlertHours;
    }

    public String getDailySMSReportHours() {
        return dailySMSReportHours;
    }

    public void setDailySMSReportHours(String dailySMSReportHours) {
        this.dailySMSReportHours = dailySMSReportHours;
    }

    public String getVstrEntryAt() {
        return vstrEntryAt;
    }

    public void setVstrEntryAt(String vstrEntryAt) {
        this.vstrEntryAt = vstrEntryAt;
    }

    public String getVstrArrivalNotificationToHostBy() {
        return vstrArrivalNotificationToHostBy;
    }

    public void setVstrArrivalNotificationToHostBy(String vstrArrivalNotificationToHostBy) {
        this.vstrArrivalNotificationToHostBy = vstrArrivalNotificationToHostBy;
    }

    public Object getBlacklistedAlertToComplexAdminBy() {
        return blacklistedAlertToComplexAdminBy;
    }

    public void setBlacklistedAlertToComplexAdminBy(Object blacklistedAlertToComplexAdminBy) {
        this.blacklistedAlertToComplexAdminBy = blacklistedAlertToComplexAdminBy;
    }

    public String getContractorEmployeeInOutFunctionality() {
        return contractorEmployeeInOutFunctionality;
    }

    public void setContractorEmployeeInOutFunctionality(String contractorEmployeeInOutFunctionality) {
        this.contractorEmployeeInOutFunctionality = contractorEmployeeInOutFunctionality;
    }

    public Boolean getPersonToMeetRequierd() {
        return personToMeetRequierd;
    }

    public void setPersonToMeetRequierd(Boolean personToMeetRequierd) {
        this.personToMeetRequierd = personToMeetRequierd;
    }

    public Integer getValidityOfForgotPasswordLinkInHours() {
        return validityOfForgotPasswordLinkInHours;
    }

    public void setValidityOfForgotPasswordLinkInHours(Integer validityOfForgotPasswordLinkInHours) {
        this.validityOfForgotPasswordLinkInHours = validityOfForgotPasswordLinkInHours;
    }

    public int getTimeGapBetweenScanInMinutes() {
        return timeGapBetweenScanInMinutes;
    }

    public void setTimeGapBetweenScanInMinutes(int timeGapBetweenScanInMinutes) {
        this.timeGapBetweenScanInMinutes = timeGapBetweenScanInMinutes;
    }

    public Boolean getPrintPass() {
        return printPass;
    }

    public void setPrintPass(Boolean printPass) {
        this.printPass = printPass;
    }

    public Boolean getGenerateSoftPass() {
        return GenerateSoftPass;
    }

    public void setGenerateSoftPass(Boolean generateSoftPass) {
        GenerateSoftPass = generateSoftPass;
    }
    public Boolean getVisitorCapturePhotoAllowed() {
        return isVisitorCapturePhotoAllowed;
    }

    public void setVisitorCapturePhotoAllowed(Boolean visitorCapturePhotoAllowed) {
        isVisitorCapturePhotoAllowed = visitorCapturePhotoAllowed;
    }

    public Boolean getVisitorCapturePhotoRequired() {
        return isVisitorCapturePhotoRequired;
    }

    public void setVisitorCapturePhotoRequired(Boolean visitorCapturePhotoRequired) {
        isVisitorCapturePhotoRequired = visitorCapturePhotoRequired;
    }


}
