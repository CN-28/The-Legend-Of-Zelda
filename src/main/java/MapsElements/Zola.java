package MapsElements;

import GUI.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

import static MapsElements.MoveDirection.*;

public class Zola extends Creature {
    private static final Image[] images = new Image[5];
    private final ImageView[] imageViews = new ImageView[5];
    public boolean ballPushed = false;

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

    public Zola(){
        this.health = 2;
        imageViews[0] = new ImageView(images[0]);
        imageViews[1] = new ImageView(images[1]);
        imageViews[2] = new ImageView(images[2]);
        imageViews[3] = new ImageView(images[3]);
        imageViews[4] = new ImageView(images[4]);
    }

    public void move(AbstractMap map, MoveDirection direction){
        if (direction == SOUTH)
            this.prevImage = getCreatureAnimation()[2];
        else
            this.prevImage = getCreatureAnimation()[3];

        map.nodes[this.getY()][this.getX()].getChildren().add(this.prevImage);

        int index = map.zolaPositions.get(ThreadLocalRandom.current().nextInt(0, map.zolaPositions.size()));
        this.position = new Vector2d(index % AbstractMap.width, index / AbstractMap.width);
    }

    public void removeCreature() {
        App.map.zola = null;
        App.map.nodes[this.getY()][this.getX()].getChildren().remove(this.getCreatureAnimation()[2]);
        App.map.nodes[this.getY()][this.getX()].getChildren().remove(this.getCreatureAnimation()[3]);
        if (this.prevY != -1){
            App.map.nodes[this.prevY][this.prevX].getChildren().remove(this.getCreatureAnimation()[2]);
            App.map.nodes[this.prevY][this.prevX].getChildren().remove(this.getCreatureAnimation()[3]);
        }
        if (App.map.mobs.containsKey(this.getY()) && App.map.mobs.get(this.getY()).containsKey(this.getX()))
            App.map.mobs.get(this.getY()).get(this.getX()).remove(this);
        if (prevX != -1 && App.map.mobs.containsKey(this.prevY) && App.map.mobs.get(this.prevY).containsKey(this.prevX))
            App.map.mobs.get(this.prevY).get(this.prevX).remove(this);
    }

    public ImageView[] getCreatureAnimation() {
        return this.imageViews;
    }
}
