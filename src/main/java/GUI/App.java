package GUI;

import MapsElements.*;

import static MapsElements.MoveDirection.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application implements IMapChangeObserver {
    protected static Scene scene;
    public InterfaceBar interfaceBar = new InterfaceBar();
    public static AbstractMap map;
    public static final VBox root = new VBox();
    protected static ImageView[] attackViews = new ImageView[8];
    public static boolean animationRunning = false;
    public static boolean throwAnimation = false;
    protected static int iter;
    protected static int attackFrameCount = 0;
    public static AnimationTimer attackAnimation;
    static {AbstractMap.getMapsReferences(new StartingMap());
    map = AbstractMap.maps.get("Start");}
    private static Creature mob;
    protected static int attackPower = 1;
    protected static Vector2d moveVector;
    private static boolean isRunning = false;

    public void init(){
        AbstractMap.addMapChangeObserver(this);
        this.renderHeroPic();
        interfaceBar.generateInterfaceBar();
        root.getChildren().add(map.grid);
        scene = new Scene(root);
        this.setKeyAssignments(scene);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Legend of Zelda");
        primaryStage.setScene(scene);
        primaryStage.show();


        attackAnimation = new AnimationTimer(){
            private final Hero hero = AbstractMap.hero;
            private Vector2d swordPos;
            private MoveDirection swordDir;
            public void handle(long currTime){
                if (attackFrameCount % 3 == 0){
                    doHandle();
                    attackFrameCount = 0;
                }
                attackFrameCount += 1;
            }

            private void doHandle(){
                if (!throwAnimation){
                    if (iter - 2 >= 0){
                        map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[iter - 2]);
                        if (inBorders())
                            map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[iter - 1]);
                    }

                    map.nodes[hero.getY()][hero.getX()].getChildren().add(attackViews[iter]);
                    if (attackViews[iter + 1] != null && inBorders())
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().add(attackViews[iter + 1]);

                    iter += 2;
                    if (iter == 4 && inBorders() && !map.isOccupied(hero.getPosition().add(moveVector)) && hero.getHealth() == Hero.maxHealth && !(App.map.mobs.containsKey(hero.getY()) && App.map.mobs.get(hero.getY()).containsKey(hero.getX()) && App.map.mobs.get(hero.getY()).get(hero.getX()).size() > 0
                            || App.map.mobs.containsKey(hero.getY() + moveVector.getY()) && App.map.mobs.get(hero.getY() + moveVector.getY()).containsKey(hero.getX() + moveVector.getX()) && App.map.mobs.get(hero.getY() + moveVector.getY()).get(hero.getX() + moveVector.getX()).size() > 0))
                        throwAnimation = true;


                    if (iter >= attackViews.length){
                        handleAttackDamageToNormalCreature(hero.getPosition(), hero.getPosition().add(moveVector));

                        handleBossAttackDamage(hero.getPosition(), hero.getPosition().add(moveVector));

                        map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[iter - 2]);
                        if (inBorders())
                            map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[iter - 1]);
                        map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
                        animationRunning = false;
                        attackAnimation.stop();
                    }
                }
                else{
                    if (iter == 4){
                        swordPos = hero.getPosition().add(moveVector);
                        swordDir = hero.getOrientation();

                        map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[iter - 2]);
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[iter - 1]);

                        map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().add(hero.getSwordPicture(swordDir));
                        animationRunning = false;
                        iter = 0;
                    }
                    else{
                        map.nodes[swordPos.getY()][swordPos.getX()].getChildren().remove(hero.getSwordPicture(swordDir));
                        Vector2d newPos = swordPos.add(moveVector);
                        if (newPos.follows(AbstractMap.lowerLeft) && newPos.precedes(AbstractMap.upperRight) && !map.isOccupied(newPos) && !handleBossAttackDamage(swordPos, swordPos)
                        && !handleAttackDamageToNormalCreature(swordPos, swordPos)){
                            swordPos = newPos;
                            map.nodes[swordPos.getY()][swordPos.getX()].getChildren().add(hero.getSwordPicture(swordDir));
                        }
                        else{
                            throwAnimation = false;
                            attackAnimation.stop();
                        }
                    }
                }
            }

            private boolean handleAttackDamageToNormalCreature(Vector2d heroPos, Vector2d swordPos){
                if (App.map.mobs.containsKey(heroPos.getY()) && App.map.mobs.get(heroPos.getY()).containsKey(heroPos.getX()) && App.map.mobs.get(heroPos.getY()).get(heroPos.getX()).size() > 0
                        || App.map.mobs.containsKey(swordPos.getY()) && App.map.mobs.get(swordPos.getY()).containsKey(swordPos.getX()) && App.map.mobs.get(swordPos.getY()).get(swordPos.getX()).size() > 0){
                    if (App.map.mobs.containsKey(heroPos.getY()) && App.map.mobs.get(heroPos.getY()).containsKey(heroPos.getX()) && App.map.mobs.get(heroPos.getY()).get(heroPos.getX()).size() > 0)
                        mob = App.map.mobs.get(heroPos.getY()).get(heroPos.getX()).get(App.map.mobs.get(heroPos.getY()).get(heroPos.getX()).size() - 1);
                    else
                        mob = App.map.mobs.get(swordPos.getY()).get(swordPos.getX()).get(App.map.mobs.get(swordPos.getY()).get(swordPos.getX()).size() - 1);

                    if (mob.getHealth() > 0){
                        mob.removeHealth(attackPower);
                        if (mob.getHealth() <= 0)
                            mob.removeCreature();
                    }
                    return true;
                }
                return false;
            }

            private boolean handleBossAttackDamage(Vector2d heroPos, Vector2d swordPos){
                if (map instanceof NorthWestMap && NorthWestMap.boss != null){
                    collidesWithBoss(heroPos);
                    if (NorthWestMap.boss != null)
                        collidesWithBoss(swordPos);
                    return true;
                }
                return false;
            }

            private boolean inBorders(){
                return hero.getY() + moveVector.getY() < AbstractMap.height && hero.getY() + moveVector.getY() >= 0 &&
                        hero.getX() + moveVector.getX() < AbstractMap.width && hero.getX() + moveVector.getX() >= 0;
            }

            private void collidesWithBoss(Vector2d position){
                Vector2d bossPos = NorthWestMap.boss.getPosition();
                if (position.equals(bossPos.add(WEST.toUnitVector())))
                    NorthWestMap.boss.removeHealth(1, "left");
                else if (position.equals(bossPos.add(EAST.toUnitVector())))
                    NorthWestMap.boss.removeHealth(1, "right");
                else if (position.equals(bossPos.add(NORTH.toUnitVector())))
                    NorthWestMap.boss.removeHealth(1, "top");
                else if (position.equals(bossPos.add(SOUTH.toUnitVector())))
                    NorthWestMap.boss.removeHealth(1, "bot");
            }
        };

        AnimationTimer gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (!isRunning){
                    AbstractMap.maps.get("East").animation.start();
                    AbstractMap.maps.get("SouthEast").animation.start();
                    AbstractMap.maps.get("North").animation.start();
                    AbstractMap.maps.get("NorthEast").animation.start();
                    AbstractMap.maps.get("South").animation.start();
                    AbstractMap.maps.get("SouthWest").animation.start();
                    AbstractMap.maps.get("West").animation.start();
                    AbstractMap.maps.get("NorthWest").animation.start();
                    isRunning = true;
                }
            }
        };
        gameLoop.start();
    }

    public static void main(String[] args){
        launch(args);
    }

    private void setKeyAssignments(Scene scene){
        scene.setOnKeyPressed(ke -> {
            if (!animationRunning){
                switch (ke.getCode()) {
                    case A -> moveHero(AbstractMap.hero, WEST);
                    case D -> moveHero(AbstractMap.hero, EAST);
                    case S -> moveHero(AbstractMap.hero, SOUTH);
                    case W -> moveHero(AbstractMap.hero, NORTH);
                    case SPACE -> {
                        if (!throwAnimation)
                            renderAttackAnimation();
                    }
                }
                ke.consume();
            }
        });
    }

    public void moveHero(Hero hero, MoveDirection direction){
        this.removeHeroPic();
        hero.move(map, direction);
        this.renderHeroPic();
    }

    public void renderAttackAnimation(){
        iter = 0;
        attackViews = AbstractMap.hero.getAnimationAttack();
        map.nodes[AbstractMap.hero.getY()][AbstractMap.hero.getX()].getChildren().remove(AbstractMap.hero.getPicture());

        animationRunning = true;
        moveVector = AbstractMap.hero.getOrientation().toUnitVector();
        attackAnimation.start();
    }

    public void renderHeroPic(){
        map.nodes[AbstractMap.hero.getY()][AbstractMap.hero.getX()].getChildren().add(AbstractMap.hero.getPicture());
    }

    public void removeHeroPic(){
        map.nodes[AbstractMap.hero.getY()][AbstractMap.hero.getX()].getChildren().remove(AbstractMap.hero.getPicture());
    }

    public void notifyMapChange(AbstractMap newMap){
        root.getChildren().remove(map.grid);
        map = newMap;
        root.getChildren().add(map.grid);
        AbstractMap.hero.setPositionAfterMapChange();
    }
}
