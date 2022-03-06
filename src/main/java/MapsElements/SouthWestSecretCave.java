package MapsElements;

import GUI.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SouthWestSecretCave extends AbstractCave{
    public final ImageView[] merchantString = new ImageView[11];
    public ImageView gold, X;
    public static boolean itemsBought;
    public Vector2d leftItemPos, midItemPos, rightItemPos;
    public int leftItemPrice, midItemPrice, rightItemPrice;

    public SouthWestSecretCave(){
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

        try {
            for (int i = 1; i <= 11; i++){
                merchantString[i - 1] = new ImageView(new Image(new FileInputStream("src/main/resources/merchantString" + i + ".png"), AbstractMap.size, AbstractMap.size, false, false));
                this.nodes[6][9 + i].getChildren().add(merchantString[i - 1]);
            }
            this.X = new ImageView(new Image(new FileInputStream("src/main/resources/X.png"), AbstractMap.size, AbstractMap.size, false, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.gold = new ImageView(AbstractMap.gold);

        this.nodes[12][9].getChildren().add(this.gold);
        this.nodes[12][10].getChildren().add(this.X);
        this.cavePosition = new Vector2d(4, 6);
        addMerchant(15, 8);
        addFire(11, 8);
        addFire(19, 8);
        leftItemPos = new Vector2d(12, 11);
        midItemPos = new Vector2d(15, 11);
        rightItemPos = new Vector2d(18, 11);
        addRandomItem(leftItemPos.getX(), leftItemPos.getY());
        renderPriceImages(leftItemPos);
        addRandomItem(midItemPos.getX(), midItemPos.getY());
        renderPriceImages(midItemPos);
        addRandomItem(rightItemPos.getX(), rightItemPos.getY());
        renderPriceImages(rightItemPos);

        pickUpAnimation = new AnimationTimer() {
            Vector2d itemPos;
            boolean isWhiteSword = false;
            Image item;
            public void handle(long now) {
                handleBuyingItems();
                frameCount += 1;
            }

            private void handleBuyingItems(){
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

                    InterfaceBar.updateGoldCounter(hero.goldCnt);
                    App.map.nodes[8][15].getChildren().remove(merchant);
                    App.map.occupancyMap[8][15] = false;
                    nodes[12][9].getChildren().remove(gold);
                    nodes[12][10].getChildren().remove(X);
                    nodes[leftItemPos.getY() + 1][leftItemPos.getX()].getChildren().clear();
                    nodes[leftItemPos.getY() + 1][leftItemPos.getX()].getChildren().add(new ImageView(InterfaceBar.blackTile));
                    nodes[midItemPos.getY() + 1][midItemPos.getX()].getChildren().clear();
                    nodes[midItemPos.getY() + 1][midItemPos.getX()].getChildren().add(new ImageView(InterfaceBar.blackTile));
                    nodes[rightItemPos.getY() + 1][rightItemPos.getX()].getChildren().clear();
                    nodes[rightItemPos.getY() + 1][rightItemPos.getX()].getChildren().add(new ImageView(InterfaceBar.blackTile));

                    for (int i = 0; i < merchantString.length; i++)
                        App.map.nodes[6][10 + i].getChildren().remove(merchantString[i]);
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
                    itemsBought = true;
                    pickUpAnimation.stop();
                }
            }
        };
    }

    public boolean canGetItem(){
        Vector2d position = hero.getPosition();
        if (position.equals(midItemPos) && hero.goldCnt >= midItemPrice){
            hero.goldCnt -= midItemPrice;
            return true;
        }
        if (position.equals(leftItemPos) && hero.goldCnt >= leftItemPrice){
            hero.goldCnt -= leftItemPrice;
            return true;
        }
        if (position.equals(rightItemPos) && hero.goldCnt >= rightItemPrice){
            hero.goldCnt -= rightItemPrice;
            return true;
        }
        return false;
    }

    public void renderPriceImages(Vector2d position){
        int price = 0;
        if (itemsDrawnImages.get(position).equals(bomb)){
            nodes[position.getY() + 1][position.getX()].getChildren().add(new ImageView(InterfaceBar.rightNumbers[5]));
            price = 5;
        }
        else if (itemsDrawnImages.get(position).equals(bigHeart)){
            nodes[position.getY() + 1][position.getX()].getChildren().add(new ImageView(InterfaceBar.leftNumbers[1]));
            nodes[position.getY() + 1][position.getX()].getChildren().add(new ImageView(InterfaceBar.rightNumbers[0]));
            price = 10;
        }
        else if (itemsDrawnImages.get(position).equals(healthPotion)){
            nodes[position.getY() + 1][position.getX()].getChildren().add(new ImageView(InterfaceBar.leftNumbers[1]));
            nodes[position.getY() + 1][position.getX()].getChildren().add(new ImageView(InterfaceBar.rightNumbers[5]));
            price = 15;
        }
        else if (itemsDrawnImages.get(position).equals(whiteSword)){
            nodes[position.getY() + 1][position.getX()].getChildren().add(new ImageView(InterfaceBar.leftNumbers[2]));
            nodes[position.getY() + 1][position.getX()].getChildren().add(new ImageView(InterfaceBar.rightNumbers[0]));
            price = 20;
        }

        if (position.equals(leftItemPos)) leftItemPrice = price;
        else if (position.equals(midItemPos)) midItemPrice = price;
        else if (position.equals(rightItemPos)) rightItemPrice = price;
    }

    public boolean isHeroOnItem(){
        return leftItemPos.equals(hero.getPosition()) || midItemPos.equals(hero.getPosition()) || rightItemPos.equals(hero.getPosition());
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(StartingMap.rightBottomPassageBorder) && position.precedes(StartingMap.rightUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("SouthWest"));

        return false;
    }
}
