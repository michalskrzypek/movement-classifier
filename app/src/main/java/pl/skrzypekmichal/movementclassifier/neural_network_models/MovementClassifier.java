package pl.skrzypekmichal.movementclassifier.neural_network_models;

import android.util.Log;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

import pl.skrzypekmichal.movementclassifier.enums.MovementType;
import pl.skrzypekmichal.movementclassifier.neural_network_models.features.SensorFeatures;

public class MovementClassifier {

    private MultiLayerNetwork model;

    public MovementClassifier(MultiLayerNetwork multiLayerNetwork){
        model = multiLayerNetwork;
    }

    public MultiLayerNetwork getModel() {
        return model;
    }

    public void setModel(MultiLayerNetwork model) {
        this.model = model;
    }

    public MovementType predictMovementType(SingleRowData singleRowData) {
        SensorFeatures accXFeatures = singleRowData.getAccXFeatures();
        SensorFeatures accYFeatures = singleRowData.getAccYFeatures();
        SensorFeatures accZFeatures = singleRowData.getAccZFeatures();
        SensorFeatures gyroXFeatures = singleRowData.getGyroXFeatures();
        SensorFeatures gyroYFeatures = singleRowData.getGyroYFeatures();
        SensorFeatures gyroZFeatures = singleRowData.getGyroZFeatures();

        INDArray accAvg = Nd4j.create(new double[]{accXFeatures.getAverage(), accYFeatures.getAverage(), accZFeatures.getAverage()});
        INDArray accStd = Nd4j.create(new double[]{accXFeatures.getStd(), accYFeatures.getStd(), accZFeatures.getStd()});
        INDArray accMad = Nd4j.create(new double[]{accXFeatures.getMad(), accYFeatures.getMad(), accZFeatures.getMad()});
        INDArray accMax = Nd4j.create(new double[]{accXFeatures.getMax(), accYFeatures.getMax(), accZFeatures.getMax()});
        INDArray accMin = Nd4j.create(new double[]{accXFeatures.getMin(), accYFeatures.getMin(), accZFeatures.getMin()});

        INDArray gyroAvg = Nd4j.create(new double[]{gyroXFeatures.getAverage(), gyroYFeatures.getAverage(), gyroZFeatures.getAverage()});
        INDArray gyroStd = Nd4j.create(new double[]{gyroXFeatures.getStd(), gyroYFeatures.getStd(), gyroZFeatures.getStd()});
        INDArray gyroMad = Nd4j.create(new double[]{gyroXFeatures.getMad(), gyroYFeatures.getMad(), gyroZFeatures.getMad()});
        INDArray gyroMax = Nd4j.create(new double[]{gyroXFeatures.getMax(), gyroYFeatures.getMax(), gyroZFeatures.getMax()});
        INDArray gyroMin = Nd4j.create(new double[]{gyroXFeatures.getMin(), gyroYFeatures.getMin(), gyroZFeatures.getMin()});

        INDArray features = Nd4j.hstack(accAvg, accStd, accMad, accMax, accMin, gyroAvg, gyroStd, gyroMad, gyroMax, gyroMin);

        Log.d("FEATURES", "predictMovementType: " + features);

        //TODO Using MovementType enum return the movement type
        int[] movementType = model.predict(features);
        Log.d("MOVEMENT", "predictMovementType: " + Arrays.toString(movementType));
        return MovementType.WALKING;

    }
}
