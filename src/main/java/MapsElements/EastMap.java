package MapsElements;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class EastMap extends AbstractMap {
    public EastMap() {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.nodes[i][j] = new Group();
                this.nodes[i][j].getChildren().addAll(new ImageView(grassTile));
                this.grid.add(this.nodes[i][j], j, i);
            }
        }
    }
}
