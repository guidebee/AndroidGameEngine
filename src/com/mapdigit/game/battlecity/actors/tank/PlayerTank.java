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

import android.view.KeyEvent;
import com.mapdigit.game.battlecity.ResourceManager;
import com.mapdigit.game.battlecity.actors.BattleField;
import com.mapdigit.game.battlecity.actors.Bullet;
import com.mapdigit.game.battlecity.actors.Powerup;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Player's tank.
 *
 * @author James Shen
 */
public final class PlayerTank extends Tank {

    /**
     * Is key pressed?
     */
    private boolean keyPressed = false;

    /**
     * player's current grade,if equal 0,mean player dies.
     */
    private int grade = MIN_GRADE;

    /**
     * Player's minimum grade
     */
    private static final int MIN_GRADE = 1;

    /**
     * tank can shoot 2 bulltes.   **
     */
    private static final int GRADE_TWO_BULLETS = 2;

    /**
     * tank can shoot 3 bulltes.   ***
     */
    private static final int GRADE_THREE_BULLETS = 3;

    /**
     * tank can break concrete walls. ****
     */
    private static final int GRADE_BREAK_CONCRETE_WALL = 4;

    /**
     * tank can break water and snow. *****
     */
    private static final int GRADE_BREAK_WATER = 5;

    /**
     * tank add one layer of shell. ******
     */
    private static final int GRADE_SHELL_1 = 6;

    /**
     * tank move fast, go one star. *
     */
    private static final int GRADE_SPEED = 7;

    /**
     * tank add two layers of shell. *******
     */
    private static final int GRADE_SHELL_2 = 8;

    /**
     * Player's maximum grade
     */
    private static final int MAX_GRADE = 8;

    /**
     * the player's tank is just created.
     */
    private static final int NEW_BORN = 9;

    /**
     * the Invulnerable sheild for the player tank.
     */
    private Powerup sheild;

    /**
     * player tank is invulnerable at the start of the level,
     * and can become invulnerable if collects {@see Powerup.SHIELD}.
     */
    private int invulnerabilityTicks;

    /**
     * the time begin invulnerable
     */
    private long invulnerableTime;

    /**
     * invulnerable period. for start ,the time is 7.5 seconds.
     */
    private static final int invulnerablePeriod = 30000;

    /**
     * store current direction as shooting direction
     */
    private int currentDirection = BattleField.NONE;

    /**
     * how many bullets can player shoot at same time.
     */
    private int avaiableBullets = 1;

    /**
     * same direction,switch 2 image to make tank move animation.
     */
    private boolean switchImage = false;

    /**
     * how many lifes player has.
     */
    private int avaiableLife = 3;

    /**
     * the score the player gets.
     */
    private int score = 0;


    /**
     * Constructor
     */
    protected PlayerTank() {
        super(ResourceManager.getInstance().getImage(ResourceManager.PLAYER),
                ResourceManager.TILE_WIDTH, ResourceManager.TILE_WIDTH);
        sheild = Powerup.getInvulnerable();
        speed = DEFAULT_SPEED * 4;
    }

    /**
     * Add player's score with given value
     *
     * @param value the score value.
     */
    public void addScore(int value) {
        score += value;
    }


    /**
     * set player's score with given value
     *
     * @param value the score value.
     */
    public void setScore(int value) {
        score = value;
    }

    /**
     * return player's score with given value.
     *
     * @return player's score with given value.
     */
    public int getScore() {
        return score;
    }


    /**
     * Intialize the player tank after been destoryed or first start.
     */
    public void initTank() {
        if (battleField != null) {
            battleField.initPlayerTankPos(this);
            setVisible(true);
            direction = BattleField.NONE;
            grade = NEW_BORN;
            newBornTimer = 0;
            avaiableBullets = 1;
            speed = DEFAULT_SPEED;
            shoot = false;
        }
    }


    /**
     * Player tanks handle key press event.
     *
     * @param gameAction the game key code.
     */
    public void keyPressed(int gameAction) {
        keyPressed = true;
        if (gameAction == KeyEvent.KEYCODE_DPAD_UP) {
            direction = BattleField.NORTH;
        } else if (gameAction == KeyEvent.KEYCODE_DPAD_RIGHT) {
            direction = BattleField.EAST;
        } else if (gameAction == KeyEvent.KEYCODE_DPAD_LEFT) {
            direction = BattleField.WEST;
        } else if (gameAction == KeyEvent.KEYCODE_DPAD_DOWN) {
            direction = BattleField.SOUTH;
        } else if (gameAction == KeyEvent.KEYCODE_DPAD_CENTER) {
            shoot = true;
        }
        if (direction != BattleField.NONE) {
            currentDirection = direction;
        }
    }


