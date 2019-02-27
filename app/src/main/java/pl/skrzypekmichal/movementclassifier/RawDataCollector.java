package pl.skrzypekmichal.movementclassifier;

import java.util.ArrayList;
import java.util.List;

public class RawDataCollector {

    public static final int WINDOW_SAMPLES = 24; //stands for ~2 sec. using delay of SENSOR_DELAY_NORMAL
    private List<Float> accX;
    private List<Float> accY;
    private List<Float> accZ;
    private List<Float> gyroX;
    private List<Float> gyroY;
    private List<Float> gyroZ;

    public RawDataCollector() {
        accX = new ArrayList<Float>();
        accY = new ArrayList<Float>();
        accZ = new ArrayList<Float>();
        gyroX = new ArrayList<Float>();
        gyroY = new ArrayList<Float>();
        gyroZ = new ArrayList<Float>();
    }

    public void clearSensorData() {
        accX.clear();
        accY.clear();
        accZ.clear();
        gyroX.clear();
        gyroY.clear();
        gyroZ.clear();
    }

    public void addAccData(float... values) {
        accX.add(values[0]);
        accY.add(values[1]);
        accZ.add(values[2]);
    }

    public void addGyroData(float... values) {
        gyroX.add(values[0]);
        gyroY.add(values[1]);
        gyroZ.add(values[2]);
    }

    public boolean isDataCollected() {
        return isEnoughSensorData(accX) && isEnoughSensorData(accY) && isEnoughSensorData(accZ) &&
                isEnoughSensorData(gyroX) && isEnoughSensorData(gyroY) && isEnoughSensorData(gyroZ);
    }

    private boolean isEnoughSensorData(List<Float> sensorData) {
        if (sensorData.size() >= WINDOW_SAMPLES) {
            return true;
        }
        return false;
    }

    public List<Float> getAccX() {
        return accX;
    }

    public List<Float> getAccY() {
        return accY;
    }

    public List<Float> getAccZ() {
        return accZ;
    }

    public List<Float> getGyroX() {
        return gyroX;
    }

    public List<Float> getGyroY() {
        return gyroY;
    }

    public List<Float> getGyroZ() {
        return gyroZ;
    }
}