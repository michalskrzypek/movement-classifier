package pl.skrzypekmichal.movementclassifier.sensor_data_collector;

import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import pl.skrzypekmichal.movementclassifier.enums.MovementType;

public class DataRecorder {

    public static final String BASE_FILE_FORMAT = ".csv";
    private static final String[] header = "username#date#time#acc_x#acc_y#acc_z#gyro_x#gyro_y#gyro_z#movement_type".split("#");

    public void saveData(String username,  List<LocalDateTime> timestamps, List<Float[]> sensorData, MovementType movementType) {
        if (isExternalStorageWritable()) {
            File f;
            FileWriter fileWriter;
            try {
                f = getFileToSave(username);
                fileWriter = new FileWriter(f, false);
                CSVWriter writer = new CSVWriter(fileWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
                writer.writeNext(header);
                List<String[]> rows = getDataAsRows(username, timestamps, sensorData, movementType.getIndex());
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
        String currentDate = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm").format(LocalDateTime.now());
        String fileName = currentDate + "_" + username + BASE_FILE_FORMAT;
        return fileName;
    }

    private List<String[]> getDataAsRows(String username, List<LocalDateTime> timestamps, List<Float[]> sensorData, int movementType){
        List<String[]> rows = new ArrayList<>();
        for (int i = 0; i<sensorData.size(); i++){
            String day = timestamps.get(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = timestamps.get(i).toLocalTime().toString();
            String accX = String.valueOf(sensorData.get(i)[0]);
            String accY = String.valueOf(sensorData.get(i)[1]);
            String accZ = String.valueOf(sensorData.get(i)[2]);
            String label = String.valueOf(movementType);
            String[] row = {username, day, time, accX, accY, accZ, label};
            rows.add(row);
        }
        return rows;
    }
}
