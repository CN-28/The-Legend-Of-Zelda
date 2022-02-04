package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.LinkedHashMap;

public class SouthEastMap extends AbstractMap {
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5 + 1, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 2, -1);
    public static final Vector2d leftUpperPassageBorder = new Vector2d(-1, 3 * height/5 - 1);
    public static final Vector2d leftBottomPassageBorder = new Vector2d(-1, 2 * height/5);
    public static final LinkedHashMap<Integer, LinkedHashMap<Integer, Tektite>> tektites = new LinkedHashMap<>();


    public SouthEastMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                if (i >= -j + 18 + height)
                    this.nodes[i][j].getChildren().add(new ImageView(waterTile));
                else if (i == height - 2 && (j >= 7 && j <= 18))
                    this.nodes[i][j].getChildren().add(new ImageView(sandSphereTile));
                else if (i == height - 1 && (j > 7 && j < 18) || i == 0 && (j > 7 & j <= 12 || j >= 17 && j < 24))
                    this.nodes[i][j].getChildren().add(new ImageView(sandSphereTile));
                else if (i == 3 * height/5 + j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperRightCornerTile));
                else if (i == 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierBottomRightCornerTile));
                else if (i > 3 * height/5 + j || i < 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierTile));
                else if (i == -j + 17 + height && j < 24)
                    this.nodes[i][j].getChildren().add(new ImageView(bottomRightWaterCornerTile));
                else if (j < width - 6)
                    this.nodes[i][j].getChildren().add(new ImageView(sandTile));
                else if (j == width - 6 && i <= 15)
                    this.nodes[i][j].getChildren().add(new ImageView(waterEdgeTile));
                else
                    this.nodes[i][j].getChildren().add(new ImageView(waterTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }

        Tektite tektite;
        for (int i = 6; i < 16; i += 3){
            tektites.put(i, new LinkedHashMap<>());
            for (int j = 6; j < 16; j += 3){
                tektite = new Tektite(i, j);
                tektites.get(i).put(j, tektite);
                nodes[i][j].getChildren().add(tektite.getTektiteAnimation()[1]);
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(upperLeftPassageBorder) && position.precedes(upperRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("East"));
        else if (position.follows(leftBottomPassageBorder) && position.precedes(leftUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("South"));

        return false;
    }
}
