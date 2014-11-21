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

import com.mapdigit.game.examples.{ExampleGamePlay, ExampleScene}

//[------------------------------ MAIN CLASS ----------------------------------]
/**
 * scene to show drawing examples.
 */
class DrawingScene(override val gamePlay:ExampleGamePlay) extends ExampleScene(gamePlay){
  private val background = new Background()
  stage.addActor(background)

  override def dispose(): Unit ={
    super.dispose()
  }

}
