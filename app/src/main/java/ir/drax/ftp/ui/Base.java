package ir.drax.ftp.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import ir.drax.ftp.data.DataManager;
import ir.drax.ftp.service.FTP;
import ir.drax.ftp.R;
import ir.drax.ftp.service.ftp.Commands;
import ir.drax.ftp.service.ftp.Init;
import ir.drax.ftp.util.LoadingView;

public class Base extends AppCompatActivity implements Init {
    FTP ftp;
    public DataManager dataManager;
    public LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ftp = FTP.getInstance(this,this);
        dataManager  = DataManager.getInstance(this);
        loadingView = new LoadingView(this).attach(getWindow().getDecorView().findViewById(android.R.id.content));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //File write logic here
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

    }


    @Override
    protected void onDestroy() {
        ftp.Do(Commands.Disconnect());
        super.onDestroy();
    }

    void message(int text){message(getString(text));}
    void message(String text){
        ((TextView)findViewById(R.id.message)).setText(text);
    }

    @Override
    public void loading(boolean on) {
        if (on)findViewById(R.id.loading).setVisibility(View.VISIBLE);
        else findViewById(R.id.loading).setVisibility(View.GONE);
    }
}
