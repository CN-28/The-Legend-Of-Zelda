package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;

import static MapsElements.MoveDirection.*;

public class Ghini extends Creature {
    private static final HashMap<MoveDirection, Image> images = new HashMap<>();
    private final HashMap<MoveDirection, ImageView[]> imageViews = new HashMap<>();
    public boolean push = false;
    public int pushCnt = 4;
    public MoveDirection pushDirection;

    static {
        try {
            images.put(WEST, new Image(new FileInputStream("src/main/resources/ghiniLeft.png"), size, size, false, false));
            images.put(EAST, new Image(new FileInputStream("src/main/resources/ghiniRight.png"), size, size, false, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Ghini(int x, int y, MoveDirection[] moves){
        this.position = new Vector2d(x, y);
        this.health = 3;
        this.i = 0;
        imageViews.put(WEST, new ImageView[]{new ImageView(images.get(WEST))});
        imageViews.put(EAST, new ImageView[]{new ImageView(images.get(EAST))});
        this.moveCycle.addAll(Arrays.asList(moves));
    }

    public void removeHealth(AbstractMap map, int attackPower){
        super.removeHealth(map, attackPower);
        if (this.health > 0){
            this.pushCnt = 4;
            this.push = true;
        }
    }

    public void pushBack(AbstractMap map, MoveDirection direction){
        Vector2d newPos = this.position.add(direction.toUnitVector());
        changeCreaturePosition(map, newPos);
    }

    public void removeCreature(AbstractMap map) {
        dropLoot(map, this.position);

        map.nodes[this.getY()][this.getX()].getChildren().remove(this.getCreatureAnimation()[0]);
        map.mobs.get(this.getY()).get(this.getX()).remove(this);
    }

    public ImageView[] getCreatureAnimation() {
        return this.imageViews.get(this.orientation);
    }
}
