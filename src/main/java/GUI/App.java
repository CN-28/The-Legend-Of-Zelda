package GUI;

import MapsElements.*;

import static MapsElements.MoveDirection.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class App extends Application implements IMapChangeObserver {
    protected static Scene scene;
    protected static GridPane interface_bar = new GridPane();
    protected static AbstractMap map = new StartingMap();
    protected static final Hero hero = new Hero();
    protected static final VBox root = new VBox();
    protected static ImageView[] attackViews = new ImageView[8];
    protected static boolean animationRunning = false;
    protected static int iter;
    protected static int frameCount = 0;
    protected static int attackFrameCount = 0;
    protected static AnimationTimer octorokAnimation, attackAnimation;
    static {AbstractMap.getMapsReferences(map);}
    static boolean octorokStarted = false;
    private static Octorok octorok;
    protected static int attackPower = 1;
    protected static Vector2d moveVector;
    protected static LinkedHashMap<MoveDirection, ArrayList<Octorok>> toMove = new LinkedHashMap<>();
    static {
        toMove.put(SOUTH, new ArrayList<>()); toMove.put(EAST, new ArrayList<>()); toMove.put(WEST, new ArrayList<>()); toMove.put(NORTH, new ArrayList<>());
    }

    public void init(){
        AbstractMap.addMapChangeObserver(this);
        this.renderHeroPic();
        this.generateInterfaceBar();
        root.getChildren().add(interface_bar);
        root.getChildren().add(map.grid);
        scene = new Scene(root);
        this.setKeyAssignments(scene);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Legend of Zelda");
        primaryStage.setScene(scene);
        primaryStage.show();


        attackAnimation = new AnimationTimer(){
            public void handle(long currTime){
                if (attackFrameCount % 3 == 0){
                    doHandle();
                    attackFrameCount = 0;
                }
                attackFrameCount += 1;
            }

            private void doHandle(){
                if (iter - 2 >= 0){
                    map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[iter - 2]);
                    if (inBorders())
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[iter - 1]);
                }

                map.nodes[hero.getY()][hero.getX()].getChildren().add(attackViews[iter]);
                if (attackViews[iter + 1] != null && inBorders())
                    map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().add(attackViews[iter + 1]);

                iter += 2;
                if (iter >= attackViews.length){
                    // handleAttackDamageToCreature
                    if (EastMap.octoroks.containsKey(hero.getY()) && EastMap.octoroks.get(hero.getY()).containsKey(hero.getX()) || EastMap.octoroks.containsKey(hero.getY() + moveVector.getY()) && EastMap.octoroks.get(hero.getY() + moveVector.getY()).containsKey(hero.getX() + moveVector.getX())){
                        if (EastMap.octoroks.containsKey(hero.getY()) && EastMap.octoroks.get(hero.getY()).containsKey(hero.getX()))
                            octorok = EastMap.octoroks.get(hero.getY()).get(hero.getX());
                        else
                            octorok = EastMap.octoroks.get(hero.getY() + moveVector.getY()).get(hero.getX() + moveVector.getX());

                        if (octorok.getHealth() > 0){
                            octorok.removeHealth(attackPower);
                            if (octorok.getHealth() <= 0){
                                map.nodes[octorok.getY()][octorok.getX()].getChildren().remove(octorok.getOctorokAnimation()[0]);
                                map.nodes[octorok.getY()][octorok.getX()].getChildren().remove(octorok.getOctorokAnimation()[1]);
                                EastMap.octoroks.get(octorok.getY()).remove(octorok.getX());
                            }
                        }
                    }

                    map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[iter - 2]);
                    if (inBorders())
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[iter - 1]);
                    map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
                    animationRunning = false;
                    attackAnimation.stop();
                }
            }

            private boolean inBorders(){
                return hero.getY() + moveVector.getY() < AbstractMap.height && hero.getY() + moveVector.getY() >= 0 &&
                        hero.getX() + moveVector.getX() < AbstractMap.width && hero.getX() + moveVector.getX() >= 0;
            }
        };


        octorokAnimation = new AnimationTimer() {
            public void handle(long now) {
                if (frameCount % 8 == 0 && map instanceof EastMap){
                    toMove.get(SOUTH).clear(); toMove.get(NORTH).clear(); toMove.get(EAST).clear(); toMove.get(WEST).clear();
                    for (Integer i : EastMap.octoroks.keySet()){
                        for (Octorok octorok : EastMap.octoroks.get(i).values()){
                            if (octorok.prevImage != null)
                                map.nodes[octorok.prevY][octorok.prevX].getChildren().remove(octorok.prevImage);

                            if (octorok.i == 2)
                                toMove.get(octorok.getOrientation().next()).add(octorok);
                            else
                                toMove.get(octorok.getOrientation()).add(octorok);

                        }
                    }

                    for (MoveDirection direction : toMove.keySet()){
                        for (Octorok octorok : toMove.get(direction)){
                            if (octorok.i == 2 || octorok.i == 4)
                                octorok.move(map, direction);
                            map.nodes[octorok.getY()][octorok.getX()].getChildren().add(octorok.getOctorokAnimation()[octorok.i % 2]);
                            octorok.prevImage = octorok.getOctorokAnimation()[octorok.i % 2];
                            octorok.prevX = octorok.getX();
                            octorok.prevY = octorok.getY();
                            if (octorok.i == 4) octorok.i = -1;
                            octorok.i += 1;
                        }
                    }
                    frameCount = 0;
                }
                frameCount += 1;
            }
        };

        AnimationTimer gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (!octorokStarted){
                    octorokStarted = true;
                    octorokAnimation.start();
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
                    case A -> moveHero(hero, WEST);
                    case D -> moveHero(hero, EAST);
                    case S -> moveHero(hero, SOUTH);
                    case W -> moveHero(hero, NORTH);
                    case SPACE -> renderAttackAnimation();
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
        attackViews = hero.getAnimationAttack();
        map.nodes[hero.getY()][hero.getX()].getChildren().remove(hero.getPicture());

        animationRunning = true;
        moveVector = hero.getOrientation().toUnitVector();
        attackAnimation.start();
    }

    private void generateInterfaceBar(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 30; j++){
                Group group = new Group();
                group.getChildren().add(new ImageView(AbstractMap.blackTile));
                interface_bar.add(group, j, i);
            }
        }
    }

    public void renderHeroPic(){
        map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
    }

    public void removeHeroPic(){
        map.nodes[hero.getY()][hero.getX()].getChildren().remove(hero.getPicture());
    }

    public void notifyMapChange(AbstractMap newMap){
        root.getChildren().remove(map.grid);
        map = newMap;
        root.getChildren().add(map.grid);
        hero.setPositionAfterMapChange();
    }
}
