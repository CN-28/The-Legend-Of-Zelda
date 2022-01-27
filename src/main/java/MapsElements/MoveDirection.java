package MapsElements;

public enum MoveDirection {
    NORTH,
    WEST,
    EAST,
    SOUTH;

    public String toString(){
        return switch (this) {
            case NORTH -> "North";
            case SOUTH -> "South";
            case WEST -> "West";
            case EAST -> "East";
        };
    }

    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0, -1);
            case SOUTH -> new Vector2d(0, 1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
        };
    }
}
