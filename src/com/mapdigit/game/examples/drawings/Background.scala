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
package com.mapdigit.game.examples.drawings

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.graphics.Batch
import com.guidebee.game.scene.Actor
import com.guidebee.utils.Disposable

//[------------------------------ MAIN CLASS ----------------------------------]
/**
 * act as a drawing canvas.
 */
class Background extends Actor with Disposable {
  private val pear = new Pear()
  //private val beziers = new Beziers()
  //beziers.drawDemo(100, 100)
  private val colors = new Colors()
  private val patterns = new Patterns()
  private val polys = new Polys();


  override def draw(batch: Batch, alpha: Float): Unit = {
    batch.draw(pear.getTexture, 200, 20)
    //batch.draw(beziers.getTexture, 200, 300)
    batch.draw(polys.getTexture, 200, 300)
    batch.draw(colors.getTexture, 400, 0)
    batch.draw(patterns.getTexture, 600, 200)
  }

  override def act(delta: Float): Unit = {
    //beziers.drawDemo(100, 100)
  }

  override def dispose(): Unit = {
    pear.dispose()
    //beziers.dispose()
    polys.dispose()
    colors.dispose()
    patterns.dispose()
  }
}
