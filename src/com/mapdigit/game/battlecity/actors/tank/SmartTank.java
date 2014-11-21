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

import com.mapdigit.game.battlecity.ResourceManager;
import com.mapdigit.game.battlecity.actors.BattleField;
import com.mapdigit.game.battlecity.actors.Score;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Smart tank.
 * @author James Shen.
 */
public final class SmartTank extends EnemyTank {

    /**
     * Constructor.
     *
     * @param hasPrize if true, when player hit the tank, a new powerup is put
     *                 in the battle field.
     */
    protected SmartTank(boolean hasPrize) {
        super(hasPrize);
        direction = BattleField.SOUTH;
        speed = ResourceManager.TILE_WIDTH / 2;
        score = Score.SCORE_300;
    }

    /**
     * Look for the player tank,if in the same row or col return true.
     *
     * @param dir current direction.
     */
    private boolean lookforPlayer(int dir) {
        PlayerTank playerTank = (PlayerTank) TANK_POOL[0];
        int playerX = (int) (playerTank.getX() + playerTank.getOriginX());
        int playerY = (int) (playerTank.getY() + playerTank.getOriginY());
        int dx = 0;
        int dy = 0;
        if (dir == BattleField.NORTH) {
            dy = speed;
        } else if (dir == BattleField.SOUTH) {
            dy = -speed;
        } else if (dir == BattleField.EAST) {
            dx = speed;
        } else if (dir == BattleField.WEST) {
            dx = -speed;
        } else {
            return false;
        }
        int myx = (int) (getX() + getOriginX());
        int myy = (int) (getY() + getOriginY());
        int width = (int) battleField.getWidth();
        int height = (int) battleField.getHeight();
        while (myx > 0 && myx < width && myy > 0 && myy < height) {
            //if(battleField.hitWall(myx,myy,0)){return false;}
            if (myx > playerX - speed && myx < playerX + speed && myy > playerY - speed && myy < playerY + speed) {
                return true;
            }
            myx += dx;
            myy += dy;
        }
        return false;
    }


