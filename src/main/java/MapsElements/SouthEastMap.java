package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class SouthEastMap extends AbstractMap {
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 1, -1);
    public static final Vector2d leftUpperPassageBorder = new Vector2d(-1, 3 * height/5 - 1);
    public static final Vector2d leftBottomPassageBorder = new Vector2d(-1, 2 * height/5);

    public SouthEastMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                if (i >= -j + 18 + height)
                    this.nodes[i][j].getChildren().add(new ImageView(waterTile));
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
