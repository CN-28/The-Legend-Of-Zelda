package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Manhandla extends Creature {
    private static final Image[] images = new Image[9];
    private final ImageView[] imageViews = new ImageView[9];
    public boolean attackAnimation = true;
    public boolean topAlive, rightAlive, botAlive, leftAlive, leftAttacking, topAttacking, rightAttacking, botAttacking;
    public int topHealth, rightHealth, botHealth, leftHealth;
    static {
        try {
            images[0] = new Image(new FileInputStream("src/main/resources/manhandlaMid.png"), size, size, false, false);
            images[1] = new Image(new FileInputStream("src/main/resources/manhandlaTop.png"), size, size, false, false);
            images[2] = new Image(new FileInputStream("src/main/resources/manhandlaRight.png"), size, size, false, false);
            images[3] = new Image(new FileInputStream("src/main/resources/manhandlaBot.png"), size, size, false, false);
            images[4] = new Image(new FileInputStream("src/main/resources/manhandlaLeft.png"), size, size, false, false);
            images[5] = new Image(new FileInputStream("src/main/resources/manhandlaTopAttack.png"), size, size, false, false);
            images[6] = new Image(new FileInputStream("src/main/resources/manhandlaRightAttack.png"), size, size, false, false);
            images[7] = new Image(new FileInputStream("src/main/resources/manhandlaBotAttack.png"), size, size, false, false);
            images[8] = new Image(new FileInputStream("src/main/resources/manhandlaLeftAttack.png"), size, size, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Manhandla(int x, int y, MoveDirection[] moves){
        this.position = new Vector2d(x, y);
        this.topHealth = 4; this.rightHealth = 4; this.botHealth = 4; this.leftHealth = 4;
        this.moveCycle.addAll(Arrays.asList(moves));
        this.topAlive = true; this.rightAlive = true; this.botAlive = true; this.leftAlive = true;
        this.leftAttacking = false; this.topAttacking = false; this.rightAttacking = false; this.botAttacking = false;
        for (int i = 0; i < images.length; i++)
            this.imageViews[i] = new ImageView(images[i]);
    }

    public void move(){
        MoveDirection direction = this.moveCycle.get(this.i % this.moveCycle.size());
        this.i += 1;
        Vector2d moveVector = direction.toUnitVector();

        this.setPosition(this.position.getX() + moveVector.getX(), this.position.getY() + moveVector.getY());
    }

    public void removeHealth(int attackPower, String whichClam){
        switch (whichClam) {
            case "top" -> {
                this.topHealth -= attackPower;
                if (this.topHealth <= 0)
                    removeCreature(AbstractMap.maps.get("NorthWest"));
            }
            case "right" -> {
                this.rightHealth -= attackPower;
                if (this.rightHealth <= 0)
                    removeCreature(AbstractMap.maps.get("NorthWest"));
            }
            case "bot" -> {
                this.botHealth -= attackPower;
                if (this.botHealth <= 0)
                    removeCreature(AbstractMap.maps.get("NorthWest"));
            }
            case "left" -> {
                this.leftHealth -= attackPower;
                if (this.leftHealth <= 0)
                    removeCreature(AbstractMap.maps.get("NorthWest"));
            }
        }
    }

    public void removeCreature(AbstractMap map){
        int y = NorthWestMap.boss.position.getY(); int x = NorthWestMap.boss.position.getX();
        if (this.topHealth <= 0 && this.topAlive){
            this.topAlive = false;
            map.nodes[y - 1][x].getChildren().remove(getCreatureAnimation()[1]);
            map.nodes[y - 1][x].getChildren().remove(getCreatureAnimation()[5]);
        }
        else if (this.rightHealth <= 0 && this.rightAlive){
            this.rightAlive = false;
            map.nodes[y][x + 1].getChildren().remove(getCreatureAnimation()[2]);
            map.nodes[y][x + 1].getChildren().remove(getCreatureAnimation()[6]);
        }
        else if (this.botHealth <= 0 && this.botAlive){
            this.botAlive = false;
            map.nodes[y + 1][x].getChildren().remove(getCreatureAnimation()[3]);
            map.nodes[y + 1][x].getChildren().remove(getCreatureAnimation()[7]);
        }
        else if (this.leftHealth <= 0 && this.leftAlive){
            this.leftAlive = false;
            map.nodes[y][x - 1].getChildren().remove(getCreatureAnimation()[4]);
            map.nodes[y][x - 1].getChildren().remove(getCreatureAnimation()[8]);
        }

        if (!this.leftAlive && !this.topAlive && !this.rightAlive && !this.botAlive){
            NorthWestMap.boss = null;
            map.nodes[y][x].getChildren().remove(getCreatureAnimation()[0]);
        }
    }

    public ImageView[] getCreatureAnimation() {
        return this.imageViews;
    }
}
