package pl.skrzypekmichal.movementclassifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import pl.skrzypekmichal.movementclassifier.sensor_data_collector.DataCollectorActivity;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_UNKNOWN = 0;
    private static final int REQUEST_CODE_CLASSIFY = 1;
    private static final int REQUEST_CODE_COLLECT = 2;

    private Button btnClassifier;
    private Button btnCollectData;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initializeViews();
        initializeDrawerMenu();
    }

    private void initializeDrawerMenu(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initializeViews() {
        btnClassifier = findViewById(R.id.btn_classifier);
        btnClassifier.setOnClickListener(this);

        btnCollectData = findViewById(R.id.btn_collect_data);
        btnCollectData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        if (v == btnClassifier) {
            i = new Intent(this, ClassifierActivity.class);
        } else if (v == btnCollectData) {
            i = new Intent(this, DataCollectorActivity.class);
        }
        startActivity(i);
    }
}
