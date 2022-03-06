package MapsElements;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import static MapsElements.MoveDirection.*;

public class NorthMap extends AbstractMap {
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5 + 1, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 2, height);
    public static final Vector2d rightBottomPassageBorder = new Vector2d(width, 2 * height/5);
    public static final Vector2d rightUpperPassageBorder = new Vector2d(width, 3 * height/5 - 1);

    public NorthMap() {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.occupancyMap[i][j] = true;
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
                    this.occupancyMap[i][j] = false;
                    if (i == 12 && j == width - 1) {
                        this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperLeftCornerTile));
                        this.occupancyMap[i][j] = true;
                    }
                }
                else if (i <= j - 22){
                    this.nodes[i][j].getChildren().add(new ImageView(sandTile));
                    this.occupancyMap[i][j] = false;
                }
                else if ((i == height - 1 || i == height - 6) && j > 2 * width/5 && j < 3 * width/5 - 1 || (i == height - 2 || i == height - 5) && j >= 2 * width/5 && j < 3 * width/5
                || (i >= height - 4 && i < height - 2) && j > 2 * width/5 - 2 && j < 3 * width/5 + 1){
                    this.nodes[i][j].getChildren().add(new ImageView(pathTile));
                    this.occupancyMap[i][j] = false;
                }
                else if (i > -j + 21 + height)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierTile));
                else if (i == -j + 21 + height){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperLeftCornerTile));
                }
                else{
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.occupancyMap[i][j] = false;
                }
                this.grid.add(this.nodes[i][j], j, i);
            }
        }

        addGhini(5, 5, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(20, 5, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(8, 7, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(12, 9, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(6, 5, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(7, 5, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(7, 15, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(23, 14, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(17, 10, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(5, 8, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(15, 1, new MoveDirection[]{EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST});
        addGhini(8, 2, new MoveDirection[]{EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST});
        addGhini(24, 3, new MoveDirection[]{EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST});
        addGhini(20, 8, new MoveDirection[]{EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST});

        animation = new AnimationTimer() {
            public void handle(long now){
                if (frameCount % 3 == 0)
                    handleGhiniPushBack();

                if (frameCount % 14 == 0){
                    handleGhiniMovement();
                    frameCount = 0;
                }
                frameCount += 1;
            }
        };
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
