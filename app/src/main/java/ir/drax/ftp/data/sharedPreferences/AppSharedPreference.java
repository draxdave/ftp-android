package ir.drax.ftp.data.sharedPreferences;

import ir.drax.ftp.data.sharedPreferences.model.Profile;

import java.util.List;

public interface AppSharedPreference {
    void insertProfile(Profile profile);
    void deleteProfile(Profile profile);
    void updateProfile(Profile oldProfile);
    List<Profile> getProfiles();

}
