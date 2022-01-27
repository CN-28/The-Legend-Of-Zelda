package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import static MapsElements.MoveDirection.*;

public class Hero {
    private Vector2d position;
    private MoveDirection orientation;

    private static final HashMap<MoveDirection, ImageView> imageViews = new HashMap<>();
    private static final int size = 30;

    static {
        try {
            imageViews.put(SOUTH, new ImageView(new Image(new FileInputStream("src/main/resources/frontHero.png"), size, size, false, false)));
            imageViews.put(EAST, new ImageView(new Image(new FileInputStream("src/main/resources/rightHero.png"), size, size, false, false)));
            imageViews.put(WEST, new ImageView(new Image(new FileInputStream("src/main/resources/leftHero.png"), size, size, false, false)));
            imageViews.put(NORTH, new ImageView(new Image(new FileInputStream("src/main/resources/backHero.png"), size, size, false, false)));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public Hero() {
        this.position = new Vector2d(1, 1);
        this.orientation = SOUTH;
    }

    public void move(MoveDirection direction){
        if (direction == this.orientation){
            Vector2d newPos = this.position.add(this.orientation.toUnitVector());
            if (AbstractMap.canMoveTo(newPos))
                this.position = newPos;
        }
        else
            this.orientation = direction;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public int getX(){
        return this.position.x;
    }

    public int getY(){
        return this.position.y;
    }

    public ImageView getPicture(){
        return imageViews.get(this.orientation);
    }
}
