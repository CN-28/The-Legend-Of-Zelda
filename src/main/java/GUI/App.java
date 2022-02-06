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

public class App extends Application implements IMapChangeObserver {
    protected static Scene scene;
    protected static GridPane interface_bar = new GridPane();
    public static AbstractMap map;
    protected static final VBox root = new VBox();
    protected static ImageView[] attackViews = new ImageView[8];
    public static boolean animationRunning = false;
    protected static int iter;
    protected static int attackFrameCount = 0;
    public static AnimationTimer attackAnimation;
    static {AbstractMap.getMapsReferences(new StartingMap());
    map = AbstractMap.maps.get("Start");}
    private static Creature mob;
    protected static int attackPower = 1;
    protected static Vector2d moveVector;

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
            private final Hero hero = AbstractMap.hero;
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
                    if (App.map.mobs.containsKey(hero.getY()) && App.map.mobs.get(hero.getY()).containsKey(hero.getX()) && App.map.mobs.get(hero.getY()).get(hero.getX()).size() > 0
                            || App.map.mobs.containsKey(hero.getY() + moveVector.getY()) && App.map.mobs.get(hero.getY() + moveVector.getY()).containsKey(hero.getX() + moveVector.getX()) && App.map.mobs.get(hero.getY() + moveVector.getY()).get(hero.getX() + moveVector.getX()).size() > 0){
                        if (App.map.mobs.containsKey(hero.getY()) && App.map.mobs.get(hero.getY()).containsKey(hero.getX()) && App.map.mobs.get(hero.getY()).get(hero.getX()).size() > 0)
                            mob = App.map.mobs.get(hero.getY()).get(hero.getX()).get(App.map.mobs.get(hero.getY()).get(hero.getX()).size() - 1);
                        else
                            mob = App.map.mobs.get(hero.getY() + moveVector.getY()).get(hero.getX() + moveVector.getX()).get(App.map.mobs.get(hero.getY() + moveVector.getY()).get(hero.getX() + moveVector.getX()).size() - 1);

                        if (mob.getHealth() > 0){
                            mob.removeHealth(attackPower);
                            if (mob.getHealth() <= 0)
                                mob.removeCreature();
                        }
                    }

                    map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[iter - 2]);
                    if (inBorders())
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[iter - 1]);
                    map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
                    animationRunning = false;
                    attackAnimation.stop();
                    App.map.animation.start();
                }
            }

            private boolean inBorders(){
                return hero.getY() + moveVector.getY() < AbstractMap.height && hero.getY() + moveVector.getY() >= 0 &&
                        hero.getX() + moveVector.getX() < AbstractMap.width && hero.getX() + moveVector.getX() >= 0;
            }
        };

        AnimationTimer gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                AbstractMap.maps.get("East").animation.start();
                AbstractMap.maps.get("SouthEast").animation.start();
                AbstractMap.maps.get("North").animation.start();
                AbstractMap.maps.get("NorthEast").animation.start();
                AbstractMap.maps.get("South").animation.start();
                AbstractMap.maps.get("SouthWest").animation.start();
                AbstractMap.maps.get("West").animation.start();
                AbstractMap.maps.get("NorthWest").animation.start();
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
        attackViews = AbstractMap.hero.getAnimationAttack();
        map.nodes[AbstractMap.hero.getY()][AbstractMap.hero.getX()].getChildren().remove(AbstractMap.hero.getPicture());

        animationRunning = true;
        moveVector = AbstractMap.hero.getOrientation().toUnitVector();
        App.map.animation.stop();
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
