package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class StartingMap extends AbstractMap {
    public static final Vector2d rightBottomPassageBorder = new Vector2d(width, 2 * height/5);
    public static final Vector2d rightUpperPassageBorder = new Vector2d(width, 3 * height/5 - 1);
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5 + 1, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 2, height);
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5 + 1, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 2, -1);

    public StartingMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                if (j + 1 > 2 * width/5 + 1 && j + 1 <= 3 * width/5 - 1 || i + 1 > 2 * height/5 && i + 1 <= 3 * height/5 && j + 1 > 2 * width/5 || (j == 11 || j == 18) && i > 6 && i < 13 ||
                    (j == 12 || j == 17) && i > 5 && i < 14 || (j == 10 || j == 19) && i > 7 && i < 12)
                    this.nodes[i][j].getChildren().add(new ImageView(pathTile));
                else if (j < 4)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
                else if (i == 3 * height/5 + j){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierUpperRightCornerTile));
                }
                else if (i == 2 * height/5 - 1 - j){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierBottomRightCornerTile));
                }
                else if ((j >= 8 && j <= 12 || j >= 17 && j <= 21) && (i == 0 || i == height - 1)){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassSphereTile));
                }
                else if (i > 3 * height/5 + j || i < 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
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
                    this.nodes[i][j].getChildren().addAll(new ImageView(grassTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }
        addTree(3, 6); addTree(15, 8); addTree(13, 23); addTree(3, 19);
        addDecoration(6,6); addDecoration(11,6); addDecoration(4, 22);
    }

    public void addTree(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(upperLeftGrassTreeTile));
        this.nodes[i + 1][j].getChildren().add(new ImageView(bottomLeftGrassTreeTile));
        this.nodes[i][j + 1].getChildren().add(new ImageView(upperRightGrassTreeTile));
        this.nodes[i + 1][j + 1].getChildren().add(new ImageView(bottomRightGrassTreeTile));
    }

    public void addDecoration(int i, int j){
        for (int k = 0; k < 3; k++){
            for (int l = 0; l < 3; l++){
                if (i + 1 == i + k && j + l == j + 1)
                    this.nodes[i + k][j + l].getChildren().add(new ImageView(grassStatueTile));
                else
                    this.nodes[i + k][j + l].getChildren().add(new ImageView(grassSphereTile));
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(rightBottomPassageBorder) && position.precedes(rightUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("East"));
        else if (position.follows(bottomLeftPassageBorder) && position.precedes(bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("South"));
        else if (position.follows(upperLeftPassageBorder) && position.precedes(upperRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("North"));

        return false;
    }
}
