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
    private static final int size = 35;

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

    public void move(AbstractMap map, MoveDirection direction){
        if (direction == this.orientation){
            Vector2d newPos = this.position.add(this.orientation.toUnitVector());
            if (map.canMoveTo(newPos))
                this.position = newPos;
        }
        else
            this.orientation = direction;
    }

    public Vector2d getPosition() {
        return this.position;
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
