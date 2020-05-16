package ir.drax.ftp.data.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.drax.ftp.data.sharedPreferences.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferenceManager implements AppSharedPreference{
    private static SharedPreferenceManager instance;
    // Shared preferences file name
    private String PREF_NAME = "ftp_nav_v1";
    private SharedPreferences pref;
    private int PRIVATE_MODE = 0;

    public static SharedPreferenceManager getInstance(Context context){
        if (instance==null)
            instance=new SharedPreferenceManager(context);

        return instance;
    }

    private String PROFILE = "ftp_profile";

    public SharedPreferenceManager(Context context) {
        this.pref= context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
    }


    @Override
    public void insertProfile(Profile profile) {

        Gson gson=new Gson();
        ArrayList<Profile> profiles = gson.fromJson(pref.getString(PROFILE,""), new TypeToken<List<Profile>>(){}.getType());
        profiles.add(profile);

        pref.edit().putString(PROFILE,gson.toJson(profiles)).apply();
    }

    @Override
    public void deleteProfile(Profile profile) {
        Gson gson=new Gson();
        ArrayList<Profile> profiles = gson.fromJson(pref.getString(PROFILE,""), new TypeToken<List<Profile>>(){}.getType());
        profiles.remove(profile);

        pref.edit().putString(PROFILE,gson.toJson(profiles)).apply();
    }

    @Override
    public void updateProfile(Profile profile) {
        Gson gson=new Gson();
        ArrayList<Profile> profiles = gson.fromJson(pref.getString(PROFILE,""), new TypeToken<List<Profile>>(){}.getType());
        for (Profile oldProfile : profiles) {
            if (profile.getId()==oldProfile.getId()){
                oldProfile.update(profile);
                break;
            }
        }

        pref.edit().putString(PROFILE,gson.toJson(profiles)).apply();
    }

    @Override
    public List<Profile> getProfiles() {
        List<Profile> profiles=new Gson().fromJson(pref.getString(PROFILE,""), new TypeToken<List<Profile>>(){}.getType());
        return profiles==null?new ArrayList<>():profiles;
    }
}
