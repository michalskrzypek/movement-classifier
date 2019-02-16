package pl.skrzypekmichal.movementclassifier.neural_network_models.features;

public class SensorFeatures {

    private double median;
    private double average;
    private double min;
    private double max;
    private double std;
    private double mean;
    private double rootMeanSquare;

    public SensorFeatures() {
    }

    public SensorFeatures(double median, double average, double min, double max, double std, double mean, double rootMeanSquare) {
        this.median = median;
        this.average = average;
        this.min = min;
        this.max = max;
        this.std = std;
        this.mean = mean;
        this.rootMeanSquare = rootMeanSquare;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStd() {
        return std;
    }

    public void setStd(double std) {
        this.std = std;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getRootMeanSquare() {
        return rootMeanSquare;
    }

    public void setRootMeanSquare(double rootMeanSquare) {
        this.rootMeanSquare = rootMeanSquare;
    }
}
