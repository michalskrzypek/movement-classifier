package pl.skrzypekmichal.movementclassifier;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.skrzypekmichal.movementclassifier.enums.MovementType;
import pl.skrzypekmichal.movementclassifier.neural_network_models.KerasModelImporter;
import pl.skrzypekmichal.movementclassifier.neural_network_models.MovementClassifierModel;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public MovementType movementType = MovementType.STANDING;
    private TextView tvMovementType;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorGyroscope;
    private DataCollector dataCollector;
    private MovementClassifierModel movementClassifierModel;
    private List<Sensor> registeredSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        initializeSensors();
        initializeNeuralNetworkModel();
        dataCollector = new DataCollector();
        registeredSensors = new ArrayList<>();
    }

    private void initializeView() {
        tvMovementType = findViewById(R.id.tv_movement_type);
        tvMovementType.setText(movementType.getType());
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    private void initializeNeuralNetworkModel(){
        movementClassifierModel = new MovementClassifierModel();
        movementClassifierModel.setModel(KerasModelImporter.importModel(this, "movement_classification_model.h5"));
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
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_LINEAR_ACCELERATION:
                dataCollector.addAccData(event.values);
                break;
            case Sensor.TYPE_GYROSCOPE:
                dataCollector.addGyroData(event.values);
                break;
        }

        if (dataCollector.isDataCollected()) {
            updateMovementType();
            updateTextView();
            dataCollector.clearSensorData();
        }
    }

    private void updateMovementType() {
        movementType = movementClassifierModel.determineMovementType();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void updateTextView() {
        tvMovementType.setText(movementType.getType());
    }
}
