package pl.skrzypekmichal.movementclassifier.sensor_data_collector;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pl.skrzypekmichal.movementclassifier.RawDataCollector;
import pl.skrzypekmichal.movementclassifier.neural_network_models.SingleRowData;
import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeatures;
import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeaturesProcessor;

import static pl.skrzypekmichal.movementclassifier.RawDataCollector.WINDOW_WIDTH_IN_SECONDS;

public class RawDataProcessor {

    private RawDataCollector rawDataCollector;

    public RawDataProcessor(RawDataCollector rawDataCollector) {
        this.rawDataCollector = rawDataCollector;
    }

    public List<SingleRowData> getProcessedData() {
        List<SingleRowData> processedDataRows = new ArrayList<>();

        while (!rawDataCollector.isEmpty()) {
            SensorFeatures accXFeatures = SensorFeaturesProcessor.calculateSensorFeatures(getNextWindowData(rawDataCollector.getAccX()));
            SensorFeatures accYFeatures = SensorFeaturesProcessor.calculateSensorFeatures(getNextWindowData(rawDataCollector.getAccY()));
            SensorFeatures accZFeatures = SensorFeaturesProcessor.calculateSensorFeatures(getNextWindowData(rawDataCollector.getAccZ()));
            SensorFeatures gyroXFeatures = SensorFeaturesProcessor.calculateSensorFeatures(getNextWindowData(rawDataCollector.getGyroX()));
            SensorFeatures gyroYFeatures = SensorFeaturesProcessor.calculateSensorFeatures(getNextWindowData(rawDataCollector.getGyroY()));
            SensorFeatures gyroZFeatures = SensorFeaturesProcessor.calculateSensorFeatures(getNextWindowData(rawDataCollector.getGyroZ()));
            SingleRowData singleRowData = new SingleRowData(accXFeatures, accYFeatures, accZFeatures, gyroXFeatures, gyroYFeatures, gyroZFeatures);
            processedDataRows.add(singleRowData);
        }

        return processedDataRows;
    }


    public List<Float> getFirstWindowData(Map<LocalDateTime, Float> sensorData) {
        List<Float> window = new ArrayList<>();

        LocalDateTime firstObservationTime = Collections.min(sensorData.keySet());
        int firstSecondOfWindow = firstObservationTime.getSecondOfMinute();

        List<LocalDateTime> timestamps = sensorData.keySet().stream()
                .filter(ldt -> ldt.getSecondOfMinute() < (firstSecondOfWindow + WINDOW_WIDTH_IN_SECONDS))
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < timestamps.size(); i++) {
            window.add(sensorData.get(timestamps.get(i)));
        }
        return window;
    }

    private List<Float> getNextWindowData(Map<LocalDateTime, Float> sensorData){
        List<Float> window = new ArrayList<>();

        LocalDateTime firstObservationTime = Collections.min(sensorData.keySet());
        int firstSecondOfWindow = firstObservationTime.getSecondOfMinute();

        List<LocalDateTime> timestamps = sensorData.keySet().stream()
                .filter(ldt -> ldt.getSecondOfMinute() < (firstSecondOfWindow + WINDOW_WIDTH_IN_SECONDS))
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < timestamps.size(); i++) {
            window.add(sensorData.get(timestamps.get(i)));
            sensorData.remove(timestamps.get(i));
        }

        return window;
    }

    public void removeFirstWindowData(Map<LocalDateTime, Float> sensorData){
        LocalDateTime firstObservationTime = Collections.min(sensorData.keySet());
        int secondOfWindow = firstObservationTime.getSecondOfMinute();

        List<LocalDateTime> timestamps = sensorData.keySet().stream()
                .filter(ldt -> ldt.getSecondOfMinute() < (secondOfWindow + WINDOW_WIDTH_IN_SECONDS))
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < timestamps.size(); i++) {
            sensorData.remove(timestamps.get(i));
        }
    }
}