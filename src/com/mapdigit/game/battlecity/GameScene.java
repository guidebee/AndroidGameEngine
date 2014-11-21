/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
//--------------------------------- PACKAGE ------------------------------------
package com.mapdigit.game.battlecity;

//--------------------------------- IMPORTS ------------------------------------

import android.view.KeyEvent;
import com.guidebee.game.GameEngine;
import com.guidebee.game.InputProcessor;
import com.guidebee.game.ScreenAdapter;
import com.guidebee.game.camera.viewports.ScalingViewport;
import com.guidebee.game.camera.viewports.Viewport;
import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.Texture;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.microedition.LayerManager;
import com.guidebee.game.microedition.Sprite;
import com.guidebee.game.ui.GameController;
import com.guidebee.game.ui.GameControllerListener;
import com.guidebee.game.ui.Skin;
import com.guidebee.game.ui.Touchpad;
import com.guidebee.game.ui.drawable.TextureRegionDrawable;
import com.mapdigit.game.battlecity.actors.*;
import com.mapdigit.game.battlecity.actors.tank.EnemyTank;
import com.mapdigit.game.battlecity.actors.tank.PlayerTank;
import com.mapdigit.game.battlecity.actors.tank.Tank;

import java.util.Random;

//[------------------------------ MAIN CLASS ----------------------------------]




/**
 * The game scene.
 *
 * @author James Shen.
 */
public class GameScene extends ScreenAdapter implements GameControllerListener {

    class BattleCityLayerManager extends LayerManager{

        /**
         * Creates a stage with the specified viewport. The stage will use its own
         * {@link Batch} which will be disposed when the stage
         * is disposed.
         */
        public BattleCityLayerManager(Viewport viewport){
            super(viewport);
        }

