package GUI;

import MapsElements.AbstractMap;
import MapsElements.Hero;
import static MapsElements.MoveDirection.*;

import MapsElements.MoveDirection;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class App extends Application {
    protected static Image grassTile;
    static int size = 30;

    static {
        try {
            grassTile = new Image(new FileInputStream("src/main/resources/grassTile.png"), size, size, false, false);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Legend of Zelda");

        Hero hero = new Hero();
        GridPane grid = new GridPane();
        int height = AbstractMap.height;
        int width = AbstractMap.width;

        Group[][] gridNodes = new Group[height][width];

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                gridNodes[i][j] = new Group();
                gridNodes[i][j].getChildren().addAll(new ImageView(grassTile));
                grid.add(gridNodes[i][j], j, i);
            }
        }

        gridNodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);

        scene.setOnKeyPressed(ke -> {
            switch (ke.getCode()){
                case A -> moveHero(hero, WEST, gridNodes);
                case D -> moveHero(hero, EAST, gridNodes);
                case S -> moveHero(hero, SOUTH, gridNodes);
                case W -> moveHero(hero, NORTH, gridNodes);
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
