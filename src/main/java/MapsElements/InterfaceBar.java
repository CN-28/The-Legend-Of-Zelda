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
    public static final ArrayList<ImageView> hpImageViews = new ArrayList<>();
    public static Image blackTile, emptyHeart, halfHeart, twoHearts, oneHeart, oneHalfHearts, lifeBarName1, lifeBarName2, lifeBarName3, attackBarTop, attackBarBot,
    bombBarBot, bombBarTop, goldBar, bombBar, potionBar, healthPotionBarTop,
    healthPotionBarBot;
    public static ImageView woodSwordTop, woodSwordBot, bombBot, bombTop, healthPotionBot, healthPotionTop, whiteSwordTop, whiteSwordBot, singleHalfHeart, singleOneHeart, singleZeroHeart;
    static {
        try {
            blackTile = new Image(new FileInputStream("src/main/resources/blackTile.png"), size, size, false, false);
            emptyHeart = new Image(new FileInputStream("src/main/resources/zeroHeart.png"), size, size, false, false);
            halfHeart = new Image(new FileInputStream("src/main/resources/halfHeart.png"), size, size, false, false);
            oneHeart = new Image(new FileInputStream("src/main/resources/oneHeart.png"), size, size, false, false);
            oneHalfHearts = new Image(new FileInputStream("src/main/resources/oneHalfHearts.png"), size, size, false, false);
            twoHearts = new Image(new FileInputStream("src/main/resources/twoHearts.png"), size, size, false, false);
            singleHalfHeart = new ImageView(new Image(new FileInputStream("src/main/resources/singleHalfHeart.png"), size, size, false, false));
            singleOneHeart = new ImageView(new Image(new FileInputStream("src/main/resources/singleOneHeart.png"), size, size, false, false));
            singleZeroHeart = new ImageView(new Image(new FileInputStream("src/main/resources/singleZeroHeart.png"), size, size, false, false));
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
            bombTop = new ImageView(new Image(new FileInputStream("src/main/resources/bombTop.png"), size, size, false, false));
            bombBot = new ImageView(new Image(new FileInputStream("src/main/resources/bombBot.png"), size, size, false, false));
            whiteSwordBot = new ImageView(new Image(new FileInputStream("src/main/resources/whiteSwordBot.png"), size, size, false, false));
            whiteSwordTop = new ImageView(new Image(new FileInputStream("src/main/resources/whiteSwordTop.png"), size, size, false, false));
            potionBar = new Image(new FileInputStream("src/main/resources/potionBar.png"), size, size, false, false);
            healthPotionBarTop = new Image(new FileInputStream("src/main/resources/healthPotionBarTop.png"), size, size, false, false);
            healthPotionBarBot = new Image(new FileInputStream("src/main/resources/healthPotionBarBot.png"), size, size, false, false);
            healthPotionTop = new ImageView(new Image(new FileInputStream("src/main/resources/healthPotionTop.png"), size, size, false, false));
            healthPotionBot = new ImageView(new Image(new FileInputStream("src/main/resources/healthPotionBot.png"), size, size, false, false));
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
                    if (i == 0)
                        group.getChildren().add(new ImageView(bombBarTop));
                    else
                        group.getChildren().add(new ImageView(bombBarBot));
                }
                if (j == 25){
                    if (i == 0)
                        group.getChildren().add(new ImageView(healthPotionBarTop));
                    else
                        group.getChildren().add(new ImageView(healthPotionBarBot));
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

                if (i == 1 && j >= 5 && j <= 9){
                    ImageView hpImageView = new ImageView(twoHearts);
                    group.getChildren().add(hpImageView);
                    hpImageViews.add(hpImageView);
                }
                nodes[i][j] = group;
                grid.add(group, j, i);
            }
        }
        App.root.getChildren().add(InterfaceBar.grid);
    }

    public static void updateBombCounter(int newCnt){
        if (newCnt == 0){
            nodes[0][23].getChildren().remove(bombTop);
            nodes[1][23].getChildren().remove(bombBot);
        }
        else if (!nodes[0][23].getChildren().contains(bombTop)){
            nodes[0][23].getChildren().add(bombTop);
            nodes[1][23].getChildren().add(bombBot);
        }

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
        if (newCnt == 0){
            nodes[0][25].getChildren().remove(healthPotionTop);
            nodes[1][25].getChildren().remove(healthPotionBot);
        }
        else if (!nodes[0][25].getChildren().contains(healthPotionTop)){
            nodes[0][25].getChildren().add(healthPotionTop);
            nodes[1][25].getChildren().add(healthPotionBot);
        }

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

    public static void regenerateOneHeart(int health){
        nodes[1][5 + health / 4].getChildren().remove(hpImageViews.get(health / 4));
        ImageView hpImageView = null;
        switch (health % 4){
            case 0 -> {
                if (Hero.maxHealth % 4 != 0 && health == Hero.maxHealth - 2) hpImageView = singleOneHeart;
                else hpImageView = new ImageView(oneHeart);
            }
            case 1 -> {
                if (Hero.maxHealth % 4 != 0 && health == Hero.maxHealth - 1) hpImageView = singleOneHeart;
                else hpImageView = new ImageView(oneHalfHearts);
            }
            case 2 -> hpImageView = new ImageView(twoHearts);
            case 3 -> {
                hpImageView = new ImageView(twoHearts);
                if (health < Hero.maxHealth - 1){
                    ImageView nextHpImageView;
                    if (Hero.maxHealth % 4 != 0 && health == Hero.maxHealth - 3) nextHpImageView = singleHalfHeart;
                    else nextHpImageView = new ImageView(halfHeart);
                    nodes[1][6 + health / 4].getChildren().remove(hpImageViews.get(health / 4 + 1));
                    nodes[1][6 + health / 4].getChildren().add(nextHpImageView);
                    hpImageViews.set(health / 4 + 1, nextHpImageView);
                }
            }
        }
        nodes[1][5 + health / 4].getChildren().add(hpImageView);
        hpImageViews.set(health / 4, hpImageView);
    }

    public static void regenerateFullHp(int health){
        for (int i = health / 4; i < Hero.maxHealth / 4; i++){
            nodes[1][5 + i].getChildren().remove(hpImageViews.get(i));
            ImageView hpImageView;
            hpImageView = new ImageView(twoHearts);

            nodes[1][5 + i].getChildren().add(hpImageView);
            hpImageViews.set(i, hpImageView);
        }
        if (Hero.maxHealth % 4 != 0){
            nodes[1][5 + Hero.maxHealth / 4].getChildren().remove(singleZeroHeart);
            nodes[1][5 + Hero.maxHealth / 4].getChildren().remove(singleHalfHeart);
            nodes[1][5 + Hero.maxHealth / 4].getChildren().add(singleOneHeart);
            hpImageViews.set(Hero.maxHealth / 4, singleOneHeart);
        }
    }

    public static void updateHealthBar(int newHP){
        nodes[1][5 + newHP / 4].getChildren().remove(hpImageViews.get(newHP / 4));
        ImageView hpImageView = null;
        switch (newHP % 4){
            case 0 -> {
                if (Hero.maxHealth % 4 != 0 && newHP == Hero.maxHealth - 2) hpImageView = singleZeroHeart;
                else hpImageView = new ImageView(emptyHeart);
            }
            case 1 -> {
                if (Hero.maxHealth % 4 != 0 && newHP == Hero.maxHealth - 1) hpImageView = singleHalfHeart;
                else hpImageView = new ImageView(halfHeart);
            }
            case 2 -> hpImageView = new ImageView(oneHeart);
            case 3 -> hpImageView = new ImageView(oneHalfHearts);
        }
        nodes[1][5 + newHP / 4].getChildren().add(hpImageView);
        hpImageViews.set(newHP / 4, hpImageView);
    }

    public static void updateHpAfterBigHeartUse(int health){
        if (health < Hero.maxHealth - 1){
            if (health == Hero.maxHealth - 2) regenerateFullHp(health);
            else updateHealthBar(health + 2);
            nodes[1][5 + Hero.maxHealth / 4].getChildren().add(singleZeroHeart);
            hpImageViews.add(singleZeroHeart);
        }
        else if (health == Hero.maxHealth - 1){
            nodes[1][5 + health / 4].getChildren().remove(hpImageViews.get(health / 4));
            ImageView imageView = new ImageView(twoHearts);
            nodes[1][5 + health / 4].getChildren().add(imageView);
            hpImageViews.set(health / 4, imageView);

            nodes[1][6 + health / 4].getChildren().add(singleHalfHeart);
            hpImageViews.add(singleHalfHeart);
        }
        else if (health == Hero.maxHealth){
            nodes[1][5 + health / 4].getChildren().add(singleOneHeart);
            hpImageViews.add(singleOneHeart);
        }
    }
}
