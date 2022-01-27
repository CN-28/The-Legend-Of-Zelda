package MapsElements;

public abstract class AbstractMap {
    public static final int width = 30;
    public static final int height = 20;
    public static final Vector2d lowerLeft = new Vector2d(0, 0);
    public static final Vector2d upperRight = new Vector2d(width - 1, height - 1);

    public static boolean canMoveTo(Vector2d position){
        return position.precedes(upperRight) && position.follows(lowerLeft);
    }
}