        @Override
        public void drawExtra (Batch batch){
            drawScoreBar(batch);
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    //Game content variables,here used class public static member variables.
    /**
     * enemy tank killed in each level for each tank type.
     */
    public static int[] enemyTanksCount = new int[4];

    /**
     * Total enemy tanks in one level.
     */
    private static final int TOTAL_ENEMY_TANKS = 20;

    /**
     * total enemy tanks remains.
     */
    public static int enemyTankRemains = TOTAL_ENEMY_TANKS;

    /**
     * whether can put a powerup, it's because a red tank is been shot dead or
     * in the given period.
     */
    public static boolean canPutPowerup = false;

    ////////////////////////////////////////////////////////////////////////////

    /**
     * The layer manager manage all actors in the game.
     */
    private final BattleCityLayerManager layerManager;

    /**
     * Player's tank.
     */
    private PlayerTank playerTank;

    /**
     * Resource Manager.
     */
    private static ResourceManager resourceManager = ResourceManager.getInstance();

    /**
     * The battle field object.
     */
    private BattleField battleField = null;


    /**
     * the width of the game scene.
     */
    private static int sceneWidth;

    /**
     * the height of the game scene.
     */
    private static int sceneHeight;


    /**
     * the height of the score bar.
     */
    private static int barHeight = 32;


    /**
     * the X origin of the battle field.
     */
    private int battleFieldX;

    /**
     * the Y origin of the battle field.
     */
    private int battleFieldY;

    /**
     * default lives of player
     */
    private final static int DEFAULT_PLAYER_LIVE = 9;

    /**
     * maximum game leves.
     */
    private static final int TOTAL_GAME_LEVELS = 50;

    /**
     * total actors in the scene.
     */
    private int totalLayers = 0;

    /**
     * is game over?
     */
    private boolean isGameover = false;

    /**
     * game over image
     */
    private static TextureRegion imgGameover =
            resourceManager.getImage(ResourceManager.GAME_OVER_SMALL);

    /**
     * pause image
     */
    private static TextureRegion imgPause =
            resourceManager.getImage(ResourceManager.PAUSE);

    /**
     * black number image from 0 to 9
     */
    private static TextureRegion imgNumberBlack =
            resourceManager.getImage(ResourceManager.NUMBER_BLACK);

    /**
     * enemy icon
     */
    private static TextureRegion imgEnemyIcon =
            resourceManager.getImage(ResourceManager.ENEMY_ICON);

    /**
     * first player icon
     */
    private static TextureRegion imgIP =
            resourceManager.getImage(ResourceManager.IP);

    /**
     * flag image
     */
    private static TextureRegion imgFlag =
            resourceManager.getImage(ResourceManager.FLAG);


    /**
     * offset X where start to draw the score bar
     */
    private int marginX = 0;

    /**
     * offset Y where start to draw the score bar
     */
    private int marginY = 0;

    /**
     * Random object to create random numbers.
     */
    private static Random rnd = new Random();

    /**
     * time control to create new enemy tank
     */
    private long enemySpawnStartTime = 0;

    /**
     * minimun spawn timer
     */
    private final static long enemySpawnPeriod = 2000;

    /**
     * timer control when to put an poweup in the battle field
     */
    private long putPowerupStartTime = 0;

    /**
     * put poweup minimum period, 2 minutes
     */
    private final static long putPowerupPeriod = 10000;

    /**
     * display game over or pause
     */
    private static Sprite gameStatus = null;

    /**
     * timer control when game over to display game over image,from bottom
     * to the middle of the screen.
     */
    private long gameOverStartTime = 0;

    /**
     * after this period, display the score screen,default 4 seconds.
     */
    private final static long gameOverPeriod = 4000;


    private final int gameWorldWidth = 400;
    private final int gameWoldHeight = 280;


    /**
     * Constructor.
     */
    public GameScene() {
        layerManager = new BattleCityLayerManager(new ScalingViewport(gameWorldWidth, gameWoldHeight));
        Skin touchpadSkin = new Skin();
        Texture backgroundTexture = GameEngine.assetManager.get("Back_08.png",
                Texture.class);
        touchpadSkin.add("touchBackground", backgroundTexture);
        touchpadSkin.add("touchKnob", GameEngine.assetManager.get("Joystick_08.png",
                Texture.class));
        touchpadSkin.add("shoot", GameEngine.assetManager.get("Button_08_Normal_Shoot.png",
                Texture.class));
        touchpadSkin.add("shoot_pressed", GameEngine.assetManager.get("Button_08_Pressed_Shoot.png",
                Texture.class));
        touchpadSkin.add("virgin", GameEngine.assetManager.get("Button_08_Normal_Virgin.png",
                Texture.class));
        touchpadSkin.add("virgin_pressed", GameEngine.assetManager.get("Button_08_Pressed_Virgin.png",
                Texture.class));

        GameController gameController
                = new GameController((TextureRegionDrawable) touchpadSkin.getDrawable("touchBackground"),
                (TextureRegionDrawable) touchpadSkin.getDrawable("touchKnob"),
                (TextureRegionDrawable) touchpadSkin.getDrawable("shoot"),
                (TextureRegionDrawable) touchpadSkin.getDrawable("shoot_pressed"),
                (TextureRegionDrawable) touchpadSkin.getDrawable("virgin"),
                (TextureRegionDrawable) touchpadSkin.getDrawable("virgin_pressed")
        );
        gameController.addGameControllerListener(this);
        layerManager.setGameController(gameController);

    }


    @Override
    public void resize(int w, int h) {
        sceneWidth = gameWorldWidth;
        sceneHeight = gameWoldHeight;

        if (battleField == null) {
            int xTiles = sceneWidth / ResourceManager.TILE_WIDTH;
            int yTiles = (sceneHeight-barHeight) / ResourceManager.TILE_WIDTH;
            if (xTiles % 2 == 0) xTiles--;
            xTiles = Math.min(xTiles, yTiles);
            yTiles = xTiles;
            battleFieldX = (sceneWidth - xTiles * ResourceManager.TILE_WIDTH) / 2;
            battleFieldY = 0;
            battleField = new BattleField(xTiles, yTiles);
            battleField.readBattlefieldFromHZK(3384);
            Powerup.setBattleField(battleField);
            Powerup.setLayerManager(layerManager);
            Tank.setBattleField(battleField);
            Tank.setLayerManager(layerManager);
            Bullet.setBattleField(battleField);
            Bullet.setLayerManager(layerManager);
            Explosion.setLayerManager(layerManager);
            Score.setBattleField(battleField);
            Score.setLayerManager(layerManager);
            playerTank = Tank.getPlayerTank();
            layerManager.append(battleField);
            int offset=8;
            marginX=(sceneWidth-(imgEnemyIcon.getRegionWidth()*10+offset*2
                    +imgIP.getRegionWidth()+imgFlag.getRegionWidth()+imgNumberBlack.getRegionHeight()))/2;
            marginY=barHeight/4*3;
            totalLayers = layerManager.getSize();
            gameStatus=new Sprite(imgGameover);
            gameStatus.setPosition(xTiles/2*ResourceManager.TILE_WIDTH,
                    (sceneHeight-gameStatus.getHeight())/2);
            gameStatus.setVisible(false);
            layerManager.append(gameStatus);
            newGame();
        }


    }

    /**
     * Start a new game.
     */
    public void newGame() {
        ResourceManager.playSound(ResourceManager.OPEN_SOUND);
        playerTank.initTank();
        playerTank.setAvaiableLives(DEFAULT_PLAYER_LIVE);
        EnemyTank.explodeAllEmenies();
        Bullet.stopAllBullets();
        Explosion.stopAllExplosions();
        Powerup.removeAllPowerups();
        Powerup.putNewPowerup(Powerup.HOME);
        enemyTankRemains = TOTAL_ENEMY_TANKS;
        canPutPowerup = false;
        enemySpawnStartTime = 0;
        enemySpawnStartTime = 0;
        putPowerupStartTime = 0;
        gameOverStartTime = 0;
        gameStatus.setVisible(false);
        //reset player's score to zero.
        if (isGameover) {
            playerTank.setScore(0);
        }
        isGameover = false;
        rnd = new Random();
        for (int i = 0; i < 4; i++) enemyTanksCount[i] = 0;
        //String fileName="/level" + ResourceManager.gameLevel;
        try {
            //battleField.initBattlefield(this.getClass().getResourceAsStream(fileName));
            battleField.readBattlefieldFromHZK(ResourceManager.gameLevels[
                    ResourceManager.gameLevel]);
        } catch (Exception e) {
            //inglore the exception.
        }
        if(ResourceManager.gameLevel>ResourceManager.MAX_GAME_LEVEL){
            ResourceManager.gameLevel=0;
        }

    }

    /**
     * draw number in score bar
     * @param g the graphics object
     * @param number the number need to be drawn
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    private void drawNumber(Batch g, int number,int x,int y){
        TextureRegion imageNumber=imgNumberBlack;
        String strNumber=String.valueOf(number);
        int numberWidth=imageNumber.getRegionHeight();
        for(int i=0;i<strNumber.length();i++){
            char ch=strNumber.charAt(i);
            int index=(ch-'0') % 10;
            TextureRegion oneNumber=new TextureRegion(imageNumber,index*numberWidth,0,
                    numberWidth,numberWidth);

            g.draw(oneNumber, x + i * numberWidth, y);
        }
    }


    /**
     * put an power up in the battle field.
     */
    private void putAnPowerup() {
        int powupSelection = Math.abs(rnd.nextInt()) % 100;
        int type;
        if (powupSelection > 95) {
            type = Powerup.TANK;
        } else if (powupSelection > 80) {
            type = Powerup.BOMB;
        } else if (powupSelection > 70) {
            type = Powerup.SHOVEL;
        } else if (powupSelection > 60) {
            type = Powerup.CLOCK;
        } else if (powupSelection > 50) {
            type = Powerup.SHIELD;
        } else {
            type = Powerup.STAR;
        }
        Powerup.putNewPowerup(type);
        putPowerupStartTime = System.currentTimeMillis();
        canPutPowerup = false;
    }


    /**
     * Game over,either all player tanks or the home has been destroyed.
     */
    private void gameOver() {
        isGameover=true;
        gameStatus.setVisible(true);
        gameStatus.setPosition(gameStatus.getX(),0);
        playerTank.stop();
        gameOverStartTime=System.currentTimeMillis();
    }


    /**
     * Game logic is here.
     */
    private void applyGameLogic() {
        //normal game sequence.
        long tickTime = System.currentTimeMillis();
        if (!isGameover) {
            //Check if player obtain some powerup.
            Powerup.checkPlayerTank(playerTank);
            //Spawn enemy tank if needed
            boolean canSpawnEnemyTank = false;
            if (enemyTankRemains - EnemyTank.getVisibleEnemyTanks() > 0) {
                if (EnemyTank.getVisibleEnemyTanks() < 10) {
                    if (enemySpawnStartTime > 0) {
                        if (tickTime - enemySpawnStartTime > enemySpawnPeriod) {
                            canSpawnEnemyTank = true;
                        }
                    } else {
                        canSpawnEnemyTank = true;
                    }
                }
            } else {
                if (EnemyTank.getVisibleEnemyTanks() == 0) {
                    ResourceManager.gameLevel++;
                    newGame();

                }
            }
            if (canSpawnEnemyTank) {
                EnemyTank enemyTank;
                int tankSelection = Math.abs(rnd.nextInt()) % 100;
                if (tankSelection > 90 - ResourceManager.gameLevel) {
                    enemyTank = EnemyTank.newEnemyTank(EnemyTank.TYPE_HEAVY);
                } else if (tankSelection > 75 - ResourceManager.gameLevel) {
                    enemyTank = EnemyTank.newEnemyTank(EnemyTank.TYPE_SMART);
                } else if (tankSelection > 55 - ResourceManager.gameLevel) {
                    enemyTank = EnemyTank.newEnemyTank(EnemyTank.TYPE_FAST);
                } else {
                    enemyTank = EnemyTank.newEnemyTank(EnemyTank.TYPE_SIMPLE);
                }

                if (enemyTank != null) {

                    tankSelection = Math.abs(rnd.nextInt()) % 100;
                    if (tankSelection + ResourceManager.gameLevel > 90) {

                        enemyTank.setHasPrize(true);
                    }
                    enemySpawnStartTime = tickTime;
                }

            }
            //Check if player has been killed
            if (!playerTank.isVisible()) {
                if (playerTank.getAvaiableLives() > 0) {
                    playerTank.initTank();
                    playerTank.setVisible(true);
                } else {
                    gameOver();
                }
            }
            //Check if home is been destoryed, game over
            if (Powerup.isHomeDestroyed()) {
                gameOver();
            }
            //put an poweup in the battle field
            if ((tickTime - putPowerupStartTime > putPowerupPeriod) || canPutPowerup) {
                putAnPowerup();
            }
        } else {
            //game is over, display game over animation.
            if (((tickTime - gameOverStartTime) < gameOverPeriod) &&
                    gameOverStartTime > 0) {
                int finalY=(int)(sceneHeight-gameStatus.getHeight())/2;
                if(gameStatus.getY()<finalY) {
                    gameStatus.setPosition(gameStatus.getX(),
                            gameStatus.getY() + 1);
                }


            } else {
                newGame();
                gameOverStartTime = 0;
            }

        }
    }


    private void drawScoreBar(Batch g){

        int offset=8;
        int x,y;

        for(int i=0;i<enemyTankRemains-EnemyTank.getVisibleEnemyTanks();i++){
            int changeRow=i>9 ? 1:0;
            x=marginX+(i %10) *imgEnemyIcon.getRegionWidth();
            y=sceneHeight-(marginY+changeRow*imgEnemyIcon.getRegionWidth());
            g.draw(imgEnemyIcon, x, y);
        }
        //draw IP
        x=marginX+imgEnemyIcon.getRegionWidth()*10+offset;
        y=sceneHeight-marginY;
        int lives=playerTank.getAvaiableLives();
        drawNumber(g,lives,x+imgIP.getRegionWidth()-imgNumberBlack.getRegionHeight(),
                y+imgIP.getRegionHeight()-imgNumberBlack.getRegionHeight());
        g.draw(imgIP, x, y);
        x+=imgIP.getRegionWidth()+offset;
        y=sceneHeight-marginY;
        g.draw(imgFlag, x, y);
        drawNumber(g,ResourceManager.gameLevel,x+imgFlag.getRegionWidth(),
                y+imgIP.getRegionHeight()-imgNumberBlack.getRegionHeight());
    }


    private InputProcessor savedInputProcessor;

    @Override
    public void show() {
        savedInputProcessor = GameEngine.input.getInputProcessor();
        GameEngine.input.setInputProcessor(layerManager);
    }

    @Override
    public void hide() {
        GameEngine.input.setInputProcessor(savedInputProcessor);
    }


    @Override
    public void render(float delta) {
        layerManager.act();
        applyGameLogic();
        GameEngine.graphics.clearScreen(0.25f, 0.25f, 0.25f, 1);
        layerManager.draw(battleFieldX, battleFieldY);
    }


    @Override
    public void KnobMoved(Touchpad touchpad, Direction direction) {
        int gameAction = 0;
        switch (direction) {
            case EAST:
                gameAction = KeyEvent.KEYCODE_DPAD_RIGHT;
                break;
            case NORTH:
                gameAction = KeyEvent.KEYCODE_DPAD_UP;
                break;
            case WEST:
                gameAction = KeyEvent.KEYCODE_DPAD_LEFT;
                break;
            case SOUTH:
                gameAction = KeyEvent.KEYCODE_DPAD_DOWN;
                break;

        }
        playerTank.keyPressed(gameAction);
    }

    @Override
    public void ButtonPressed(GameButton button) {
        if (button == GameButton.BUTTON_A) {
            playerTank.keyPressed(KeyEvent.KEYCODE_DPAD_CENTER);
        }

    }
}
