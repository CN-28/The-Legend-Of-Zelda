package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class NorthMap extends AbstractMap {
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5 + 1, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 2, height);
    public static final Vector2d rightBottomPassageBorder = new Vector2d(width, 2 * height/5);
    public static final Vector2d rightUpperPassageBorder = new Vector2d(width, 3 * height/5 - 1);

    public NorthMap() {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                if (j == width - 1 && i <= 7 || i == 0 && j >= 22)
                    this.nodes[i][j].getChildren().add(new ImageView(sandSphereTile));
                else if (i == 3 * height/5 + j){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierUpperRightCornerTile));
                }
                else if (i > 3 * height/5 + j)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
                else if (i == 0 || j == 0 || i == height - 1 && (j <= 12 || j >= 17 && j <= 21)){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassSphereTile));
                }
                else if (j == width - 1 && i < 13 || j == width - 2 && i >= 7 && i <= 12 || j == width - 3 && i >= 8 && i <= 11 || j == width - 4 && i >= 9 && i <= 10){
                    this.nodes[i][j].getChildren().add(new ImageView(sandTile));
                    if (i == 12 && j == width - 1) this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperLeftCornerTile));
                }
                else if (i <= j - 22)
                    this.nodes[i][j].getChildren().add(new ImageView(sandTile));
                else if ((i == height - 1 || i == height - 6) && j > 2 * width/5 && j < 3 * width/5 - 1 || (i == height - 2 || i == height - 5) && j >= 2 * width/5 && j < 3 * width/5
                || (i >= height - 4 && i < height - 2) && j > 2 * width/5 - 2 && j < 3 * width/5 + 1)
                    this.nodes[i][j].getChildren().add(new ImageView(pathTile));
                else if (i > -j + 21 + height)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierTile));
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

        if (position.follows(bottomLeftPassageBorder) && position.precedes(bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("Start"));
        else if (position.follows(rightBottomPassageBorder) && position.precedes(rightUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("NorthEast"));

        return false;
    }
}
