package ir.drax.ftp;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import ir.drax.ftp.ftp.On;

public class Home extends Base {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.list);


        message(R.string.login_msg);
        ftp.Go(FTP.Commands.LOGIN, new On() {
            @Override
            public void done(Object o) {
                message("Connected");
                ftp.Go(FTP.Commands.LIST_DIR, new On() {
                    @Override
                    public void done(Object o) {
                        String[] names = (String[]) o;
                        for (String name : names) {
                            Log.e("name",name);

                        }
                            Log.e("Done","ssss");
                    }

                    @Override
                    public void fail(String message) {
                        message(message);

                    }
                });
            }

            @Override
            public void fail(String message) {
                message(message);
            }
        });
    }





}
