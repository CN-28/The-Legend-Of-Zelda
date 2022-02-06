package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ZolaAttackBall {
    private static Image attackBallImage;
    private final ImageView attackBall;
    private static final int size = 35;
    public Vector2d position;
    public float a, b;
    static {
        try {
            attackBallImage = new Image(new FileInputStream("src/main/resources/zolaAttackBall.png"), size, size, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ZolaAttackBall(Vector2d position, Vector2d startPos, Vector2d endPos){
        this.attackBall = new ImageView(attackBallImage);
        this.position = position;
        this.a = ((float) (startPos.getY() - endPos.getY())) / ((float) (startPos.getX() - endPos.getX()));
        this.b = startPos.getY() - this.a * ((float) startPos.getX());
    }

    public Vector2d getNewBallPosition(int x){
        Vector2d newPos = new Vector2d(x, Math.round(this.a * ((float) x) + this.b));
        this.position = newPos;
        return newPos;
    }

    public ImageView getAttackBallImage(){
        return this.attackBall;
    }
}
