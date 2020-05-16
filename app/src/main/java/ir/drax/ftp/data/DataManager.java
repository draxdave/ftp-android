package ir.drax.ftp.data;

import android.content.Context;
import ir.drax.ftp.data.sharedPreferences.AppSharedPreference;
import ir.drax.ftp.data.sharedPreferences.SharedPreferenceManager;
import ir.drax.ftp.data.sharedPreferences.model.Profile;

import java.util.List;

public class DataManager implements AppDataManager , AppSharedPreference{
    private static DataManager instance;
    private SharedPreferenceManager preferences;

    public static DataManager getInstance(Context context){
        if (instance==null)
            instance=new DataManager(context);

        return instance;
    }

    public DataManager(Context context) {
        preferences = SharedPreferenceManager.getInstance(context);
    }

    @Override
    public void insertProfile(Profile profile) {
        preferences.insertProfile(profile);
    }

    @Override
    public void deleteProfile(Profile profile) {
        preferences.deleteProfile(profile);
    }

    @Override
    public void updateProfile(Profile oldProfile) {
        preferences.updateProfile(oldProfile);
    }

    @Override
    public List<Profile> getProfiles() {
        return preferences.getProfiles();
    }
}
