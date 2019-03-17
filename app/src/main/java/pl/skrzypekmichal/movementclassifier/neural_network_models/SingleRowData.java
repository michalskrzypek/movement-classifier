package pl.skrzypekmichal.movementclassifier.neural_network_models;

import org.joda.time.LocalDateTime;

import java.util.LinkedHashMap;

import pl.skrzypekmichal.movementclassifier.RawDataCollector;
import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeatures;
import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeaturesProcessor;

public class SingleRowData {

    private SensorFeatures accXFeatures;
    private SensorFeatures accYFeatures;
    private SensorFeatures accZFeatures;
    private SensorFeatures gyroXFeatures;
    private SensorFeatures gyroYFeatures;
    private SensorFeatures gyroZFeatures;

    public SingleRowData(RawDataCollector rawDataCollector){
        accXFeatures = SensorFeaturesProcessor.calculateSensorFeatures(rawDataCollector.getWindowedValues((LinkedHashMap<LocalDateTime, Float>) rawDataCollector.getAccX()));
        accYFeatures = SensorFeaturesProcessor.calculateSensorFeatures(rawDataCollector.getWindowedValues((LinkedHashMap<LocalDateTime, Float>) rawDataCollector.getAccY()));
        accZFeatures = SensorFeaturesProcessor.calculateSensorFeatures(rawDataCollector.getWindowedValues((LinkedHashMap<LocalDateTime, Float>) rawDataCollector.getAccZ()));
        gyroXFeatures = SensorFeaturesProcessor.calculateSensorFeatures(rawDataCollector.getWindowedValues((LinkedHashMap<LocalDateTime, Float>) rawDataCollector.getGyroX()));
        gyroYFeatures= SensorFeaturesProcessor.calculateSensorFeatures(rawDataCollector.getWindowedValues((LinkedHashMap<LocalDateTime, Float>) rawDataCollector.getGyroY()));
        gyroZFeatures = SensorFeaturesProcessor.calculateSensorFeatures(rawDataCollector.getWindowedValues((LinkedHashMap<LocalDateTime, Float>) rawDataCollector.getGyroZ()));
    }

    public SingleRowData(SensorFeatures accXFeatures, SensorFeatures accYFeatures, SensorFeatures accZFeatures,
                         SensorFeatures gyroXFeatures, SensorFeatures gyroYFeatures, SensorFeatures gyroZFeatures) {
        this.accXFeatures = accXFeatures;
        this.accYFeatures = accYFeatures;
        this.accZFeatures = accZFeatures;
        this.gyroXFeatures = gyroXFeatures;
        this.gyroYFeatures = gyroYFeatures;
        this.gyroZFeatures = gyroZFeatures;
    }

    public SensorFeatures getAccXFeatures() {
        return accXFeatures;
    }

    public void setAccXFeatures(SensorFeatures accXFeatures) {
        this.accXFeatures = accXFeatures;
    }

    public SensorFeatures getAccYFeatures() {
        return accYFeatures;
    }

    public void setAccYFeatures(SensorFeatures accYFeatures) {
        this.accYFeatures = accYFeatures;
    }

    public SensorFeatures getAccZFeatures() {
        return accZFeatures;
    }

    public void setAccZFeatures(SensorFeatures accZFeatures) {
        this.accZFeatures = accZFeatures;
    }

    public SensorFeatures getGyroXFeatures() {
        return gyroXFeatures;
    }

    public void setGyroXFeatures(SensorFeatures gyroXFeatures) {
        this.gyroXFeatures = gyroXFeatures;
    }

    public SensorFeatures getGyroYFeatures() {
        return gyroYFeatures;
    }

    public void setGyroYFeatures(SensorFeatures gyroYFeatures) {
        this.gyroYFeatures = gyroYFeatures;
    }

    public SensorFeatures getGyroZFeatures() {
        return gyroZFeatures;
    }

    public void setGyroZFeatures(SensorFeatures gyroZFeatures) {
        this.gyroZFeatures = gyroZFeatures;
    }
}
