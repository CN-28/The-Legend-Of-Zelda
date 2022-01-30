package GUI;

import MapsElements.*;

import static MapsElements.MoveDirection.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class App extends Application implements IMapChangeObserver {
    protected static AbstractMap map = new StartingMap();
    protected static final Hero hero = new Hero();
    protected static final Group root = new Group();
    protected static ImageView[] attackViews = new ImageView[8];
    protected static boolean animationRunning = false;
    protected static int iter = 0;
    static {AbstractMap.getMapsReferences(map);}

    public void start(Stage primaryStage) {
        AbstractMap.addObserver(this);
        primaryStage.setTitle("The Legend of Zelda");

        this.renderHeroPic();
        root.getChildren().add(map.grid);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

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

        new AnimationTimer() {
            public void handle(long currentNanoTime) {

            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
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
        Vector2d moveVector = hero.getOrientation().toUnitVector();
        new AnimationTimer(){
            public void handle(long currTime){
                doHandle();
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
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (iter >= attackViews.length){
                    map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[iter - 2]);
                    if (inBorders())
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[iter - 1]);
                    map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
                    animationRunning = false;
                    stop();
                }
            }

            private boolean inBorders(){
                return hero.getY() + moveVector.getY() < AbstractMap.height && hero.getY() + moveVector.getY() >= 0 &&
                        hero.getX() + moveVector.getX() < AbstractMap.width && hero.getX() + moveVector.getX() >= 0;
            }
        }.start();
    }

    public void renderHeroPic(){
        map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
    }

    public void removeHeroPic(){
        map.nodes[hero.getY()][hero.getX()].getChildren().remove(hero.getPicture());
    }

    public void notifyMapChange(AbstractMap newMap){
        root.getChildren().clear();
        map = newMap;
        root.getChildren().add(map.grid);
        hero.setPositionAfterMapChange();
    }
}
