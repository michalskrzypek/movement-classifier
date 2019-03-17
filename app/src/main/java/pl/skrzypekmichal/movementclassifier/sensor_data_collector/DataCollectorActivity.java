package pl.skrzypekmichal.movementclassifier.sensor_data_collector;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pl.skrzypekmichal.movementclassifier.R;
import pl.skrzypekmichal.movementclassifier.HomeActivity;
import pl.skrzypekmichal.movementclassifier.enums.MovementType;

public class DataCollectorActivity extends AppCompatActivity implements SensorEventListener {

    public MovementType chosenMovement = MovementType.WALKING;
    private String username;
    public boolean collecting;

    private EditText et_username;
    private Button btnRecord;
    private Spinner spWalkType;
    private TextView tvSensors;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorGyroscope;
    private DataRecorder dataRecorder;

    private List<Float[]> accData;
    private List<Float[]> gyroData;
    private List<LocalDateTime> timestamps;
    private List<Sensor> registeredSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collector);
        initializeView();
        initializeSensors();
        initializeDrawerMenu();
        initializeNavigation();

        accData = new ArrayList<>();
        timestamps = new ArrayList<>();
        registeredSensors = new ArrayList<>();
        dataRecorder = new DataRecorder();
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

    private void initializeView() {
        et_username = findViewById(R.id.edit_text_username);
        et_username.addTextChangedListener(usernameTextWatcher);

        btnRecord = findViewById(R.id.button_record);
        btnRecord.setOnClickListener(v -> {
                    collecting = !collecting;
                    if (collecting) {
                        startCollecting();
                    } else {
                        stopRecording();
                    }
                }
        );

        spWalkType = findViewById(R.id.spinner_walk);
        ArrayAdapter<MovementType> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MovementType.values());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWalkType.setAdapter(spinnerAdapter);
        spWalkType.setOnItemSelectedListener(new SpinnerListener());

        tvSensors = findViewById(R.id.sensor_list);
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
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
        }
        return true;
    }


    private void startCollecting() {
        btnRecord.setText(R.string.stop_button);
        btnRecord.setBackgroundColor(ContextCompat.getColor(this, R.color.btn_stop));
    }

    private void stopRecording() {
        saveData();
        makeToast("Data Saved!", Toast.LENGTH_SHORT);
        clearSensorData();
        btnRecord.setText(R.string.start_button);
        btnRecord.setBackgroundColor(ContextCompat.getColor(this, R.color.btn_start));
    }

    private void saveData() {
        dataRecorder.saveData(username, timestamps, accData, chosenMovement);
    }

    private void clearSensorData() {
        accData.clear();
        gyroData.clear();
        timestamps.clear();
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

            registeredSensors.add(sensorAccelerometer);
        }
        if (sensorGyroscope != null) {
            sensorManager.registerListener(this, sensorGyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);

            registeredSensors.add(sensorGyroscope);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, sensorAccelerometer);
        sensorManager.unregisterListener(this, sensorGyroscope);

        registeredSensors.remove(sensorAccelerometer);
        registeredSensors.remove(sensorGyroscope);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvSensors.setText(getRegisteredSensors());
    }

    private String getRegisteredSensors() {
        StringBuilder sb = new StringBuilder();
        for (Sensor sensor : registeredSensors) {
            sb.append(sensor.getName());
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (collecting) {
            int sensorType = event.sensor.getType();
            switch (sensorType) {
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    Float[] record = {event.values[0], event.values[1], event.values[2]};
                    accData.add(record);
                    timestamps.add(LocalDateTime.now());
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            chosenMovement = (MovementType) parent.getItemAtPosition(position);
            Toast.makeText(parent.getContext(), "Wybrano: " + chosenMovement.getType().toLowerCase(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private TextWatcher usernameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            username = et_username.getText().toString().trim();

            if (username.length() < 3) {
                btnRecord.setEnabled(false);
            } else {
                btnRecord.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
