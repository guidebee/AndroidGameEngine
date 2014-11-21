/** *****************************************************************************
  * Copyright 2014 See AUTHORS file.
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
//--------------------------------- PACKAGE ------------------------------------
package com.mapdigit.game.cal24

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.GameEngine._
import com.guidebee.game.GamePlay
import com.guidebee.game.audio.{Music, Sound}
import com.guidebee.game.graphics.{SVGImage, Texture}
import com.mapdigit.game.drop.MainScene

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Example game play.
 */
class Cal24GamePlay extends GamePlay {


  override def create() {
    // load the assets
    assetManager.load("pokercard.svg",classOf[SVGImage])
    assetManager.finishLoading()
    val scene = new Cal24Scene()
    setScreen(scene)

  }

  override def dispose(): Unit = {
    assetManager.dispose()
  }


}
