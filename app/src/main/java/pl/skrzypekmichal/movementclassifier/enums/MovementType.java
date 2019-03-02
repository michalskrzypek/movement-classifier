package pl.skrzypekmichal.movementclassifier.enums;

public enum MovementType {

    LAYING(0, "Leżenie"), SITTING(1, "Siedzenie"), STANDING(2, "Stanie"), WALKING(3, "Chód"), WALKING_DOWNSTAIRS(4, "Schody w dół"), WALKING_UPSTAIRS(5, "Schody w górę");
    /*RUNNING(2, "Bieg"), CYCLING(3, "Jazda rowerem"), TRAM(4, "Jazda tramwajem");*/

    private int index;
    private String type;

    MovementType(int index, String type) {
        this.index = index;
        this.type = type;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
