package ir.drax.ftp.ui;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import ir.drax.ftp.R;
import ir.drax.ftp.data.sharedPreferences.model.Profile;
import ir.drax.ftp.service.ftp.Commands;
import ir.drax.ftp.service.ftp.Content;
import ir.drax.ftp.service.ftp.On;
import ir.drax.ftp.ui.browser.BrowserAdapter;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Browser extends Base {
    public static String ACTIVE_PROFILE_KEY = "active_ftp_profile";
    private RecyclerView recyclerView;
    private BrowserAdapter adapter;
    private List<Content> contentsList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.list);
        setupRecycler();


        message(R.string.login_msg);

        Profile profile = new Gson().fromJson(getIntent().getStringExtra(ACTIVE_PROFILE_KEY),Profile.class);

        ftp.Do(Commands.Login(
                profile.getUrl(),
                profile.getUsername(),
                profile.getPassword(),
                profile.getName(),
                new On(Browser.this) {
                    @Override
                    public void done(Object o) {
                        super.done(o);
                        profile.setLastConnect(System.currentTimeMillis());
                        dataManager.updateProfile(profile);

                        message("Connected");
                        ftp.Do(Commands.ListRoot(new On(Browser.this) {
                            @Override
                            public void done(Object o) {
                                super.done(o);
                                loadList((FTPFile[]) o);
                            }

                            @Override
                            public void fail(String message) {
                                super.fail(message);
                                message(message);

                            }
                        }));
                    }

                    @Override
                    public void fail(String message) {
                        super.fail(message);
                        Browser.this.setResult(RESULT_CANCELED);
                        getIntent().putExtra(ACTIVE_PROFILE_KEY,message);
                    }
                }));
    }

    private void loadList(FTPFile[] ftpFiles) {
        message("List Loaded");
        contentsList.clear();
        contentsList.add(new Content("Up",0l,"",-1,null));

        for (FTPFile file : ftpFiles){
            contentsList.add(new Content(file.getName(),file.getSize(),file.getLink(),file.getType(),file.getModifiedDate()));
        }

        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BrowserAdapter(this, contentsList, item -> {

            if (item.getType()==1)//Relative dir
                ftp.Do(Commands.ListDir(item.getName(), new On(Browser.this) {
                    @Override
                    public void done(Object o) {
                        super.done(o);
                        loadList((FTPFile[]) o);
                    }

                    @Override
                    public void fail(String message) {
                        super.fail(message);
                        message(message);
                    }
                }));

            else if (item.getType()==-1) {//Upper dir
                listUpperDir();

            } else if (item.getType()==0) {//File
                ftp.Do(Commands.GetFile(item.getName(),createFile(item), new On(Browser.this) {
                    @Override
                    public void done(Object o) {
                        super.done(o);
                        boolean state = (boolean) o;
                        if (state)
                            Toast.makeText(Browser.this, item.getName()+" Downloaded successfully!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(Browser.this, item.getName()+" Failed!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void fail(String message) {
                        super.fail(message);
                        message(message);
                    }
                }));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void listUpperDir() {
        ftp.Do(Commands.ListUpperDir(new On(Browser.this) {
            @Override
            public void done(Object o) {
                super.done(o);
                loadList((FTPFile[]) o);
            }

            @Override
            public void fail(String message) {
                super.fail(message);
                message(message);
            }
        }));
    }

    private File createFile(Content item) {
        Log.e("ss",item.getPath()+"---"+item.getName());
        File myFile = new File(Environment.getExternalStorageDirectory(), "FTP/" + ftp.getAccount()+"/" + item.getName());
        try {
            File DIR_ftp = new File(Environment.getExternalStorageDirectory(), "FTP");
            if (!DIR_ftp.isDirectory())
                DIR_ftp.mkdirs();
            File DIR_user = new File(Environment.getExternalStorageDirectory(), "FTP/" + ftp.getAccount());
            if (!DIR_user.isDirectory())
                DIR_user.mkdirs();



            myFile.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return myFile;
    }

    @Override
    public void onBackPressed() {
        ftp.Do(Commands.GetCurrentDir(new On(Browser.this) {
            @Override
            public void done(Object o) {
                super.done(o);
                if (o.equals("/"))
                    Browser.super.onBackPressed();

                else
                    listUpperDir();
            }

            @Override
            public void fail(String message) {
                super.fail(message);
                Browser.super.onBackPressed();
            }
        }));


    }
}


