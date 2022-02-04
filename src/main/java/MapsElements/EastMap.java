package MapsElements;

import GUI.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.LinkedHashMap;

import static MapsElements.MoveDirection.*;

public class EastMap extends AbstractMap {
    public static final Vector2d leftBottomPassageBorder = new Vector2d(-1, 2 * height/5);
    public static final Vector2d leftUpperPassageBorder = new Vector2d(-1, 3 * height/5 - 1);
    public static final Vector2d upperLeftPassageBorder = new Vector2d(2 * width/5 + 1, -1);
    public static final Vector2d upperRightPassageBorder = new Vector2d(3 * width/5 - 2, -1);
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5 + 1, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 2, height);
    public static final LinkedHashMap<Integer, LinkedHashMap<Integer, Octorok>> octoroks = new LinkedHashMap<>();

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
                else if (j == width - 6)
                    this.nodes[i][j].getChildren().add(new ImageView(waterEdgeTile));
                else
                    this.nodes[i][j].getChildren().add(new ImageView(waterTile));
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


        octoroks.put(5, new LinkedHashMap<>());
        octoroks.get(5).put(8, new Octorok(8, 5, new MoveDirection[]{SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH,
                SOUTH, SOUTH, EAST, EAST, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, WEST, WEST}));

        octoroks.put(height - 4, new LinkedHashMap<>());
        octoroks.get(height - 4).put(9, new Octorok(9, height - 4, new MoveDirection[]{NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH, NORTH,
                WEST, WEST, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, SOUTH, EAST, EAST}));

        octoroks.get(5).put(10, new Octorok(10, 5, new MoveDirection[]{SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, NORTH, NORTH,
                NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST}));

        octoroks.put(7, new LinkedHashMap<>());
        octoroks.get(7).put(20, new Octorok(20, 7, new MoveDirection[]{NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST,SOUTH, SOUTH,
                SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST}));

        octoroks.put(11, new LinkedHashMap<>());
        octoroks.get(11).put(10, new Octorok(10, 11, new MoveDirection[]{NORTH, NORTH, NORTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, SOUTH, SOUTH,
                SOUTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST}));

        octoroks.put(9, new LinkedHashMap<>());
        octoroks.get(9).put(20, new Octorok(20, 9, new MoveDirection[]{SOUTH, SOUTH, SOUTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, NORTH, NORTH,
                NORTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST}));

        octoroks.put(15, new LinkedHashMap<>());
        octoroks.get(15).put(15, new Octorok(15, 15, new MoveDirection[]{EAST, EAST, EAST, EAST, EAST, EAST, NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST, WEST,
                WEST, SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST}));

        octoroks.put(13, new LinkedHashMap<>());
        octoroks.get(13).put(15, new Octorok(15, 13, new MoveDirection[]{WEST, WEST, WEST, WEST, WEST, WEST, SOUTH, SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST, EAST, EAST, EAST,
        EAST, EAST, EAST, NORTH, NORTH, NORTH, WEST, WEST, WEST, WEST, WEST}));


        Zola zola = new Zola(28, 4);
        nodes[4][28].getChildren().add(zola.getZolaAnimation()[2]);

        animation = new AnimationTimer() {
            private int cnt = 0;

            public void handle(long now) {
                if (frameCount % 8 == 0){
                    if (cnt % 2 == 0){
                        toMove.get(SOUTH).clear(); toMove.get(NORTH).clear(); toMove.get(EAST).clear(); toMove.get(WEST).clear();
                        for (Integer i : EastMap.octoroks.keySet()){
                            for (Octorok octorok : EastMap.octoroks.get(i).values())
                                toMove.get(octorok.moveCycle.get(octorok.i)).add(octorok);
                        }
                    }

                    for (MoveDirection direction : toMove.keySet()){
                        for (Octorok octorok : toMove.get(direction)){
                            if (octorok.prevImage != null)
                                nodes[octorok.prevY][octorok.prevX].getChildren().remove(octorok.prevImage);
                            if (cnt % 2 == 0){
                                octorok.move(App.map, direction);
                                octorok.i += 1;
                            }
                            if (octorok.getHealth() > 0)
                                nodes[octorok.getY()][octorok.getX()].getChildren().add(octorok.getOctorokAnimation()[cnt % 2]);
                            octorok.prevImage = octorok.getOctorokAnimation()[cnt % 2];
                            octorok.prevX = octorok.getX();
                            octorok.prevY = octorok.getY();
                            if (octorok.i == octorok.moveCycle.size()) octorok.i = 0;
                        }
                    }
                    cnt += 1;
                    frameCount = 0;
                }
                frameCount += 1;
            }
        };
    }

    public void addTreeWithHiddenCave(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(upperLeftSandTreeTile));
        this.occupancyMap[i][j] = true;
        this.nodes[i + 1][j].getChildren().add(new ImageView(bottomLeftSandTreeTile));
        this.occupancyMap[i + 1][j] = true;
        this.nodes[i + 1][j + 1].getChildren().add(new ImageView(bottomMiddleSandTreeTile));
        this.occupancyMap[i + 1][j + 1] = true;
        this.nodes[i][j + 1].getChildren().add(new ImageView(upperMiddleSandTreeTile));
        this.occupancyMap[i][j + 1] = true;
        this.nodes[i][j + 2].getChildren().add(new ImageView(upperRightSandTreeTile));
        this.occupancyMap[i][j + 2] = true;
        this.nodes[i + 1][j + 2].getChildren().add(new ImageView(bottomRightSandTreeTile));
        this.occupancyMap[i + 1][j + 2] = true;
    }

    public void addTree(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(upperLeftSandTreeTile));
        this.occupancyMap[i][j] = true;
        this.nodes[i + 1][j].getChildren().add(new ImageView(bottomLeftSandTreeTile));
        this.occupancyMap[i + 1][j] = true;
        this.nodes[i][j + 1].getChildren().add(new ImageView(upperRightSandTreeTile));
        this.occupancyMap[i][j + 1] = true;
        this.nodes[i + 1][j + 1].getChildren().add(new ImageView(bottomRightSandTreeTile));
        this.occupancyMap[i + 1][j + 1] = true;
    }

    public void addBlocker(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(sandBlockerTile));
        this.occupancyMap[i][j] = true;
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(leftBottomPassageBorder) && position.precedes(leftUpperPassageBorder)){
            MapChangeObserver.notifyMapChange(maps.get("Start"));
            this.animation.stop();
        }
        else if (position.follows(upperLeftPassageBorder) && position.precedes(upperRightPassageBorder)){
            MapChangeObserver.notifyMapChange(maps.get("NorthEast"));
            this.animation.stop();
        }
        else if (position.follows(bottomLeftPassageBorder) && position.precedes(bottomRightPassageBorder)){
            MapChangeObserver.notifyMapChange(maps.get("SouthEast"));
            this.animation.stop();
        }

        return false;
    }
}
