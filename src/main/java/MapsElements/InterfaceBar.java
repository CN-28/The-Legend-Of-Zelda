package MapsElements;

import GUI.App;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class InterfaceBar {
    public static int size = 35;
    public static GridPane grid = new GridPane();
    public static final Group[][] nodes = new Group[2][AbstractMap.width];
    public static final Image[] leftNumbers = new Image[10];
    public static final Image[] rightNumbers = new Image[10];
    public static final ArrayList<ImageView> bombCntImageViews = new ArrayList<>();
    public static final ArrayList<ImageView> goldCntImageViews = new ArrayList<>();
    public static final ArrayList<ImageView> healthPotionCntImageViews = new ArrayList<>();
    public static Image blackTile, emptyHeart, halfHeart, twoHearts, oneHeart, oneHalfHearts, lifeBarName1, lifeBarName2, lifeBarName3, attackBarTop, attackBarBot,
    bombBarBot, bombBarTop, goldBar, bombBar, bombBot, bombTop, whiteSwordTop, whiteSwordBot, potionBar, healthPotionBarTop,
    healthPotionBarBot, healthPotionBot, healthPotionTop;
    public static ImageView woodSwordTop, woodSwordBot;
    static {
        try {
            blackTile = new Image(new FileInputStream("src/main/resources/blackTile.png"), size, size, false, false);
            emptyHeart = new Image(new FileInputStream("src/main/resources/zeroHeart.png"), size, size, false, false);
            halfHeart = new Image(new FileInputStream("src/main/resources/halfHeart.png"), size, size, false, false);
            oneHeart = new Image(new FileInputStream("src/main/resources/oneHeart.png"), size, size, false, false);
            oneHalfHearts = new Image(new FileInputStream("src/main/resources/oneHalfHearts.png"), size, size, false, false);
            twoHearts = new Image(new FileInputStream("src/main/resources/twoHearts.png"), size, size, false, false);
            lifeBarName1 = new Image(new FileInputStream("src/main/resources/lifeBarName1.png"), size, size, false, false);
            lifeBarName2 = new Image(new FileInputStream("src/main/resources/lifeBarName2.png"), size, size, false, false);
            lifeBarName3 = new Image(new FileInputStream("src/main/resources/lifeBarName3.png"), size, size, false, false);
            attackBarTop = new Image(new FileInputStream("src/main/resources/attackBarTop.png"), size, size, false, false);
            attackBarBot = new Image(new FileInputStream("src/main/resources/attackBarBot.png"), size, size, false, false);
            bombBarBot = new Image(new FileInputStream("src/main/resources/bombBarBot.png"), size, size, false, false);
            bombBarTop = new Image(new FileInputStream("src/main/resources/bombBarTop.png"), size, size, false, false);
            woodSwordTop = new ImageView(new Image(new FileInputStream("src/main/resources/woodenSwordTop.png"), size, size, false, false));
            woodSwordBot = new ImageView(new Image(new FileInputStream("src/main/resources/woodenSwordBot.png"), size, size, false, false));
            bombBar = new Image(new FileInputStream("src/main/resources/bombBar.png"), size, size, false, false);
            goldBar = new Image(new FileInputStream("src/main/resources/goldBar.png"), size, size, false, false);
            bombTop = new Image(new FileInputStream("src/main/resources/bombTop.png"), size, size, false, false);
            bombBot = new Image(new FileInputStream("src/main/resources/bombBot.png"), size, size, false, false);
            whiteSwordBot = new Image(new FileInputStream("src/main/resources/whiteSwordBot.png"), size, size, false, false);
            whiteSwordTop = new Image(new FileInputStream("src/main/resources/whiteSwordTop.png"), size, size, false, false);
            potionBar = new Image(new FileInputStream("src/main/resources/potionBar.png"), size, size, false, false);
            healthPotionBarTop = new Image(new FileInputStream("src/main/resources/healthPotionBarTop.png"), size, size, false, false);
            healthPotionBarBot = new Image(new FileInputStream("src/main/resources/healthPotionBarBot.png"), size, size, false, false);
            healthPotionTop = new Image(new FileInputStream("src/main/resources/healthPotionTop.png"), size, size, false, false);
            healthPotionBot = new Image(new FileInputStream("src/main/resources/healthPotionBot.png"), size, size, false, false);
            for (int i = 0; i < 10; i++){
                leftNumbers[i] = new Image(new FileInputStream("src/main/resources/" + i + "Left.png"), size, size, false, false);
                rightNumbers[i] = new Image(new FileInputStream("src/main/resources/" + i + "Right.png"), size, size, false, false);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateInterfaceBar(){
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 30; j++){
                Group group = new Group();
                group.getChildren().add(new ImageView(blackTile));
                if (i == 0){
                    if (j == 6)
                        group.getChildren().add(new ImageView(lifeBarName1));
                    else if (j == 7)
                        group.getChildren().add(new ImageView(lifeBarName2));
                    else if (j == 8)
                        group.getChildren().add(new ImageView(lifeBarName3));
                }
                if (j == 21){
                    if (i == 0)
                        group.getChildren().add(new ImageView(attackBarTop));
                    else
                        group.getChildren().add(new ImageView(attackBarBot));
                }
                if (j == 23){
                    if (i == 0){
                        group.getChildren().add(new ImageView(bombBarTop));
                        group.getChildren().add(new ImageView(bombTop));
                    }
                    else{
                        group.getChildren().add(new ImageView(bombBarBot));
                        group.getChildren().add(new ImageView(bombBot));
                    }
                }
                if (j == 25){
                    if (i == 0){
                        group.getChildren().add(new ImageView(healthPotionBarTop));
                        group.getChildren().add(new ImageView(healthPotionTop));
                    }
                    else{
                        group.getChildren().add(new ImageView(healthPotionBarBot));
                        group.getChildren().add(new ImageView(healthPotionBot));
                    }
                }
                if (j == 15){
                    if (i == 0)
                        group.getChildren().add(new ImageView(potionBar));
                }
                if (j == 16){
                    if (i == 0){
                        ImageView temp = new ImageView(leftNumbers[0]);
                        group.getChildren().add(temp);
                        healthPotionCntImageViews.add(temp);
                    }
                }
                if (j == 18){
                    if (i == 0)
                        group.getChildren().add(new ImageView(goldBar));
                    else
                        group.getChildren().add(new ImageView(bombBar));
                }
                if (j == 19){
                    ImageView temp = new ImageView(leftNumbers[0]);
                    if (i == 0) goldCntImageViews.add(temp);
                    if (i == 1) bombCntImageViews.add(temp);

                    group.getChildren().add(temp);
                }

                if (i == 1 && j >= 4 && j <= 11)
                    group.getChildren().add(new ImageView(twoHearts));
                nodes[i][j] = group;
                grid.add(group, j, i);
            }
        }
        App.root.getChildren().add(InterfaceBar.grid);
    }

    public static void updateBombCounter(int newCnt){
        for (ImageView imageView : bombCntImageViews)
            nodes[1][19].getChildren().remove(imageView);
        bombCntImageViews.clear();

        ImageView temp;
        if (newCnt < 10)
            temp = new ImageView(leftNumbers[newCnt]);
        else{
            temp = new ImageView(rightNumbers[newCnt % 10]);
            nodes[1][19].getChildren().add(temp);
            bombCntImageViews.add(temp);

            temp = new ImageView(leftNumbers[newCnt / 10]);
        }
        nodes[1][19].getChildren().add(temp);
        bombCntImageViews.add(temp);
    }

    public static void updateGoldCounter(int newCnt){
        for (ImageView imageView : goldCntImageViews)
            nodes[0][19].getChildren().remove(imageView);
        goldCntImageViews.clear();

        ImageView temp;
        if (newCnt < 10)
            temp = new ImageView(leftNumbers[newCnt]);
        else{
            temp = new ImageView(rightNumbers[newCnt % 10]);
            nodes[0][19].getChildren().add(temp);
            goldCntImageViews.add(temp);

            temp = new ImageView(leftNumbers[newCnt / 10]);
        }
        nodes[0][19].getChildren().add(temp);
        goldCntImageViews.add(temp);
    }

    public static void updateHealthPotionCounter(int newCnt){
        for (ImageView imageView : healthPotionCntImageViews)
            nodes[0][16].getChildren().remove(imageView);
        healthPotionCntImageViews.clear();

        ImageView temp;
        if (newCnt < 10)
            temp = new ImageView(leftNumbers[newCnt]);
        else{
            temp = new ImageView(rightNumbers[newCnt % 10]);
            nodes[0][16].getChildren().add(temp);
            healthPotionCntImageViews.add(temp);

            temp = new ImageView(leftNumbers[newCnt / 10]);
        }
        nodes[0][16].getChildren().add(temp);
        healthPotionCntImageViews.add(temp);
    }

    public static void regenerateOneHeart(){

    }
}
