package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class NorthWestMap extends AbstractMap {
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5 + 1, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 2, height);


    public NorthWestMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                if (i == -j + 21 + height)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierUpperLeftCornerTile));
                else if (i > 3 * height/5 + j || i == -j + 22 + height || i == -j + 23 + height && j < width - 1 && j > width - 6)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierTile));
                else if (i >= -j + 21 + height)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
                else if (i == 3 * height/5 + j)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierUpperRightCornerTile));
                else if (j == 0 || i == 0 || j == width - 1 || i == height - 1 && (j <= 2 * width/5 || j >= 3 * width/5 - 1))
                    this.nodes[i][j].getChildren().add(new ImageView(graySphereTile));
                else
                    this.nodes[i][j].getChildren().add(new ImageView(grayTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }
        addBigTree(18, 9); addBigTree(18, 18);
    }

    public void addBigTree(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(upperLeftGrayTreeTile));
        this.nodes[i + 1][j].getChildren().add(new ImageView(bottomLeftGrayTreeTile));
        this.nodes[i + 1][j + 1].getChildren().add(new ImageView(bottomMiddleGrayTreeTile));
        this.nodes[i][j + 1].getChildren().add(new ImageView(upperMiddleGrayTreeTile));
        this.nodes[i][j + 2].getChildren().add(new ImageView(upperRightGrayTreeTile));
        this.nodes[i + 1][j + 2].getChildren().add(new ImageView(bottomRightGrayTreeTile));
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(bottomLeftPassageBorder) && position.precedes(bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("West"));

        return false;
    }
}
