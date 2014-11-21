/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
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

import com.guidebee.game.GameEngine;
import com.guidebee.game.microedition.TiledLayer;
import com.mapdigit.game.battlecity.ResourceManager;
import com.mapdigit.game.battlecity.actors.tank.EnemyTank;
import com.mapdigit.game.battlecity.actors.tank.PlayerTank;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

//[------------------------------ MAIN CLASS ----------------------------------]


/**
 * This class defines the battle field for the game.
 * <p/>
 *
 * @author James Shen
 */
public class BattleField extends TiledLayer {
    /**
     * No direction.
     */
    public static final int NONE = -1;

    /**
     * Heading north.
     */
    public static final int NORTH = 0;

    /**
     * Heading east.
     */
    public static final int EAST = 1;

    /**
     * Heading south.
     */
    public static final int SOUTH = 2;

    /**
     * Heading west.
     */
    public static final int WEST = 3;

    /**
     * Snow tile type.
     */
    private static final int SNOW = 1;

    /**
     * Brick wall tile type.
     */
    private static final int BRICK_WALL = 2;

    /**
     * Forest tile type.
     */
    private static final int FOREST = 3;

    /**
     * Concrete wall tile type.
     */
    private static final int CONCRETE_WALL = 6;

    /**
     * water animation.
     */
    private static final int WATER1 = 4;


    /**
     * water animation.
     */
    private static final int WATER2 = 5;

    private int waterFramesIndex = -1;

    /**
     * water animation frames.
     */
    private static int[][] waterFrames = {{4, 5}, {5, 4}};

    /**
     * default number of tiles in each direction.
     */
    private static final int NUMBER_IN_TILES = 26;
    /**
     * the number of horizontal tiles
     */
    private static int WIDTH_IN_TILES = 26;
    /**
     * the number of vertical tiles.
     */
    private static int HEIGHT_IN_TILES = 26;

    /**
     * Random used to create randome position pair for powerups.
     */
    private static Random rnd = new Random();

    /**
     * where enemy tanks appears ,left, middle, right
     */
    private static int[][] enemyPos = new int[3][2];

    /**
     * tick used to control the water animation speed.
     */
    private int tickCount = 0;

    /**
     * change enemy tank apprears position in sequence.
     */
    private static int nextEnemyPos = 0;

    /**
     * The player's home became concrete wall time.
     */
    private long concreteWallStartTime = 0;

    /**
     * how long player's home concrete wall can be
     */
    private static long concreteWallPeriod = 30000;


    /**
     * Constructor used to create a battle fields.
     *
     * @param xTiles the number of tiles in width.
     * @param yTiles the number of tiles in height.
     */
    public BattleField(int xTiles, int yTiles) {
        //When read from file, each number stand for 2X2 tiles
        super(xTiles * 2, yTiles * 2, ResourceManager.getInstance().getTileImage(),
                ResourceManager.TILE_WIDTH / 2, ResourceManager.TILE_WIDTH / 2);
        createAnimatedTile(waterFrames[0][0]); // tile -1
        createAnimatedTile(waterFrames[1][0]); // tile -2
        WIDTH_IN_TILES = xTiles * 2;
        HEIGHT_IN_TILES = yTiles * 2;
        if (xTiles * 2 < NUMBER_IN_TILES || xTiles * 2 < NUMBER_IN_TILES) {
            throw new IllegalArgumentException("Tiles shall be greater than 13");
        }
        //Initialized array which stores enemy appears start position.
        //Left
        enemyPos[0][0] = 0;
        enemyPos[0][1] = (yTiles - 1) * ResourceManager.TILE_WIDTH;
        //Middle
        enemyPos[1][0] = xTiles / 2 * ResourceManager.TILE_WIDTH;
        enemyPos[1][1] = (yTiles - 1) * ResourceManager.TILE_WIDTH;
        //Right
        enemyPos[2][0] = (xTiles - 1) * ResourceManager.TILE_WIDTH;
        enemyPos[2][1] = (yTiles - 1) * ResourceManager.TILE_WIDTH;

    }

