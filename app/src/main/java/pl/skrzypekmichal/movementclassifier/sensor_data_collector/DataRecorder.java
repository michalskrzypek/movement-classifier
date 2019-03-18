package pl.skrzypekmichal.movementclassifier.sensor_data_collector;

import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVWriter;

import org.joda.time.LocalDateTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.skrzypekmichal.movementclassifier.RawDataCollector;
import pl.skrzypekmichal.movementclassifier.enums.MovementType;
import pl.skrzypekmichal.movementclassifier.neural_network_models.SingleRowData;

public class DataRecorder {

    public static final String BASE_FILE_FORMAT = ".csv";
    private static final String[] HEADER_RAW_DATA = "username#date#time#acc_x#acc_y#acc_z#gyro_x#gyro_y#gyro_z#movement_type".split("#");
    private static final String[] HEADER_PROCESSED_DATA = ("username#" +
            "acc_x_avg#acc_x_median#acc_x_min#acc_x_max#acc_x_std#acc_x_rms#acc_x_mad#" +
            "acc_y_avg#acc_y_median#acc_y_min#acc_y_max#acc_y_std#acc_y_rms#acc_y_mad#" +
            "acc_z_avg#acc_z_median#acc_z_min#acc_z_max#acc_z_std#acc_z_rms#acc_z_mad#" +
            "gyro_x_avg#gyro_x_median#gyro_x_min#gyro_x_max#gyro_x_std#gyro_x_rms#gyro_x_mad#" +
            "gyro_y_avg#gyro_y_median#gyro_y_min#gyro_y_max#gyro_y_std#gyro_y_rms#gyro_y_mad#" +
            "gyro_z_avg#gyro_z_median#gyro_z_min#gyro_z_max#gyro_z_std#gyro_z_rms#gyro_z_mad#" +
            "movement_type").split("#");

    public void saveRawData(String username, RawDataCollector rawDataCollector, List<LocalDateTime> timestamps, MovementType movementType) {
        List<String[]> rows = getRawDataAsRows(username, rawDataCollector, timestamps, movementType.getIndex());
        saveData(HEADER_RAW_DATA, username, rows, false);
    }

    public void saveProcessedData(String username, List<SingleRowData> processedData, MovementType movementType) {
        List<String[]> rows = getProcessedDataAsRows(username, processedData, movementType.getIndex());
        saveData(HEADER_PROCESSED_DATA, username, rows, true);
    }

    private void saveData(String[] header, String username, List<String[]> rows, boolean isDataProcessed) {
        if (isExternalStorageWritable()) {
            File f;
            FileWriter fileWriter;
            try {
                f = getFileToSave(username, isDataProcessed);
                fileWriter = new FileWriter(f, false);
                CSVWriter writer = new CSVWriter(fileWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
                writer.writeNext(header);
                writer.writeAll(rows);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("UWAGA!", "NIE MOGE ZAPISAĆ");
        }
    }

    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private File getFileToSave(String username, boolean isDataProcessed) {
        return new File(getPublicAlbumStorageDir(), getFileName(username, isDataProcessed));
    }

    private File getPublicAlbumStorageDir() {
        // Get the directory for the user's public documents directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "sensor_data");
        if (!file.mkdirs()) {
            file.mkdir();
        }
        return file;
    }

    private String getFileName(String username, boolean isDataProcessed) {
        String currentDate = LocalDateTime.now().toString("dd-MM-yyyy_HH:mm");
        String fileName = "";

        if(isDataProcessed){
            fileName = "PROCESSED_" + currentDate + "_" + username + BASE_FILE_FORMAT;
        } else {
            fileName = currentDate + "_" + username + BASE_FILE_FORMAT;
        }

        return fileName;
    }

    private List<String[]> getRawDataAsRows(String username, RawDataCollector rawDataCollector, List<LocalDateTime> timestamps, int movementType) {
        List<String[]> rows = new ArrayList<>();
        int totalRowsOfData = timestamps.size();

        for (int i = 0; i < totalRowsOfData; i++) {
            LocalDateTime timestamp = timestamps.get(i);
            String day = timestamp.toString("yyyy-MM-dd");
            String time = timestamp.toLocalTime().toString();
            String accX = "";
            String accY = "";
            String accZ = "";
            String gyroX = "";
            String gyroY = "";
            String gyroZ = "";
            String label = String.valueOf(movementType);
            String labelCode = String.valueOf(movementType);

            if (rawDataCollector.getAccX().containsKey(timestamp)) {
                accX = String.valueOf(rawDataCollector.getAccX().get(timestamp));
                accY = String.valueOf(rawDataCollector.getAccY().get(timestamp));
                accZ = String.valueOf(rawDataCollector.getAccZ().get(timestamp));
            }

            if (rawDataCollector.getGyroX().containsKey(timestamp)) {
                gyroX = String.valueOf(rawDataCollector.getGyroX().get(timestamp));
                gyroY = String.valueOf(rawDataCollector.getGyroY().get(timestamp));
                gyroZ = String.valueOf(rawDataCollector.getGyroZ().get(timestamp));
            }

            String[] row = {username, timestamp.toString(), day, time, accX, accY, accZ, gyroX, gyroY, gyroZ, label};
            rows.add(row);
        }

        return rows;
    }

    private List<String[]> getProcessedDataAsRows(String username, List<SingleRowData> processedData, int index) {
        List<String[]> rows = new ArrayList<>();

        for (int row = 0; row < processedData.size(); row++) {
            SingleRowData singleRowData = processedData.get(row);
            String[] accXData = (singleRowData.getAccXFeatures().getFeaturesAsArray());
            String[] accYData = (singleRowData.getAccYFeatures().getFeaturesAsArray());
            String[] accZData = (singleRowData.getAccZFeatures().getFeaturesAsArray());
            String[] gyroXData = (singleRowData.getGyroXFeatures().getFeaturesAsArray());
            String[] gyroYData = (singleRowData.getGyroYFeatures().getFeaturesAsArray());
            String[] gyroZData = (singleRowData.getGyroZFeatures().getFeaturesAsArray());

            List<String> featureList = new ArrayList<>();
            featureList.add(username);
            featureList.addAll(Arrays.asList(accXData));
            featureList.addAll(Arrays.asList(accYData));
            featureList.addAll(Arrays.asList(accZData));
            featureList.addAll(Arrays.asList(gyroXData));
            featureList.addAll(Arrays.asList(gyroYData));
            featureList.addAll(Arrays.asList(gyroZData));
            featureList.add(String.valueOf(index));

            String[] singleRow = featureList.toArray(new String[featureList.size()]);
            rows.add(singleRow);
        }

        return rows;
    }
}