    /**
     * Tank thinks before move.
     */
    public void think() {
        PlayerTank playerTank = (PlayerTank) TANK_POOL[0];
        int newdir = BattleField.SOUTH;
        int minDistance = Integer.MAX_VALUE;
        int distance = 0;

        boolean blockedeast = battleField.containsImpassableArea
                ((int) getX() + speed, (int) getY(), (int) getWidth(), (int) getHeight());
        boolean blockednorth = battleField.containsImpassableArea
                ((int) getX(), (int) getY() + speed, (int) getWidth(), (int) getHeight());
        boolean blockedwest = battleField.containsImpassableArea
                ((int) getX() - speed, (int) getY(), (int) getWidth(), (int) getHeight());
        boolean blockedsouth = battleField.containsImpassableArea
                ((int) getX(), (int) getY() - speed, (int) getWidth(), (int) getHeight());

        if (direction == BattleField.NORTH) {
            if (!blockedwest) {
                distance = ((int) getX() - speed - (int) playerTank.getX()) *
                        ((int) getX() - speed - (int) playerTank.getX()) +
                        ((int) getY() - (int) playerTank.getY()) *
                                ((int) getY() - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.WEST;
                    minDistance = distance;
                }
            }
            if (!blockedeast) {

                distance = ((int) getX() + speed - (int) playerTank.getX()) *
                        ((int) getX() + speed - (int) playerTank.getX()) +
                        ((int) getY() - (int) playerTank.getY()) *
                                ((int) getY() - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.EAST;
                    minDistance = distance;
                }
            }

            if (!blockednorth) {

                distance = ((int) getX() - (int) playerTank.getX()) *
                        ((int) getX() - (int) playerTank.getX()) +
                        ((int) getY() + speed - (int) playerTank.getY()) *
                                ((int) getY() + speed - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.NORTH;
                    minDistance = distance;
                }
            }
            distance = ((int) getX() - (int) playerTank.getX()) *
                    ((int) getX() - (int) playerTank.getX()) +
                    ((int) getY() - (int) playerTank.getY()) *
                            ((int) getY() - (int) playerTank.getY());
            if (distance < minDistance) {
                newdir = BattleField.NONE;
                minDistance = distance;
            }

        } else if (direction == BattleField.WEST) {
            if (!blockedwest) {

                distance = ((int) getX() - speed - (int) playerTank.getX()) *
                        ((int) getX() - speed - (int) playerTank.getX()) +
                        ((int) getY() - (int) playerTank.getY()) *
                                ((int) getY() - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.WEST;
                    minDistance = distance;
                }
            }
            if (!blockedsouth) {

                distance = ((int) getX() - (int) playerTank.getX()) *
                        ((int) getX() - (int) playerTank.getX()) +
                        ((int) getY() - speed - (int) playerTank.getY()) *
                                ((int) getY() - speed - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.SOUTH;
                    minDistance = distance;
                }
            }

            if (!blockednorth) {

                distance = ((int) getX() - (int) playerTank.getX()) *
                        ((int) getX() - (int) playerTank.getX()) +
                        ((int) getY() + speed - (int) playerTank.getY()) *
                                ((int) getY() + speed - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.NORTH;
                    minDistance = distance;
                }
            }
            distance = ((int) getX() - (int) playerTank.getX()) *
                    ((int) getX() - (int) playerTank.getX()) +
                    ((int) getY() - (int) playerTank.getY()) *
                            ((int) getY() - (int) playerTank.getY());
            if (distance < minDistance) {
                newdir = BattleField.NONE;
                minDistance = distance;
            }
        } else if (direction == BattleField.SOUTH) {
            if (!blockedwest) {

                distance = ((int) getX() - speed - (int) playerTank.getX()) *
                        ((int) getX() - speed - (int) playerTank.getX()) +
                        ((int) getY() - (int) playerTank.getY()) *
                                ((int) getY() - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.WEST;
                    minDistance = distance;
                }
            }
            if (!blockedsouth) {

                distance = ((int) getX() - (int) playerTank.getX()) *
                        ((int) getX() - (int) playerTank.getX()) +
                        ((int) getY() - speed - (int) playerTank.getY()) *
                                ((int) getY() - speed - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.SOUTH;
                    minDistance = distance;
                }
            }

            if (!blockedeast) {

                distance = ((int) getX() + speed - (int) playerTank.getX()) *
                        ((int) getX() + speed - (int) playerTank.getX()) +
                        ((int) getY() - (int) playerTank.getY()) *
                                ((int) getY() - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.EAST;
                    minDistance = distance;
                }
            }
            distance = ((int) getX() - (int) playerTank.getX()) *
                    ((int) getX() - (int) playerTank.getX()) +
                    ((int) getY() - (int) playerTank.getY()) *
                            ((int) getY() - (int) playerTank.getY());
            if (distance < minDistance) {
                newdir = BattleField.NONE;
                minDistance = distance;
            }
        } else if (direction == BattleField.EAST) {
            if (!blockednorth) {

                distance = ((int) getX() - (int) playerTank.getX()) *
                        ((int) getX() - (int) playerTank.getX()) +
                        ((int) getY() - speed - (int) playerTank.getY()) *
                                ((int) getY() - speed - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.NORTH;
                    minDistance = distance;
                }
            }
            if (!blockedsouth) {

                distance = ((int) getX() - (int) playerTank.getX()) *
                        ((int) getX() - (int) playerTank.getX()) +
                        ((int) getY() - speed - (int) playerTank.getY()) *
                                ((int) getY() - speed - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.SOUTH;
                    minDistance = distance;
                }
            }

            if (!blockedeast) {

                distance = ((int) getX() + speed - (int) playerTank.getX()) *
                        ((int) getX() + speed - (int) playerTank.getX()) +
                        ((int) getY() - (int) playerTank.getY()) *
                                ((int) getY() - (int) playerTank.getY());
                if (distance < minDistance) {
                    newdir = BattleField.EAST;
                    minDistance = distance;
                }
            }
            distance = ((int) getX() - (int) playerTank.getX()) *
                    ((int) getX() - (int) playerTank.getX()) +
                    ((int) getY() - (int) playerTank.getY()) *
                            ((int) getY() - (int) playerTank.getY());
            if (distance < minDistance) {
                newdir = BattleField.NONE;
                minDistance = distance;
            }
        } else {
            if ((Math.abs(rnd.nextInt()) % 10) > 5) {
                //every so often, go crazy
                newdir = Math.abs(rnd.nextInt()) % 4;
            }
        }
        if(newdir!=BattleField.NONE) {
            changeDirection(newdir);
        }

        shoot = false;
        int shooting = Math.abs(rnd.nextInt()) % 100;
        if (shooting >= 50 || lookforPlayer(direction)) {
            shoot = true;
        }

        super.think();
    }

}
