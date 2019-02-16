package pl.skrzypekmichal.movementclassifier;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public class MovementClassifierModel {

    private MultiLayerNetwork model;

    public MovementClassifierModel(MultiLayerNetwork multiLayerNetwork){
        model = multiLayerNetwork;
    }

    public MovementType determineMovementType() {
        //TODO calculate statistics on collected data
        //TODO Pass the calculated stats to the neural network model
        //TODO Using MovementType enum return the movement type
        return MovementType.WALKING;

    }
}