    /**
     * Check if given rectangle contains impassable area.
     *
     * @param x      x coordinate.
     * @param y      y coordinate.
     * @param width  the width of given area.
     * @param height the height of given area.
     * @return true contains impassable area.
     */
    public boolean containsImpassableArea(int x, int y, int width, int height) {
        int TILE_WIDTH = ResourceManager.TILE_WIDTH / 2;
        int rowMin = y / TILE_WIDTH;
        int rowMax = (y + height - 1) / TILE_WIDTH;
        if (rowMax >= HEIGHT_IN_TILES) {
            rowMax = HEIGHT_IN_TILES - 1;
        }
        int columnMin = x / TILE_WIDTH;
        if (x < 0 || y < 0 || columnMin > WIDTH_IN_TILES - 1 ||
                rowMin > HEIGHT_IN_TILES - 1) {
            return true;
        }
        rowMin = Math.min(rowMin, getRows() - 1);
        columnMin = Math.min(columnMin, getColumns() - 1);
        int columnMax = (x + width - 1) / TILE_WIDTH;
        if (columnMax >= WIDTH_IN_TILES) {
            columnMax = WIDTH_IN_TILES - 1;
        }
        for (int row = rowMin; row <= rowMax; ++row) {
            for (int column = columnMin; column <= columnMax; ++column) {
                int cell = getCell(column, row);
                if ((cell < 0) || (cell == BRICK_WALL)
                        || (cell == CONCRETE_WALL)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * check if one snow filed, if on snow, the tank move a bit faster.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return true on the snow field.
     */
    public boolean isOnSnow(int x, int y) {
        int TILE_WIDTH = ResourceManager.TILE_WIDTH / 2;
        int row = y / TILE_WIDTH;
        int column = x / TILE_WIDTH;
        if (x < 0 || y < 0 || column > WIDTH_IN_TILES - 1 ||
                row > HEIGHT_IN_TILES - 1) {
            return false;
        }
        row = Math.min(row, getRows() - 1);
        column = Math.min(column, getColumns() - 1);
        int cell = getCell(column, row);
        return cell == SNOW;
    }

    /**
     * Check if given point hit wall in the battle field. If hits wall, the wall
     * will be destoryed if with enough strength.
     *
     * @param x        x coordinate.
     * @param y        y coordinate.
     * @param strength the strength of the the hitting object.
     * @return true hit the wall.
     */
    public boolean hitWall(int x, int y, int strength) {
        boolean bRet = false;
        int TILE_WIDTH = ResourceManager.TILE_WIDTH / 2;
        int[] col = new int[2];
        int[] row = new int[2];
        int maxRows = getRows() - 1;
        int maxCols = getColumns() - 1;
        col[0] = Math.min((x - TILE_WIDTH / 4) / TILE_WIDTH, maxCols);
        row[0] = Math.min((y - TILE_WIDTH / 4) / TILE_WIDTH, maxRows);

        col[1] = Math.min((x + TILE_WIDTH / 4) / TILE_WIDTH, maxCols);
        row[0] = Math.min((y - TILE_WIDTH / 4) / TILE_WIDTH, maxRows);

        col[0] = Math.min((x - TILE_WIDTH / 4) / TILE_WIDTH, maxCols);
        row[1] = Math.min((y + TILE_WIDTH / 4) / TILE_WIDTH, maxRows);

        col[1] = Math.min((x + TILE_WIDTH / 4) / TILE_WIDTH, maxCols);
        row[1] = Math.min((y + TILE_WIDTH / 4) / TILE_WIDTH, maxRows);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int cell = getCell(col[i], row[j]);
                if (cell == BRICK_WALL && strength > 0) {
                    setCell(col[i], row[j], 0);
                    bRet = true;
                } else if (cell == CONCRETE_WALL) {
                    if (strength > Bullet.GRADE_DEFAULT)
                        setCell(col[i], row[j], 0);
                    bRet = true;
                } else if (cell == FOREST || cell < 0 || cell == SNOW) {
                    //here a bullet can destory water, snow field and forest
                    //which is unrealistic:) just for fun.
                    if (strength > Bullet.GRADE_BREAK_CONCRETE_WALL) {
                        setCell(col[i], row[j], 0);
                        bRet = true;
                    }
                }
            }
        }
        return bRet;
    }

    /**
     * Initialize the Enemy's start position.
     *
     * @param tank the Enemy's tank.
     */
    public void initEnemyTankPos(EnemyTank tank) {
        nextEnemyPos %= 3;
        int x = enemyPos[nextEnemyPos][0];
        int y = enemyPos[nextEnemyPos][1];
        tank.setPosition(x, y);
        nextEnemyPos++;
    }

    /**
     * duplicate adjacent cell with given value. The reason for this ,the width
     * for each image tile is 6X6 ,when design the battle fields, for simplicity
     * we combine adjacent 2X2 cell to stand for a 12X12 area,i.e the 4 cells
     * store the same value.
     *
     * @param x     the x index of the cell
     * @param y     the y index of the cell
     * @param value the value for the cell
     */
    private void duplicateCell(int x, int y, int value) {
        int maxCols = getColumns() - 1;
        int maxRows = getRows() - 1;
        if (x < 0 || x > maxCols || y < 0 || y > maxRows)
            return;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                setCell(x + i, y + j, value);
            }
        }
    }


