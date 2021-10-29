package info.vams.zktecoedu.reception.Service;

import android.app.IntentService;
import android.content.Intent;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import info.vams.zktecoedu.reception.Model.ImageUploadObject;

/**
 * Created by Nithin on 7/19/2018.
 */

public class UploadImageServise extends IntentService {


    public UploadImageServise() {
        super("UploadImageServise");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ArrayList<ImageUploadObject> objects = (ArrayList<ImageUploadObject>) intent.getSerializableExtra("upload");
        Log.d("TAG","Image data in service = "+ new Gson().toJson(objects));
    }
}
