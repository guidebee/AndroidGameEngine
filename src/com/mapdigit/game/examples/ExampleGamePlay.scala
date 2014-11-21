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
package com.mapdigit.game.examples

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.GameEngine._
import com.guidebee.game.GamePlay
import com.guidebee.game.audio.{Music, Sound}
import com.guidebee.game.graphics.Texture

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Example game play.
 */
class ExampleGamePlay extends GamePlay {

  var mainScene:MainWindow=null

  override def create() {
    // load the assets
    assetManager.load("backbutton.png", classOf[Texture])
    assetManager.load("Toronto2048.jpg", classOf[Texture])
    assetManager.load("droplet.png", classOf[Texture])
    assetManager.load("jet.png", classOf[Texture])
    assetManager.load("bucket.png", classOf[Texture])

    assetManager.load("drop.wav", classOf[Sound])
    assetManager.load("rain.mp3", classOf[Music])

    assetManager.load("Back_08.png", classOf[Texture])
    assetManager.load("Joystick_08.png", classOf[Texture])

    assetManager.load("Button_08_Normal_Shoot.png", classOf[Texture])
    assetManager.load("Button_08_Pressed_Shoot.png", classOf[Texture])

    assetManager.load("Button_08_Normal_Virgin.png", classOf[Texture])
    assetManager.load("Button_08_Pressed_Virgin.png", classOf[Texture])
    assetManager.finishLoading()
    mainScene= new MainWindow(this)
    setScreen(mainScene)
  }

  override def dispose(): Unit = {
    assetManager.dispose()
  }

  def setMainScreen(): Unit ={
    if(mainScene!=null){
      setScreen(mainScene)
    }
  }
}
