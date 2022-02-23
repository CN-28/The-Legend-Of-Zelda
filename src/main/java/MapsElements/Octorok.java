package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;

import static MapsElements.MoveDirection.*;

public class Octorok extends Creature {
    private static final HashMap<MoveDirection, Image[]> images = new HashMap<>();
    private final HashMap<MoveDirection, ImageView[]> imageViews = new HashMap<>();
    public boolean ballPushed = false;
    public OctorokAttackBall ball;
    static {
        images.put(SOUTH, new Image[2]); images.put(EAST, new Image[2]); images.put(WEST, new Image[2]); images.put(NORTH, new Image[2]);
        try {
            images.get(SOUTH)[0] = new Image(new FileInputStream("src/main/resources/octorok_bot.png"), size, size, false, false);
            images.get(SOUTH)[1] = new Image(new FileInputStream("src/main/resources/octorok_bot_attack.png"), size, size, false, false);
            images.get(WEST)[0] = new Image(new FileInputStream("src/main/resources/octorok_left.png"), size, size, false, false);
            images.get(WEST)[1] = new Image(new FileInputStream("src/main/resources/octorok_left_attack.png"), size, size, false, false);
            images.get(EAST)[0] = new Image(new FileInputStream("src/main/resources/octorok_right.png"), size, size, false, false);
            images.get(EAST)[1] = new Image(new FileInputStream("src/main/resources/octorok_right_attack.png"), size, size, false, false);
            images.get(NORTH)[0] = new Image(new FileInputStream("src/main/resources/octorok_top.png"), size, size, false, false);
            images.get(NORTH)[1] = new Image(new FileInputStream("src/main/resources/octorok_top_attack.png"), size, size, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Octorok(int x, int y, MoveDirection[] moves){
        this.position = new Vector2d(x, y);
        this.i = 0;
        this.health = 2;
        imageViews.put(SOUTH, new ImageView[2]); imageViews.put(EAST, new ImageView[2]); imageViews.put(WEST, new ImageView[2]); imageViews.put(NORTH, new ImageView[2]);
        imageViews.get(SOUTH)[0] = new ImageView(images.get(SOUTH)[0]); imageViews.get(SOUTH)[1] = new ImageView(images.get(SOUTH)[1]);
        imageViews.get(WEST)[0] = new ImageView(images.get(WEST)[0]); imageViews.get(WEST)[1] = new ImageView(images.get(WEST)[1]);
        imageViews.get(EAST)[0] = new ImageView(images.get(EAST)[0]); imageViews.get(EAST)[1] = new ImageView(images.get(EAST)[1]);
        imageViews.get(NORTH)[0] = new ImageView(images.get(NORTH)[0]); imageViews.get(NORTH)[1] = new ImageView(images.get(NORTH)[1]);
        this.moveCycle.addAll(Arrays.asList(moves));
    }

    public void removeCreature(AbstractMap map){
        if (this.ballPushed)
            map.octoroksBalls.add(this.ball);
        map.nodes[this.getY()][this.getX()].getChildren().remove(this.getCreatureAnimation()[0]);
        map.nodes[this.getY()][this.getX()].getChildren().remove(this.getCreatureAnimation()[1]);
        map.mobs.get(this.getY()).get(this.getX()).remove(this);
    }

    public boolean sees(Vector2d position){
        return this.position.getY() == position.getY() && (this.position.getX() < position.getX() && this.orientation == EAST || this.position.getX() > position.getX() && this.orientation == WEST)
                || this.position.getX() == position.getX() && (this.position.getY() < position.getY() && this.orientation == SOUTH || this.position.getY() > position.getY() && this.orientation == NORTH);
    }

    public ImageView[] getCreatureAnimation(){
        return imageViews.get(this.orientation);
    }
}
