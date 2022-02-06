package MapsElements;

import GUI.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

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

    public Tektite(int x, int y, MoveDirection[] moves){
        this.position = new Vector2d(x, y);
        this.health = 3;
        this.i = 0;
        imageViews[0] = new ImageView(images[0]);
        imageViews[1] = new ImageView(images[1]);
        this.moveCycle.addAll(Arrays.asList(moves));
    }

    public void removeCreature() {
        App.map.nodes[this.getY()][this.getX()].getChildren().remove(this.getCreatureAnimation()[0]);
        App.map.nodes[this.getY()][this.getX()].getChildren().remove(this.getCreatureAnimation()[1]);
        App.map.mobs.get(this.getY()).get(this.getX()).remove(this);
    }

    public ImageView[] getCreatureAnimation(){
        return this.imageViews;
    }
}
