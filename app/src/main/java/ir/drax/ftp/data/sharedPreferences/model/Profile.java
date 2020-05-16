package ir.drax.ftp.data.sharedPreferences.model;

public class Profile {
    private long id;
    private String name;
    private String url;
    private String username;
    private String password;
    private long lastConnect = 0;

    public Profile(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void update(Profile profile) {
        this.name=profile.name;
        this.url=profile.url;
        this.username=profile.username;
        this.password=profile.password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLastConnect() {
        return lastConnect;
    }

    public void setLastConnect(long lastConnect) {
        this.lastConnect = lastConnect;
    }
}
