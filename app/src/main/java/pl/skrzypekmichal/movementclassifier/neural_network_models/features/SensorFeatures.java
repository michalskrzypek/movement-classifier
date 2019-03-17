package pl.skrzypekmichal.movementclassifier.neural_network_models.features;

public class SensorFeatures {

    private double median;
    private double average;
    private double min;
    private double max;
    private double std;
    private double rootMeanSquare;
    private double mad; //median absolute deviation

    private SensorFeatures(Builder builder) {
        this.median = builder.median;
        this.average = builder.average;
        this.min = builder.min;
        this.max = builder.max;
        this.std = builder.std;
        this.rootMeanSquare = builder.rootMeanSquare;
        this.mad = builder.mad;
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

    public double getRootMeanSquare() {
        return rootMeanSquare;
    }

    public void setRootMeanSquare(double rootMeanSquare) {
        this.rootMeanSquare = rootMeanSquare;
    }

    public double getMad() {
        return mad;
    }

    public void setMad(double mad) {
        this.mad = mad;
    }

    public static class Builder {

        private double median;
        private double average;
        private double min;
        private double max;
        private double std;
        private double rootMeanSquare;
        private double mad;

        public SensorFeatures build() {
            return new SensorFeatures(this);
        }

        public Builder median(double median) {
            this.median = median;
            return this;
        }

        public Builder average(double average) {
            this.average = average;
            return this;
        }

        public Builder min(double min) {
            this.min = min;
            return this;
        }

        public Builder max(double max) {
            this.max = max;
            return this;
        }

        public Builder std(double std) {
            this.std = std;
            return this;
        }

        public Builder rootMeanSquare(double rootMeanSquare){
            this.rootMeanSquare = rootMeanSquare;
            return this;
        }

        public Builder mad(double mad){
            this.mad = mad;
            return this;
        }
    }
}
