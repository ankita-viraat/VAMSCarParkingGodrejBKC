package info.vams.zktecoedu.reception.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import info.vams.zktecoedu.reception.Model.SelectedBuilding;

public class SPbean {
    public static final String LOGIN_DATA = "shred_login_data";
    public static final String HAS_TO_DOWNLOAD_MASTER_DATA = "shred_masters_needed";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SPbean(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    //General Settings Primary settings
    public String getPreference(String key, String value) {
        return sharedPreferences.getString(key, value);
    }

    public boolean getPreference(String key, boolean value) {
        return sharedPreferences.getBoolean(key, value);
    }

    public boolean existPreference(String key) {
        return sharedPreferences.contains(key);
    }

    public int getPreference(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

    public Set getPreference(String key, Set value) {
        return sharedPreferences.getStringSet(key, value);
    }

    public void setPreference(String key, String value) {
        editor.putString(key, value).commit();
    }

    public void setPreference(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public void setPreference(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public void setPreference(String key, Set value) {
        editor.putStringSet(key, value).commit();
    }

    public void removePreference(String key) {
        editor.remove(key).commit();
    }

    //Using same method for Reverse Set so don't get confuse it is correct ok
    public Set<String> getParameterSet(String key) {
        return sharedPreferences.getStringSet(key, null);
    }

    public void setParameterSet(String key, Set<String> ParameterSet) {
        editor.putStringSet(key, ParameterSet).commit();

    }

    //for saving the Selected Building List in Shared Preference
    public void setSelectedBuildings(String key, ArrayList<SelectedBuilding> selectedBuildings){
        Gson gson = new Gson();

        String json = gson.toJson(selectedBuildings);

        editor.putString(key, json);
        editor.commit();
    }

    public ArrayList<SelectedBuilding> getSelectedBuildings(String key){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        Type type = new TypeToken<List<SelectedBuilding>>() {}.getType();
        ArrayList<SelectedBuilding> arrayList = gson.fromJson(json, type);
        return arrayList;

    }

    //for saving the Selected Building List in Shared Preference
    public void setSelectedBuildingIds(String key, ArrayList<Integer> selectedBuildingIds){
        Gson gson = new Gson();

        String json = gson.toJson(selectedBuildingIds);

        editor.putString(key, json);
        editor.commit();
    }

    public ArrayList<Integer> getSelectedBuildingIds(String key){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        Type type = new TypeToken<List<Integer>>() {}.getType();
        ArrayList<Integer> arrayList = gson.fromJson(json, type);
        return arrayList;
    }
}
