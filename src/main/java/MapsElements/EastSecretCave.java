package MapsElements;

import GUI.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static MapsElements.MoveDirection.*;


public class EastSecretCave extends AbstractCave{
    public final ImageView[] EastCaveString = new ImageView[11];
    public Vector2d leftItemPos, rightItemPos;
    public static boolean itemsPickedUp;

    public EastSecretCave(){
        this.leftItemPos = new Vector2d(12, 10);
        this.rightItemPos = new Vector2d(16, 10);
        this.cavePosition = new Vector2d(8, 3);
        for (int i = 0; i < AbstractMap.height; i++){
            for (int j = 0; j < AbstractMap.width; j++){
                this.nodes[i][j] = new Group();
                if (i < 4 || i > 14 && (j < 12 || j > 16) || j < 6 || j > 22){
                    this.nodes[i][j].getChildren().add(new ImageView(caveTile));
                    this.occupancyMap[i][j] = true;
                }
                else
                    this.nodes[i][j].getChildren().add(new ImageView(InterfaceBar.blackTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }

        for (int i = 1; i <= 11; i++){
            try {
                EastCaveString[i - 1] = new ImageView(new Image(new FileInputStream("src/main/resources/freeString" + i + ".png"), AbstractMap.size, AbstractMap.size, false, false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.nodes[5][8 + i].getChildren().add(EastCaveString[i - 1]);
        }

        addOldMan(14, 7);
        addFire(18, 7);
        addFire(10, 7);
        addRandomItem(12, 10);
        addRandomItem(16, 10);

        pickUpAnimation = new AnimationTimer() {
            Vector2d itemPos;
            boolean isWhiteSword = false;
            Image item;
            public void handle(long now) {
                handlePickUpAnimation();
                frameCount += 1;
            }

            private void handlePickUpAnimation(){
                if (frameCount == 10){
                    itemPos = hero.getPosition();
                    item = itemsDrawnImages.get(itemPos);
                    App.map.nodes[hero.getY()][hero.getX()].getChildren().remove(hero.getPicture());
                    if (item.equals(whiteSword))
                        isWhiteSword = true;
                    for (Vector2d pos : itemsDrawn.keySet())
                       App.map.nodes[pos.getY()][pos.getX()].getChildren().remove(itemsDrawn.get(pos));

                    if (isWhiteSword){
                        App.map.nodes[hero.getY()][hero.getX()].getChildren().add(pickUpWeaponAnimation);
                        App.map.nodes[hero.getY() - 1][hero.getX()].getChildren().add(pickUpWhiteSwordAnimation);
                        hero.changeToWhiteSword();

                        InterfaceBar.nodes[0][21].getChildren().remove(InterfaceBar.woodSwordTop);
                        InterfaceBar.nodes[1][21].getChildren().remove(InterfaceBar.woodSwordBot);
                        InterfaceBar.nodes[0][21].getChildren().add(InterfaceBar.whiteSwordTop);
                        InterfaceBar.nodes[1][21].getChildren().add(InterfaceBar.whiteSwordBot);
                    }
                    else{
                        App.map.nodes[hero.getY()][hero.getX()].getChildren().add(pickUpItemAnimation);
                        App.map.nodes[hero.getY() - 1][hero.getX()].getChildren().add(itemsDrawn.get(itemPos));
                    }

                    App.map.nodes[7][14].getChildren().remove(oldMan);
                    App.map.occupancyMap[7][14] = false;

                    for (int i = 0; i < EastCaveString.length; i++)
                        App.map.nodes[5][9 + i].getChildren().remove(EastCaveString[i]);
                }
                else if (frameCount == 120){
                    if (isWhiteSword){
                        App.map.nodes[hero.getY()][hero.getX()].getChildren().remove(pickUpWeaponAnimation);
                        App.map.nodes[hero.getY() - 1][hero.getX()].getChildren().remove(pickUpWhiteSwordAnimation);
                    }
                    else{
                        App.map.nodes[hero.getY()][hero.getX()].getChildren().remove(pickUpItemAnimation);
                        App.map.nodes[hero.getY() - 1][hero.getX()].getChildren().remove(itemsDrawn.get(itemPos));
                        if (item.equals(bomb)) {
                            hero.bombCnt += 1;
                            InterfaceBar.updateBombCounter(hero.bombCnt);
                        } else if (item.equals(healthPotion)) {
                            hero.healthPotionCnt += 1;
                            InterfaceBar.updateHealthPotionCounter(hero.healthPotionCnt);
                        } else if (item.equals(bigHeart)){
                            InterfaceBar.updateHpAfterBigHeartUse(hero.getHealth());
                            hero.setHealth(hero.getHealth() + 2);
                            Hero.maxHealth += 2;
                        }
                    }

                    App.map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
                    App.animationRunning = false;
                    itemsPickedUp = true;
                    pickUpAnimation.stop();
                }
            }
        };
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(EastMap.bottomLeftPassageBorder.add(WEST.toUnitVector())) && position.precedes(EastMap.bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("East"));

        return false;
    }
}
