package ir.drax.ftp;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.drax.ftp.ftp.Commands;
import ir.drax.ftp.ftp.Content;
import ir.drax.ftp.ftp.On;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Home extends Base {

    private RecyclerView recyclerView;
    private DirectoryContentAdapter adapter;
    private List<Content> contentsList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.list);
        setupRecycler();


        message(R.string.login_msg);
        ftp.Go(Commands.Login(new On(Home.this) {
            @Override
            public void done(Object o) {
                message("Connected");
                ftp.Go(Commands.ListRoot(new On(Home.this) {
                    @Override
                    public void done(Object o) {
                        loadList((FTPFile[]) o);
                    }

                    @Override
                    public void fail(String message) {
                        message(message);

                    }
                }));
            }

            @Override
            public void fail(String message) {
                message(message);
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
        adapter = new DirectoryContentAdapter(this, contentsList, item -> {

            if (item.getType()==1)//Relative dir
                ftp.Go(Commands.ListDir(item.getName(), new On(Home.this) {
                    @Override
                    public void done(Object o) {
                        loadList((FTPFile[]) o);
                    }

                    @Override
                    public void fail(String message) {
                        message(message);
                    }
                }));

            else if (item.getType()==-1) {//Upper dir
                listUpperDir();

            } else if (item.getType()==0) {//File
                ftp.Go(Commands.GetFile(item.getName(),createFile(item), new On(Home.this) {
                    @Override
                    public void done(Object o) {
                        boolean state = (boolean) o;
                        if (state)
                            Toast.makeText(Home.this, item.getName()+" Downloaded successfully!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(Home.this, item.getName()+" Failed!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void fail(String message) {
                        message(message);
                    }
                }));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void listUpperDir() {
        ftp.Go(Commands.ListUpperDir(new On(Home.this) {
            @Override
            public void done(Object o) {
                loadList((FTPFile[]) o);
            }

            @Override
            public void fail(String message) {
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
        ftp.Go(Commands.GetCurrentDir(new On(Home.this) {
            @Override
            public void done(Object o) {
                if (o.equals("/"))
                    Home.super.onBackPressed();

                else
                    listUpperDir();
            }

            @Override
            public void fail(String message) {
                Home.super.onBackPressed();
            }
        }));


    }
}


