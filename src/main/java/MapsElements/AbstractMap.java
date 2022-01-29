package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public abstract class AbstractMap implements IWorldMap {
    protected static Image grassTile;
    protected static Image sandTile;
    protected static int size = 35;
    protected static IMapChangeObserver MapChangeObserver;
    public static final int width = 30;
    public static final int height = 20;
    public static final Vector2d lowerLeft = new Vector2d(0, 0);
    public static final Vector2d upperRight = new Vector2d(width - 1, height - 1);
    public final Group[][] nodes = new Group[height][width];
    public final GridPane grid = new GridPane();
    public static HashMap<String, AbstractMap> maps = new HashMap<>();

    static {
        try {
            grassTile = new Image(new FileInputStream("src/main/resources/grassTile.png"), size, size, false, false);
            sandTile = new Image(new FileInputStream("src/main/resources/sandTile.png"), size, size, false, false);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public boolean canMoveTo(Vector2d position){
        return position.precedes(upperRight) && position.follows(lowerLeft);
    }

    public static void addObserver(IMapChangeObserver observer){
        MapChangeObserver = observer;
    }

    public static void getMapsReferences(AbstractMap firstMap){
        maps.put("Start", firstMap); maps.put("North", new NorthMap()); maps.put("NorthEast", new NorthEastMap());
        maps.put("East", new EastMap()); maps.put("SouthEast", new SouthEastMap()); maps.put("South", new SouthMap());
        maps.put("SouthWest", new SouthWestMap()); maps.put("West", new WestMap()); maps.put("NorthWest", new NorthWestMap());
    }
}
