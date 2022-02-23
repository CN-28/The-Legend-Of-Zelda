package MapsElements;


import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class Creature {
    protected Vector2d position;
    protected MoveDirection orientation;
    protected int health;
    public final ArrayList<MoveDirection> moveCycle = new ArrayList<>();
    protected static final int size = 35;
    public int i, prevX, prevY;
    public ImageView prevImage;

    public void move(AbstractMap map, MoveDirection direction){
        if (direction == this.orientation){
            Vector2d newPos = this.position.add(this.orientation.toUnitVector());
            changeCreaturePosition(map, newPos);
        }
        else
            this.orientation = direction;
    }

    public void changeCreaturePosition(AbstractMap map, Vector2d newPos){
        if (map.canMoveTo(newPos)){
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
