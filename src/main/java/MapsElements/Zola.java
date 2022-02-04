package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static MapsElements.MoveDirection.*;

public class Zola extends Creature {
    private static final Image[] images = new Image[5];
    private final ImageView[] imageViews = new ImageView[5];

    static {
        try {
            images[0] = new Image(new FileInputStream("src/main/resources/zolaIncoming1.png"), size, size, false, false);
            images[1] = new Image(new FileInputStream("src/main/resources/zolaIncoming2.png"), size, size, false, false);
            images[2] = new Image(new FileInputStream("src/main/resources/frontZola.png"), size, size, false, false);
            images[3] = new Image(new FileInputStream("src/main/resources/backZola.png"), size, size, false, false);
            images[4] = new Image(new FileInputStream("src/main/resources/zolaAttackBall.png"), size, size, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Zola(int x, int y){
        this.position = new Vector2d(x, y);
        this.health = 3;
        this.orientation = SOUTH;
        imageViews[0] = new ImageView(images[0]);
        imageViews[1] = new ImageView(images[1]);
        imageViews[2] = new ImageView(images[2]);
        imageViews[3] = new ImageView(images[3]);
        imageViews[4] = new ImageView(images[4]);
    }

    public void move(AbstractMap map, MoveDirection direction) {

    }

    public ImageView[] getZolaAnimation(){
        return this.imageViews;
    }
}
