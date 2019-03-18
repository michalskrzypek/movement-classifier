package pl.skrzypekmichal.movementclassifier;


import org.joda.time.LocalDateTime;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Collects and stores raw sensor data along with timestamp of each collection
 */
public class RawDataCollector {

    public static final int WINDOW_WIDTH_IN_SECONDS = 1;

    private Map<LocalDateTime, Float> accX;
    private Map<LocalDateTime, Float> accY;
    private Map<LocalDateTime, Float> accZ;
    private Map<LocalDateTime, Float> gyroX;
    private Map<LocalDateTime, Float> gyroY;
    private Map<LocalDateTime, Float> gyroZ;

    public RawDataCollector() {
        accX = new LinkedHashMap<>();
        accY = new LinkedHashMap<>();
        accZ = new LinkedHashMap<>();
        gyroX = new LinkedHashMap<>();
        gyroY = new LinkedHashMap<>();
        gyroZ = new LinkedHashMap<>();
    }

    public void clearSensorData() {
        accX.clear();
        accY.clear();
        accZ.clear();
        gyroX.clear();
        gyroY.clear();
        gyroZ.clear();
    }

    public boolean isEmpty(){
        return accX.isEmpty() && accY.isEmpty() && accZ.isEmpty() && gyroX.isEmpty() && gyroY.isEmpty() && gyroX.isEmpty();
    }

    public void addAccData(LocalDateTime observationTime, float... values) {
        accX.put(observationTime, values[0]);
        accY.put(observationTime, values[1]);
        accZ.put(observationTime, values[2]);
    }

    public void addGyroData(LocalDateTime observationTime, float... values) {
        gyroX.put(observationTime, values[0]);
        gyroY.put(observationTime, values[1]);
        gyroZ.put(observationTime, values[2]);
    }

    //wystarczy że sprawdzimy dane z jednej płaszczyzny z kazdego sensora, bo zawsze dane sa dodawane do wszystkich plaszczyzn równocześnie
    public boolean isDataCollected() {
        return isEnoughSensorData(accX) && isEnoughSensorData(gyroX);
    }

    private boolean isEnoughSensorData(Map<LocalDateTime, Float> sensorData) {
        if(!sensorData.isEmpty()){
            LocalDateTime firstObservationTime = Collections.min(sensorData.keySet());
            LocalDateTime lastObservationTime = Collections.max(sensorData.keySet());
            long differenceInMilliseconds = lastObservationTime.toDate().getTime() - firstObservationTime.toDate().getTime();
            if (differenceInMilliseconds >= WINDOW_WIDTH_IN_SECONDS * 1000) {
                return true;
            }
            return false;
        }

        return false;
    }

    public Map<LocalDateTime, Float> getAccX() {
        return accX;
    }

    public Map<LocalDateTime, Float> getAccY() {
        return accY;
    }

    public Map<LocalDateTime, Float> getAccZ() {
        return accZ;
    }

    public Map<LocalDateTime, Float> getGyroX() {
        return gyroX;
    }

    public Map<LocalDateTime, Float> getGyroY() {
        return gyroY;
    }

    public Map<LocalDateTime, Float> getGyroZ() {
        return gyroZ;
    }
}