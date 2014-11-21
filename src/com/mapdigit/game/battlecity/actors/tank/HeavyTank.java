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
import com.mapdigit.game.battlecity.actors.Score;
//[------------------------------ MAIN CLASS ----------------------------------]


/**
 * Heavy tank.
 *
 * @author James Shen.
 */
public final class HeavyTank extends SimpleTank {


    /**
     * Constructor.
     *
     * @param hasPrize if true, when player hit the tank, a new powerup is put
     *                 in the battle field.
     */
    public HeavyTank(boolean hasPrize) {
        super(hasPrize);
        speed = ResourceManager.TILE_WIDTH / 6;
        type = TYPE_HEAVY;
        score = Score.SCORE_400;
    }

}
