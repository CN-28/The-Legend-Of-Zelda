package MapsElements;


import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;


import static MapsElements.MoveDirection.*;

public class EastMap extends AbstractMap {
    public static final Vector2d leftBottomPassageBorder = new Vector2d(-1, 2 * height/5);
    public static final Vector2d leftUpperPassageBorder = new Vector2d(-1, 3 * height/5 - 1);
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5 + 1, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 2, -1);
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5 + 1, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 2, height);

    public EastMap() {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.occupancyMap[i][j] = true;
                if (j < 7 && i >= 2 * height/5 && i < 3 * height/5 || j > 0 && j < 6 && (i == 2 * height/5 - 1 || i == 3 * height/5)
                || j > 1 && j < 5 && (i == 2 * height/5 - 2 || i == 3 * height/5 + 1) || j == 7 && i >= 9 && i <= 10){
                    this.nodes[i][j].getChildren().add(new ImageView(pathTile));
                    this.occupancyMap[i][j] = false;
                }
                else if (i == 3 * height/5 + j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierUpperRightCornerTile));
                else if (i == 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierBottomRightCornerTile));
                else if (i > 3 * height/5 + j || i < 2 * height/5 - 1 - j)
                    this.nodes[i][j].getChildren().add(new ImageView(sandBarrierTile));
                else if (i == height - 1 && (j > 7 && j <= 10 || j >= 19 && j < 24) || i == 0 && (j > 7 & j <= 12 || j >= 17 && j < 24))
                    this.nodes[i][j].getChildren().add(new ImageView(sandSphereTile));
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
        addTreeWithHiddenCave(2, 7);
        addTree(18, 11);
        addTree(18, 17);
        for (int i = 6; i < 15; i += 4){
            for (int j = 11; j < 21; j += 4)
                addBlocker(i, j);
        }

        addOctorok(8, 5, new MoveDirection[]{SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH,
        SOUTH, SOUTH, EAST, EAST, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, WEST, WEST});
        addOctorok(9, height - 4, new MoveDirection[]{NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH,
        WEST, WEST, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, EAST, EAST});
        addOctorok(10, 5, new MoveDirection[]{SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, NORTH, NORTH,
        NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST});
        addOctorok(20, 7, new MoveDirection[]{NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST,SOUTH, SOUTH,
        SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST});
        addOctorok(10, 11, new MoveDirection[]{NORTH, NORTH, NORTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, SOUTH, SOUTH,
        SOUTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST});
        addOctorok(20, 9, new MoveDirection[]{SOUTH, SOUTH, SOUTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, NORTH, NORTH,
        NORTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST});
        addOctorok(15, 15, new MoveDirection[]{EAST, EAST, EAST, EAST, EAST, EAST, NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST,
        WEST, SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST});
        addOctorok(15, 13, new MoveDirection[]{WEST, WEST, WEST, WEST, WEST, WEST, SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST,
        EAST, EAST, EAST, NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST});
        addZola();

        animation = new AnimationTimer() {
            public void handle(long now) {
                if (frameCount % 6 == 0)
                    handleOctorokBallsAttacks("East");

                if (frameCount % 8 == 0){
                    handleOctorokMovement("East");
                    handleZolaAttack("East");
                }

                if (frameCount % 70 == 0){
                    handleZolaMovement("East");
                    frameCount = 0;
                }

                frameCount += 1;
            }
        };
    }

    public void addTreeWithHiddenCave(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(upperLeftSandTreeTile));
        this.nodes[i + 1][j].getChildren().add(new ImageView(bottomLeftSandTreeTile));
        this.nodes[i + 1][j + 1].getChildren().add(new ImageView(bottomMiddleSandTreeTile));
        this.nodes[i][j + 1].getChildren().add(new ImageView(upperMiddleSandTreeTile));
        this.nodes[i][j + 2].getChildren().add(new ImageView(upperRightSandTreeTile));
        this.nodes[i + 1][j + 2].getChildren().add(new ImageView(bottomRightSandTreeTile));
        this.occupancyMap[i + 1][j] = true;
        this.occupancyMap[i][j] = true;
        this.occupancyMap[i + 1][j + 1] = true;
        this.occupancyMap[i][j + 1] = true;
        this.occupancyMap[i][j + 2] = true;
        this.occupancyMap[i + 1][j + 2] = true;
    }

    public void addTree(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(upperLeftSandTreeTile));
        this.nodes[i + 1][j].getChildren().add(new ImageView(bottomLeftSandTreeTile));
        this.nodes[i][j + 1].getChildren().add(new ImageView(upperRightSandTreeTile));
        this.nodes[i + 1][j + 1].getChildren().add(new ImageView(bottomRightSandTreeTile));
        this.occupancyMap[i][j] = true;
        this.occupancyMap[i + 1][j] = true;
        this.occupancyMap[i][j + 1] = true;
        this.occupancyMap[i + 1][j + 1] = true;
    }

    public void addBlocker(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(sandBlockerTile));
        this.occupancyMap[i][j] = true;
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(leftBottomPassageBorder) && position.precedes(leftUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("Start"));
        else if (position.follows(upperLeftPassageBorder) && position.precedes(upperRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("NorthEast"));
        else if (position.follows(bottomLeftPassageBorder) && position.precedes(bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("SouthEast"));
        else if (hero.getPosition().equals(AbstractCave.caves.get("East").getPosition()) && hero.getOrientation() == NORTH)
            MapChangeObserver.notifyMapChange(AbstractCave.caves.get("East"));

        return false;
    }
}
