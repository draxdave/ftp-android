package ir.drax.ftp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.drax.ftp.R;
import ir.drax.ftp.data.sharedPreferences.model.Profile;
import ir.drax.ftp.service.ftp.Content;
import ir.drax.ftp.ui.home.ProfilesAdapter;

import java.util.ArrayList;
import java.util.List;

public class Home extends Base {

    private RecyclerView recyclerView;
    private ProfilesAdapter adapter;
    private List<Content> contentsList=new ArrayList<>();
    private EditText urlInput ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.list);
        urlInput = findViewById(R.id.url);
        urlInput.setOnEditorActionListener((v, actionId, event) -> {

            switch (actionId){
                case EditorInfo.IME_ACTION_DONE:
                case EditorInfo.IME_ACTION_NEXT:
                case EditorInfo.IME_ACTION_PREVIOUS:
                    connect();
                    return true;
            }
            return false;
        });

        findViewById(R.id.connectTV).setOnClickListener(v->connect());

        setupRecycler();

    }

    private void connect() {
        loadingView.on();
        hideKeyboard(urlInput);
        Toast.makeText(this, "ss", Toast.LENGTH_SHORT).show();
    }

    private void setupRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProfilesAdapter(this, dataManager.getProfiles(), new ProfilesAdapter.On() {
            @Override
            public void click(Profile profile) {

            }

            @Override
            public void remove(Profile profile) {

            }

            @Override
            public void duplicate(Profile profile) {

            }

            @Override
            public void edit(Profile profile) {

            }

            @Override
            public void sync(Profile profile) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void hideKeyboard(EditText editText)
    {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


