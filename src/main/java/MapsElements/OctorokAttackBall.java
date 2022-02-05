package MapsElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OctorokAttackBall {
    private static Image attackBallImage;
    private final ImageView attackBall;
    public Vector2d ballPosition;
    public MoveDirection ballDirection;
    protected static int size = 35;
    static {
        try {
            attackBallImage = new Image(new FileInputStream("src/main/resources/octorok_attack_item.png"), size, size, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public OctorokAttackBall(Vector2d position, MoveDirection direction){
        this.attackBall = new ImageView(attackBallImage);
        this.ballDirection = direction;
        this.ballPosition = position;
    }

    public ImageView getAttackBallImage(){
        return this.attackBall;
    }
}
