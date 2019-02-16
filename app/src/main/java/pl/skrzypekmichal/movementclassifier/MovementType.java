package pl.skrzypekmichal.movementclassifier;

public enum MovementType {

    STANDING(0, "Stanie"), WALKING(1, "Chód"), RUNNING(2, "Bieg"), CYCLING(3, "Jazda rowerem"), TRAM(4, "Jazda tramwajem");

    private int index;
    private String type;

    MovementType(int index, String type){
        this.index = index;
        this.type = type;
    }

    public int getIndex(){
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
