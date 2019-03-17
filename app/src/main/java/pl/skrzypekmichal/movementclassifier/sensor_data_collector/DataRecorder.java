package pl.skrzypekmichal.movementclassifier.sensor_data_collector;

import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVWriter;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import pl.skrzypekmichal.movementclassifier.RawDataCollector;
import pl.skrzypekmichal.movementclassifier.enums.MovementType;

public class DataRecorder {

    public static final String BASE_FILE_FORMAT = ".csv";
    private static final String[] HEADER = "username#date#time#acc_x#acc_y#acc_z#gyro_x#gyro_y#gyro_z#movement_type".split("#");

    public void saveRawData(String username, RawDataCollector rawDataCollector, List<LocalDateTime> timestamps, MovementType movementType) {
        if (isExternalStorageWritable()) {
            File f;
            FileWriter fileWriter;
            try {
                f = getFileToSave(username);
                fileWriter = new FileWriter(f, false);
                CSVWriter writer = new CSVWriter(fileWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
                writer.writeNext(HEADER);
                List<String[]> rows = getDataAsRows(username, rawDataCollector, timestamps, movementType.getIndex());
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

    private File getFileToSave(String username){
        return new File(getPublicAlbumStorageDir(), getFileName(username));
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

    private String getFileName(String username){
        String currentDate = LocalDateTime.now().toString("dd-MM-yyyy_HH:mm");
        String fileName = currentDate + "_" + username + BASE_FILE_FORMAT;
        return fileName;
    }

    private List<String[]> getDataAsRows(String username, RawDataCollector rawDataCollector, List<LocalDateTime> timestamps, int movementType){
        List<String[]> rows = new ArrayList<>();
        int totalRowsOfData = timestamps.size();

        for (int i = 0; i<totalRowsOfData; i++){
            LocalDateTime timestamp = timestamps.get(i);
            String label = String.valueOf(movementType);
            String date = timestamps.get(i).toString();
            String day = timestamps.get(i).toString("yyyy-MM-dd");
            String time = timestamps.get(i).toLocalTime().toString();
            String accX = "";
            String accY = "";
            String accZ = "";
            String gyroX = "";
            String gyroY = "";
            String gyroZ = "";

            if(rawDataCollector.getAccX().containsKey(timestamp)){
                accX = String.valueOf(rawDataCollector.getAccX().get(timestamp));
                accY = String.valueOf(rawDataCollector.getAccY().get(timestamp));
                accZ = String.valueOf(rawDataCollector.getAccZ().get(timestamp));
            } else if(rawDataCollector.getGyroX().containsKey(timestamp)){
                gyroX = String.valueOf(rawDataCollector.getGyroX().get(timestamp));
                gyroY = String.valueOf(rawDataCollector.getGyroY().get(timestamp));
                gyroZ = String.valueOf(rawDataCollector.getGyroZ().get(timestamp));
            }

            String[] row = {username, date, day, time, accX, accY, accZ, gyroX, gyroY, gyroZ, label};
            rows.add(row);
        }
        return rows;
    }
}
