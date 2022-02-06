package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import static MapsElements.MoveDirection.*;

public class Hero extends Creature {
    private static final HashMap<MoveDirection, ImageView[]> attackImageViews = new HashMap<>();
    private static final HashMap<MoveDirection, ImageView> basicImageViews = new HashMap<>();
    static {
        attackImageViews.put(SOUTH, new ImageView[8]);
        attackImageViews.put(EAST, new ImageView[8]);
        attackImageViews.put(WEST, new ImageView[8]);
        attackImageViews.put(NORTH, new ImageView[8]);
        try {
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
        this.health = 10;
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

    public void setPosition(int x, int y) {
        this.position = new Vector2d(x, y);
    }

    public void setPositionAfterMapChange() {
        int x = this.getX();
        int y = this.getY();
        if (x == 0 || x == AbstractMap.width - 1)
            this.setPosition((AbstractMap.width - x - 1) % AbstractMap.width, y);
        else if (y == 0 || y == AbstractMap.height - 1)
            this.setPosition(x, (AbstractMap.height - y - 1) % AbstractMap.height);
    }

    public ImageView[] getCreatureAnimation(){
        return null;
    }

    public void removeCreature(){

    }

    public ImageView getPicture(){
        return basicImageViews.get(this.orientation);
    }

    public ImageView[] getAnimationAttack(){
        return attackImageViews.get(this.orientation);
    }
}
