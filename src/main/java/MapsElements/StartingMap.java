package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class StartingMap extends AbstractMap {
    public StartingMap(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                if (j + 1 > 2 * width/5 && j + 1 <= 3 * width/5 || i + 1 > 2 * height/5 && i + 1 <= 3 * height/5 && j + 1 > 2 * width/5)
                    this.nodes[i][j].getChildren().addAll(new ImageView(sandTile));
                else
                    this.nodes[i][j].getChildren().addAll(new ImageView(grassTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }
    }
}