    /**
     * Player tanks handle key release event.
     */
    public void keyReleased(int gameAction) {
        keyPressed = false;
        switch (gameAction) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (direction == BattleField.NORTH) {
                    //direction = BattleField.NONE;
                    break;
                }
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (direction == BattleField.SOUTH) {
                    //direction = BattleField.NONE;
                    break;
                }
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (direction == BattleField.WEST) {
                    //direction = BattleField.NONE;
                    break;
                }
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (direction == BattleField.EAST) {
                    //direction = BattleField.NONE;
                    break;
                }
            case KeyEvent.KEYCODE_DPAD_CENTER:
                //shoot = false;
        }
    }


    /**
     * Upgrade player's tank
     */
    public void upgrade(Powerup powerup) {
        switch (powerup.getType()) {
            case Powerup.CLOCK:
                EnemyTank.immobilizedStartTime = System.currentTimeMillis();
                break;
            case Powerup.STAR:
                grade++;
                if (grade > MAX_GRADE) grade = MAX_GRADE;
                switch (grade) {
                    case GRADE_SPEED: {
                        speed *= 2;
                        int x = (int) getX();
                        int y = (int) getY();
                        x = (x / speed) * speed;
                        y = (y / speed) * speed;
                        setPosition(x, y);
                    }
                    break;
                    case GRADE_TWO_BULLETS:
                        avaiableBullets = 2;
                        break;
                    case GRADE_THREE_BULLETS:
                        avaiableBullets = 3;
                        break;
                }

                break;
            case Powerup.SHIELD:
                invulnerableTime = System.currentTimeMillis();
                invulnerabilityTicks = invulnerablePeriod;
                sheild.setPosition(getX(), getY());
                sheild.setVisible(true);
                break;
            case Powerup.BOMB:
                EnemyTank.explodeAllEmenies();
                break;
            case Powerup.TANK:
                avaiableLife++;
                break;
            case Powerup.SHOVEL:
                battleField.makeHomeConcreteWall();
                break;

        }
    }


    /**
     * set player available lives.
     *
     * @param live new life count.
     */
    public void setAvaiableLives(int live) {
        avaiableLife = live;
    }


    /**
     * Get player available lives.
     */
    public int getAvaiableLives() {
        return avaiableLife;
    }


    /**
     * Tank thinks before move.
     */
    public void think() {
        if (grade == NEW_BORN) {
            newBornTimer++;
            if (newBornTimer > 4) {
                grade = MIN_GRADE;
                direction = BattleField.NONE;
                currentDirection = BattleField.NORTH;
                newBornTimer = 0;
                setFrame(0);
                invulnerableTime = System.currentTimeMillis();
                invulnerabilityTicks = invulnerablePeriod / 4;
                sheild.setPosition(getX(), getY());
                sheild.setVisible(true);

            } else {
                try {
                    setFrame(newBornTimer * 9 - 1);
                } catch (Exception e) {
                    //System.out.println("Playertank");
                }
            }

        } else {
            long tickTime = System.currentTimeMillis();
            if (tickTime - invulnerableTime > invulnerabilityTicks) {
                sheild.setVisible(false);
            } else {
                sheild.setPosition(getX(), getY());
                sheild.setVisible(true);
            }
            changeDirection(direction);
            if (currentDirection != BattleField.NONE) {
                switchImage = !switchImage;
                int offset = switchImage ? 0 : 1;
                try {
                    setFrame(currentDirection * 9 + ((int) (grade - 1) / 2) * 2 + offset);
                } catch (Exception e) {
                    //System.out.println("Playertank1");
                }

            }
        }

    }


    /**
     * Check if the player's tank is invulnerable
     *
     * @return true, the tank is invulnerable.
     */
    public boolean isInvulnerable() {
        return sheild.isVisible();
    }


    /**
     * Tank shoots.
     *
     * @return the bullet the tank shoots.
     */
    public Bullet shoot() {
        Bullet bullet = null;

        int bulletCount = Bullet.getPlayerBulletCount();
        if (shoot && bulletCount < avaiableBullets) {
            shoot = false;
            direction = BattleField.NONE;
            int step = ResourceManager.TILE_WIDTH;
            bullet = Bullet.getFreeBullet();
            if (bullet != null) {
                ResourceManager.playSound(ResourceManager.SHOOT_SOUND);
                int x = (int) (getX() + getOriginX());
                int y = (int) (getY() + getOriginY());

                switch (currentDirection) {
                    case BattleField.NORTH:
                        y += step / 2;
                        break;
                    case BattleField.EAST:
                        x += step / 2;
                        break;
                    case BattleField.SOUTH:
                        y -= step / 2;
                        break;
                    case BattleField.WEST:
                        x -= step / 2;
                        break;
                }
                bullet.setSpeed(ResourceManager.TILE_WIDTH / 2);
                bullet.setDirection(currentDirection);
                if (grade >= GRADE_BREAK_WATER) {
                    bullet.setStrength(Bullet.GRADE_BREAK_WATER);
                } else if (grade >= GRADE_BREAK_CONCRETE_WALL) {
                    bullet.setStrength(Bullet.GRADE_BREAK_CONCRETE_WALL);
                } else {
                    bullet.setStrength(Bullet.GRADE_DEFAULT);
                }

                bullet.setPosition(x - 1, y - 1);
                bullet.setFriendly(true);
                bullet.setVisible(true);
            }
        }
        return bullet;
    }


    /**
     * Stop the player tank
     */
    public void stop() {
        direction = BattleField.NONE;
        shoot = false;
    }

    /**
     * Explode a tank.
     */
    public void explode() {
        if (!isInvulnerable()) {
            if (grade >= GRADE_BREAK_WATER) {
                grade--;
            } else {
                avaiableLife--;
                super.explode();
            }
        }
    }
}
