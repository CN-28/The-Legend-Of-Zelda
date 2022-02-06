package MapsElements;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import static MapsElements.MoveDirection.*;
import static MapsElements.MoveDirection.SOUTH;

public class WestMap extends AbstractMap {
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5 + 1, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 2, height);
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5 + 1, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 2, -1);

    public WestMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.occupancyMap[i][j] = true;
                if (j > width - 5)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
                else if (i == -j + 21 + height){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierUpperLeftCornerTile));
                }
                else if (i >= -j + 21 + height)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
                else if (i < j - 22)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
                else if (i == j - 22){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierBottomLeftCornerTile));
                }
                else if (j < 4)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierTile));
                else if (i == 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierBottomRightCornerTile));
                else if (i < 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierTile));
                else if (i == 0 && (j <= 2 * width/5 || j >= 3 * width/5 - 1) || i == height - 1 && j <= 5)
                    this.nodes[i][j].getChildren().add(new ImageView(graySphereTile));
                else if (i == height - 1 && (j <= 12 || j >= 17)){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassSphereTile));
                }
                else if (j <= 5 || i <= -j + 21){
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
        addGhini(20, 8, new MoveDirection[]{EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST});
        addTektite(4, 15, new MoveDirection[]{NORTH_EAST, NORTH_EAST, WEST, WEST, NORTH, NORTH, SOUTH_WEST, SOUTH_WEST, SOUTH_EAST, SOUTH_EAST});
        addOctorok(9, height - 4, new MoveDirection[]{NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH,
                WEST, WEST, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, EAST, EAST});
        addOctorok(20, 7, new MoveDirection[]{NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST,SOUTH, SOUTH,
                SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST});

        animation = new AnimationTimer() {
            public void handle(long now) {
                if (frameCount % 3 == 0)
                    handleGhiniPushBack("West");

                if (frameCount % 6 == 0)
                    handleOctorokBallsAttacks("West");

                if (frameCount % 8 == 0)
                    handleOctorokMovement("West");

                if (frameCount % 12 == 0)
                    handleTektiteMovement("West");

                if (frameCount % 14 == 0)
                    handleGhiniMovement("West");

                if (frameCount % 168 == 0)
                    frameCount = 0;

                frameCount += 1;
            }
        };
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(upperLeftPassageBorder) && position.precedes(upperRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("NorthWest"));
        else if (position.follows(bottomLeftPassageBorder) && position.precedes(bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("SouthWest"));

        return false;
    }
}
