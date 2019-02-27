package pl.skrzypekmichal.movementclassifier.neural_network_models;

import android.content.Context;
import android.net.Uri;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;

import java.io.IOException;

import pl.skrzypekmichal.movementclassifier.R;

public class KerasModelImporter {

    private KerasModelImporter() {
    }

/*    public static MultiLayerNetwork importModel(Context context, String fileName) {
        MultiLayerNetwork restoredModel = null;
        try {
            Uri path = Uri.parse("android.resource://pl.skrzypekmichal.movementclassifier.neural_network_models.features/" + R.raw.);
            restoredModel = ModelSerializer.restoreMultiLayerNetwork(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restoredModel;
    }*/
}
