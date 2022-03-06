package MapsElements;

import GUI.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import static MapsElements.MoveDirection.*;

public class SouthWestMap extends AbstractMap {
    public static final Vector2d rightBottomPassageBorder = new Vector2d(width, 2 * height/5);
    public static final Vector2d rightUpperPassageBorder = new Vector2d(width, 3 * height/5 - 1);
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5 + 1, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 2, -1);

    public SouthWestMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.occupancyMap[i][j] = true;
                if (i == j - 22){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierBottomLeftCornerTile));
                }
                else if (i < j - 22)
                    this.nodes[i][j].getChildren().add(new ImageView(grassBarrierTile));
                else if (j == width - 1 && i >= 12 || i == height - 1 && j > 14 || i == 0 && (j >= 6 && j <= 12 || j >= 17)){
                    this.nodes[i][j].getChildren().add(new ImageView(grassTile));
                    this.nodes[i][j].getChildren().add(new ImageView(grassSphereTile));
                }
                else if (i > 3 * height/5 + j - 5 || j < 4)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierTile));
                else if (i == 3 * height/5 + j - 5)
                    this.nodes[i][j].getChildren().add(new ImageView(grayBarrierUpperRightCornerTile));
                else if (i == 6 && j == 4)
                    this.nodes[i][j].getChildren().add(new ImageView(grayHiddenCave));
                else if (i == 3 * height/5 + j - 6 || j == 4 || i == height - 1|| i == 0 && j == 5)
                    this.nodes[i][j].getChildren().add(new ImageView(graySphereTile));
                else if (i == 3 * height/5 + j - 7 || j == 5){
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
        addGhini(6, 3, new MoveDirection[]{EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST});
        addGhini(6, 6, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addGhini(5, 8, new MoveDirection[]{EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST});
        addGhini(5, 10, new MoveDirection[]{EAST, EAST, EAST, EAST, EAST, WEST, WEST, WEST, WEST, WEST});
        addGhini(10, 12, new MoveDirection[]{WEST, WEST, WEST, WEST, EAST, EAST, EAST, EAST});
        addOctorok(15, 11, new MoveDirection[]{EAST, EAST, EAST, EAST, EAST, EAST, NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST,
                WEST, SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST});
        addOctorok(16, 6, new MoveDirection[]{WEST, WEST, WEST, WEST, WEST, WEST, SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST,
                EAST, EAST, EAST, NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST});
        addOctorok(15, 9, new MoveDirection[]{EAST, EAST, EAST, EAST, EAST, EAST, NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST,
                WEST, SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST});
        addOctorok(16, 8, new MoveDirection[]{WEST, WEST, WEST, WEST, WEST, WEST, SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST,
                EAST, EAST, EAST, NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST});
        addTektite(10, 15, new MoveDirection[]{EAST, EAST, NORTH_WEST, NORTH_WEST, SOUTH, SOUTH});
        addTektite(15, 17, new MoveDirection[]{NORTH_EAST, NORTH_EAST, SOUTH_EAST, SOUTH_EAST, NORTH_EAST, NORTH_EAST, SOUTH_WEST, SOUTH_WEST, NORTH_WEST, NORTH_WEST, SOUTH_WEST, SOUTH_WEST});
        addTektite(24, 16, new MoveDirection[]{WEST, WEST, NORTH_EAST, NORTH_EAST, SOUTH, SOUTH});

        animation = new AnimationTimer() {
            public void handle(long now) {
                if (App.map instanceof SouthWestMap)
                    checkOctoroksBallsCollisions();

                if (frameCount % 3 == 0)
                    handleGhiniPushBack();

                if (frameCount % 6 == 0)
                    handleOctorokBallsAttacks();

                if (frameCount % 8 == 0)
                    handleOctorokMovement();

                if (frameCount % 12 == 0)
                    handleTektiteMovement();

                if (frameCount % 14 == 0)
                    handleGhiniMovement();

                if (frameCount == 168)
                    frameCount = 0;
                frameCount += 1;
            }
        };
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(rightBottomPassageBorder) && position.precedes(rightUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("South"));
        else if (position.follows(upperLeftPassageBorder) && position.precedes(upperRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("West"));
        else if (hero.getPosition().equals(AbstractCave.caves.get("SouthWest").getPosition()) && hero.getOrientation() == WEST)
            MapChangeObserver.notifyMapChange(AbstractCave.caves.get("SouthWest"));

        return false;
    }
}