    /**
     * Read the battle from an input stream.
     *
     * @param is input stream stores the battle field information.
     */
    public synchronized void initBattlefield(java.io.InputStream is)
            throws IOException {
        //The actually canvas can be larger than 13X13 . initialiaze the battle
        //field with some random value.
        Random rnd = new Random();
        for (int i = 0; i < WIDTH_IN_TILES; i += 2) {
            for (int j = 0; j < HEIGHT_IN_TILES; j += 2) {
                int value = Math.abs(rnd.nextInt()) % 24;
                if (value > 17) {
                    if (value == 21 || value == 22) {
                        duplicateCell(i, j, -1 - ((i ^ j) & 1));
                    } else {
                        duplicateCell(i, j, value - 17);

                    }
                } else {
                    duplicateCell(i, j, 0);
                }
            }
        }
        try {
            if (is != null) {
                readBattlefield(is);
            }
        } catch (Exception e) {
        }
        makeHomeBrickWall();

    }

    /**
     * Make home wall a brick wall.
     */
    private void makeHomeBrickWall() {
        //Draw the player's home area.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                setCell(i + (WIDTH_IN_TILES / 2 - 3), j, BRICK_WALL);
            }
        }
        //this place will be placed with player's flag.
        duplicateCell((WIDTH_IN_TILES - 2) / 2, 0, 0);
        concreteWallStartTime = 0;
    }


    /**
     * Initialize the player's start position.
     *
     * @param tank the player's tank.
     */
    public void initPlayerTankPos(PlayerTank tank) {
        int x = (WIDTH_IN_TILES / 2 - 3) * ResourceManager.TILE_WIDTH / 2 - ResourceManager.TILE_WIDTH;
        int y = 0;
        //this place will be placed with player's tank.
        duplicateCell(x * 2 / ResourceManager.TILE_WIDTH,
                y * 2 / ResourceManager.TILE_WIDTH, 0);
        tank.setPosition(x, y);

    }


    /**
     * Initialize the powerup's random start position.
     *
     * @param powerup the powerup object.
     */
    public void initPowerupPos(Powerup powerup) {
        if (powerup.getType() == Powerup.HOME ||
                powerup.getType() == Powerup.HOME_DESTROYED) {
            int x = (WIDTH_IN_TILES / 4) * ResourceManager.TILE_WIDTH - ResourceManager.TILE_WIDTH / 2;
            int y = 0;
            powerup.setPosition(x, y);
        } else {
            int x0 = (WIDTH_IN_TILES / 4) * ResourceManager.TILE_WIDTH;
            int y0 = (HEIGHT_IN_TILES / 2 - 1) * ResourceManager.TILE_WIDTH;
            int x = (Math.abs(rnd.nextInt()) % (WIDTH_IN_TILES / 2))
                    * ResourceManager.TILE_WIDTH;
            int y = (Math.abs(rnd.nextInt()) % (WIDTH_IN_TILES / 2))
                    * ResourceManager.TILE_WIDTH;
            //aovid the home cell.
            while (x == x0 && y == y0) {
                x = (Math.abs(rnd.nextInt()) % (WIDTH_IN_TILES / 2))
                        * ResourceManager.TILE_WIDTH;
                y = (Math.abs(rnd.nextInt()) % (WIDTH_IN_TILES / 2))
                        * ResourceManager.TILE_WIDTH;
            }
            powerup.setPosition(x, y);
        }

    }


    /**
     * Make home wall a concrete wall.
     */
    public void makeHomeConcreteWall() {
        //Draw the player's home area.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                setCell(i + (WIDTH_IN_TILES / 2 - 3), j, CONCRETE_WALL);
            }
        }
        //this place will be placed with player's flag.
        duplicateCell((WIDTH_IN_TILES - 2) / 2, 0, 0);
        concreteWallStartTime = System.currentTimeMillis();
    }


    /**
     * read battle field from HZK.
     *
     * @param gameLevel the current game level.
     */
    public void readBattlefieldFromHZK(int gameLevel) {
        InputStream is = GameEngine.files.internal("hzk12").read();
        int[] buffer = new int[24];
        int[] hzData = new int[13 * 13 + 4];
        int index = 0;
        try {
            is.skip(gameLevel * 24);
            for (int i = 0; i < 24; i++) buffer[i] = is.read();
            int tempChar;
            int tempBit;
            ;
            index = 0;
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 2; j++) {
                    tempChar = buffer[i * 2 + j];
                    tempBit = 0x80;
                    for (int k = 0; k < 8; k++) {
                        if (j == 1 && k > 3) break;
                        if ((tempBit & tempChar) != 0) {
                            hzData[index++] = '2';
                        } else {
                            hzData[index++] = '0';
                        }
                        tempBit = tempBit >> 1;
                    }
                }
                hzData[index++] = '0';
                hzData[index++] = '\n';
            }
            byte[] byteArray = new byte[hzData.length];
            for (int i = 0; i < byteArray.length; i++) {
                byteArray[i] = (byte) hzData[i];
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);

            initBattlefield(bais);
        } catch (Exception e) {
            //inglore the exception.
        }
    }

    /**
     * Read the battle from an input stream.
     *
     * @param is input stream stores the battle field information.
     */
    private void readBattlefield(java.io.InputStream is) throws IOException {
        int c = -1;
        int x0 = (WIDTH_IN_TILES - NUMBER_IN_TILES) / 2;
        int y0 = (HEIGHT_IN_TILES - NUMBER_IN_TILES) / 2;
        int x = 0, y = 0;
        while ((c = is.read()) != -1 && y < NUMBER_IN_TILES) {
            switch (c) {
                case ' '://empty
                case '0':
                    duplicateCell(x + x0, HEIGHT_IN_TILES - (y + y0), 0);
                    x += 2;
                    break;
                case '1'://snow field
                    duplicateCell(x + x0, HEIGHT_IN_TILES - (y + y0), SNOW);
                    x += 2;
                    break;
                case '2'://brick wall
                    duplicateCell(x + x0, HEIGHT_IN_TILES - (y + y0), BRICK_WALL);
                    x += 2;
                    break;
                case '3'://forest
                    duplicateCell(x + x0, HEIGHT_IN_TILES - (y + y0), FOREST);
                    x += 2;
                    break;
                case '4':
                case '5'://water
                    duplicateCell(x + x0, HEIGHT_IN_TILES - (y + y0), -1 - ((x ^ y) & 1));
                    x += 2;
                    break;
                case '6': //Concrete wall
                    duplicateCell(x + x0, HEIGHT_IN_TILES - (y + y0), CONCRETE_WALL);
                    x += 2;
                    break;
                case '\n'://new line
                    y += 2;
                    x = 0;
                    break;
                default:
            }
        }
    }

    private float animatedTime = 0;

    @Override
    public void act(float alpha) {
        animatedTime += alpha;
        if (animatedTime > 0.5f) {
            animatedTime = 0;
            if (getAnimatedTile(waterFramesIndex) == WATER1) {
                setAnimatedTile(waterFramesIndex, WATER2);
            } else {
                setAnimatedTile(waterFramesIndex, WATER1);
            }
        }
        int tickState = (tickCount++ >> 3); // slow down x8
        int tile = tickState % 2;
        setAnimatedTile(-1 - tile, waterFrames[tile][(tickState % 4) / 2]);
        if(concreteWallStartTime>0){
            long tickTime=System.currentTimeMillis();
            if(tickTime-concreteWallStartTime>concreteWallPeriod){
                makeHomeBrickWall();
                concreteWallStartTime=0;
            }
        }
    }

}
