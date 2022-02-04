package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.LinkedHashMap;

public class SouthWestMap extends AbstractMap {
    public static final Vector2d rightBottomPassageBorder = new Vector2d(width, 2 * height/5);
    public static final Vector2d rightUpperPassageBorder = new Vector2d(width, 3 * height/5 - 1);
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5 + 1, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 2, -1);
    public static final LinkedHashMap<Integer, LinkedHashMap<Integer, Ghini>> ghinis = new LinkedHashMap<>();

    public SouthWestMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.occupancyMap[i][j] = true;
                if (i == j - 22){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierBottomLeftCornerTile));
                }
                else if (i < j - 22)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
                else if (j == width - 1 && i >= 12 || i == height - 1 && j > 14 || i == 0 && (j >= 6 && j <= 12 || j >= 17)){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassSphereTile));
                }
                else if (i > 3 * height/5 + j - 5 || j < 4)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierTile));
                else if (i == 3 * height/5 + j - 5)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierUpperRightCornerTile));
                else if (i == 6 && j == 4)
                    this.nodes[i][j].getChildren().add(new ImageView(grayHiddenCave));
                else if (i == 3 * height/5 + j - 6 || j == 4 || i == height - 1|| i == 0 && j == 5)
                    this.nodes[i][j].getChildren().add(new ImageView(graySphereTile));
                else if (i == 3 * height/5 + j - 7 || j == 5){
                    this.nodes[i][j].getChildren().add(new ImageView(grayTile));
                    this.occupancyMap[i][j] = false;
                }
                else{
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.occupancyMap[i][j] = false;
                }
                this.grid.add(this.nodes[i][j], j, i);
            }
        }

        Ghini ghini;
        for (int i = 4; i < 14; i += 3){
            ghinis.put(i, new LinkedHashMap<>());
            for (int j = 10; j < 20; j += 3){
                ghini = new Ghini(i, j);
                ghinis.get(i).put(j, ghini);
                nodes[i][j].getChildren().add(ghini.getGhiniAnimation()[0]);
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(rightBottomPassageBorder) && position.precedes(rightUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("South"));
        else if (position.follows(upperLeftPassageBorder) && position.precedes(upperRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("West"));

        return false;
    }
}