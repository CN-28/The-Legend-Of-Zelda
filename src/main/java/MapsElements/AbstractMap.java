package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public abstract class AbstractMap implements IWorldMap {
    public static Image grassTile, sandTile, waterTile, waterEdgeTile, pathTile, sandBarrierUpperRightCornerTile, sandBarrierBottomRightCornerTile,
    sandBarrierTile, bottomLeftSandTreeTile, upperLeftSandTreeTile, upperMiddleSandTreeTile, bottomMiddleSandTreeTile, bottomRightSandTreeTile, upperRightSandTreeTile,
    sandBlockerTile, sandSphereTile, upperRightWaterTile, upperRightWaterCornerTile, bottomRightWaterCornerTile, blackTile, sandBarrierUpperLeftCornerTile,
    sandBarrierBottomLeftCornerTile, grassBarrierUpperRightCornerTile, grassBarrierBottomRightCornerTile, grassBarrierTile, grassSphereTile, bottomLeftGrassTreeTile,
    upperLeftGrassTreeTile, upperRightGrassTreeTile, bottomRightGrassTreeTile, grassBlockerTile, grassStatueTile, grassBarrierBottomLeftCornerTile, grayBarrierUpperRightCornerTile,
    grayBarrierTile, grayTile, graySphereTile, grayHiddenCave, grassBarrierUpperLeftCornerTile, grayBarrierBottomRightCornerTile, grayBarrierUpperLeftCornerTile, bottomLeftGrayTreeTile,
    upperLeftGrayTreeTile, upperMiddleGrayTreeTile, bottomMiddleGrayTreeTile, upperRightGrayTreeTile, bottomRightGrayTreeTile;
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
            pathTile = new Image(new FileInputStream("src/main/resources/pathTile.png"), size, size, false, false);
            sandTile = new Image(new FileInputStream("src/main/resources/sandTile.png"), size, size, false, false);
            sandBarrierUpperRightCornerTile = new Image(new FileInputStream("src/main/resources/sandBarrierUpperRightCorner.png"), size, size, false, false);
            sandBarrierBottomRightCornerTile = new Image(new FileInputStream("src/main/resources/sandBarrierBottomRightCorner.png"), size, size, false, false);
            sandBarrierTile = new Image(new FileInputStream("src/main/resources/sandBarrierTile.png"), size, size, false, false);
            waterTile = new Image(new FileInputStream("src/main/resources/waterTile.png"), size, size, false, false);
            waterEdgeTile = new Image(new FileInputStream("src/main/resources/waterEdgeTile.png"), size, size, false, false);
            bottomLeftSandTreeTile = new Image(new FileInputStream("src/main/resources/bottomLeftSandTree.png"), size, size, false, false);
            upperLeftSandTreeTile = new Image(new FileInputStream("src/main/resources/upperLeftSandTree.png"), size, size, false, false);
            upperMiddleSandTreeTile = new Image(new FileInputStream("src/main/resources/upperMiddleSandTree.png"), size, size, false, false);
            bottomMiddleSandTreeTile = new Image(new FileInputStream("src/main/resources/bottomMiddleSandTree.png"), size, size, false, false);
            upperRightSandTreeTile = new Image(new FileInputStream("src/main/resources/upperRightSandTree.png"), size, size, false, false);
            bottomRightSandTreeTile = new Image(new FileInputStream("src/main/resources/bottomRightSandTree.png"), size, size, false, false);
            sandBlockerTile = new Image(new FileInputStream("src/main/resources/sandBlockerTile.png"), size, size, false, false);
            sandSphereTile = new Image(new FileInputStream("src/main/resources/sandSphereTile.png"), size, size, false, false);
            upperRightWaterTile = new Image(new FileInputStream("src/main/resources/upperRightWaterTile.png"), size, size, false, false);
            upperRightWaterCornerTile = new Image(new FileInputStream("src/main/resources/upperRightCornerTile.png"), size, size, false, false);
            bottomRightWaterCornerTile = new Image(new FileInputStream("src/main/resources/bottomRightCornerTile.png"), size, size, false, false);
            blackTile = new Image(new FileInputStream("src/main/resources/blackTile.png"), size, size, false, false);
            sandBarrierUpperLeftCornerTile = new Image(new FileInputStream("src/main/resources/sandBarrierUpperLeftCorner.png"), size, size, false, false);
            sandBarrierBottomLeftCornerTile = new Image(new FileInputStream("src/main/resources/sandBarrierBottomLeftCorner.png"), size, size, false, false);
            grassBarrierUpperRightCornerTile = new Image(new FileInputStream("src/main/resources/grassBarrierUpperRightCorner.png"), size, size, false, false);
            grassBarrierBottomRightCornerTile = new Image(new FileInputStream("src/main/resources/grassBarrierBottomRightCorner.png"), size, size, false, false);
            grassBarrierTile = new Image(new FileInputStream("src/main/resources/grassBarrierTile.png"), size, size, false, false);
            grassSphereTile = new Image(new FileInputStream("src/main/resources/grassSphereTile.png"), size, size, false, false);
            bottomLeftGrassTreeTile = new Image(new FileInputStream("src/main/resources/bottomLeftGrassTree.png"), size, size, false, false);
            upperLeftGrassTreeTile = new Image(new FileInputStream("src/main/resources/upperLeftGrassTree.png"), size, size, false, false);
            upperRightGrassTreeTile = new Image(new FileInputStream("src/main/resources/upperRightGrassTree.png"), size, size, false, false);
            bottomRightGrassTreeTile = new Image(new FileInputStream("src/main/resources/bottomRightGrassTree.png"), size, size, false, false);
            grassBlockerTile = new Image(new FileInputStream("src/main/resources/grassBlockerTile.png"), size, size, false, false);
            grassStatueTile = new Image(new FileInputStream("src/main/resources/grassStatueTile.png"), size, size, false, false);
            grassBarrierBottomLeftCornerTile = new Image(new FileInputStream("src/main/resources/grassBarrierBottomLeftCornerTile.png"), size, size, false, false);
            grayBarrierUpperRightCornerTile = new Image(new FileInputStream("src/main/resources/grayBarrierUpperRightCornerTile.png"), size, size, false, false);
            grayBarrierTile = new Image(new FileInputStream("src/main/resources/grayBarrierTile.png"), size, size, false, false);
            grayTile = new Image(new FileInputStream("src/main/resources/grayTile.png"), size, size, false, false);
            grayHiddenCave = new Image(new FileInputStream("src/main/resources/grayHiddenCave.png"), size, size, false, false);
            graySphereTile = new Image(new FileInputStream("src/main/resources/graySphereTile.png"), size, size, false, false);
            grassBarrierUpperLeftCornerTile = new Image(new FileInputStream("src/main/resources/grassBarrierUpperLeftCornerTile.png"), size, size, false, false);
            grayBarrierBottomRightCornerTile = new Image(new FileInputStream("src/main/resources/grayBarrierBottomRightCornerTile.png"), size, size, false, false);
            grayBarrierUpperLeftCornerTile = new Image(new FileInputStream("src/main/resources/grayBarrierUpperLeftCornerTile.png"), size, size, false, false);
            bottomLeftGrayTreeTile = new Image(new FileInputStream("src/main/resources/bottomLeftGrayTree.png"), size, size, false, false);
            upperLeftGrayTreeTile = new Image(new FileInputStream("src/main/resources/upperLeftGrayTree.png"), size, size, false, false);
            upperMiddleGrayTreeTile = new Image(new FileInputStream("src/main/resources/upperMiddleGrayTree.png"), size, size, false, false);
            bottomMiddleGrayTreeTile = new Image(new FileInputStream("src/main/resources/bottomMiddleGrayTree.png"), size, size, false, false);
            upperRightGrayTreeTile = new Image(new FileInputStream("src/main/resources/upperRightGrayTree.png"), size, size, false, false);
            bottomRightGrayTreeTile = new Image(new FileInputStream("src/main/resources/bottomRightGrayTree.png"), size, size, false, false);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public boolean canMoveTo(Vector2d position){
        return position.precedes(upperRight) && position.follows(lowerLeft);
    }

    public static void addMapChangeObserver(IMapChangeObserver observer){
        MapChangeObserver = observer;
    }

    public static void getMapsReferences(AbstractMap firstMap){
        maps.put("Start", firstMap); maps.put("North", new NorthMap()); maps.put("NorthEast", new NorthEastMap());
        maps.put("East", new EastMap()); maps.put("SouthEast", new SouthEastMap()); maps.put("South", new SouthMap());
        maps.put("SouthWest", new SouthWestMap()); maps.put("West", new WestMap()); maps.put("NorthWest", new NorthWestMap());
    }
}
