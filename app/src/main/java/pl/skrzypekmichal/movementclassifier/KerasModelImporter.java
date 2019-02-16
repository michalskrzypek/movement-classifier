package pl.skrzypekmichal.movementclassifier;

import android.content.Context;
import android.content.res.AssetManager;

import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.IOException;
import java.io.InputStream;

public class KerasModelImporter {

    private KerasModelImporter() {
    }

    public static MultiLayerNetwork importModel(Context context, String fileName) {
        AssetManager am = context.getAssets();
        InputStream inputStreamForKerasModel = null;
        MultiLayerNetwork model = null;
        try {
            inputStreamForKerasModel = am.open(fileName);
            model = KerasModelImport.importKerasSequentialModelAndWeights(inputStreamForKerasModel);
        } catch (IOException | UnsupportedKerasConfigurationException | InvalidKerasConfigurationException e) {
            e.printStackTrace();
        }
        return model;
    }

}
