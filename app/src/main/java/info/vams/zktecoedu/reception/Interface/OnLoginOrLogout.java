package info.vams.zktecoedu.reception.Interface;


import info.vams.zktecoedu.reception.Model.BuildingLevelLogoutListResponse;

/**
 * Created by vishal on 09-Apr-19.
 */

public interface OnLoginOrLogout {

    void onLoginLogout(BuildingLevelLogoutListResponse buildingLevelLogoutListResponse, String status);

}
