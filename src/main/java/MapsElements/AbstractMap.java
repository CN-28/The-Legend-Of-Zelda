package MapsElements;

import GUI.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static MapsElements.MoveDirection.*;
import static MapsElements.MoveDirection.NORTH;

public abstract class AbstractMap implements IWorldMap {
    public static Image grassTile, sandTile, waterTile, waterEdgeTile, pathTile, sandBarrierUpperRightCornerTile, sandBarrierBottomRightCornerTile,
    sandBarrierTile, bottomLeftSandTreeTile, upperLeftSandTreeTile, upperMiddleSandTreeTile, bottomMiddleSandTreeTile, bottomRightSandTreeTile, upperRightSandTreeTile,
    sandBlockerTile, sandSphereTile, upperRightWaterTile, upperRightWaterCornerTile, bottomRightWaterCornerTile, sandBarrierUpperLeftCornerTile,
    sandBarrierBottomLeftCornerTile, grassBarrierUpperRightCornerTile, grassBarrierBottomRightCornerTile, grassBarrierTile, grassSphereTile, bottomLeftGrassTreeTile,
    upperLeftGrassTreeTile, upperRightGrassTreeTile, bottomRightGrassTreeTile, grassBlockerTile, grassStatueTile, grassBarrierBottomLeftCornerTile, grayBarrierUpperRightCornerTile,
    grayBarrierTile, grayTile, graySphereTile, grayHiddenCave, grassBarrierUpperLeftCornerTile, grayBarrierBottomRightCornerTile, grayBarrierUpperLeftCornerTile, bottomLeftGrayTreeTile,
    upperLeftGrayTreeTile, upperMiddleGrayTreeTile, bottomMiddleGrayTreeTile, upperRightGrayTreeTile, bottomRightGrayTreeTile, bomb, bigHeart, heart, healthPotion,
    gold, greenLeftStairs, grayLeftStairs;
    protected static int size = 35;
    protected int frameCount;
    public static IMapChangeObserver MapChangeObserver;
    public static final int width = 30;
    public static final int height = 20;
    public final boolean[][] occupancyMap = new boolean[height][width];
    public static final Hero hero = new Hero();
    public static final Vector2d lowerLeft = new Vector2d(0, 0);
    public static final Vector2d upperRight = new Vector2d(width - 1, height - 1);
    public final Group[][] nodes = new Group[height][width];
    public final GridPane grid = new GridPane();
    public static HashMap<String, AbstractMap> maps = new HashMap<>();
    public AnimationTimer animation;
    protected LinkedHashMap<MoveDirection, ArrayList<Octorok>> octorokToMove = new LinkedHashMap<>();
    protected LinkedHashMap<MoveDirection, ArrayList<Tektite>> tektiteToMove = new LinkedHashMap<>();
    protected LinkedHashMap<MoveDirection, ArrayList<Ghini>> ghiniToMove = new LinkedHashMap<>();
    public final HashMap<Vector2d, HashMap<Image, ArrayList<ImageView>>> loots = new HashMap<>();
    public final LinkedHashMap<Integer, LinkedHashMap<Integer, ArrayList<Creature>>> mobs = new LinkedHashMap<>();
    public final ArrayList<OctorokAttackBall> octoroksBalls = new ArrayList<>();
    public ZolaAttackBall zolaAttackBall;
    public Zola zola;
    public ArrayList<Integer> zolaPositions = new ArrayList<>();
    public int octorokCnt, zolaCnt, tektiteCnt;
    {
        MoveDirection[] possibleValues = MoveDirection.values();
        for (MoveDirection direction : possibleValues){
            octorokToMove.put(direction, new ArrayList<>());
            ghiniToMove.put(direction, new ArrayList<>());
            tektiteToMove.put(direction, new ArrayList<>());
        }
    }

