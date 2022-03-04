package MapsElements;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SouthWestSecretCave extends AbstractCave{
    public final ImageView[] merchantString = new ImageView[11];

    public SouthWestSecretCave(){
        for (int i = 0; i < AbstractMap.height; i++){
            for (int j = 0; j < AbstractMap.width; j++){
                this.nodes[i][j] = new Group();
                if (i < 4 || i > 15 || j < 7 || j > 23 && (i < 8 || i >= 12)){
                    this.nodes[i][j].getChildren().add(new ImageView(caveTile));
                    this.occupancyMap[i][j] = true;
                }
                else
                    this.nodes[i][j].getChildren().add(new ImageView(InterfaceBar.blackTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }

        for (int i = 1; i <= 11; i++){
            try {
                merchantString[i - 1] = new ImageView(new Image(new FileInputStream("src/main/resources/merchantString" + i + ".png"), AbstractMap.size, AbstractMap.size, false, false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.nodes[6][9 + i].getChildren().add(merchantString[i - 1]);
        }

        this.cavePosition = new Vector2d(4, 6);
        addMerchant(15, 8);
        addFire(11, 8);
        addFire(19, 8);
        addRandomItem(12, 11);
        addRandomItem(15, 11);
        addRandomItem(18, 11);

        pickUpAnimation = new AnimationTimer() {
            public void handle(long now) {

            }
        };
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(StartingMap.rightBottomPassageBorder) && position.precedes(StartingMap.rightUpperPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("SouthWest"));

        return false;
    }
}
