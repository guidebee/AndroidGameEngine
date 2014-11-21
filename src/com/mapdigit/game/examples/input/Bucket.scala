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
package com.mapdigit.game.examples.input

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.GameEngine._
import com.guidebee.game.graphics.Texture
import com.guidebee.game.scene.Actor
import com.guidebee.math.Vector3

//[------------------------------ MAIN CLASS ----------------------------------]
class Bucket extends Actor("Bucket") {

  setTexture(assetManager.get("bucket.png", classOf[Texture]))
  setPosition(800 / 2 - 64 / 2, 20)
  private var freeMove: Boolean = false

  def setFreeMove(freeMove: Boolean): Unit = {
    this.freeMove = freeMove
  }

  override def act(delta: Float): Unit = {



    // make sure the bucket stays within the screen bounds

    if (getX() < 0) setX(0)
    if (getX() > 2048 - 64) setX(2048 - 64)
    if (getY() < 0) setY(0)
    if (getY() > 1024 - 64) setY(1024 - 64)


    //if (freeMove)
    {
      val camera = getStage().getCamera()
      camera.position.set(getX(), getY(), 0)
      camera.update()
    }
  }
}
