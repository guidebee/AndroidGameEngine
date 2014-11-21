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
package com.mapdigit.game.battlecity.actors;


//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.microedition.LayerManager;
import com.guidebee.game.microedition.Sprite;
import com.mapdigit.game.battlecity.ResourceManager;


//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * The classes displays score number when an enemy tank is destoryed or player
 * obtains an powerup.
 *
 * @author James Shen.
 */
public final class Score extends Sprite {

    /**
     * score 100
     */
    public final static int SCORE_100 = 0;

    /**
     * score 200
     */
    public final static int SCORE_200 = 1;

    /**
     * score 300
     */
    public final static int SCORE_300 = 2;

    /**
     * score 400
     */
    public final static int SCORE_400 = 3;

    /**
     * score 500
     */
    public final static int SCORE_500 = 4;

    /**
     * the score value.
     */
    private int scoreValue = -1;

    /**
     * maximun number of score in the battle field.
     */
    private static final int POOL_SIZE = 10;

    /**
     * This pool store all scores.
     */
    private static Score SCORE_POOL[];

    /**
     * the start time of the displaying the score.
     */
    private long startTime = 0;
    /**
     * the score live time, default 1 second
     */
    private static long livePeriod = 3000;

    /**
     * Tank should know about the layer manager.
     */
    private static LayerManager layerManager;

    /**
     * initial the score pool.
     */
    static {
        SCORE_POOL = new Score[POOL_SIZE];
        for (int i = 0; i < POOL_SIZE; i++) {
            SCORE_POOL[i] = new Score();
            SCORE_POOL[i].setVisible(false);
        }

    }

    /**
     * Set the value of the score.
     *
     * @param value new value.
     */
    public void setValue(int value) {
        this.scoreValue = value;
        setFrame(value);
    }


    /**
     * return the value for the score.
     *
     * @return the value of the score.
     */
    public int getValue() {
        return this.scoreValue;
    }

    /**
     * Constructor.
     */
    private Score() {
        super(ResourceManager.getInstance().getImage(ResourceManager.SCORE),
                ResourceManager.getInstance().
                        getImage(ResourceManager.SCORE).getRegionWidth() / 5,
                ResourceManager.getInstance().
                        getImage(ResourceManager.SCORE).getRegionHeight());
    }


    /**
     * Operation be done in each tick.
     */
    @Override
    public void act(float delta) {
        if (isVisible()) {
            long tickTime = System.currentTimeMillis();
            if (startTime > 0) {
                if (tickTime - startTime > livePeriod) {
                    setVisible(false);
                    startTime = 0;
                    return;
                }
            }
        }
    }

    /**
     * In give position, display score.
     *
     * @param x     the x coordinate.
     * @param y     the y coordinate.
     * @param value the score value.
     * @return the score object.
     */
    public static Score show(int x, int y, int value) {
        for (int i = 0; i < POOL_SIZE; ++i) {
            Score score = SCORE_POOL[i];
            if (!score.isVisible()) {
                score.startTime = System.currentTimeMillis();
                score.setPosition(x, y);
                score.setValue(value);
                score.setVisible(true);
                return score;
            }
        }
        return null;
    }


    /**
     * Set the layerManager for scores.
     */
    public static void setLayerManager(LayerManager manager) {
        layerManager = manager;
        if (layerManager != null) {

            for (int i = 0; i < POOL_SIZE; i++)
                layerManager.append(SCORE_POOL[i]);
        }
    }

    /**
     * Set the Battle field for scores.
     */
    public static void setBattleField(BattleField field) {
    }

}
