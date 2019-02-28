package pl.skrzypekmichal.movementclassifier;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        accX = new LinkedHashMap<LocalDateTime, Float>();
        accY = new LinkedHashMap<LocalDateTime, Float>();
        accZ = new LinkedHashMap<LocalDateTime, Float>();
        gyroX = new LinkedHashMap<LocalDateTime, Float>();
        gyroY = new LinkedHashMap<LocalDateTime, Float>();
        gyroZ = new LinkedHashMap<LocalDateTime, Float>();
    }

    private void removeLastWindowData(int secondOfCurrentWindow) {

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
        LocalDateTime observationTime = LocalDateTime.now();
        accX.put(observationTime, values[0]);
        accY.put(observationTime, values[1]);
        accZ.put(observationTime, values[2]);
    }

    public void addGyroData(float... values) {
        LocalDateTime observationTime = LocalDateTime.now();
        gyroX.put(observationTime, values[0]);
        gyroY.put(observationTime, values[1]);
        gyroZ.put(observationTime, values[2]);
    }

    //wystarczy że sprawdzimy dane z jednej płaszczyzny z kazdego sensora, bo zawsze dane sa dodawane do wszystkich plaszczyzn równocześnie
    public boolean isDataCollected() {
        return isEnoughSensorData(accX) && isEnoughSensorData(gyroX);
    }

    private boolean isEnoughSensorData(Map<LocalDateTime, Float> sensorData) {
        LocalDateTime firstObservationTime = Collections.min(sensorData.keySet());
        LocalDateTime lastObservationTime = Collections.max(sensorData.keySet());

        long differenceInMilliseconds = lastObservationTime.toDate().getTime() - firstObservationTime.toDate().getTime();
        if (differenceInMilliseconds >= WINDOW_WIDTH_IN_SECONDS * 1000) {
            return true;
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

    public List<Float> getWindowedValues(LinkedHashMap<LocalDateTime, Float> sensorData) {
        List<Float> window = new ArrayList<>();

        LocalDateTime firstObservationTime = sensorData.keySet().iterator().next();
        int secondOfWindow = firstObservationTime.getSecondOfMinute();

        List<LocalDateTime> timestamps = sensorData.keySet().stream()
                .filter(ldt -> ldt.getSecondOfMinute() == secondOfWindow)
                .collect(Collectors.toList());

        Collections.sort(timestamps);
        for (int i = 0; i < timestamps.size(); i++) {
            window.add(sensorData.get(timestamps.get(i)));
        }
        return window;
    }
}