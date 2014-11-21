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

import com.guidebee.game.{Screen, GamePlay, GameEngine}
import com.guidebee.game.engine.scene.Actor
import com.guidebee.game.ui.ChangeListener.ChangeEvent
import com.guidebee.game.ui._
import com.guidebee.utils.collections.Array
import com.mapdigit.game.examples.camera.CameraScene
import com.mapdigit.game.examples.drawings.DrawingScene
import com.mapdigit.game.examples.input.InputScene

//[------------------------------ MAIN CLASS ----------------------------------]
/**
 * Main windows for all examples.
 */
class MainWindow(val gamePlay:ExampleGamePlay) extends Window(800, 480) {


  val skin = new Skin(GameEngine.files.internal("skin/default/uiskin.json"))
  val list = new List(skin)

  val drawingsScene=new DrawingScene(gamePlay)
  val cameraScene=new CameraScene(gamePlay)
  val inputScene=new InputScene(gamePlay)

  val topMenus = new Array[String]()
  topMenus.add("1. drawings")
  topMenus.add("2. camera")
  topMenus.add("3. entity")
  topMenus.add("4. graphics")
  topMenus.add("5. maps")
  topMenus.add("6. audio")
  topMenus.add("7. input")
  topMenus.add("8. scene")
  topMenus.add("9. physics")
  topMenus.add("10. microedition")
  topMenus.add("11. files")
  topMenus.add("12. utils")


  list.setItems(topMenus)
  list.getSelection().setMultiple(false)
  list.getSelection().setRequired(false)

  val scrollPane1 = new ScrollPane(list, skin)
  scrollPane1.setFillParent(true)

  this.addComponent(scrollPane1)

  list.addListener(new ChangeListener {
    override def changed(event: ChangeEvent, actor: Actor): Unit = {
      val list= event.getTarget().asInstanceOf[List[String]]
      if(list!=null){
        list.getSelectedIndex match{
          case 0 => gamePlay.setScreen(drawingsScene.asInstanceOf[Screen])
          case 1 => gamePlay.setScreen(cameraScene.asInstanceOf[Screen])
          case 6 => gamePlay.setScreen(inputScene.asInstanceOf[Screen])
          case _ =>
        }
      }


    }
  })


}
