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
package com.mapdigit.game.battlecity.actors.tank;

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.microedition.LayerManager;
import com.guidebee.game.microedition.Sprite;
import com.mapdigit.game.battlecity.actors.BattleField;
import com.mapdigit.game.battlecity.actors.Bullet;
import com.mapdigit.game.battlecity.actors.Explosion;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Base class for all tanks.
 *
 * @author James Shen.
 */
public abstract class Tank extends Sprite {

    /**
     * Tank should know about the battle field.
     */
    protected static BattleField battleField;

    /**
     * Tank should know about the layer manager.
     */
    protected static LayerManager layerManager;

    /**
     * the speed of the tank, default speed is 6 ,half of the
     * width of each tile.
     */
    protected int speed = DEFAULT_SPEED;

    /**
     * The direction in which player is driving.
     */
    protected int direction = BattleField.NONE;

    /**
     * default speed for tank.
     */
    protected static final int DEFAULT_SPEED = 3;

    /**
     * maximun number of tanks in the battle field.
     */
    public static final int POOL_SIZE = 21;

    /**
     * This pool store all tanks include player and enemy tanks.
     */
    protected static Tank TANK_POOL[];

    /**
     * time monitored to avoid the tank move to fast.
     */
    protected long driveStartTime = 0;

    /**
     * minimum time period between each move
     */
    private static final long minimumDrivePeriod = 40;

    /**
     * Should the tank shoot?
     */
    protected boolean shoot = false;

    /**
     * new boun timer
     */
    protected int newBornTimer = 0;

    /**
     * initial the tank pool ,the game will resume the tank object.
     * the first tank is the player's tank.
     */
    static {
        TANK_POOL = new Tank[POOL_SIZE];
        TANK_POOL[0] = new PlayerTank();
        //max 7 simple tanks
        for (int i = 1; i < 8; i++) {
            TANK_POOL[i] = new SimpleTank(false);
        }
        //max 4 fast tanks
        for (int i = 8; i < 12; i++) {
            TANK_POOL[i] = new FastTank(false);
        }
        //max 4 smart tanks
        for (int i = 12; i < 16; i++) {
            TANK_POOL[i] = new SmartTank(false);
        }
        //max 4 heavy tanks
        for (int i = 16; i < 21; i++) {
            TANK_POOL[i] = new HeavyTank(false);
        }

    }


    /**
     * Creates a new animated tank using frames contained in the provided Image.
     *
     * @param image       the Image to use for Sprite.
     * @param frameWidth  the width, in pixels, of the individual raw frames.
     * @param frameHeight the height, in pixels, of the individual raw frames.
     */
    protected Tank(TextureRegion image, int frameWidth, int frameHeight) {
        super(image, frameWidth, frameHeight);
        setOrigin(frameWidth / 2, frameHeight / 2);
    }


    /**
     * Tank moves.
     */
    public void drive() {
        long tickTime = System.currentTimeMillis();
        boolean canDrive = (tickTime - driveStartTime) > minimumDrivePeriod;
        boolean onSnow = battleField.isOnSnow((int) getX(), (int) getY());
        int extraPace = 0;
        if (onSnow) extraPace = speed;
        if (canDrive) {
            switch (direction) {
                case BattleField.NORTH:
                    if ((getY() < battleField.getHeight() - getHeight()) &&
                            !battleField.containsImpassableArea
                                    ((int) getX(), (int) getY() + (int) getHeight(), (int) getWidth(), speed)) {
                        tryMove(0, speed + extraPace);
                    }
                    break;
                case BattleField.EAST:
                    if ((getX() < battleField.getWidth() - getWidth()) &&
                            !battleField.containsImpassableArea
                                    ((int) getX() + (int) getWidth(), (int) getY(), speed, (int) getHeight())) {
                        tryMove(speed + extraPace, 0);
                    }
                    break;
                case BattleField.SOUTH:
                    if ((getY() > 0) &&
                            !battleField.containsImpassableArea
                                    ((int) getX(), (int) getY() - speed, (int) getWidth(), speed)) {
                        tryMove(0, -speed - extraPace);
                    }
                    break;
                case BattleField.WEST:
                    if ((getX() > 0) &&
                            !battleField.containsImpassableArea
                                    ((int) getX() - speed, (int) getY(), speed, (int) getHeight())) {
                        tryMove(-speed - extraPace, 0);
                    }
                    break;
            }
            driveStartTime = tickTime;
        }

    }

    /**
     * return player tank object.
     *
     * @return player tank object.
     */
    public static PlayerTank getPlayerTank() {
        return (PlayerTank) TANK_POOL[0];
    }


    /**
     * Set the layerManager for tanks.
     */
    public static void setLayerManager(LayerManager manager) {
        layerManager = manager;
        if (layerManager != null) {

            for (int i = 0; i < POOL_SIZE; i++)
                layerManager.append(TANK_POOL[i]);
        }
    }

    /**
     * Set the Battle field for tanks.
     */
    public static void setBattleField(BattleField field) {
        battleField = field;
    }

    /**
     * Operation be done in each tick.
     */
    @Override
    public void act(float delta) {
        if (isVisible()) {
            think();
            drive();
            shoot();
        }
    }

    /**
     * Tank thinks before move.
     */
    public abstract void think();


    /**
     * Tank shoots.
     *
     * @return the bullet the tank shoots.
     */
    public abstract Bullet shoot();


    /**
     * Initialize tank status.
     */
    public abstract void initTank();


    /**
     * Tank try to move (dx,dy)
     *
     * @param dx the delta x
     * @param dy the delta y
     */
    private void tryMove(int dx, int dy) {
        move(dx, dy);
        if (overlapsTank(this))
            move(-dx, -dy);
    }


    /**
     * Explode a tank.
     */
    protected void explode() {
        Explosion.explode((int) (getX() + getOriginX()), (int) (getY() + getOriginY()), Explosion.BIG);
        setVisible(false);
    }


    /**
     * Get an tank object.
     *
     * @param i the index of tank.
     */
    public static Tank getTank(int i) {
        if (i > POOL_SIZE - 1) {
            return null;
        }
        if (TANK_POOL[i] != null) {
            return TANK_POOL[i];
        } else {
            return null;
        }
    }


    /**
     * Check if there's overlap of tanks.
     *
     * @param sprite the tank need to be checked
     * @return true, overlap.
     */
    protected static boolean overlapsTank(Sprite sprite) {
        for (int i = 0; i < POOL_SIZE; i++) {
            if (sprite != TANK_POOL[i] &&
                    TANK_POOL[i].isVisible() &&
                    sprite.collidesWith(TANK_POOL[i]))
                return true;
        }
        return false;
    }

    /**
     * change the direction of current tank.
     *
     * @param direction new direction for the tank.
     */
    protected void changeDirection(int direction) {
        this.direction = direction;
    }


}