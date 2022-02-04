package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class SouthMap extends AbstractMap {
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5 + 1, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 2, -1);
    public static final Vector2d rightUpperPassageBorder = new Vector2d(width, 3 * height/5 - 1);
    public static final Vector2d rightBottomPassageBorder = new Vector2d(width, 2 * height/5);
    public static final Vector2d leftUpperPassageBorder = new Vector2d(-1, 3 * height/5 - 1);
    public static final Vector2d leftBottomPassageBorder = new Vector2d(-1, 2 * height/5);

    public SouthMap() {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                if (j == width - 1 && i >= 7 && i < 13 || j == width - 2 && i >= 7 && i <= 12 || j == width - 3 && i >= 8 && i <= 11 || j == width - 4 && i >= 9 && i <= 10){
                    this.nodes[i][j].getChildren().add(new ImageView(sandTile));
                    if (i == 7 && j == width - 1) this.nodes[i][j].getChildren().add(new ImageView(sandBarrierBottomLeftCornerTile));
                    if (i == 12 && j == width - 1) this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperLeftCornerTile));
                }
                else if ((j >= 8 && j <= 12 || j >= 17 && j <= 21) && i == 0 || i == height - 1 && j <= 21 || j == 0 && i >= 12){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassSphereTile));
                }
                else if (i < 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
                else if (i == 2 * height/5 - 1 - j){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierBottomRightCornerTile));
                }
                else if ((i == 0 || i == 5) && j > 12 && j <= 16 || (i == 1 || i == 4) && j >= 12 && j <= 17 || i >= 2 && i <= 3 && j >= 11 && j <= 18)
                    this.nodes[i][j].getChildren().add(new ImageView(pathTile));
                else if (i < j - 22 || i > -j + 21 + height)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierTile));
                else if (i == j - 22){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierBottomLeftCornerTile));
                }
                else if (i == -j + 21 + height){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperLeftCornerTile));
                }
                else
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(upperLeftPassageBorder) && position.precedes(upperRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("Start"));
        else if (position.follows(rightBottomPassageBorder) && position.precedes(rightUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("SouthEast"));
        else if (position.follows(leftBottomPassageBorder) && position.precedes(leftUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("SouthWest"));

        return false;
    }
}
