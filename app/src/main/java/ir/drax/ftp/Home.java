package ir.drax.ftp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.IOException;

public class Home extends Base {

    private TextView message;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        message = findViewById(R.id.message);
        recyclerView = findViewById(R.id.list);


        message(R.string.login_msg);
        ftp.login(new On() {
            @Override
            public void Connected() {
                Toast.makeText(Home.this, "Done", Toast.LENGTH_SHORT).show();
                message("Connected");
            }

            @Override
            public void fail(String message) {
                message(message);
            }
        });
    }





}
