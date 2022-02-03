package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static MapsElements.MoveDirection.*;

public class Octorok extends Creature {
    private static final HashMap<MoveDirection, Image[]> images = new HashMap<>();
    private final HashMap<MoveDirection, ImageView[]> imageViews = new HashMap<>();
    private static final int size = 35;
    public int i = 0;
    public int prevX, prevY;
    public ImageView prevImage;
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

    {
        imageViews.put(SOUTH, new ImageView[2]); imageViews.put(EAST, new ImageView[2]); imageViews.put(WEST, new ImageView[2]); imageViews.put(NORTH, new ImageView[2]);
        imageViews.get(SOUTH)[0] = new ImageView(images.get(SOUTH)[0]); imageViews.get(SOUTH)[1] = new ImageView(images.get(SOUTH)[1]);
        imageViews.get(WEST)[0] = new ImageView(images.get(WEST)[0]); imageViews.get(WEST)[1] = new ImageView(images.get(WEST)[1]);
        imageViews.get(EAST)[0] = new ImageView(images.get(EAST)[0]); imageViews.get(EAST)[1] = new ImageView(images.get(EAST)[1]);
        imageViews.get(NORTH)[0] = new ImageView(images.get(NORTH)[0]); imageViews.get(NORTH)[1] = new ImageView(images.get(NORTH)[1]);
    }

    public Octorok(int x, int y){
        this.position = new Vector2d(x, y);
        this.orientation = SOUTH;
        this.health = 2;
    }

    public void move(AbstractMap map, MoveDirection direction){
        if (direction == this.orientation){
            Vector2d newPos = this.position.add(this.orientation.toUnitVector());
            if (map.canMoveTo(newPos)){
                EastMap.octoroks.get(this.position.getY()).remove(this.position.getX());
                if (!EastMap.octoroks.containsKey(newPos.getY()))
                    EastMap.octoroks.put(newPos.getY(), new LinkedHashMap<>());
                EastMap.octoroks.get(newPos.getY()).put(newPos.getX(), this);
                this.position = newPos;
            }
        }
        else
            this.orientation = direction;
    }

    public ImageView[] getOctorokAnimation(){
        return imageViews.get(this.orientation);
    }
}
