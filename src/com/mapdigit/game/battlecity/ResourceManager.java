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

import com.guidebee.game.GameEngine;
import com.guidebee.game.audio.Sound;
import com.guidebee.game.graphics.Texture;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;

import java.util.Random;

//[------------------------------ MAIN CLASS ----------------------------------]


/**
 * Resource Manager.A Singleton class used to manage images and wav/midi
 * resources.
 *
 * @author James Shen
 */
public class ResourceManager {
    /**
     * the maximum game level.
     */
    public static final int MAX_GAME_LEVEL = 50;
    /**
     * game level index, for simplicity ,the game  use chinese character as
     * the battle field setting.
     */
    public static int[] gameLevels = new int[MAX_GAME_LEVEL];


    /**
     * The width/height of each tile.
     */
    public static final int TILE_WIDTH = 12;

    /**
     * Bonus images.
     */
    public static final int BONUS = 0;

    /**
     * Explode images.
     */
    public static final int EXPLODE = 1;

    /**
     * Bullet images.
     */
    public static final int BULLET = 2;

    /**
     * Player tank images.
     */
    public static final int PLAYER = 3;

    /**
     * Enemy tank images.
     */
    public static final int ENEMY = 4;

    /**
     * Splash screen
     */
    public static final int SPLASH = 10;

    /**
     * Score screen
     */
    public static final int SCORE_SCREEN = 11;

    /**
     * First player tanks.
     */
    public static final int IP = 12;

    /**
     * Level
     */
    public static final int FLAG = 13;

    /**
     * enemy tanks icon in score bar.
     */
    public static final int ENEMY_ICON = 14;

    /**
     * white numbers.
     */
    public static final int NUMBER_WHITE = 15;

    /**
     * red numbers.
     */
    public static final int NUMBER_RED = 16;

    /**
     * Big game over image
     */
    public static final int GAME_OVER_BIG = 17;

    /**
     * small game over image
     */
    public static final int GAME_OVER_SMALL = 18;

    /**
     * small numbers for scores.
     */
    public static final int SCORE = 19;

    /**
     * game pause
     */
    public static final int PAUSE = 20;

    /**
     * numbers in black.
     */
    public static final int NUMBER_BLACK = 21;

    /**
     * Stage image
     */
    public static final int STAGE = 22;

    /**
     * turn on sound
     */
    public static final int TURN_SOUND = 23;

    /**
     * game help
     */
    public static final int GAME_HELP = 27;

    /**
     * Guidebee logo
     */
    public static final int GUIDEBEE_LOGO = 28;


    /**
     * Score board sound.
     */
    public static final int SCORE_SOUND = 1;

    /**
     * Open game sound
     */
    public static final int OPEN_SOUND = 2;

    /**
     * Game over sound
     */
    public static final int GAMEOVER_SOUND = 0;

    /**
     * Explode sound.
     */
    public static final int EXPLODE_SOUND = 4;

    /**
     * Shooting sound index.
     */
    public static final int SHOOT_SOUND = 3;


    /**
     * current game level.
     */
    public static int gameLevel = 1;

    /**
     * the highest score.
     */
    public static int highScore = 20000;

    /**
     * Singleton instance
     */
    private static ResourceManager instance = new ResourceManager();

    /**
     * image array.
     */
    private static TextureRegion[] imageResources = new TextureRegion[29];

    /**
     * tile image
     */
    private static TextureRegion tileImage = null;


