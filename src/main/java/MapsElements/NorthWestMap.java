package MapsElements;

import GUI.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Iterator;

import static MapsElements.MoveDirection.*;

public class NorthWestMap extends AbstractMap {
    public static final Vector2d bottomLeftPassageBorder = new Vector2d(2 * width/5 + 1, height);
    public static final Vector2d bottomRightPassageBorder = new Vector2d(3 * width/5 - 2, height);
    public static Manhandla boss;
    public static ArrayList<ManhandlaAttackBall> manhandlaAttackBalls = new ArrayList<>();

    public NorthWestMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.occupancyMap[i][j] = true;
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
                else{
                    this.nodes[i][j].getChildren().add(new ImageView(grayTile));
                    this.occupancyMap[i][j] = false;
                }
                this.grid.add(this.nodes[i][j], j, i);
            }
        }
        addBigTree(18, 9); addBigTree(18, 18);
        addBoss(14, 8);

        animation = new AnimationTimer() {
            public void handle(long now) {

                if (frameCount % 12 == 0 && App.map instanceof NorthWestMap)
                    handleManhandlaAttack();

                if (boss != null){
                    if (frameCount % 8 == 0 && frameCount != 48)
                        handleManhandlaAnimation();
                    else if (frameCount == 48){
                        handleManhandlaMovement();
                        frameCount = 0;
                    }
                }

                frameCount += 1;

            }
        };
    }

    public void addBigTree(int i, int j){
        this.nodes[i][j].getChildren().add(new ImageView(upperLeftGrayTreeTile));
        this.occupancyMap[i][j] = true;
        this.nodes[i + 1][j].getChildren().add(new ImageView(bottomLeftGrayTreeTile));
        this.occupancyMap[i + 1][j] = true;
        this.nodes[i + 1][j + 1].getChildren().add(new ImageView(bottomMiddleGrayTreeTile));
        this.occupancyMap[i + 1][j + 1] = true;
        this.nodes[i][j + 1].getChildren().add(new ImageView(upperMiddleGrayTreeTile));
        this.occupancyMap[i][j + 1] = true;
        this.nodes[i][j + 2].getChildren().add(new ImageView(upperRightGrayTreeTile));
        this.occupancyMap[i][j + 2] = true;
        this.nodes[i + 1][j + 2].getChildren().add(new ImageView(bottomRightGrayTreeTile));
        this.occupancyMap[i + 1][j + 2] = true;
    }

    public void addBoss(int x, int y){
        boss = new Manhandla(x, y, new MoveDirection[]{NORTH_WEST, NORTH_WEST, WEST, WEST, SOUTH_WEST, SOUTH, SOUTH, SOUTH_EAST, EAST, EAST, NORTH_EAST, NORTH_EAST,
        NORTH_EAST, NORTH_EAST, EAST, EAST, SOUTH_EAST, SOUTH, SOUTH, SOUTH_WEST, WEST, WEST, NORTH_WEST, NORTH_WEST});

        this.nodes[y][x].getChildren().add(boss.getCreatureAnimation()[0]);
        this.nodes[y - 1][x].getChildren().add(boss.getCreatureAnimation()[1]);
        this.nodes[y][x + 1].getChildren().add(boss.getCreatureAnimation()[2]);
        this.nodes[y + 1][x].getChildren().add(boss.getCreatureAnimation()[3]);
        this.nodes[y][x - 1].getChildren().add(boss.getCreatureAnimation()[4]);
    }

    private void handleManhandlaAnimation(){
        Vector2d currPosition = boss.getPosition();
        int x = currPosition.getX(); int y = currPosition.getY();

        this.removeManhandlaRender(y, x);

        this.addManhandlaRender(y, x);
        boss.attackAnimation = !boss.attackAnimation;
    }

    private void removeManhandlaRender(int y, int x){
        int var = 0;
        if (!boss.attackAnimation)
            var += 4;

        if (boss.topAlive) this.nodes[y - 1][x].getChildren().remove(boss.getCreatureAnimation()[1 + var]);
        if (boss.rightAlive) this.nodes[y][x + 1].getChildren().remove(boss.getCreatureAnimation()[2 + var]);
        if (boss.botAlive) this.nodes[y + 1][x].getChildren().remove(boss.getCreatureAnimation()[3 + var]);
        if (boss.leftAlive) this.nodes[y][x - 1].getChildren().remove(boss.getCreatureAnimation()[4 + var]);
    }

    private void addManhandlaRender(int y, int x){
        int var = 0;
        if (boss.attackAnimation)
            var += 4;

        if (boss.topAlive) this.nodes[y - 1][x].getChildren().add(boss.getCreatureAnimation()[1 + var]);
        if (boss.rightAlive) this.nodes[y][x + 1].getChildren().add(boss.getCreatureAnimation()[2 + var]);
        if (boss.botAlive) this.nodes[y + 1][x].getChildren().add(boss.getCreatureAnimation()[3 + var]);
        if (boss.leftAlive) this.nodes[y][x - 1].getChildren().add(boss.getCreatureAnimation()[4 + var]);
    }

    public void handleManhandlaMovement(){
        Vector2d currPosition = boss.getPosition();
        int x = currPosition.getX(); int y = currPosition.getY();

        this.nodes[y][x].getChildren().remove(boss.getCreatureAnimation()[0]);
        this.removeManhandlaRender(y, x);

        boss.move();

        currPosition = boss.getPosition();
        x = currPosition.getX(); y = currPosition.getY();
        this.nodes[y][x].getChildren().add(boss.getCreatureAnimation()[0]);
        this.addManhandlaRender(y, x);
        boss.attackAnimation = !boss.attackAnimation;
    }


    public void handleManhandlaAttack(){
        if (boss != null){
            if (!boss.leftAttacking){
                manhandlaAttackBalls.add(new ManhandlaAttackBall(boss.getPosition().add(WEST.toUnitVector()), AbstractMap.hero.getPosition(), WEST));
                boss.leftAttacking = true;
            }
            if (!boss.topAttacking){
                manhandlaAttackBalls.add(new ManhandlaAttackBall(boss.getPosition().add(NORTH.toUnitVector()), AbstractMap.hero.getPosition(), NORTH));
                boss.topAttacking = true;
            }
            if (!boss.rightAttacking){
                manhandlaAttackBalls.add(new ManhandlaAttackBall(boss.getPosition().add(EAST.toUnitVector()), AbstractMap.hero.getPosition(), EAST));
                boss.rightAttacking = true;
            }
            if (!boss.botAttacking){
                manhandlaAttackBalls.add(new ManhandlaAttackBall(boss.getPosition().add(SOUTH.toUnitVector()), AbstractMap.hero.getPosition(), SOUTH));
                boss.botAttacking = true;
            }
        }

        for (Iterator<ManhandlaAttackBall> iter = manhandlaAttackBalls.iterator(); iter.hasNext();){
            ManhandlaAttackBall ball = iter.next();
            if (!(ball.position.precedes(upperRight) && ball.position.follows(lowerLeft)) || isOccupied(ball.position) || collidesWithHero(ball.position)){
                if (collidesWithHero(ball.position))
                    hero.removeHealth(this, 1);

                if (boss != null){
                    switch (ball.direction){
                        case WEST -> boss.leftAttacking = false;
                        case EAST -> boss.rightAttacking = false;
                        case SOUTH -> boss.botAttacking = false;
                        case NORTH -> boss.topAttacking = false;
                    }
                }
                if (ball.prevY != -1)
                    this.nodes[ball.prevY][ball.prevX].getChildren().remove(ball.getAttackBallImage());
                iter.remove();
            }
            else{
                if (ball.prevY != -1)
                    this.nodes[ball.prevY][ball.prevX].getChildren().remove(ball.getAttackBallImage());

                this.nodes[ball.position.getY()][ball.position.getX()].getChildren().add(ball.getAttackBallImage());
                if (ball.startPos.getX() < ball.position.getX())
                    ball.updatePosition(ball.position.getX() + 1);
                else if (ball.startPos.getX() > ball.position.getX())
                    ball.updatePosition(ball.position.getX() - 1);
                else
                    ball.updatePosition(ball.position.getX());
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(bottomLeftPassageBorder) && position.precedes(bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("West"));

        return false;
    }
}
