package MapsElements;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Creature {
    protected Vector2d position;
    protected MoveDirection orientation;
    protected int health;
    public final ArrayList<MoveDirection> moveCycle = new ArrayList<>();
    protected static final int size = 35;
    public int i, prevX, prevY, randomNumber;
    public ImageView prevImage;
    public static final ArrayList<Image> lootsToDraw = new ArrayList<>();

    public Creature(){
        for (int i = 0; i < 3; i++) lootsToDraw.add(AbstractMap.bomb);
        for (int i = 0; i < 3; i++) lootsToDraw.add(AbstractMap.heart);
        lootsToDraw.add(AbstractMap.healthPotion); lootsToDraw.add(AbstractMap.healthPotion);
        for (int i = 0; i < 9; i++) lootsToDraw.add(AbstractMap.gold);
    }

    public void move(AbstractMap map, MoveDirection direction){
        if (direction == this.orientation){
            Vector2d newPos = this.position.add(this.orientation.toUnitVector());
            changeCreaturePosition(map, newPos);
        }
        else
            this.orientation = direction;
    }

    public void changeCreaturePosition(AbstractMap map, Vector2d newPos){
        if (newPos.precedes(AbstractMap.upperRight) && newPos.follows(AbstractMap.lowerLeft) && !map.isOccupied(newPos)){
            map.mobs.get(this.position.getY()).get(this.position.getX()).remove(this);
            if (!map.mobs.containsKey(newPos.getY()))
                map.mobs.put(newPos.getY(), new LinkedHashMap<>());
            if (!map.mobs.get(newPos.getY()).containsKey(newPos.getX()))
                map.mobs.get(newPos.getY()).put(newPos.getX(), new ArrayList<>());

            map.mobs.get(newPos.getY()).get(newPos.getX()).add(this);
            this.position = newPos;
        }
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public void setPosition(int x, int y){
        this.position = new Vector2d(x, y);
    }

    public MoveDirection getOrientation(){
        return this.orientation;
    }

    public void removeHealth(AbstractMap map, int attackPower){
        this.health -= attackPower;
        if (this.health <= 0)
            removeCreature(map);
    }

    public void dropLoot(AbstractMap map, Vector2d position){
        randomNumber = ThreadLocalRandom.current().nextInt(0, lootsToDraw.size());
        Image imageItem = lootsToDraw.get(randomNumber);
        if (!map.loots.containsKey(position))
            map.loots.put(position, new HashMap<>());
        if (!map.loots.get(position).containsKey(imageItem))
            map.loots.get(position).put(imageItem, new ArrayList<>());
        ImageView newItem = new ImageView(imageItem);
        map.loots.get(position).get(imageItem).add(newItem);
        map.nodes[position.getY()][position.getX()].getChildren().add(newItem);
    }

    public int getHealth(){
        return this.health;
    }

    public int getX(){
        return this.position.getX();
    }

    public int getY(){
        return this.position.getY();
    }

    public abstract void removeCreature(AbstractMap map);

    public abstract ImageView[] getCreatureAnimation();
}
