package info.vams.zktecoedu.reception.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.SPbean;


/**
 * Created by Nithin on 4/19/2018.
 */

public class AppStatusService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG","App killed");
        SPbean sPbean=new SPbean(getBaseContext());
        sPbean.removePreference(Constants.MASTER_RESPONSE);
        Log.d("TAG_DESTROY","Masters creared");
        sPbean.removePreference(Constants.PROFILE_RESPONSE);
        Log.d("TAG_DESTROY","Profile Cleared");
        sPbean.removePreference(Constants.EMPLOYEE_LIST_RESPONSE);
        Log.d("TAG_DESTROY","Employee list");
        sPbean.removePreference(Constants.TOKEN);
        Log.d("TAG_DESTROY","Token");
        sPbean.removePreference(Constants.TENANTLIST_RESPONSE);
        Log.d("TAG_DESTROY","Tanent list cleared");
        sPbean.removePreference(Constants.DATA_TO_UPLOADED);
        Log.d("TAG_DESTROY","Data to upload cleared");
        sPbean.removePreference(Constants.SETTINGS);
        Log.d("TAG_DESTROY","Data to Settings");
        sPbean.removePreference(Constants.BUILDING_LIST);
        Log.d("TAG_DESTROY", "Data to Building List");
        sPbean.removePreference(Constants.BUILDING_ID_LIST);
        Log.d("TAG_DESTROY", "Data to Building Id List");
        sPbean.removePreference(Constants.STUDENTLIST_RESPONSE);
        Log.d("TAG_DESTROY", "Students cleared");
        sPbean.removePreference(Constants.BUILDINGLIST_RESPONSE);
        Log.d("TAG_DESTROY", "Data to Building List Response");
        sPbean.removePreference(Constants.SELECTED_LEVEL);
        Log.d("TAG_DESTROY", "Data to Building Level Cleared");

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        stopSelf();

    }

}
