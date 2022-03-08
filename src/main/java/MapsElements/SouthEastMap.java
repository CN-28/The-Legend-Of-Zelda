package MapsElements;

import GUI.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import static MapsElements.MoveDirection.*;

public class SouthEastMap extends AbstractMap {
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5 + 1, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 2, -1);
    public static final Vector2d leftUpperPassageBorder = new Vector2d(-1, 3 * height/5 - 1);
    public static final Vector2d leftBottomPassageBorder = new Vector2d(-1, 2 * height/5);

    public SouthEastMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.occupancyMap[i][j] = true;
                if (i >= -j + 18 + height){
                    this.nodes[i][j].getChildren().add(new ImageView(waterTile));
                    this.zolaPositions.add(i * width + j);
                }
                else if (i == height - 2 && (j >= 7 && j <= 18))
                    this.nodes[i][j].getChildren().add(new ImageView(sandSphereTile));
                else if (i == height - 1 && (j > 7 && j < 18) || i == 0 && (j > 7 & j <= 12 || j >= 17 && j < 24))
                    this.nodes[i][j].getChildren().add(new ImageView(sandSphereTile));
                else if (i == 3 * height/5 + j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperRightCornerTile));
                else if (i == 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierBottomRightCornerTile));
                else if (i > 3 * height/5 + j || i < 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierTile));
                else if (i == -j + 17 + height && j < 24)
                    this.nodes[i][j].getChildren().add(new ImageView(bottomRightWaterCornerTile));
                else if (j < width - 6){
                    this.nodes[i][j].getChildren().add(new ImageView(sandTile));
                    this.occupancyMap[i][j] = false;
                }
                else if (j == width - 6 && i <= 15){
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

        addTektite(2, 6, new MoveDirection[]{NORTH_EAST, NORTH_EAST, SOUTH_EAST, SOUTH_EAST, SOUTH_WEST, SOUTH_WEST, NORTH, NORTH, WEST, WEST});
        addTektite(4, 15, new MoveDirection[]{NORTH_EAST, NORTH_EAST, WEST, WEST, NORTH, NORTH, SOUTH_WEST, SOUTH_WEST, SOUTH_EAST, SOUTH_EAST});
        addTektite(10, 15, new MoveDirection[]{EAST, EAST, NORTH_WEST, NORTH_WEST, SOUTH, SOUTH});
        addTektite(15, 16, new MoveDirection[]{EAST, EAST, NORTH_WEST, NORTH_WEST, SOUTH, SOUTH});
        addTektite(12, 6, new MoveDirection[]{EAST, EAST, NORTH_WEST, NORTH_WEST, SOUTH, SOUTH});
        addTektite(20, 5, new MoveDirection[]{WEST, WEST, NORTH_EAST, NORTH_EAST, SOUTH, SOUTH});
        addTektite(22, 12, new MoveDirection[]{WEST, WEST, NORTH_EAST, NORTH_EAST, SOUTH, SOUTH});
        addTektite(11, 10, new MoveDirection[]{NORTH_EAST, NORTH_EAST, SOUTH_EAST, SOUTH_EAST, NORTH_EAST, NORTH_EAST, SOUTH_WEST, SOUTH_WEST, NORTH_WEST, NORTH_WEST, SOUTH_WEST, SOUTH_WEST});
        addTektite(8, 9, new MoveDirection[]{NORTH_EAST, NORTH_EAST, SOUTH_EAST, SOUTH_EAST, NORTH_EAST, NORTH_EAST, SOUTH_WEST, SOUTH_WEST, NORTH_WEST, NORTH_WEST, SOUTH_WEST, SOUTH_WEST});
        addZola();

        animation = new AnimationTimer() {

            public void handle(long now) {
                if (App.map instanceof SouthEastMap){
                    App.checkSwordCollision();
                    checkZolaBallCollision();
                }

                if (frameCount % 8 == 0)
                    handleZolaAttack();

                if (frameCount % 12 == 0)
                    handleTektiteMovement();

                if (frameCount % 70 == 0){
                    handleZolaMovement();
                    frameCount = 0;
                }

                frameCount += 1;
            }
        };
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
