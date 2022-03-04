package MapsElements;

import GUI.App;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public abstract class AbstractCave extends AbstractMap{
    protected Vector2d cavePosition;
    public AnimationTimer pickUpAnimation;
    public int frameCount;
    public static Image caveTile, fire, whiteSword;
    public static ImageView pickUpWeaponAnimation, pickUpItemAnimation, pickUpWhiteSwordAnimation;
    public ImageView oldMan, merchant;
    public static final ArrayList<Image> itemsToDraw = new ArrayList<>();
    public final HashMap<Vector2d, ImageView> itemsDrawn = new HashMap<>();
    public final HashMap<Vector2d, Image> itemsDrawnImages = new HashMap<>();
    public static int drawIter;
    static {
        try {
            whiteSword = new Image(new FileInputStream("src/main/resources/whiteSword.png"), AbstractMap.size, AbstractMap.size, false, false);
            fire = new Image(new FileInputStream("src/main/resources/fire.png"), AbstractMap.size, AbstractMap.size, false, false);
            caveTile = new Image(new FileInputStream("src/main/resources/caveTile.png"), AbstractMap.size, AbstractMap.size, false, false);
            pickUpItemAnimation = new ImageView(new Image(new FileInputStream("src/main/resources/pickUpItemAnimation.png"), AbstractMap.size, AbstractMap.size, false, false));
            pickUpWeaponAnimation = new ImageView(new Image(new FileInputStream("src/main/resources/pickUpWeaponAnimation.png"), AbstractMap.size, AbstractMap.size, false, false));
            pickUpWhiteSwordAnimation = new ImageView(new Image(new FileInputStream("src/main/resources/pickUpWhiteSword.png"), AbstractMap.size, AbstractMap.size, false, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        itemsToDraw.add(whiteSword);
        itemsToDraw.add(bigHeart);
        itemsToDraw.add(bomb);
        itemsToDraw.add(healthPotion);
        Collections.shuffle(itemsToDraw);
    }

    public AbstractCave(){
        try {
            oldMan = new ImageView(new Image(new FileInputStream("src/main/resources/oldMan.png"), AbstractMap.size, AbstractMap.size, false, false));
            merchant = new ImageView(new Image(new FileInputStream("src/main/resources/merchant.png"), AbstractMap.size, AbstractMap.size, false, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static final HashMap<String, AbstractCave> caves = new HashMap<>();

    public void addRandomItem(int x, int y){
        drawIter = drawIter % itemsToDraw.size();
        Image drawn = itemsToDraw.get(drawIter);
        ImageView picture = new ImageView(drawn);

        if (drawn.equals(whiteSword))
            itemsToDraw.remove(whiteSword);
        else
            drawIter += 1;

        itemsDrawnImages.put(new Vector2d(x, y), drawn);
        itemsDrawn.put(new Vector2d(x, y), picture);
        this.nodes[y][x].getChildren().add(picture);
    }

    public void addFire(int x, int y){
        this.nodes[y][x].getChildren().add(new ImageView(fire));
        this.occupancyMap[y][x] = true;
    }

    public void addOldMan(int x, int y){
        this.nodes[y][x].getChildren().add(oldMan);
        this.occupancyMap[y][x] = true;
    }

    public void addMerchant(int x, int y){
        this.nodes[y][x].getChildren().add(merchant);
        this.occupancyMap[y][x] = true;
    }

    public static void getCavesReferences(){
        caves.put("Starting", new StartingCave());
        caves.put("East", new EastSecretCave());
        caves.put("SouthWest", new SouthWestSecretCave());
    }

    public void doPickUpAnimation(){
        App.animationRunning = true;
        this.pickUpAnimation.start();
    }

    public Vector2d getPosition(){
        return this.cavePosition;
    }
}
