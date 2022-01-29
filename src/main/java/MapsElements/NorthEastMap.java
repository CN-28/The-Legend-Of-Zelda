package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class NorthEastMap extends AbstractMap {
    public static final Vector2d leftBottomPassageBorder = new Vector2d(-1, 2 * height/5);
    public static final Vector2d leftUpperPassageBorder = new Vector2d(-1, 3 * height/5 - 1);
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 1, height);

    public NorthEastMap() {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.nodes[i][j].getChildren().addAll(new ImageView(grassTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(leftBottomPassageBorder) && position.precedes(leftUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("North"));
        else if (position.follows(bottomLeftPassageBorder) && position.precedes(bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("East"));

        return false;
    }
}
