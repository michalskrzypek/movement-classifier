package pl.skrzypekmichal.movementclassifier.neural_network_models;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import pl.skrzypekmichal.movementclassifier.enums.MovementType;

public class MovementClassifierModel {

    private MultiLayerNetwork model;

    public MovementClassifierModel(){
    }

    public MovementClassifierModel(MultiLayerNetwork multiLayerNetwork){
        model = multiLayerNetwork;
    }

    public MultiLayerNetwork getModel() {
        return model;
    }

    public void setModel(MultiLayerNetwork model) {
        this.model = model;
    }

    public MovementType predictMovementType(SingleRowData singleRowData) {
        //TODO calculate statistics on collected data
        //TODO Pass the calculated stats to the neural network model
        //TODO Using MovementType enum return the movement type
        return MovementType.WALKING;

    }
}
