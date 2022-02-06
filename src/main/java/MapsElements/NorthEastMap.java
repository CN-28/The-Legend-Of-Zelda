package MapsElements;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import static MapsElements.MoveDirection.*;
import static MapsElements.MoveDirection.WEST;

public class NorthEastMap extends AbstractMap {
    public static final Vector2d leftBottomPassageBorder = new Vector2d(-1, 2 * height/5);
    public static final Vector2d leftUpperPassageBorder = new Vector2d(-1, 3 * height/5 - 1);
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5 + 1, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 2, height);

    public NorthEastMap() {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.occupancyMap[i][j] = true;
                if (i <= j - 18){
                    this.nodes[i][j].getChildren().add(new ImageView(waterTile));
                    this.zolaPositions.add(i * width + j);
                }
                else if (i == height - 1 && (j > 7 & j <= 12 || j >= 17 && j < 24) || j == 0 && i <= 7 || i == 0 && j <= 16)
                    this.nodes[i][j].getChildren().add(new ImageView(sandSphereTile));
                else if (i == 3 * height/5 + j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperRightCornerTile));
                else if (i > 3 * height/5 + j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierTile));
                else if (i == j - 17 && j < 24){
                    this.nodes[i][j].getChildren().add(new ImageView(upperRightWaterCornerTile));
                    this.zolaPositions.add(i * width + j);
                }
                else if (j < width - 6){
                    this.nodes[i][j].getChildren().add(new ImageView(sandTile));
                    this.occupancyMap[i][j] = false;
                }
                else if (j == width - 6){
                    this.nodes[i][j].getChildren().add(new ImageView(waterEdgeTile));
                    this.zolaPositions.add(i * width + j);
                }
                else{
                    this.nodes[i][j].getChildren().add(new ImageView(waterTile));
                    this.zolaPositions.add(i * width + j);
                }
                this.grid.add(this.nodes[i][j], j, i);
            }
        }

        addOctorok(8, 6, new MoveDirection[]{SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH,
                SOUTH, SOUTH, EAST, EAST, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, WEST, WEST});
        addGhini(5, 3, new MoveDirection[]{WEST, WEST, WEST, EAST, EAST, EAST});
        addGhini(10, 4, new MoveDirection[]{WEST, WEST, WEST, EAST, EAST, EAST});
        addGhini(6, 5, new MoveDirection[]{EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST});
        addGhini(15, 4, new MoveDirection[]{EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST});
        addGhini(13, 2, new MoveDirection[]{EAST, EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST, WEST});
        addOctorok(10, 7, new MoveDirection[]{SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, NORTH, NORTH,
                NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST});
        addOctorok(20, 10, new MoveDirection[]{NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST,SOUTH, SOUTH,
                SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST});
        addOctorok(15, 15, new MoveDirection[]{EAST, EAST, EAST, EAST, EAST, EAST, NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST,
                WEST, SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST});
        addZola();

        animation = new AnimationTimer() {
            public void handle(long now) {
                if (frameCount % 3 == 0)
                    handleGhiniPushBack("NorthEast");

                if (frameCount % 6 == 0)
                    handleOctorokBallsAttacks("NorthEast");

                if (frameCount % 8 == 0){
                    handleZolaAttack("NorthEast");
                    handleOctorokMovement("NorthEast");
                }

                if (frameCount % 14 == 0)
                    handleGhiniMovement("NorthEast");

                if (frameCount % 70 == 0)
                    handleZolaMovement("NorthEast");

                if (frameCount == 168)
                    frameCount = 0;

                frameCount += 1;
            }
        };
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
