package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class AbstractMap {
    protected static Image grassTile;
    protected static Image sandTile;
    protected static int size = 30;
    public static final int width = 30;
    public static final int height = 20;
    public static final Vector2d lowerLeft = new Vector2d(0, 0);
    public static final Vector2d upperRight = new Vector2d(width - 1, height - 1);
    public final Group[][] nodes = new Group[height][width];
    public final GridPane grid = new GridPane();

    static {
        try {
            grassTile = new Image(new FileInputStream("src/main/resources/grassTile.png"), size, size, false, false);
            sandTile = new Image(new FileInputStream("src/main/resources/sandTile.png"), size, size, false, false);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean canMoveTo(Vector2d position){
        return position.precedes(upperRight) && position.follows(lowerLeft);
    }
}
