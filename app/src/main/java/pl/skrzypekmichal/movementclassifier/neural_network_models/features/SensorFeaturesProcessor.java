package pl.skrzypekmichal.movementclassifier.neural_network_models.features;

import java.util.Collections;
import java.util.List;

import pl.skrzypekmichal.movementclassifier.RawDataCollector;

/**
 * Takes raw data and creates SensorFeatures out of it
 */
public class SensorFeaturesProcessor {

    public static SensorFeatures getSensorFeatures(List<Float> sensorData) {
        double average = calculateAverage(sensorData);
        double median = calculateMedian(sensorData);
        double min = calculateMin(sensorData);
        double max = calculateMax(sensorData);
        double std = calculateStd(sensorData);
        double rms = calculateRootMeanSquare(sensorData);

        return new SensorFeatures.Builder()
                .average(average)
                .median(median)
                .max(max)
                .min(min)
                .std(std)
                .rootMeanSquare(rms)
                .build();
    }

    private static double calculateRootMeanSquare(List<Float> sensorData) {
        double square = 0;
        double mean = 0;
        double root = 0;

        for (int i = 0; i < sensorData.size(); i++) {
            square += Math.pow(sensorData.get(i), 2);
        }

        mean = (square / (double) (sensorData.size()));
        root = Math.sqrt(mean);
        return root;
    }

    private static double calculateStd(List<Float> sensorData) {
        double sum = 0.0, standardDeviation = 0.0;
        int length = sensorData.size();

        for (double num : sensorData) {
            sum += num;
        }

        double mean = sum / length;

        for (double num : sensorData) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    private static double calculateMin(List<Float> sensorData) {
        return Collections.min(sensorData);
    }

    private static double calculateMax(List<Float> sensorData) {
        return Collections.max(sensorData);
    }

    private static double calculateMedian(List<Float> sensorData) {
        int numbOfSamples = RawDataCollector.WINDOW_SAMPLES;
        int indexOfMedian = numbOfSamples / 2;
        double median;

        if (numbOfSamples % 2 == 0) {
            median = (sensorData.get(indexOfMedian) + sensorData.get(indexOfMedian - 1)) / (double) 2;
        } else {
            median = sensorData.get(indexOfMedian);
        }
        return median;
    }

    private static double calculateAverage(List<Float> sensorData) {
        double sum = 0;
        for (Float f : sensorData) {
            sum += f;
        }
        return sum / sensorData.size();
    }
}
