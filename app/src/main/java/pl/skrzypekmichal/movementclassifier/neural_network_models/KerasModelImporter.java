package pl.skrzypekmichal.movementclassifier.neural_network_models;

import android.content.Context;
import android.content.res.AssetManager;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;

import java.io.IOException;
import java.io.InputStream;

public class KerasModelImporter {

    private KerasModelImporter() {
    }

    public static MultiLayerNetwork importModel(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream modelStream = null;
        MultiLayerNetwork restoredModel = null;

        try {
            modelStream = assetManager.open(fileName);
            restoredModel = ModelSerializer.restoreMultiLayerNetwork(modelStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restoredModel;
    }
}
