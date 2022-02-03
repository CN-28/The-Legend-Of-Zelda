package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.LinkedHashMap;

public class EastMap extends AbstractMap {
    public static final Vector2d leftBottomPassageBorder = new Vector2d(-1, 2 * height/5);
    public static final Vector2d leftUpperPassageBorder = new Vector2d(-1, 3 * height/5 - 1);
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 1, -1);
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 1, height);
    public static final LinkedHashMap<Integer, LinkedHashMap<Integer, Octorok>> octoroks = new LinkedHashMap<>();

    public EastMap() {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                if (j < 7 && i >= 2 * height/5 && i < 3 * height/5 || j > 0 && j < 6 && (i == 2 * height/5 - 1 || i == 3 * height/5)
                || j > 1 && j < 5 && (i == 2 * height/5 - 2 || i == 3 * height/5 + 1) || j == 7 && i >= 9 && i <= 10)
                    this.nodes[i][j].getChildren().add(new ImageView(pathTile));
                else if (i == 3 * height/5 + j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperRightCornerTile));
                else if (i == 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierBottomRightCornerTile));
                else if (i > 3 * height/5 + j || i < 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierTile));
                else if (i == height - 1 && (j > 7 && j < 10 || j > 19 && j < 24) || i == 0 && (j > 7 & j < 12 || j > 17 && j < 24))
                    this.nodes[i][j].getChildren().add(new ImageView(sandSphereTile));
                else if (j < width - 6)
                    this.nodes[i][j].getChildren().add(new ImageView(sandTile));
                else if (j == width - 6)
                    this.nodes[i][j].getChildren().add(new ImageView(waterEdgeTile));
                else
                    this.nodes[i][j].getChildren().add(new ImageView(waterTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }
        addTreeWithHiddenCave(2, 7);
        addTree(18, 10);
        addTree(18, 18);
        for (int i = 5; i < 14; i += 4){
            for (int j = 12; j < 21; j += 4)
                addBlocker(i, j);
        }

        for (int i = 6; i < 16; i += 3){
            octoroks.put(i, new LinkedHashMap<>());
            for (int j = 6; j < 16; j += 3)
                octoroks.get(i).put(j, new Octorok(i, j));
        }
    }

    public void addTreeWithHiddenCave(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(upperLeftSandTreeTile));
        this.nodes[i + 1][j].getChildren().add(new ImageView(bottomLeftSandTreeTile));
        this.nodes[i + 1][j + 1].getChildren().add(new ImageView(bottomMiddleSandTreeTile));
        this.nodes[i][j + 1].getChildren().add(new ImageView(upperMiddleSandTreeTile));
        this.nodes[i][j + 2].getChildren().add(new ImageView(upperRightSandTreeTile));
        this.nodes[i + 1][j + 2].getChildren().add(new ImageView(bottomRightSandTreeTile));
    }

    public void addTree(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(upperLeftSandTreeTile));
        this.nodes[i + 1][j].getChildren().add(new ImageView(bottomLeftSandTreeTile));
        this.nodes[i][j + 1].getChildren().add(new ImageView(upperRightSandTreeTile));
        this.nodes[i + 1][j + 1].getChildren().add(new ImageView(bottomRightSandTreeTile));
    }

    public void addBlocker(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(sandBlockerTile));
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(leftBottomPassageBorder) && position.precedes(leftUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("Start"));
        else if (position.follows(upperLeftPassageBorder) && position.precedes(upperRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("NorthEast"));
        else if (position.follows(bottomLeftPassageBorder) && position.precedes(bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("SouthEast"));

        return false;
    }
}
