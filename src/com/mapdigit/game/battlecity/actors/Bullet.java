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
import com.mapdigit.game.battlecity.actors.tank.EnemyTank;
import com.mapdigit.game.battlecity.actors.tank.PlayerTank;
import com.mapdigit.game.battlecity.actors.tank.Tank;


//[------------------------------ MAIN CLASS ----------------------------------]


/**
 * Bullet class. Bullet can be shoot by player and enemy tank.
 * @author James Shen.
 */
public final class Bullet extends Sprite {

    /**
     * bullet can break brick walls and enmeny tanks. ****
     */
    public static final int GRADE_DEFAULT = 1;
    /**
     * bullet can break concrete walls. ****
     */
    public static final int GRADE_BREAK_CONCRETE_WALL = 2;

    /**
     * bullet can break water and snow. *****
     */
    public static final int GRADE_BREAK_WATER = 3;


    /**
     * Tank should know about the battle field.
     */
    private static BattleField battleField;

    /**
     * Tank should know about the layer manager.
     */
    private static LayerManager layerManager;

    /**
     * bullet strength
     */
    private int strength = GRADE_DEFAULT;

    /**
     * shot by player or enmeny tanks
     */
    private boolean friendly = false;

    /**
     * maximun number of tanks in the battle field.
     */
    private static final int POOL_SIZE = 20;

    /**
     * This pool store all tanks include player and enemy tanks.
     */
    private static Bullet BULLET_POOL[];

    /**
     * bullet direction
     */
    private int direction = BattleField.NONE;

    /**
     * bullet direction
     */
    private int speed = ResourceManager.TILE_WIDTH / 2;

    /**
     * when move, the delta distance, dx,dy can not be nozero at the same time.
     */
    private int dx, dy;

    /**
     * initial the bullet pool ,the game will resume the bullet object.
     */
    static {
        BULLET_POOL = new Bullet[POOL_SIZE];
        for (int i = 0; i < POOL_SIZE; i++) {
            BULLET_POOL[i] = new Bullet(GRADE_DEFAULT, BattleField.NONE, 0);
            BULLET_POOL[i].setVisible(false);
        }
    }

    /**
     * Constructor
     *
     * @param strength  the bullet's hitting strength.
     * @param direction the bullet's moving direction.
     * @param speed     the bullet's moving speed.
     */
    private Bullet(int strength, int direction, int speed) {
        super(ResourceManager.getInstance().getImage(ResourceManager.BULLET),
                ResourceManager.TILE_WIDTH / 4, ResourceManager.TILE_WIDTH / 4);
        setOrigin(ResourceManager.TILE_WIDTH / 8,
                ResourceManager.TILE_WIDTH / 8);
        this.strength = strength;
        this.direction = direction;
        this.speed = speed;
    }


    /**
     * Set the bullet friendship. If true, shot by player
     *
     * @param friend true,shot by player,otherwize by enemy tank.
     */
    public void setFriendly(boolean friend) {
        this.friendly = friend;
    }


    /**
     * Set the bullet's speed.
     *
     * @param speed the bullet's moving speed.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }


    /**
     * Set the bullet's direction.
     *
     * @param direction the bullet's moving direction.
     */
    public void setDirection(int direction) {
        if (direction == BattleField.NONE) return;
        this.direction = direction;
        setFrame(direction);
        switch (direction) {
            case BattleField.NORTH:
                dx = 0;
                dy = speed;
                break;
            case BattleField.EAST:
                dx = speed;
                dy = 0;
                break;
            case BattleField.SOUTH:
                dx = 0;
                dy = -speed;
                break;
            case BattleField.WEST:
                dx = -speed;
                dy = 0;
                break;
        }
    }


    /**
     * Set the bullet's strength.
     *
     * @param strength the bullet's hitting strength.
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }


    /**
     * the bullet hit something and explodes
     */
    public void explode() {
        setVisible(false);
        int x = (int) (getX() + getOriginX());
        int y = (int) (getY() + getOriginY());
        Explosion.explode(x, y, Explosion.SMALL);
    }


    /**
     * Operation be done in each tick.
     */
    @Override
    public void act(float delta) {
        if (!isVisible() || direction == BattleField.NONE)
            return;
        // Move the bullet.
        move(dx, dy);
        int x = (int) (getX() + getOriginX());
        int y = (int) (getY() + getOriginY());
        PlayerTank playerTank = (PlayerTank) Tank.getTank(0);
        //outside the battle field, hitting the border
        if (x <= 0 || x >= battleField.getWidth() || y <= 0
                || y >= battleField.getHeight()) {

            //this is to avoid explosition outside the battlefield.
            if (x <= 0) x = 0;
            if (x >= battleField.getWidth()) x = (int) battleField.getWidth();
            if (y <= 0) y = 0;
            if (y >= battleField.getHeight()) y = (int) battleField.getHeight();
            setPosition(x, y);
            explode();
            return;
        }

        // See if it hit a tank.
        if (friendly) {
            // See if it hit an enemy tank.
            for (int i = 1; i < Tank.POOL_SIZE; i++) {
                EnemyTank enemy = (EnemyTank) Tank.getTank(i);
                if (enemy != null && enemy.isVisible() &&
                        collidesWith(enemy)) {
                    enemy.explode();
                    explode();
                    return;
                }
            }
        } else {
            // See if it hit player tank.

            if (collidesWith(playerTank)) {
                playerTank.explode();
                explode();
                return;
            }
        }

        //check to see if hit player's home
        if (Powerup.isHittingHome(this)) {
            //TODO: Game Over
            explode();
            return;
        }
        // See if it hit a wall.
        if (battleField.hitWall(x, y, strength)) {
            explode();
            return;
        }

        // See if it hit another bullet.
        for (int i = 0; i < POOL_SIZE; i++) {
            Bullet anotherBullet = BULLET_POOL[i];
            if (this != anotherBullet && anotherBullet.isVisible()) {
                if (collidesWith(anotherBullet)) {
                    explode();
                    BULLET_POOL[i].explode();
                    return;
                }
            }
        }
    }


    /**
     * Set the layerManager for tanks.
     */
    public static void setLayerManager(LayerManager manager) {
        layerManager = manager;
        if (layerManager != null) {
            for (int i = 0; i < POOL_SIZE; i++)
                layerManager.append(BULLET_POOL[i]);
        }
    }


    /**
     * Set the Battle field for tanks.
     */
    public static void setBattleField(BattleField field) {
        battleField = field;
    }



    /**
     * Get how many bullets the player shot.the player can shoot 3 bullets
     * at most.
     */
    public static int getPlayerBulletCount() {
        int count = 0;
        for (int i = 0; i < POOL_SIZE; i++) {
            if (BULLET_POOL[i].isVisible() && BULLET_POOL[i].friendly) {
                count++;
            }
        }
        return count;
    }


    /**
     * Get a free bullet from the pool.
     */
    public static Bullet getFreeBullet() {
        for (int i = 0; i < POOL_SIZE; i++) {
            if (!BULLET_POOL[i].isVisible()) {
                BULLET_POOL[i].setVisible(true);
                return BULLET_POOL[i];
            }
        }
        return null;
    }


    /**
     * Stop all bullets in the game scene.
     */
    public static void stopAllBullets() {
        for (int i = 0; i < POOL_SIZE; ++i) {
            if (!BULLET_POOL[i].friendly) {
                BULLET_POOL[i].setVisible(false);
            }
        }

    }

}
