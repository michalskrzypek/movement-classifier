package pl.skrzypekmichal.movementclassifier.neural_network_models;

import java.util.List;

import pl.skrzypekmichal.movementclassifier.RawDataCollector;
import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeatures;
import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeaturesProcessor;
import pl.skrzypekmichal.movementclassifier.sensor_data_collector.RawDataProcessor;

public class SingleRowData {

    private SensorFeatures accXFeatures;
    private SensorFeatures accYFeatures;
    private SensorFeatures accZFeatures;
    private SensorFeatures gyroXFeatures;
    private SensorFeatures gyroYFeatures;
    private SensorFeatures gyroZFeatures;

    public SingleRowData(RawDataCollector rawDataCollector){
        RawDataProcessor rawDataProcessor = new RawDataProcessor(rawDataCollector);
        List<Float> accXWindowData = rawDataProcessor.getFirstWindowData(rawDataCollector.getAccX());
        List<Float> accYWindowData = rawDataProcessor.getFirstWindowData(rawDataCollector.getAccY());
        List<Float> accZWindowData = rawDataProcessor.getFirstWindowData(rawDataCollector.getAccZ());
        List<Float> gyroXWindowData = rawDataProcessor.getFirstWindowData(rawDataCollector.getGyroX());
        List<Float> gyroYWindowData = rawDataProcessor.getFirstWindowData(rawDataCollector.getGyroY());
        List<Float> gyroZWindowData = rawDataProcessor.getFirstWindowData(rawDataCollector.getGyroZ());

        accXFeatures = SensorFeaturesProcessor.calculateSensorFeatures(accXWindowData);
        accYFeatures = SensorFeaturesProcessor.calculateSensorFeatures(accYWindowData);
        accZFeatures = SensorFeaturesProcessor.calculateSensorFeatures(accZWindowData);
        gyroXFeatures = SensorFeaturesProcessor.calculateSensorFeatures(gyroXWindowData);
        gyroYFeatures= SensorFeaturesProcessor.calculateSensorFeatures(gyroYWindowData);
        gyroZFeatures = SensorFeaturesProcessor.calculateSensorFeatures(gyroZWindowData);
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
