package GUI;

import MapsElements.*;

import static MapsElements.MoveDirection.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class App extends Application implements IMapChangeObserver {
    public static Scene scene;
    public static InterfaceBar interfaceBar = new InterfaceBar();
    public static AbstractMap map;
    public static final VBox root = new VBox();
    protected static ImageView[] attackViews;
    protected static ImageView[][] bombExplosionViews = new ImageView[3][5];
    protected static ImageView bombView = new ImageView(AbstractMap.bomb);
    public static boolean animationRunning, bombAnimationRunning, throwAnimation;
    public boolean moveLeft, moveRight, moveUp, moveDown;
    public boolean canDealDamage = true;
    public boolean throwHit, normalHit;
    private static AbstractMap bombMap;
    protected static int attackIter, attackFrameCount, bombFrameCount, bombIter;
    public static AnimationTimer attackAnimation, bombAnimation, gameLoop;
    long lastMoved, lastDealtDamage;
    public static ImageView startScreen, gameOver, winScreen;
    public static MoveDirection swordDirection;
    static {
        AbstractMap.getMapsReferences(new StartingMap());
        AbstractCave.getCavesReferences();
        map = AbstractMap.maps.get("Start");
        try {
            startScreen = new ImageView(new Image(new FileInputStream("src/main/resources/startScreen.png"), 1050, 770, false, false));
            gameOver = new ImageView(new Image(new FileInputStream("src/main/resources/gameOver.png"), 1050, 770, false, false));
            winScreen = new ImageView(new Image(new FileInputStream("src/main/resources/winScreen.png"), 1050, 770, false, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++)
                bombExplosionViews[i][j] = new ImageView(AbstractMap.hero.getBombImages()[i + 1]);
        }
    }
    private static Creature mob;
    protected static Vector2d moveVector;
    private static boolean isRunning = false;

    public void init(){
        AbstractMap.addMapChangeObserver(this);
        root.getChildren().add(startScreen);
        scene = new Scene(root, 1049, 769);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Legend of Zelda");
        primaryStage.setScene(scene);
        primaryStage.show();


        attackAnimation = new AnimationTimer(){
            private final Hero hero = AbstractMap.hero;
            private Vector2d swordPos;
            private MoveDirection swordDir;
            public void handle(long currTime){
                throwHit = false;
                checkSwordCollision();
                if (!throwHit && attackFrameCount % 3 == 0){
                    doHandle();
                    attackFrameCount = 0;
                }
                attackFrameCount += 1;
            }

            private void checkSwordCollision(){
                if (throwAnimation && attackIter == 0){
                    if (handleBossAttackDamage(swordPos, swordPos) || handleAttackDamageToNormalCreature(swordPos, swordPos)){
                        map.nodes[swordPos.getY()][swordPos.getX()].getChildren().remove(hero.getSwordPicture(swordDir));
                        throwAnimation = false;
                        throwHit = true;
                        attackAnimation.stop();
                    }
                }
                else if (animationRunning && !throwAnimation && !normalHit){
                    if (handleAttackDamageToNormalCreature(hero.getPosition(), hero.getPosition().add(moveVector)) ||
                        handleBossAttackDamage(hero.getPosition(), hero.getPosition().add(moveVector)))
                        normalHit = true;
                }
            }

            private void doHandle(){
                if (!throwAnimation){
                    if (attackIter - 2 >= 0){
                        map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[attackIter - 2]);
                        if (inBorders())
                            map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[attackIter - 1]);
                    }

                    map.nodes[hero.getY()][hero.getX()].getChildren().add(attackViews[attackIter]);
                    if (attackViews[attackIter + 1] != null && inBorders())
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().add(attackViews[attackIter + 1]);

                    attackIter += 2;
                    if (attackIter == 4 && inBorders() && !map.isOccupied(hero.getPosition().add(moveVector)) && hero.getHealth() == Hero.maxHealth && !(App.map.mobs.containsKey(hero.getY()) && App.map.mobs.get(hero.getY()).containsKey(hero.getX()) && App.map.mobs.get(hero.getY()).get(hero.getX()).size() > 0
                            || App.map.mobs.containsKey(hero.getY() + moveVector.getY()) && App.map.mobs.get(hero.getY() + moveVector.getY()).containsKey(hero.getX() + moveVector.getX()) && App.map.mobs.get(hero.getY() + moveVector.getY()).get(hero.getX() + moveVector.getX()).size() > 0))
                        throwAnimation = true;


                    if (attackIter >= attackViews.length){
                        map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[attackIter - 2]);
                        if (inBorders())
                            map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[attackIter - 1]);
                        map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
                        animationRunning = false;
                        normalHit = false;
                        attackAnimation.stop();
                    }
                }
                else{
                    if (attackIter == 4){
                        swordPos = hero.getPosition().add(moveVector);
                        swordDir = hero.getOrientation();

                        map.nodes[hero.getY()][hero.getX()].getChildren().remove(attackViews[attackIter - 2]);
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().remove(attackViews[attackIter - 1]);

                        map.nodes[hero.getY()][hero.getX()].getChildren().add(hero.getPicture());
                        map.nodes[hero.getY() + moveVector.getY()][hero.getX() + moveVector.getX()].getChildren().add(hero.getSwordPicture(swordDir));
                        animationRunning = false;
                        attackIter = 0;
                    }
                    else{
                        map.nodes[swordPos.getY()][swordPos.getX()].getChildren().remove(hero.getSwordPicture(swordDir));
                        Vector2d newPos = swordPos.add(moveVector);

                        if (newPos.follows(AbstractMap.lowerLeft) && newPos.precedes(AbstractMap.upperRight) && !map.isOccupied(newPos)){
                            swordPos = newPos;
                            map.nodes[swordPos.getY()][swordPos.getX()].getChildren().add(hero.getSwordPicture(swordDir));
                        }
                        else{
                            throwAnimation = false;
                            attackAnimation.stop();
                        }
                    }
                }
            }

            private boolean handleAttackDamageToNormalCreature(Vector2d heroPos, Vector2d swordPos){
                if (App.map.mobs.containsKey(heroPos.getY()) && App.map.mobs.get(heroPos.getY()).containsKey(heroPos.getX()) && App.map.mobs.get(heroPos.getY()).get(heroPos.getX()).size() > 0
                        || App.map.mobs.containsKey(swordPos.getY()) && App.map.mobs.get(swordPos.getY()).containsKey(swordPos.getX()) && App.map.mobs.get(swordPos.getY()).get(swordPos.getX()).size() > 0){
                    if (App.map.mobs.containsKey(heroPos.getY()) && App.map.mobs.get(heroPos.getY()).containsKey(heroPos.getX()) && App.map.mobs.get(heroPos.getY()).get(heroPos.getX()).size() > 0)
                        mob = App.map.mobs.get(heroPos.getY()).get(heroPos.getX()).get(App.map.mobs.get(heroPos.getY()).get(heroPos.getX()).size() - 1);
                    else
                        mob = App.map.mobs.get(swordPos.getY()).get(swordPos.getX()).get(App.map.mobs.get(swordPos.getY()).get(swordPos.getX()).size() - 1);

                    mob.removeHealth(App.map, hero.attackDamage);
                    return true;
                }
                return false;
            }

            private boolean handleBossAttackDamage(Vector2d heroPos, Vector2d swordPos){
                if (map instanceof NorthWestMap && NorthWestMap.boss != null){
                    if (collidesWithBoss(heroPos, hero.attackDamage)) return true;
                    return NorthWestMap.boss != null && collidesWithBoss(swordPos, hero.attackDamage);
                }
                return false;
            }

            private boolean inBorders(){
                return hero.getY() + moveVector.getY() < AbstractMap.height && hero.getY() + moveVector.getY() >= 0 &&
                        hero.getX() + moveVector.getX() < AbstractMap.width && hero.getX() + moveVector.getX() >= 0;
            }
        };

        bombAnimation = new AnimationTimer() {
            Vector2d bombPos, newPos;
            int i;
            final Hero hero = AbstractMap.hero;
            public void handle(long now){
                if (bombIter == 0)
                    doHandle();
                else if ((bombIter > 1 && bombFrameCount % 8 == 0) || bombFrameCount % 90 == 0){
                    doHandle();
                    bombFrameCount = 0;
                }

                bombFrameCount += 1;
            }

            private void doHandle(){
                if (bombIter == 0){
                    bombPos = getBombPosition();
                    bombMap.nodes[bombPos.getY()][bombPos.getX()].getChildren().add(bombView);
                }
                else if (bombIter == 1){
                    bombMap.nodes[bombPos.getY()][bombPos.getX()].getChildren().remove(bombView);
                    addAllExplosions();
                    checkSecretCaveBombing(AbstractCave.caves.get("East").getPosition(), AbstractMap.maps.get("East"));
                    checkSecretCaveBombing(AbstractCave.caves.get("SouthWest").getPosition(), AbstractMap.maps.get("SouthWest"));
                    if (bombMap.equals(App.map)) handleDamageToHero();
                    handleBombDamageToNormalCreature();
                    handleBombDamageToBoss();
                }
                else if (bombIter == 2 || bombIter == 3){
                    removeAllExplosions();
                    addAllExplosions();
                }
                else{
                    removeAllExplosions();
                    bombAnimationRunning = false;
                    bombAnimation.stop();
                }

                bombIter += 1;
            }

            private void checkSecretCaveBombing(Vector2d cavePosition, AbstractMap caveMap){
                int y = cavePosition.getY(); int x = cavePosition.getX();
                if (App.map.occupancyMap[y][x] && App.map.equals(caveMap) && (bombPos.equals(cavePosition)
                || bombPos.equals(cavePosition.add(WEST.toUnitVector())) || bombPos.equals(cavePosition.add(NORTH.toUnitVector()))
                || bombPos.equals(cavePosition.add(SOUTH.toUnitVector())) || bombPos.equals(cavePosition.add(EAST.toUnitVector())))){
                    App.map.occupancyMap[y][x] = false;
                    App.map.nodes[y][x].getChildren().clear();
                    if (caveMap instanceof EastMap)
                        App.map.nodes[y][x].getChildren().add(new ImageView(InterfaceBar.blackTile));
                    else
                        App.map.nodes[y][x].getChildren().add(new ImageView(AbstractMap.grayLeftStairs));
                }
            }

            private void removeAllExplosions(){
                removeExplosion(null);
                removeExplosion(NORTH);
                removeExplosion(WEST);
                removeExplosion(EAST);
                removeExplosion(SOUTH);
                i = 0;
            }

            private void addAllExplosions(){
                addExplosion(null);
                addExplosion(NORTH);
                addExplosion(WEST);
                addExplosion(EAST);
                addExplosion(SOUTH);
                i = 0;
            }

            private void addExplosion(MoveDirection dir){
                if (dir != null){
                    Vector2d temp = bombPos.add(dir.toUnitVector());
                    if (inBorders(temp))
                        bombMap.nodes[temp.getY()][temp.getX()].getChildren().add(bombExplosionViews[bombIter - 1][i]);
                }
                else
                    bombMap.nodes[bombPos.getY()][bombPos.getX()].getChildren().add(bombExplosionViews[bombIter - 1][i]);
                i += 1;
            }

            private void removeExplosion(MoveDirection dir){
                if (dir != null){
                    Vector2d temp = bombPos.add(dir.toUnitVector());
                    if (inBorders(temp))
                        bombMap.nodes[temp.getY()][temp.getX()].getChildren().remove(bombExplosionViews[bombIter - 2][i]);
                }
                else
                    bombMap.nodes[bombPos.getY()][bombPos.getX()].getChildren().remove(bombExplosionViews[bombIter - 2][i]);
                i += 1;
            }

            private Vector2d getBombPosition(){
                newPos = hero.getPosition().add(hero.getOrientation().toUnitVector());
                if (inBorders(newPos))
                    return newPos;
                return hero.getPosition();
            }

            private void handleBombDamageToNormalCreature(){
                handleDamage(bombPos);
                handleDamage(bombPos.add(WEST.toUnitVector()));
                handleDamage(bombPos.add(EAST.toUnitVector()));
                handleDamage(bombPos.add(NORTH.toUnitVector()));
                handleDamage(bombPos.add(SOUTH.toUnitVector()));
            }

            private void handleDamageToHero(){
                doDamageToHero(bombPos);
                doDamageToHero(bombPos.add(WEST.toUnitVector()));
                doDamageToHero(bombPos.add(EAST.toUnitVector()));
                doDamageToHero(bombPos.add(NORTH.toUnitVector()));
                doDamageToHero(bombPos.add(SOUTH.toUnitVector()));
            }

            private void doDamageToHero(Vector2d position){
                if (position.equals(hero.getPosition()))
                    hero.removeHealth(App.map, 3);
            }

            private void handleDamage(Vector2d position){
                if (bombMap.mobs.containsKey(position.getY()) && bombMap.mobs.get(position.getY()).containsKey(position.getX())){
                    for (Iterator<Creature> iter = bombMap.mobs.get(position.getY()).get(position.getX()).iterator(); iter.hasNext();){
                        Creature creature = iter.next();
                        iter.remove();
                        creature.removeHealth(bombMap, 3);
                    }
                }
            }

            private void handleBombDamageToBoss(){
                if (bombMap instanceof NorthWestMap && NorthWestMap.boss != null){
                    collidesWithBoss(bombPos, 3);
                    if (NorthWestMap.boss != null) collidesWithBoss(bombPos.add(WEST.toUnitVector()), 3);
                    if (NorthWestMap.boss != null) collidesWithBoss(bombPos.add(EAST.toUnitVector()), 3);
                    if (NorthWestMap.boss != null) collidesWithBoss(bombPos.add(NORTH.toUnitVector()), 3);
                    if (NorthWestMap.boss != null) collidesWithBoss(bombPos.add(SOUTH.toUnitVector()), 3);
                }
            }

            private boolean inBorders(Vector2d position){
                return position.precedes(AbstractMap.upperRight) && position.follows(AbstractMap.lowerLeft);
            }
        };

        gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (!isRunning){
                    AbstractMap.maps.get("East").animation.start();
                    AbstractMap.maps.get("SouthEast").animation.start();
                    AbstractMap.maps.get("North").animation.start();
                    AbstractMap.maps.get("NorthEast").animation.start();
                    AbstractMap.maps.get("South").animation.start();
                    AbstractMap.maps.get("SouthWest").animation.start();
                    AbstractMap.maps.get("West").animation.start();
                    AbstractMap.maps.get("NorthWest").animation.start();
                    isRunning = true;
                }

                // check 5 times per second
                if (currentNanoTime - lastDealtDamage >= 200_000_000)
                    canDealDamage = true;

                if (canDealDamage && map.mobCollidesWithHero()){
                    AbstractMap.hero.removeHealth(map, 1);
                    canDealDamage = false;
                    lastDealtDamage = currentNanoTime;
                }

                // check 24 times per second
                if (currentNanoTime - lastMoved >= 41_666_667){
                    if (!animationRunning) move();
                    else {
                        moveLeft = false; moveDown = false; moveUp = false; moveRight = false;
                    }
                    lastMoved = currentNanoTime;
                }

            }
        };

        scene.setOnKeyPressed(event -> {
            this.renderHeroPic();
            root.getChildren().clear();
            interfaceBar.generateInterfaceBar();
            root.getChildren().add(map.grid);
            this.setKeyAssignments(scene);
            gameLoop.start();
        });
    }

    public static void main(String[] args){
        launch(args);
    }

    public void move(){
        if (moveLeft) {
            moveHero(AbstractMap.hero, WEST);
            moveLeft = false;
        }
        else if (moveRight){
            moveHero(AbstractMap.hero, EAST);
            moveRight = false;
        }
        else if (moveUp){
            moveHero(AbstractMap.hero, NORTH);
            moveUp = false;
        }
        else if (moveDown){
            moveHero(AbstractMap.hero, SOUTH);
            moveDown = false;
        }
    }

    private void setKeyAssignments(Scene scene){
        scene.setOnKeyPressed(ke -> {
            if (!animationRunning){
                switch (ke.getCode()) {
                    case LEFT -> {
                        moveLeft = true;
                        moveRight = false;
                        moveDown = false;
                        moveUp = false;
                    }
                    case RIGHT -> {
                        moveRight = true;
                        moveLeft = false;
                        moveDown = false;
                        moveUp = false;
                    }
                    case DOWN -> {
                        moveRight = false;
                        moveLeft = false;
                        moveUp = false;
                        moveDown = true;
                    }
                    case UP -> {
                        moveRight = false;
                        moveLeft = false;
                        moveDown = false;
                        moveUp = true;
                    }
                    case A -> {
                        if (!throwAnimation && (Hero.hasWoodenSword || Hero.hasWhiteSword))
                            renderAttackAnimation();
                    }
                    case B ->{
                        if (!bombAnimationRunning && AbstractMap.hero.bombCnt > 0)
                            renderBombAnimation();
                    }
                    case H -> {
                        if (AbstractMap.hero.healthPotionCnt > 0)
                            useHealthPostion();
                    }
                }
                ke.consume();
                if (!Hero.hasWoodenSword && !Hero.hasWhiteSword && App.map instanceof StartingCave cave && AbstractMap.hero.getPosition().equals(StartingCave.woodenSwordPosition))
                    cave.doPickUpAnimation();
                else if (!EastSecretCave.itemsPickedUp && App.map instanceof EastSecretCave cave && (AbstractMap.hero.getPosition().equals(cave.leftItemPos) || AbstractMap.hero.getPosition().equals(cave.rightItemPos)))
                    cave.doPickUpAnimation();
                else if (!SouthWestSecretCave.itemsBought && App.map instanceof SouthWestSecretCave cave && cave.isHeroOnItem() && cave.canGetItem())
                    cave.doPickUpAnimation();
            }
        });
    }

    public void moveHero(Hero hero, MoveDirection direction){
        this.removeHeroPic();
        hero.move(map, direction);
        this.renderHeroPic();
        hero.pickUpItems(map);
    }

    private boolean collidesWithBoss(Vector2d position, int attackPower){
        Vector2d bossPos = NorthWestMap.boss.getPosition();
        if (position.equals(bossPos.add(WEST.toUnitVector()))){
            NorthWestMap.boss.removeHealth(attackPower, "left");
            return true;
        }
        else if (position.equals(bossPos.add(EAST.toUnitVector()))){
            NorthWestMap.boss.removeHealth(attackPower, "right");
            return true;
        }
        else if (position.equals(bossPos.add(NORTH.toUnitVector()))){
            NorthWestMap.boss.removeHealth(attackPower, "top");
            return true;
        }
        else if (position.equals(bossPos.add(SOUTH.toUnitVector()))){
            NorthWestMap.boss.removeHealth(attackPower, "bot");
            return true;
        }
        return false;
    }

    public void renderAttackAnimation(){
        swordDirection = AbstractMap.hero.getOrientation();
        attackIter = 0;
        attackViews = AbstractMap.hero.getAnimationAttack();
        map.nodes[AbstractMap.hero.getY()][AbstractMap.hero.getX()].getChildren().remove(AbstractMap.hero.getPicture());

        animationRunning = true;
        moveVector = AbstractMap.hero.getOrientation().toUnitVector();
        attackAnimation.start();
    }

    public void renderBombAnimation(){
        bombIter = 0;
        bombAnimationRunning = true;
        bombMap = App.map;
        bombAnimation.start();
        AbstractMap.hero.bombCnt -= 1;
        InterfaceBar.updateBombCounter(AbstractMap.hero.bombCnt);
    }

    public void useHealthPostion(){
        if (AbstractMap.hero.getHealth() != Hero.maxHealth){
            InterfaceBar.regenerateFullHp(AbstractMap.hero.getHealth());
            AbstractMap.hero.setHealth(Hero.maxHealth);
        }
        AbstractMap.hero.healthPotionCnt -= 1;
        InterfaceBar.updateHealthPotionCounter(AbstractMap.hero.healthPotionCnt);
    }

    public void renderHeroPic(){
        map.nodes[AbstractMap.hero.getY()][AbstractMap.hero.getX()].getChildren().add(AbstractMap.hero.getPicture());
    }

    public void removeHeroPic(){
        map.nodes[AbstractMap.hero.getY()][AbstractMap.hero.getX()].getChildren().remove(AbstractMap.hero.getPicture());
    }

    public void notifyMapChange(AbstractMap newMap){
        root.getChildren().remove(map.grid);
        root.getChildren().add(newMap.grid);
        AbstractMap.hero.setPositionAfterMapChange();
        map = newMap;
    }
}
