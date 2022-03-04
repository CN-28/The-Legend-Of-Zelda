package MapsElements;

import GUI.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import static MapsElements.MoveDirection.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Hero extends Creature {
    private static final HashMap<MoveDirection, ImageView[]> woodenAttackImageViews = new HashMap<>();
    private static final HashMap<MoveDirection, ImageView[]> whiteAttackImageViews = new HashMap<>();
    private static final HashMap<MoveDirection, ImageView> basicImageViews = new HashMap<>();
    private static final HashMap<MoveDirection, ImageView> woodenSwordImageViews = new HashMap<>();
    private static final HashMap<MoveDirection, ImageView> whiteSwordImageViews = new HashMap<>();
    private static final Image[] bombImages = new Image[4];
    public int bombCnt, healthPotionCnt, goldCnt, attackDamage;
    public static int maxHealth;
    public static boolean hasWoodenSword, hasWhiteSword;
    static {
        woodenAttackImageViews.put(SOUTH, new ImageView[8]); woodenAttackImageViews.put(EAST, new ImageView[8]);
        woodenAttackImageViews.put(WEST, new ImageView[8]); woodenAttackImageViews.put(NORTH, new ImageView[8]);
        whiteAttackImageViews.put(SOUTH, new ImageView[8]); whiteAttackImageViews.put(EAST, new ImageView[8]);
        whiteAttackImageViews.put(WEST, new ImageView[8]); whiteAttackImageViews.put(NORTH, new ImageView[8]);
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

            woodenAttackImageViews.get(SOUTH)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom" + 1 + ".png"), size, size, false, false));
            for (int i = 2; i <= 7; i++)
                woodenAttackImageViews.get(SOUTH)[i] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom" + i + ".png"), size, size, false, false));

            woodenAttackImageViews.get(WEST)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft" + 1 + ".png"), size, size, false, false));
            for (int i = 2; i <= 7; i++)
                woodenAttackImageViews.get(WEST)[i] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft" + i + ".png"), size, size, false, false));

            woodenAttackImageViews.get(EAST)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight" + 1 + ".png"), size, size, false, false));
            for (int i = 2; i <= 7; i++)
                woodenAttackImageViews.get(EAST)[i] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight" + i + ".png"), size, size, false, false));

            woodenAttackImageViews.get(NORTH)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp" + 1 + ".png"), size, size, false, false));
            for (int i = 2; i <= 7; i++)
                woodenAttackImageViews.get(NORTH)[i] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp" + i + ".png"), size, size, false, false));

            whiteAttackImageViews.get(SOUTH)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackBottom" + 1 + ".png"), size, size, false, false));
            for (int i = 2; i <= 7; i++)
                whiteAttackImageViews.get(SOUTH)[i] = new ImageView(new Image(new FileInputStream("src/main/resources/whiteAttackBottom" + i + ".png"), size, size, false, false));

            whiteAttackImageViews.get(NORTH)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackUp" + 1 + ".png"), size, size, false, false));
            for (int i = 2; i <= 7; i++)
                whiteAttackImageViews.get(NORTH)[i] = new ImageView(new Image(new FileInputStream("src/main/resources/whiteAttackUp" + i + ".png"), size, size, false, false));

            whiteAttackImageViews.get(WEST)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackLeft" + 1 + ".png"), size, size, false, false));
            for (int i = 2; i <= 7; i++)
                whiteAttackImageViews.get(WEST)[i] = new ImageView(new Image(new FileInputStream("src/main/resources/whiteAttackLeft" + i + ".png"), size, size, false, false));

            whiteAttackImageViews.get(EAST)[0] = new ImageView(new Image(new FileInputStream("src/main/resources/attackRight" + 1 + ".png"), size, size, false, false));
            for (int i = 2; i <= 7; i++)
                whiteAttackImageViews.get(EAST)[i] = new ImageView(new Image(new FileInputStream("src/main/resources/whiteAttackRight" + i + ".png"), size, size, false, false));
        } catch (FileNotFoundException ex) {
        ex.printStackTrace();
        }
    }

    public Hero() {
        this.position = new Vector2d(14, 9);
        this.health = 20;
        maxHealth = this.health;
        this.orientation = SOUTH;
        this.attackDamage = 1;
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
                    else if (AbstractMap.heart.equals(image) && this.health < maxHealth && this.health >= 0){
                        InterfaceBar.regenerateOneHeart(this.health);
                        this.health = min(this.health + 2, maxHealth);
                    }

                    map.nodes[this.position.getY()][this.position.getX()].getChildren().remove(imageView);
                }
            }
            map.loots.get(this.position).clear();
        }
    }

    public void setPosition(int x, int y) {
        this.position = new Vector2d(x, y);
    }

    public void setHealth(int newHP){
        this.health = newHP;
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

    public void removeHealth(AbstractMap map, int attackPower){
        this.health = max(0, this.health - attackPower);
        if (this.health >= 0)
            InterfaceBar.updateHealthBar(this.health);
        if (this.health <= 0)
            removeCreature(map);
    }

    public void changeToWhiteSword(){
        hasWoodenSword = false;
        hasWhiteSword = true;
        this.attackDamage = 2;
    }

    public void removeCreature(AbstractMap map){

    }

    public ImageView getSwordPicture(MoveDirection dir){
        if (hasWoodenSword)
            return woodenSwordImageViews.get(dir);
        return whiteSwordImageViews.get(dir);
    }

    public ImageView getPicture(){
        return basicImageViews.get(this.orientation);
    }

    public Image[] getBombImages(){
        return bombImages;
    }

    public ImageView[] getAnimationAttack(){
        if (hasWoodenSword)
            return woodenAttackImageViews.get(this.orientation);
        return whiteAttackImageViews.get(this.orientation);
    }
}
