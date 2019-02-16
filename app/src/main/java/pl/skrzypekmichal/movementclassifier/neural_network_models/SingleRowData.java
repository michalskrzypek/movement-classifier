package pl.skrzypekmichal.movementclassifier.neural_network_models;

import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeatures;

public class SingleRowData {

    //TODO add statistics aka model features in a single row + getters + setters
    private SensorFeatures accFeatures;
    private SensorFeatures gyroFeatures;

    public SingleRowData(SensorFeatures accFeatures, SensorFeatures gyroFeatures) {
        this.accFeatures = accFeatures;
        this.gyroFeatures = gyroFeatures;
    }

    public SensorFeatures getAccFeatures() {
        return accFeatures;
    }

    public void setAccFeatures(SensorFeatures accFeatures) {
        this.accFeatures = accFeatures;
    }

    public SensorFeatures getGyroFeatures() {
        return gyroFeatures;
    }

    public void setGyroFeatures(SensorFeatures gyroFeatures) {
        this.gyroFeatures = gyroFeatures;
    }
}
