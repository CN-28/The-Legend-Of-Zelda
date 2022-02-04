package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static MapsElements.MoveDirection.SOUTH;

public class Tektite extends Creature {
    private static final Image[] images = new Image[2];
    private final ImageView[] imageViews = new ImageView[2];

    static {
        try {
            images[0] = new Image(new FileInputStream("src/main/resources/tektiteBottom.png"), size, size, false, false);
            images[1] = new Image(new FileInputStream("src/main/resources/tektiteUpper.png"), size, size, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Tektite(int x, int y){
        this.position = new Vector2d(x, y);
        this.health = 3;
        this.orientation = SOUTH;
        imageViews[0] = new ImageView(images[0]);
        imageViews[1] = new ImageView(images[1]);
    }

    public void move(AbstractMap map, MoveDirection direction) {

    }

    public ImageView[] getTektiteAnimation(){
        return this.imageViews;
    }
}
