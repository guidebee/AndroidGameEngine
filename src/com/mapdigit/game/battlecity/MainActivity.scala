/** *****************************************************************************
  * Copyright 2011 See AUTHORS file.
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  * *****************************************************************************/
package com.mapdigit.game.battlecity

//--------------------------------- IMPORTS ------------------------------------
import android.os.Bundle
import com.guidebee.game.Configuration
import com.guidebee.game.activity.GameActivity
import com.mapdigit.game.drop.DropGamePlay

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Battle city main activity.
 */
class MainActivity extends GameActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    val config = new Configuration()
    config.useAccelerometer = false
    config.useCompass = false

    initialize(new BattleCityGamePlay(), config)
  }
}