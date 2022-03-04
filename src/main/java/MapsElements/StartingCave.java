package MapsElements;

import GUI.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StartingCave extends AbstractCave{
    public static ImageView woodenSword, pickUpWoodenSwordAnimation;
    public static final ImageView[] startingCaveString = new ImageView[19];
    public static final Vector2d woodenSwordPosition = new Vector2d(15, 11);
    static {
        try {
            woodenSword = new ImageView(new Image(new FileInputStream("src/main/resources/woodenSword.png"), AbstractMap.size, AbstractMap.size, false, false));
            pickUpWoodenSwordAnimation = new ImageView(new Image(new FileInputStream("src/main/resources/pickUpWoodenSword.png"), AbstractMap.size, AbstractMap.size, false, false));
            for (int i = 1; i <= 19; i++)
                startingCaveString[i - 1] = new ImageView(new Image(new FileInputStream("src/main/resources/string" + i + ".png"), AbstractMap.size, AbstractMap.size, false, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public StartingCave(){
        for (int i = 0; i < AbstractMap.height; i++){
            for (int j = 0; j < AbstractMap.width; j++){
                this.nodes[i][j] = new Group();
                if (i < 4 || i > 15 || j < 7 || j > 23 && (i < 8 || i >= 12)){
                    this.nodes[i][j].getChildren().add(new ImageView(caveTile));
                    this.occupancyMap[i][j] = true;
                }
                else
                    this.nodes[i][j].getChildren().add(new ImageView(InterfaceBar.blackTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }

        this.cavePosition = new Vector2d(3, 9);
        addOldMan(15, 9);
        addFire(11, 9);
        addFire(19, 9);
        this.nodes[woodenSwordPosition.getY()][woodenSwordPosition.getX()].getChildren().add(woodenSword);

        for (int i = 0; i < startingCaveString.length; i++){
            if (i < 10)
                this.nodes[6][10 + i].getChildren().add(startingCaveString[i]);
            else
                this.nodes[7][i + 1].getChildren().add(startingCaveString[i]);
        }

        pickUpAnimation = new AnimationTimer() {
            public void handle(long now) {
                handlePickUpAnimation();
                frameCount += 1;
            }

            private void handlePickUpAnimation(){
                if (frameCount == 10){
                    App.map.nodes[hero.getY()][hero.getX()].getChildren().remove(hero.getPicture());
                    App.map.nodes[woodenSwordPosition.getY()][woodenSwordPosition.getX()].getChildren().remove(woodenSword);
                    App.map.nodes[hero.getY()][hero.getX()].getChildren().add(pickUpWeaponAnimation);
                    App.map.nodes[hero.getY() - 1][hero.getX()].getChildren().add(pickUpWoodenSwordAnimation);

                    Hero.hasWoodenSword = true;
                    InterfaceBar.nodes[0][21].getChildren().add(InterfaceBar.woodSwordTop);
                    InterfaceBar.nodes[1][21].getChildren().add(InterfaceBar.woodSwordBot);
                    App.map.nodes[9][15].getChildren().remove(oldMan);
                    App.map.occupancyMap[9][15] = false;

                    for (int i = 0; i < startingCaveString.length; i++){
                        if (i < 10)
                            App.map.nodes[6][10 + i].getChildren().remove(startingCaveString[i]);
                        else
                            App.map.nodes[7][i + 1].getChildren().remove(startingCaveString[i]);
                    }
                }
                else if (frameCount == 120){
                    App.map.nodes[hero.getY()][hero.getX()].getChildren().remove(pickUpWeaponAnimation);
                    App.map.nodes[hero.getY() - 1][hero.getX()].getChildren().remove(pickUpWoodenSwordAnimation);
                    App.map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());

                    App.animationRunning = false;
                    pickUpAnimation.stop();
                }
            }
        };
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(StartingMap.rightBottomPassageBorder) && position.precedes(StartingMap.rightUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("Start"));

        return false;
    }
}
