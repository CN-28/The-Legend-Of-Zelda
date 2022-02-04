package MapsElements;

public abstract class Creature {
    protected Vector2d position;
    protected MoveDirection orientation;
    protected int health;
    protected static final int size = 35;

    public abstract void move(AbstractMap map, MoveDirection direction);

    public Vector2d getPosition() {
        return this.position;
    }

    public MoveDirection getOrientation(){
        return this.orientation;
    }

    public void removeHealth(int attackPower){
        this.health -= attackPower;
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
}
