package MapsElements;

import GUI.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import static MapsElements.MoveDirection.*;

public class Hero extends Creature {
    private static final HashMap<MoveDirection, ImageView[]> attackImageViews = new HashMap<>();
    private static final HashMap<MoveDirection, ImageView> basicImageViews = new HashMap<>();
    private static final HashMap<MoveDirection, ImageView> woodenSwordImageViews = new HashMap<>();
    private static final HashMap<MoveDirection, ImageView> whiteSwordImageViews = new HashMap<>();
    private static final Image[] bombImages = new Image[4];
    public int bombCnt, healthPotionCnt, goldCnt;
    public static int maxHealth;
    public static boolean hasWoodenSword, hasWhiteSword;
    static {
        attackImageViews.put(SOUTH, new ImageView[8]); attackImageViews.put(EAST, new ImageView[8]);
        attackImageViews.put(WEST, new ImageView[8]); attackImageViews.put(NORTH, new ImageView[8]);
        try {
            bombImages[0] = new Image(new FileInputStream("src/main/resources/bomb.png"), size, size, false, false);
            bombImages[1] = new Image(new FileInputStream("src/main/resources/bombExplosion1.png"), size, size, false, false);
            bombImages[2] = new Image(new FileInputStream("src/main/resources/bombExplosion2.png"), size, size, false, false);
            bombImages[3] = new Image(new FileInputStream("src/main/resources/bombExplosion3.png"), size, size, false, false);
            woodenSwordImageViews.put(SOUTH, new ImageView(new Image(new FileInputStream("src/main/resources/woodenSwordWholeBot.png"), size, size, false, false)));
            woodenSwordImageViews.put(NORTH, new ImageView(new Image(new FileInputStream("src/main/resources/woodenSword.png"), size, size, false, false)));
            woodenSwordImageViews.put(WEST, new ImageView(new Image(new FileInputStream("src/main/resources/woodenSwordLeft.png"), size, size, false, false)));
            woodenSwordImageViews.put(EAST, new ImageView(new Image(new FileInputStream("src/main/resources/woodenSwordRight.png"), size, size, false, false)));
            whiteSwordImageViews.put(SOUTH, new ImageView(new Image(new FileInputStream("src/main/resources/whiteSwordWholeBot.png"), size, size, false, false)));
            whiteSwordImageViews.put(NORTH, new ImageView(new Image(new FileInputStream("src/main/resources/whiteSword.png"), size, size, false, false)));
            whiteSwordImageViews.put(WEST, new ImageView(new Image(new FileInputStream("src/main/resources/whiteSwordLeft.png"), size, size, false, false)));
            whiteSwordImageViews.put(EAST, new ImageView(new Image(new FileInputStream("src/main/resources/whiteSwordRight.png"), size, size, false, false)));
            basicImageViews.put(SOUTH, new ImageView(new Image(new FileInputStream("src/main/resources/frontHero.png"), size, size, false, false)));
            basicImageViews.put(EAST, new ImageView(new Image(new FileInputStream("src/main/resources/rightHero.png"), size, size, false, false)));
            basicImageViews.put(WEST, new ImageView(new Image(new FileInputStream("src/main/resources/leftHero.png"), size, size, false, false)));
            basicImageViews.put(NORTH, new ImageView(new Image(new FileInputStream("src/main/resources/backHero.png"), size, size, false, false)));
            attackImageViews.get(SOUTH)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom1.png"), size, size, false, false));
            attackImageViews.get(SOUTH)[2] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom2_1.png"), size, size, false, false));
            attackImageViews.get(SOUTH)[3] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom2_2.png"), size, size, false, false));
            attackImageViews.get(SOUTH)[4] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom3_1.png"), size, size, false, false));
            attackImageViews.get(SOUTH)[5] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom3_2.png"), size, size, false, false));
            attackImageViews.get(SOUTH)[6] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom4_1.png"), size, size, false, false));
            attackImageViews.get(SOUTH)[7] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom4_2.png"), size, size, false, false));
            attackImageViews.get(WEST)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft1.png"), size, size, false, false));
            attackImageViews.get(WEST)[2] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft2_1.png"), size, size, false, false));
            attackImageViews.get(WEST)[3] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft2_2.png"), size, size, false, false));
            attackImageViews.get(WEST)[4] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft3_1.png"), size, size, false, false));
            attackImageViews.get(WEST)[5] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft3_2.png"), size, size, false, false));
            attackImageViews.get(WEST)[6] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft4_1.png"), size, size, false, false));
            attackImageViews.get(WEST)[7] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft4_2.png"), size, size, false, false));
            attackImageViews.get(EAST)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight1.png"), size, size, false, false));
            attackImageViews.get(EAST)[2] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight2_1.png"), size, size, false, false));
            attackImageViews.get(EAST)[3] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight2_2.png"), size, size, false, false));
            attackImageViews.get(EAST)[4] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight3_1.png"), size, size, false, false));
            attackImageViews.get(EAST)[5] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight3_2.png"), size, size, false, false));
            attackImageViews.get(EAST)[6] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight4_1.png"), size, size, false, false));
            attackImageViews.get(EAST)[7] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight4_2.png"), size, size, false, false));
            attackImageViews.get(NORTH)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp1.png"), size, size, false, false));
            attackImageViews.get(NORTH)[2] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp2_1.png"), size, size, false, false));
            attackImageViews.get(NORTH)[3] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp2_2.png"), size, size, false, false));
            attackImageViews.get(NORTH)[4] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp3_1.png"), size, size, false, false));
            attackImageViews.get(NORTH)[5] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp3_2.png"), size, size, false, false));
            attackImageViews.get(NORTH)[6] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp4_1.png"), size, size, false, false));
            attackImageViews.get(NORTH)[7] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp4_2.png"), size, size, false, false));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public Hero() {
        this.position = new Vector2d(14, 9);
        this.health = 16;
        maxHealth = this.health;
        this.orientation = SOUTH;
    }

    public void move(AbstractMap map, MoveDirection direction){
        if (direction == this.orientation){
            Vector2d newPos = this.position.add(this.orientation.toUnitVector());
            if (map.canMoveTo(newPos))
                this.position = newPos;
        }
        else
            this.orientation = direction;
    }

    public void pickUpItems(AbstractMap map){
        if (map.loots.containsKey(this.position)){
            for (Image image : map.loots.get(this.position).keySet()){
                for (ImageView imageView : map.loots.get(this.position).get(image)){
                    if (AbstractMap.bomb.equals(image)){
                        bombCnt += 1;
                        InterfaceBar.updateBombCounter(bombCnt);
                    }
                    else if (AbstractMap.gold.equals(image)){
                        goldCnt += 1;
                        InterfaceBar.updateGoldCounter(goldCnt);
                    }
                    else if (AbstractMap.healthPotion.equals(image)){
                        healthPotionCnt += 1;
                        InterfaceBar.updateHealthPotionCounter(healthPotionCnt);
                    }
                    else if (AbstractMap.heart.equals(image))
                        InterfaceBar.regenerateOneHeart();

                    map.nodes[this.position.getY()][this.position.getX()].getChildren().remove(imageView);
                }
            }
            map.loots.get(this.position).clear();
        }
    }

    public void setPosition(int x, int y) {
        this.position = new Vector2d(x, y);
    }

    public void setPositionAfterMapChange() {
        int x = this.getX();
        int y = this.getY();
        if (App.map instanceof StartingCave || App.map instanceof EastSecretCave || App.map instanceof SouthWestSecretCave){
            AbstractCave cave = (AbstractCave) App.map;
            this.setPosition(cave.getPosition().getX(), cave.getPosition().getY());
        }
        else if (x == 0 || x == AbstractMap.width - 1)
            this.setPosition((AbstractMap.width - x - 1) % AbstractMap.width, y);
        else if (y == 0 || y == AbstractMap.height - 1)
            this.setPosition(x, (AbstractMap.height - y - 1) % AbstractMap.height);
        else if (App.map instanceof StartingMap && this.position.equals(AbstractCave.caves.get("Starting").getPosition()))
            this.setPosition(AbstractMap.width - 1, this.position.getY());
        else if (App.map instanceof SouthWestMap && this.position.equals(AbstractCave.caves.get("SouthWest").getPosition()))
            this.setPosition(AbstractMap.width - 1, this.position.getY() + 3);
        else if (App.map instanceof EastMap && this.position.equals(AbstractCave.caves.get("East").getPosition()))
            this.setPosition(EastMap.bottomLeftPassageBorder.getX() + 1, AbstractMap.height - 1);
        else if (App.map instanceof EastSecretCave){
            Vector2d temp = AbstractCave.caves.get("East").getPosition();
            this.setPosition(temp.getX(), temp.getY());
        }
    }

    public ImageView[] getCreatureAnimation(){
        return null;
    }

    public void removeCreature(AbstractMap map){

    }

    public ImageView getSwordPicture(MoveDirection dir){
        return woodenSwordImageViews.get(dir);
    }

    public ImageView getPicture(){
        return basicImageViews.get(this.orientation);
    }

    public Image[] getBombImages(){
        return bombImages;
    }

    public ImageView[] getAnimationAttack(){
        return attackImageViews.get(this.orientation);
    }
}
