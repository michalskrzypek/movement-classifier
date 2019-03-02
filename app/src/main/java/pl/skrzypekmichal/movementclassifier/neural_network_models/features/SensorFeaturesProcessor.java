package pl.skrzypekmichal.movementclassifier.neural_network_models.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Takes raw data and creates SensorFeatures out of it
 */
public class SensorFeaturesProcessor {

    public static SensorFeatures calculateSensorFeatures(List<Float> sensorData) {
        double average = calculateAverage(sensorData);
        double median = calculateMedian(sensorData);
        double min = calculateMin(sensorData);
        double max = calculateMax(sensorData);
        double std = calculateStd(sensorData);
        double rms = calculateRootMeanSquare(sensorData);
        double mad = calculateMad(sensorData);

        return new SensorFeatures.Builder()
                .average(average)
                .median(median)
                .max(max)
                .min(min)
                .std(std)
                .rootMeanSquare(rms)
                .mad(mad)
                .build();
    }

    private static double calculateMad(List<Float> sensorData) {
        double median = calculateMedian(sensorData);
        List<Double> medianDeviations = arrayAbsDistance(sensorData, median);
        return calculateMedianOfDoubles(medianDeviations);
    }

    private static List<Double> arrayAbsDistance(List<Float> sensorData, Double median) {
        List<Double> medianDeviations = new ArrayList<>();
        for (int i=0; i<sensorData.size(); i++) {
            medianDeviations.add(Math.abs(sensorData.get(i) - median));
        }
        return medianDeviations;
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

        return Math.sqrt(standardDeviation / (double) length);
    }

    private static double calculateMin(List<Float> sensorData) {
        return Collections.min(sensorData);
    }

    private static double calculateMax(List<Float> sensorData) {
        return Collections.max(sensorData);
    }

    private static double calculateMedian(List<Float> sensorData) {
        List<Float> sortedSensorData = sensorData.subList(0, sensorData.size());
        Collections.sort(sortedSensorData);

        int numbOfSamples = sortedSensorData.size();
        int indexOfMedian = numbOfSamples / 2;
        double median;

        if (numbOfSamples % 2 == 0) {
            median = (sortedSensorData.get(indexOfMedian) + sortedSensorData.get(indexOfMedian - 1)) / (double) 2;
        } else {
            median = sortedSensorData.get(indexOfMedian);
        }
        return median;
    }

    private static double calculateMedianOfDoubles(List<Double> sensorData) {
        List<Double> sortedSensorData = sensorData.subList(0, sensorData.size());
        Collections.sort(sortedSensorData);

        int numbOfSamples = sortedSensorData.size();
        int indexOfMedian = numbOfSamples / 2;
        double median;

        if (numbOfSamples % 2 == 0) {
            median = (sortedSensorData.get(indexOfMedian) + sortedSensorData.get(indexOfMedian - 1)) / (double) 2;
        } else {
            median = sortedSensorData.get(indexOfMedian);
        }
        return median;
    }

    private static double calculateAverage(List<Float> sensorData) {
        double sum = 0;
        for (Float f : sensorData) {
            sum += (double) f;
        }
        return sum / (double) sensorData.size();
    }
}
