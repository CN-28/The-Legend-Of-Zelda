package GUI;

import MapsElements.*;

import static MapsElements.MoveDirection.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application implements IMapChangeObserver {
    protected static AbstractMap map = new StartingMap();
    protected static final Hero hero = new Hero();
    protected static final Group root = new Group();
    static {AbstractMap.getMapsReferences(map);}

    public void start(Stage primaryStage) {
        AbstractMap.addObserver(this);
        primaryStage.setTitle("The Legend of Zelda");

        this.renderHeroPic();
        root.getChildren().add(map.grid);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        scene.setOnKeyPressed(ke -> {
            switch (ke.getCode()){
                case A -> moveHero(hero, WEST);
                case D -> moveHero(hero, EAST);
                case S -> moveHero(hero, SOUTH);
                case W -> moveHero(hero, NORTH);
            }
            ke.consume();
        });

        new AnimationTimer(){
            public void handle(long currentNanoTime){

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
