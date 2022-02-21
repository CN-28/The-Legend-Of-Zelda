package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ManhandlaAttackBall {
    private static Image attackBallImage;
    private final ImageView attackBall;
    private static final int size = 35;
    public Vector2d position, startPos;
    public MoveDirection direction;
    public int prevX = -1;
    public int prevY = -1;
    public float a, b;

    static {
        try {
            attackBallImage = new Image(new FileInputStream("src/main/resources/manhandlaBall.png"), size, size, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ManhandlaAttackBall(Vector2d startPos, Vector2d endPos, MoveDirection direction){
        this.attackBall = new ImageView(attackBallImage);
        this.direction = direction;
        this.startPos = startPos;
        this.a = ((float) (startPos.getY() - endPos.getY())) / ((float) (startPos.getX() - endPos.getX()));
        this.b = startPos.getY() - this.a * ((float) startPos.getX());
        if (endPos.getX() < startPos.getX())
            updatePosition(startPos.getX() - 1);
        else if (endPos.getX() > startPos.getX())
            updatePosition(startPos.getX() + 1);
        else{
            if (endPos.getY() > startPos.getY())
                this.position = new Vector2d(startPos.getX(), startPos.getY() + 1);
            else
                this.position = new Vector2d(startPos.getX(), startPos.getY() - 1);
        }
    }

    public void updatePosition(int x){
        if (this.position != null){
            prevX = this.position.getX();
            prevY = this.position.getY();
        }

        if (x == prevX){
            if (this.startPos.getY() > this.position.getY())
                this.position = new Vector2d(x, this.position.getY() - 1);
            else if (this.startPos.getY() < this.position.getY())
                this.position = new Vector2d(x, this.position.getY() + 1);
        }
        else
            this.position = new Vector2d(x, Math.round(this.a * ((float) x) + this.b));
    }

    public ImageView getAttackBallImage(){
        return this.attackBall;
    }
}