    /**
     * load image and sound resources
     */
    public static void loadResources() {
        // load the assets

        GameEngine.assetManager.load("Back_08.png", Texture.class);
        GameEngine.assetManager.load("Joystick_08.png", Texture.class);

        GameEngine.assetManager.load("Button_08_Normal_Shoot.png", Texture.class);
        GameEngine.assetManager.load("Button_08_Pressed_Shoot.png", Texture.class);

        GameEngine.assetManager.load("Button_08_Normal_Virgin.png", Texture.class);
        GameEngine.assetManager.load("Button_08_Pressed_Virgin.png", Texture.class);
        GameEngine.assetManager.load("battlecity.atlas", TextureAtlas.class);
        GameEngine.assetManager.load("snd1.wav", Sound.class);
        GameEngine.assetManager.load("snd2.wav", Sound.class);
        GameEngine.assetManager.load("snd3.wav", Sound.class);
        GameEngine.assetManager.load("snd4.wav", Sound.class);
        GameEngine.assetManager.load("snd5.wav", Sound.class);
        GameEngine.assetManager.finishLoading();
        TextureAtlas textureAtlas = GameEngine.assetManager.get("battlecity.atlas", TextureAtlas.class);
        tileImage = textureAtlas.findRegion("tiles");
        //initialize the game index
        Random rnd = new Random();
        for (int i = 0; i < MAX_GAME_LEVEL; i++) {
            gameLevels[i] = Math.abs(rnd.nextInt()) % 6840;
        }
        //guidebee soft.


        imageResources[BONUS] = textureAtlas.findRegion("img11");
        imageResources[EXPLODE] = textureAtlas.findRegion("img12");
        imageResources[BULLET] = textureAtlas.findRegion("img13");
        imageResources[PLAYER] = textureAtlas.findRegion("img14");
        imageResources[ENEMY] = textureAtlas.findRegion("img15");
        imageResources[SPLASH] = textureAtlas.findRegion("img31");
        imageResources[SCORE_SCREEN] = textureAtlas.findRegion("img32");
        imageResources[IP] = textureAtlas.findRegion("img33");
        imageResources[FLAG] = textureAtlas.findRegion("img34");
        imageResources[ENEMY_ICON] = textureAtlas.findRegion("img35");
        imageResources[NUMBER_WHITE] = textureAtlas.findRegion("img41");
        imageResources[NUMBER_RED] = textureAtlas.findRegion("img42");
        imageResources[GAME_OVER_BIG] = textureAtlas.findRegion("img43");
        imageResources[GAME_OVER_SMALL] = textureAtlas.findRegion("img44");
        imageResources[SCORE] = textureAtlas.findRegion("img45");
        imageResources[PAUSE] = textureAtlas.findRegion("img46");
        imageResources[NUMBER_BLACK] = textureAtlas.findRegion("img47");
        imageResources[STAGE] = textureAtlas.findRegion("img48");

        gameLevels[1] = 3384;
        gameLevels[2] = 1810;
        gameLevels[3] = 821;
        gameLevels[4] = 2428;
        gameLevels[5] = 1317;
        Random random=new Random();
        for(int i=6;i<MAX_GAME_LEVEL;i++){
            gameLevels[i]=(1000+random.nextInt()) % 6000;
        }


    }


    /**
     * Get image resource.
     *
     * @param index the image resource index.
     * @return the image.
     */
    public TextureRegion getImage(int index) {
        return imageResources[index];
    }

    /**
     * Play sound.
     *
     * @param type the sound type.
     */
    public static void playSound(int type) {
        Sound sound = null;
        switch (type) {
            case SCORE_SOUND:
                sound = GameEngine.assetManager.get("snd1.wav", Sound.class);
                break;
            case OPEN_SOUND:
                sound = GameEngine.assetManager.get("snd2.wav", Sound.class);
                break;
            case GAMEOVER_SOUND:
                sound = GameEngine.assetManager.get("snd3.wav", Sound.class);
                break;
            case EXPLODE_SOUND:
                sound = GameEngine.assetManager.get("snd4.wav", Sound.class);
                break;
            case SHOOT_SOUND:
                sound = GameEngine.assetManager.get("snd5.wav", Sound.class);
                break;
        }
        if (sound != null) {
            sound.play();
        }
    }

    /**
     * Get the tile image.
     *
     * @return the battle field tile image.
     */
    public TextureRegion getTileImage() {
        return tileImage;
    }

    /**
     * Get an unique instance of resource manager.
     *
     * @return an instance of resource manager.
     */
    public static ResourceManager getInstance() {
        return instance;
    }

    /**
     * private constructor to avoid to be initiated directly.
     */
    private ResourceManager() {

    }

}
