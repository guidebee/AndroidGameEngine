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
 * Explosion class. Display explosion image when bullet or tank explodes.
 * <p/>
 *
 * @author James Shen.
 */
public final class Explosion extends Sprite {

    /**
     * Explosion should know about the layer manager.
     */
    private static LayerManager layerManager;

    /**
     * Small explosion. when bullets hit wall.
     */
    public static final int SMALL = 0;

    /**
     * Big explosion, when tank explodes.
     */
    public static final int BIG = 1;

    /**
     * The width of the explosion image.
     */
    private static final int WIDTH = 24;

    /**
     * The height of the explosion image.
     */
    private static final int HEIGHT = 24;

    /**
     * Explosition sequence.
     */
    private static final int[][] FRAME_SEQ = new int[][]{
            {0, 1, 1, 2, 2},
            {0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5},
    };

    /**
     * The pool size of explosion.
     */
    private static final int POOL_SIZE = 10;

    /**
     * the explosion pool.
     */
    private static Explosion[] EXPLOSIONS_POOL;

    /**
     * Initialized the explostion pool.
     */
    static {
        EXPLOSIONS_POOL = new Explosion[POOL_SIZE];
        for (int i = 0; i < POOL_SIZE; ++i)
            EXPLOSIONS_POOL[i] = new Explosion(SMALL);
    }

    /**
     * private Constructor.
     *
     * @param strength SMALL or BIG explosion.
     */
    private Explosion(int strength) {
        super(ResourceManager.getInstance().getImage(ResourceManager.EXPLODE),
                WIDTH, HEIGHT);
        setOrigin(WIDTH / 2, HEIGHT / 2);
        setVisible(false);
        setStrength(strength);
    }


    /**
     * Set explosion strength
     *
     * @param strength SMALL or BIG explosion.
     */
    private void setStrength(int strength) {
        setFrameSequence(FRAME_SEQ[strength]);
    }

    /**
     * In give position, display explosion animation.
     *
     * @param x        the x coordinate.
     * @param y        the y coordinate.
     * @param strength SMALL or BIG explosion.
     */
    public static Explosion explode(int x, int y, int strength) {
        for (int i = 0; i < POOL_SIZE; ++i) {
            Explosion explosion = EXPLOSIONS_POOL[i];
            if (!explosion.isVisible()) {
                explosion.setCenterPosition(x, y);
                explosion.setFrame(0);
                explosion.setStrength(strength);
                explosion.setVisible(true);
                ResourceManager.playSound(ResourceManager.EXPLODE_SOUND);
                return explosion;
            }
        }
        return null;
    }


    /**
     * Operation be done in each tick.
     */
    @Override
    public void act(float delta) {
        if (!isVisible())
            return;
        nextFrame();
        if (getFrame() == 0) {
            setVisible(false);
        }
    }


    /**
     * Stop all explosion.
     */
    public static void stopAllExplosions() {
        for (int i = 0; i < POOL_SIZE; i++)
            EXPLOSIONS_POOL[i].setVisible(false);
    }


    /**
     * Set the layerManager for tanks.
     */
    public static void setLayerManager(LayerManager manager) {
        layerManager = manager;
        if (layerManager != null) {
            for (int i = 0; i < POOL_SIZE; i++)
                layerManager.append(EXPLOSIONS_POOL[i]);
        }
    }

}
