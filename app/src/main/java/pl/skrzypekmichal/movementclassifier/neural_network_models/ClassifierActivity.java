package pl.skrzypekmichal.movementclassifier.neural_network_models;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.joda.time.LocalDateTime;

import java.util.Arrays;

import pl.skrzypekmichal.movementclassifier.HomeActivity;
import pl.skrzypekmichal.movementclassifier.R;
import pl.skrzypekmichal.movementclassifier.RawDataCollector;
import pl.skrzypekmichal.movementclassifier.enums.MovementType;
import pl.skrzypekmichal.movementclassifier.sensor_data_collector.SettingsFragment;

public class ClassifierActivity extends AppCompatActivity implements SensorEventListener {

    public static final String MODEL_FILE_NAME = "MultiLayerNetworkWithReducedFeatures.zip";

    public MovementType movementType = MovementType.STANDING;

    private boolean collecting;

    private TextView tvMovementType;
    private Button btnClassify;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorGyroscope;

    private RawDataCollector dataCollector;
    private MovementClassifier movementClassifierModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifier);
        initializeView();
        initializeSensors();
        initializeNeuralNetworkModel();
        initializeDrawerMenu();
        initializeNavigation();

        dataCollector = new RawDataCollector();
    }


    private void initializeDrawerMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initializeNavigation() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            return onNavItemClick(id);
        });
    }

    private boolean onNavItemClick(int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                Intent i = new Intent(this, HomeActivity.class);
                finish();
                startActivity(i);
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initializeView() {
        tvMovementType = findViewById(R.id.tv_movement_type);
        tvMovementType.setText(movementType.getType());
        btnClassify = findViewById(R.id.btn_classify);
        btnClassify.setBackgroundColor(ContextCompat.getColor(this, R.color.btn_start));
        btnClassify.setOnClickListener(v -> {
            if (collecting) {
                stopRecording();
            } else {
                startCollecting();
            }
        });
    }

    private void startCollecting() {
        collecting = true;
        btnClassify.setText(R.string.stop_button);
        btnClassify.setBackgroundColor(ContextCompat.getColor(this, R.color.btn_stop));
    }

    private void stopRecording() {
        collecting = false;
        dataCollector.clearSensorData();
        btnClassify.setText(R.string.btn_classify);
        btnClassify.setBackgroundColor(ContextCompat.getColor(this, R.color.btn_start));
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    private void initializeNeuralNetworkModel() {
        MultiLayerNetwork network = KerasModelImporter.importModel(this, MODEL_FILE_NAME);
        movementClassifierModel = new MovementClassifier(network);
    }

    private void makeToast(String msg, int length) {
        Toast.makeText(this, msg, length).show();

    }

    /**
     * The onStart() and onStop() methods are preferred over onResume() and onPause() to register and unregister listeners.
     * As of Android 7.0 (API 24), apps can run in multi-window mode (split-screen or picture-in-picture mode).
     * Apps running in this mode are paused, but still visible on screen.
     * Use onStart() and onStop() to ensure that sensors continue running even if the app is in multi-window mode.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (sensorAccelerometer != null) {
            sensorManager.registerListener(this, sensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorGyroscope != null) {
            sensorManager.registerListener(this, sensorGyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, sensorAccelerometer);
        sensorManager.unregisterListener(this, sensorGyroscope);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        LocalDateTime observationTime = LocalDateTime.now();
        if (collecting) {
            int sensorType = event.sensor.getType();
            switch (sensorType) {
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    dataCollector.addAccData(observationTime, event.values);
                    Log.d("COLLECTING", "onSensorChanged: " + Arrays.toString(event.values));
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    dataCollector.addGyroData(observationTime, event.values);
                    Log.d("COLLECTING", "onSensorChanged: " + Arrays.toString(event.values));
                    break;
            }

            if (dataCollector.isDataCollected()) {
                updateMovementType();
                updateTextView();
                dataCollector.clearSensorData();
            }
        }
    }

    private void updateMovementType() {
        SingleRowData singleRowData = new SingleRowData(dataCollector);
        movementType = movementClassifierModel.predictMovementType(singleRowData);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void updateTextView() {
        tvMovementType.setText(movementType.getType());
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
