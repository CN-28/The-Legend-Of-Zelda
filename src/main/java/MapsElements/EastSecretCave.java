package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static MapsElements.MoveDirection.*;


public class EastSecretCave extends AbstractCave{
    public final ImageView[] EastCaveString = new ImageView[11];

    public EastSecretCave(){
        this.cavePosition = new Vector2d(8, 3);
        for (int i = 0; i < AbstractMap.height; i++){
            for (int j = 0; j < AbstractMap.width; j++){
                this.nodes[i][j] = new Group();
                if (i < 4 || i > 14 && (j < 12 || j > 16) || j < 6 || j > 22){
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
                EastCaveString[i - 1] = new ImageView(new Image(new FileInputStream("src/main/resources/freeString" + i + ".png"), AbstractMap.size, AbstractMap.size, false, false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.nodes[5][8 + i].getChildren().add(EastCaveString[i - 1]);
        }

        addOldMan(14, 7);
        addFire(18, 7);
        addFire(10, 7);
        addRandomItem(12, 10);
        addRandomItem(16, 10);
    }

    public boolean canMoveTo(Vector2d position){
        if (super.canMoveTo(position)) return true;

        if (position.follows(EastMap.bottomLeftPassageBorder.add(WEST.toUnitVector())) && position.precedes(EastMap.bottomRightPassageBorder))
            MapChangeObserver.notifyMapChange(maps.get("East"));

        return false;
    }
}
