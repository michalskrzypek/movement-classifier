package pl.skrzypekmichal.movementclassifier.enums;

public enum MovementType {

    LAYING(0, "Laying"), SITTING(1, "Sitting"), STANDING(2, "Standing"), WALKING(3, "Walking"),
    WALKING_DOWNSTAIRS(4, "Walking downstairs"), WALKING_UPSTAIRS(5, "Walking upstairs"), UNKNOWN(-1, "Unknown movement");

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

    public static MovementType getByIndex(int index) {
        switch (index) {
            case 0:
                return LAYING;
            case 1:
                return SITTING;
            case 2:
                return STANDING;
            case 3:
                return WALKING;
            case 4:
                return WALKING_DOWNSTAIRS;
            case 5:
                return WALKING_UPSTAIRS;
            default:
                return UNKNOWN;
        }
    }
}
