package MapsElements;

public enum MoveDirection {
    NORTH,
    WEST,
    EAST,
    SOUTH,
    NORTH_EAST,
    NORTH_WEST,
    SOUTH_WEST,
    SOUTH_EAST;

    public String toString(){
        return switch (this) {
            case NORTH -> "North";
            case SOUTH -> "South";
            case WEST -> "West";
            case EAST -> "East";
            case NORTH_EAST -> "NorthEast";
            case SOUTH_EAST -> "SouthEast";
            case NORTH_WEST -> "NorthWest";
            case SOUTH_WEST -> "SouthWest";
        };
    }

    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0, -1);
            case SOUTH -> new Vector2d(0, 1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_WEST -> new Vector2d(-1, 1);
            case NORTH_WEST -> new Vector2d(-1, -1);
            case SOUTH_EAST -> new Vector2d(1, 1);
            case NORTH_EAST -> new Vector2d(1, -1);
        };
    }

    public MoveDirection opposite(){
        return switch (this) {
            case NORTH -> SOUTH;
            case WEST -> EAST;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case NORTH_EAST -> SOUTH_WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case NORTH_WEST -> SOUTH_EAST;
            case SOUTH_WEST -> NORTH_EAST;
        };
    }
}