    static {
        try {
            grassTile = new Image(new FileInputStream("src/main/resources/grassTile.png"), size, size, false, false);
            pathTile = new Image(new FileInputStream("src/main/resources/pathTile.png"), size, size, false, false);
            sandTile = new Image(new FileInputStream("src/main/resources/sandTile.png"), size, size, false, false);
            sandBarrierUpperRightCornerTile = new Image(new FileInputStream("src/main/resources/sandBarrierUpperRightCorner.png"), size, size, false, false);
            sandBarrierBottomRightCornerTile = new Image(new FileInputStream("src/main/resources/sandBarrierBottomRightCorner.png"), size, size, false, false);
            sandBarrierTile = new Image(new FileInputStream("src/main/resources/sandBarrierTile.png"), size, size, false, false);
            waterTile = new Image(new FileInputStream("src/main/resources/waterTile.png"), size, size, false, false);
            waterEdgeTile = new Image(new FileInputStream("src/main/resources/waterEdgeTile.png"), size, size, false, false);
            bottomLeftSandTreeTile = new Image(new FileInputStream("src/main/resources/bottomLeftSandTree.png"), size, size, false, false);
            upperLeftSandTreeTile = new Image(new FileInputStream("src/main/resources/upperLeftSandTree.png"), size, size, false, false);
            upperMiddleSandTreeTile = new Image(new FileInputStream("src/main/resources/upperMiddleSandTree.png"), size, size, false, false);
            bottomMiddleSandTreeTile = new Image(new FileInputStream("src/main/resources/bottomMiddleSandTree.png"), size, size, false, false);
            upperRightSandTreeTile = new Image(new FileInputStream("src/main/resources/upperRightSandTree.png"), size, size, false, false);
            bottomRightSandTreeTile = new Image(new FileInputStream("src/main/resources/bottomRightSandTree.png"), size, size, false, false);
            sandBlockerTile = new Image(new FileInputStream("src/main/resources/sandBlockerTile.png"), size, size, false, false);
            sandSphereTile = new Image(new FileInputStream("src/main/resources/sandSphereTile.png"), size, size, false, false);
            upperRightWaterTile = new Image(new FileInputStream("src/main/resources/upperRightWaterTile.png"), size, size, false, false);
            upperRightWaterCornerTile = new Image(new FileInputStream("src/main/resources/upperRightCornerTile.png"), size, size, false, false);
            bottomRightWaterCornerTile = new Image(new FileInputStream("src/main/resources/bottomRightCornerTile.png"), size, size, false, false);
            sandBarrierUpperLeftCornerTile = new Image(new FileInputStream("src/main/resources/sandBarrierUpperLeftCorner.png"), size, size, false, false);
            sandBarrierBottomLeftCornerTile = new Image(new FileInputStream("src/main/resources/sandBarrierBottomLeftCorner.png"), size, size, false, false);
            grassBarrierUpperRightCornerTile = new Image(new FileInputStream("src/main/resources/grassBarrierUpperRightCorner.png"), size, size, false, false);
            grassBarrierBottomRightCornerTile = new Image(new FileInputStream("src/main/resources/grassBarrierBottomRightCorner.png"), size, size, false, false);
            grassBarrierTile = new Image(new FileInputStream("src/main/resources/grassBarrierTile.png"), size, size, false, false);
            grassSphereTile = new Image(new FileInputStream("src/main/resources/grassSphereTile.png"), size, size, false, false);
            bottomLeftGrassTreeTile = new Image(new FileInputStream("src/main/resources/bottomLeftGrassTree.png"), size, size, false, false);
            upperLeftGrassTreeTile = new Image(new FileInputStream("src/main/resources/upperLeftGrassTree.png"), size, size, false, false);
            upperRightGrassTreeTile = new Image(new FileInputStream("src/main/resources/upperRightGrassTree.png"), size, size, false, false);
            bottomRightGrassTreeTile = new Image(new FileInputStream("src/main/resources/bottomRightGrassTree.png"), size, size, false, false);
            grassBlockerTile = new Image(new FileInputStream("src/main/resources/grassBlockerTile.png"), size, size, false, false);
            grassStatueTile = new Image(new FileInputStream("src/main/resources/grassStatueTile.png"), size, size, false, false);
            grassBarrierBottomLeftCornerTile = new Image(new FileInputStream("src/main/resources/grassBarrierBottomLeftCornerTile.png"), size, size, false, false);
            grayBarrierUpperRightCornerTile = new Image(new FileInputStream("src/main/resources/grayBarrierUpperRightCornerTile.png"), size, size, false, false);
            grayBarrierTile = new Image(new FileInputStream("src/main/resources/grayBarrierTile.png"), size, size, false, false);
            grayTile = new Image(new FileInputStream("src/main/resources/grayTile.png"), size, size, false, false);
            grayHiddenCave = new Image(new FileInputStream("src/main/resources/grayHiddenCave.png"), size, size, false, false);
            graySphereTile = new Image(new FileInputStream("src/main/resources/graySphereTile.png"), size, size, false, false);
            grassBarrierUpperLeftCornerTile = new Image(new FileInputStream("src/main/resources/grassBarrierUpperLeftCornerTile.png"), size, size, false, false);
            grayBarrierBottomRightCornerTile = new Image(new FileInputStream("src/main/resources/grayBarrierBottomRightCornerTile.png"), size, size, false, false);
            grayBarrierUpperLeftCornerTile = new Image(new FileInputStream("src/main/resources/grayBarrierUpperLeftCornerTile.png"), size, size, false, false);
            bottomLeftGrayTreeTile = new Image(new FileInputStream("src/main/resources/bottomLeftGrayTree.png"), size, size, false, false);
            upperLeftGrayTreeTile = new Image(new FileInputStream("src/main/resources/upperLeftGrayTree.png"), size, size, false, false);
            upperMiddleGrayTreeTile = new Image(new FileInputStream("src/main/resources/upperMiddleGrayTree.png"), size, size, false, false);
            bottomMiddleGrayTreeTile = new Image(new FileInputStream("src/main/resources/bottomMiddleGrayTree.png"), size, size, false, false);
            upperRightGrayTreeTile = new Image(new FileInputStream("src/main/resources/upperRightGrayTree.png"), size, size, false, false);
            bottomRightGrayTreeTile = new Image(new FileInputStream("src/main/resources/bottomRightGrayTree.png"), size, size, false, false);
            bomb = new Image(new FileInputStream("src/main/resources/bomb.png"), size, size, false, false);
            bigHeart = new Image(new FileInputStream("src/main/resources/bigHeart.png"), size, size, false, false);
            heart = new Image(new FileInputStream("src/main/resources/heart.png"), size, size, false, false);
            gold = new Image(new FileInputStream("src/main/resources/gold.png"), size, size, false, false);
            healthPotion = new Image(new FileInputStream("src/main/resources/healthPotion.png"), size, size, false, false);
            greenLeftStairs = new Image(new FileInputStream("src/main/resources/greenLeftStairs.png"), size, size, false, false);
            grayLeftStairs = new Image(new FileInputStream("src/main/resources/grayLeftStairs.png"), size, size, false, false);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public boolean canMoveTo(Vector2d position){
        return position.precedes(upperRight) && position.follows(lowerLeft) && !isOccupied(position);
    }

    public void addOctorok(int x, int y, MoveDirection[] moves){
        if (!mobs.containsKey(y))
            mobs.put(y, new LinkedHashMap<>());
        if (!mobs.get(y).containsKey(x))
            mobs.get(y).put(x, new ArrayList<>());
        mobs.get(y).get(x).add(new Octorok(x, y, moves));
    }

    public void addTektite(int x, int y, MoveDirection[] moves){
        if (!mobs.containsKey(y))
            mobs.put(y, new LinkedHashMap<>());
        if (!mobs.get(y).containsKey(x))
            mobs.get(y).put(x, new ArrayList<>());
        mobs.get(y).get(x).add(new Tektite(x, y, moves));
    }

    public void addGhini(int x, int y, MoveDirection[] moves){
        if (!mobs.containsKey(y))
            mobs.put(y, new LinkedHashMap<>());
        if (!mobs.get(y).containsKey(x))
            mobs.get(y).put(x, new ArrayList<>());
        mobs.get(y).get(x).add(new Ghini(x, y, moves));
    }

    public void addZola(){
        this.zola = new Zola();
    }

    public void handleOctorokBallsAttacks(){
        Vector2d tempPos;
        for (Iterator<OctorokAttackBall> iter = octoroksBalls.iterator(); iter.hasNext();){
            OctorokAttackBall ball = iter.next();
            tempPos = ball.ballPosition.add(ball.ballDirection.toUnitVector());
            nodes[ball.ballPosition.getY() + ball.ballDirection.opposite().toUnitVector().getY()][ball.ballPosition.getX() + ball.ballDirection.opposite().toUnitVector().getX()].getChildren().remove(ball.getAttackBallImage());
            if (tempPos.precedes(upperRight) && tempPos.follows(lowerLeft) && this.canMoveTo(tempPos) && !AbstractMap.collidesWithHero(tempPos)){
                ball.ballPosition = tempPos;
                nodes[tempPos.getY()][tempPos.getX()].getChildren().add(ball.getAttackBallImage());
            }
            else{
                if(AbstractMap.collidesWithHero(tempPos) && this.equals(App.map) && heroNotBlocked(ball.ballPosition))
                    hero.removeHealth(App.map, 1);

                nodes[ball.ballPosition.getY()][ball.ballPosition.getX()].getChildren().remove(ball.getAttackBallImage());
                iter.remove();
            }
        }

        for (Integer i : mobs.keySet()){
            for (Integer j : mobs.get(i).keySet()){
                for (Creature creature : mobs.get(i).get(j)){
                    if (creature instanceof Octorok octorok){
                        if (this.equals(App.map) && !octorok.ballPushed && (octorok.getX() == hero.getX() || octorok.getY() == hero.getY())){
                            tempPos = octorok.getPosition().add(octorok.getOrientation().toUnitVector());
                            if (this.canMoveTo(tempPos) && octorok.sees(hero.getPosition()) && (!octorok.getPosition().add(octorok.getOrientation().toUnitVector()).equals(hero.getPosition()) || !octorok.getOrientation().opposite().equals(hero.getOrientation()))){
                                octorok.ball = new OctorokAttackBall(tempPos, octorok.getOrientation());
                                octorok.ballPushed = true;
                                nodes[octorok.ball.ballPosition.getY()][octorok.ball.ballPosition.getX()].getChildren().add(octorok.ball.getAttackBallImage());
                            }
                        }
                        else if (octorok.ballPushed){
                            tempPos = octorok.ball.ballPosition.add(octorok.ball.ballDirection.toUnitVector());
                            nodes[octorok.ball.ballPosition.getY() + octorok.ball.ballDirection.opposite().toUnitVector().getY()][octorok.ball.ballPosition.getX() + octorok.ball.ballDirection.opposite().toUnitVector().getX()].getChildren().remove(octorok.ball.getAttackBallImage());
                            if (tempPos.precedes(upperRight) && tempPos.follows(lowerLeft) && this.canMoveTo(tempPos)){
                                octorok.ball.ballPosition = tempPos;
                                nodes[tempPos.getY()][tempPos.getX()].getChildren().add(octorok.ball.getAttackBallImage());
                            }
                            else {
                                octorok.ballPushed = false;
                                nodes[octorok.ball.ballPosition.getY()][octorok.ball.ballPosition.getX()].getChildren().remove(octorok.ball.getAttackBallImage());
                            }
                        }
                    }
                }
            }
        }
    }

    public void handleOctorokMovement(){
        if (octorokCnt % 2 == 0){
            octorokToMove.get(SOUTH).clear(); octorokToMove.get(NORTH).clear(); octorokToMove.get(EAST).clear(); octorokToMove.get(WEST).clear();
            for (Integer i : mobs.keySet()){
                for (Integer j : mobs.get(i).keySet()){
                    for (Creature creature : mobs.get(i).get(j)){
                        if (creature instanceof Octorok octorok)
                            octorokToMove.get(creature.moveCycle.get(creature.i)).add(octorok);
                    }
                }
            }
        }

        for (MoveDirection direction : octorokToMove.keySet()){
            for (Creature creature : octorokToMove.get(direction)){
                if (creature instanceof Octorok octorok) {
                    if (octorok.prevImage != null)
                        this.nodes[octorok.prevY][octorok.prevX].getChildren().remove(octorok.prevImage);
                    if (octorokCnt % 2 == 0){
                        octorok.move(this, direction);
                        octorok.i += 1;
                    }

                    ImageView octorokImageView = octorok.getCreatureAnimation()[octorokCnt % 2];
                    if (octorok.getHealth() > 0)
                        nodes[octorok.getY()][octorok.getX()].getChildren().add(octorokImageView);
                    octorok.prevImage = octorokImageView;
                    octorok.prevX = octorok.getX();
                    octorok.prevY = octorok.getY();
                    if (octorok.i == octorok.moveCycle.size()) octorok.i = 0;
                }
            }
        }
        octorokCnt += 1;
    }

    public void handleGhiniPushBack(){
        ghiniToMove.get(EAST).clear(); ghiniToMove.get(WEST).clear();
        for (Integer i : mobs.keySet()){
            for (Integer j : mobs.get(i).keySet()){
                for (Creature creature : mobs.get(i).get(j)){
                    if(creature instanceof Ghini ghini && ghini.push){
                        if (ghini.pushCnt > 0){
                            if (ghini.pushDirection == null)
                                ghini.pushDirection = App.swordDirection;
                            ghiniToMove.get(creature.getOrientation()).add(ghini);
                            ghini.pushCnt -= 1;
                        }
                        else{
                            ghini.push = false;
                            ghini.pushDirection = null;
                        }
                    }
                }
            }
        }

        for (MoveDirection direction : ghiniToMove.keySet()){
            for (Creature creature : ghiniToMove.get(direction)){
                if (creature instanceof Ghini ghini){
                    if (ghini.prevImage != null)
                        nodes[ghini.prevY][ghini.prevX].getChildren().remove(ghini.prevImage);

                    ghini.pushBack(this, ghini.pushDirection);

                    if (ghini.getHealth() > 0)
                        nodes[ghini.getY()][ghini.getX()].getChildren().add(ghini.getCreatureAnimation()[0]);
                    ghini.prevImage = ghini.getCreatureAnimation()[0];
                    ghini.prevX = ghini.getX();
                    ghini.prevY = ghini.getY();
                }
            }
        }
    }

    public void handleGhiniMovement(){
        ghiniToMove.get(EAST).clear(); ghiniToMove.get(WEST).clear();
        for (Integer i : mobs.keySet()){
            for (Integer j : mobs.get(i).keySet()){
                for (Creature creature : mobs.get(i).get(j)){
                    if (creature instanceof Ghini ghini && !ghini.push)
                        ghiniToMove.get(creature.moveCycle.get(creature.i)).add(ghini);
                }
            }
        }

        for (MoveDirection direction : ghiniToMove.keySet()){
            for (Creature creature : ghiniToMove.get(direction)){
                if (creature instanceof Ghini){
                    if (creature.prevImage != null)
                        nodes[creature.prevY][creature.prevX].getChildren().remove(creature.prevImage);

                    creature.move(this, direction);
                    creature.i += 1;

                    if (creature.getHealth() > 0)
                        nodes[creature.getY()][creature.getX()].getChildren().add(creature.getCreatureAnimation()[0]);
                    creature.prevImage = creature.getCreatureAnimation()[0];
                    creature.prevX = creature.getX();
                    creature.prevY = creature.getY();
                    if (creature.i == creature.moveCycle.size()) creature.i = 0;
                }
            }
        }
    }

    public void checkIfInContainer(int x, int y){
        if(!this.mobs.containsKey(y))
            this.mobs.put(y, new LinkedHashMap<>());
        if (!this.mobs.get(y).containsKey(x))
            this.mobs.get(y).put(x, new ArrayList<>());
    }

    public void handleZolaMovement(){
        if (this.zola != null && !zola.ballPushed){
            if (this.zola.prevImage != null){
                this.mobs.get(this.zola.prevY).get(this.zola.prevX).remove(this.zola);
                nodes[this.zola.prevY][this.zola.prevX].getChildren().remove(this.zola.prevImage);

                this.checkIfInContainer(this.zola.position.getX(), this.zola.position.getY());
                this.mobs.get(this.zola.position.getY()).get(this.zola.position.getX()).add(this.zola);
            }

            if (zolaCnt % 3 == 2) {
                zola.prevX = zola.getX();
                zola.prevY = zola.getY();
                if (!this.zola.ballPushed){
                    this.zolaAttackBall = new ZolaAttackBall(this.zola.getPosition(), this.zola.getPosition(), hero.getPosition());
                    this.zola.ballPushed = true;
                }
                if (ThreadLocalRandom.current().nextInt(0, 2) == 1)
                    this.zola.move(this, NORTH);
                else
                    this.zola.move(this, SOUTH);

            }
            else{
                if (zola.position == null){
                    int index = zolaPositions.get(ThreadLocalRandom.current().nextInt(0, zolaPositions.size()));
                    zola.position = new Vector2d(index % AbstractMap.width, index / AbstractMap.width);
                    checkIfInContainer(zola.position.getX(), zola.position.getY());
                    this.mobs.get(zola.position.getY()).get(zola.position.getX()).add(zola);
                }
                if (zola.getHealth() > 0)
                    nodes[zola.getY()][zola.getX()].getChildren().add(zola.getCreatureAnimation()[zolaCnt % 3]);
                this.zola.prevImage = zola.getCreatureAnimation()[zolaCnt % 3];
                zola.prevX = zola.getX();
                zola.prevY = zola.getY();
            }

            zolaCnt += 1;
        }
    }

    public void handleZolaAttack(){
        if (this.zolaAttackBall != null){
            Vector2d zolaAttackBallPosition = this.zolaAttackBall.position;
            this.nodes[zolaAttackBallPosition.getY()][zolaAttackBallPosition.getX()].getChildren().remove(this.zolaAttackBall.getAttackBallImage());

            Vector2d newPos;

            newPos = this.zolaAttackBall.getNewBallPosition(this.zolaAttackBall.position.getX() - 1);

            if (newPos.follows(lowerLeft) && newPos.precedes(upperRight))
                this.nodes[newPos.getY()][newPos.getX()].getChildren().add(this.zolaAttackBall.getAttackBallImage());
            else{
                zolaAttackBall = null;
                if (zola != null)
                    zola.ballPushed = false;
            }
        }
    }

    public void checkZolaBallCollision(){
        if (this.equals(App.map) && zolaAttackBall != null){
            int newX = zolaAttackBall.position.getX() - 1;
            Vector2d newPos = new Vector2d(newX, Math.round(zolaAttackBall.a * ((float) newX) + zolaAttackBall.b));
            if (collidesWithHero(newPos)){
                if(heroNotBlocked(zolaAttackBall.position))
                    hero.removeHealth(this, 1);

                nodes[zolaAttackBall.position.getY()][zolaAttackBall.position.getX()].getChildren().remove(zolaAttackBall.getAttackBallImage());
                zolaAttackBall = null;
                if (zola != null)
                    zola.ballPushed = false;
            }
        }
    }

    public void checkOctoroksBallsCollisions(){
        Vector2d tempPos;

        for (Iterator<OctorokAttackBall> iter = octoroksBalls.iterator(); iter.hasNext();){
            OctorokAttackBall ball = iter.next();
            tempPos = ball.ballPosition.add(ball.ballDirection.toUnitVector());

            if((collidesWithHero(tempPos) || collidesWithHero(ball.ballPosition)) && this.equals(App.map)){
                if (heroNotBlocked(ball.ballPosition))
                    hero.removeHealth(App.map, 1);

                nodes[ball.ballPosition.getY()][ball.ballPosition.getX()].getChildren().remove(ball.getAttackBallImage());
                iter.remove();
            }
        }

        for (Integer i : mobs.keySet()){
            for (Integer j : mobs.get(i).keySet()) {
                for (Creature creature : mobs.get(i).get(j)) {
                    if (creature instanceof Octorok octorok && octorok.ballPushed){
                        tempPos = octorok.ball.ballPosition.add(octorok.ball.ballDirection.toUnitVector());
                        nodes[octorok.ball.ballPosition.getY() + octorok.ball.ballDirection.opposite().toUnitVector().getY()][octorok.ball.ballPosition.getX() + octorok.ball.ballDirection.opposite().toUnitVector().getX()].getChildren().remove(octorok.ball.getAttackBallImage());

                        if((collidesWithHero(tempPos) || collidesWithHero(octorok.ball.ballPosition)) && this.equals(App.map)){
                            if (heroNotBlocked(octorok.ball.ballPosition))
                                hero.removeHealth(this, 1);

                            octorok.ballPushed = false;
                            nodes[octorok.ball.ballPosition.getY()][octorok.ball.ballPosition.getX()].getChildren().remove(octorok.ball.getAttackBallImage());
                        }
                    }
                }
            }
        }
    }

    public boolean heroNotBlocked(Vector2d ballPosition){
        return (!hero.getPosition().add(WEST.toUnitVector()).equals(ballPosition) || hero.getOrientation() != WEST) &&
                (!hero.getPosition().add(EAST.toUnitVector()).equals(ballPosition) || hero.getOrientation() != EAST) &&
                (!hero.getPosition().add(SOUTH.toUnitVector()).equals(ballPosition) || hero.getOrientation() != SOUTH) &&
                (!hero.getPosition().add(NORTH.toUnitVector()).equals(ballPosition) || hero.getOrientation() != NORTH);
    }

    public void handleTektiteMovement(){
        if (tektiteCnt % 2 == 0){
            tektiteToMove.get(SOUTH).clear(); tektiteToMove.get(NORTH).clear(); tektiteToMove.get(EAST).clear(); tektiteToMove.get(WEST).clear();
            tektiteToMove.get(NORTH_EAST).clear(); tektiteToMove.get(NORTH_WEST).clear(); tektiteToMove.get(SOUTH_EAST).clear(); tektiteToMove.get(SOUTH_WEST).clear();
            for (Integer i : mobs.keySet()){
                for (Integer j : mobs.get(i).keySet()){
                    for (Creature creature : mobs.get(i).get(j)){
                        if (creature instanceof Tektite tektite)
                            tektiteToMove.get(creature.moveCycle.get(creature.i)).add(tektite);
                    }
                }
            }
        }

        for (MoveDirection direction : tektiteToMove.keySet()){
            for (Creature creature : tektiteToMove.get(direction)){
                if (creature.prevImage != null)
                    nodes[creature.prevY][creature.prevX].getChildren().remove(creature.prevImage);
                if (tektiteCnt % 2 == 0){
                    if (creature.getOrientation() == direction)
                        creature.move(this, direction);
                    creature.move(this, direction);
                    creature.i += 1;
                }
                if (creature.getHealth() > 0)
                    nodes[creature.getY()][creature.getX()].getChildren().add(creature.getCreatureAnimation()[tektiteCnt % 2]);
                creature.prevImage = creature.getCreatureAnimation()[tektiteCnt % 2];
                creature.prevX = creature.getX();
                creature.prevY = creature.getY();
                if (creature.i == creature.moveCycle.size()) creature.i = 0;
            }
        }

        tektiteCnt += 1;
    }

    public static boolean collidesWithHero(Vector2d position){
        return position.equals(hero.getPosition());
    }

    public boolean mobCollidesWithHero(){
        return mobs.containsKey(hero.getY()) && mobs.get(hero.getY()).containsKey(hero.getX()) && mobs.get(hero.getY()).get(hero.getX()).size() > 0
                || this instanceof NorthWestMap && NorthWestMap.boss != null && heroCollidesWithBoss();
    }

    private boolean heroCollidesWithBoss(){
        Vector2d bossPos = NorthWestMap.boss.getPosition();
        return hero.getPosition().equals(bossPos.add(WEST.toUnitVector())) || hero.getPosition().equals(bossPos.add(EAST.toUnitVector())) ||
                hero.getPosition().equals(bossPos.add(NORTH.toUnitVector())) || hero.getPosition().equals(bossPos.add(SOUTH.toUnitVector()))
                || hero.getPosition().equals(bossPos);
    }

    public boolean isOccupied(Vector2d position){
        return occupancyMap[position.getY()][position.getX()];
    }

    public static void addMapChangeObserver(IMapChangeObserver observer){
        MapChangeObserver = observer;
    }

    public static void getMapsReferences(AbstractMap firstMap){
        maps.put("Start", firstMap); maps.put("North", new NorthMap()); maps.put("NorthEast", new NorthEastMap());
        maps.put("East", new EastMap()); maps.put("SouthEast", new SouthEastMap()); maps.put("South", new SouthMap());
        maps.put("SouthWest", new SouthWestMap()); maps.put("West", new WestMap()); maps.put("NorthWest", new NorthWestMap());
    }
}
