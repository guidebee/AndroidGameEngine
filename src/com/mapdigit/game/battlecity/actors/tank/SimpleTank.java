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

import com.mapdigit.game.battlecity.actors.BattleField;
import com.mapdigit.game.battlecity.actors.Score;
//[------------------------------ MAIN CLASS ----------------------------------]


/**
 * Simple tank.
 * @author James Shen.
 */
public class SimpleTank extends EnemyTank {


    /**
     * Constructor.
     *
     * @param hasPrize if true, when player hit the tank, a new powerup is put
     *                 in the battle field.
     */
    protected SimpleTank(boolean hasPrize) {
        super(hasPrize);
        score = Score.SCORE_100;
    }


    /**
     * Tank thinks before move.
     */
    public void think() {
        //Move blindly.
        int changeDirection = Math.abs(rnd.nextInt()) % 100;
        if (changeDirection > 90) {
            direction = Math.abs(rnd.nextInt()) % 4;
        } else if (changeDirection > 80) {
            direction = BattleField.SOUTH;
        }
        int shooting = Math.abs(rnd.nextInt()) % 100;
        shoot = false;
        //shooting blindly.
        if (shooting >= 50) {
            shoot = true;
        }
        super.think();
    }

}
