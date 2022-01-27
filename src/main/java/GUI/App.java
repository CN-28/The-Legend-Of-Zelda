package GUI;

import MapsElements.*;

import static MapsElements.MoveDirection.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    protected static AbstractMap map = new StartingMap();
    protected static final Hero hero = new Hero();
    protected static final Group root = new Group();

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Legend of Zelda");

        map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
        root.getChildren().add(map.grid);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        scene.setOnKeyPressed(ke -> {
            switch (ke.getCode()){
                case A -> moveHero(hero, WEST, map.nodes);
                case D -> moveHero(hero, EAST, map.nodes);
                case S -> moveHero(hero, SOUTH, map.nodes);
                case W -> moveHero(hero, NORTH, map.nodes);
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

    public void moveHero(Hero hero, MoveDirection direction, Group[][] gridNodes){
        gridNodes[hero.getY()][hero.getX()].getChildren().remove(hero.getPicture());
        hero.move(direction);
        gridNodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
    }
}
