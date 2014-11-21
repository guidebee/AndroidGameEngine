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
package com.mapdigit.game.examples

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.GameEngine._
import com.guidebee.game.camera.viewports.ScalingViewport
import com.guidebee.game.graphics.{Texture, TextureRegion}
import com.guidebee.game.scene.Stage
import com.guidebee.game.ui._
import com.guidebee.game.ui.drawable.TextureRegionDrawable
import com.guidebee.game.{GameEngine, InputProcessor, ScreenAdapter}

//[------------------------------ MAIN CLASS ----------------------------------]
class ExampleScene(val gamePlay:ExampleGamePlay) extends ScreenAdapter{

  protected val stage = new Stage(new ScalingViewport(800,480))

  private var savedInputProcessor:InputProcessor =null


  val textRegionDrawable
    =new TextureRegionDrawable(new TextureRegion(assetManager.get("backbutton.png",classOf[Texture])))

  val  button = new Button(textRegionDrawable)
  val table=stage.getRootHUDContainer()

  table.bottom().left()
  table.add(button)



  button.addListener(new ClickListener(){
    override def clicked( event:InputEvent,  x:Float, y:Float):Unit ={
      gamePlay.setMainScreen()
    }

  })

  override def show {
    savedInputProcessor = GameEngine.input.getInputProcessor
    GameEngine.input.setInputProcessor(stage)
  }

  override def hide {
    GameEngine.input.setInputProcessor(savedInputProcessor)
  }

  override def render(delta: Float): Unit = {
    // clear the screen with a dark blue color. The
    // arguments to glClearColor are the red, green
    // blue and alpha component in the range [0,1]
    // of the color to be used to clear the screen.
    graphics.clearScreen(0, 0, 0.2f, 1)
    stage.act()

    stage.draw()
  }

  override def resize(width:Int,height:Int): Unit ={
    stage.getViewport().update(width, height, true)
  }

  override def dispose(): Unit = {
    // dispose of all the native resources
    stage.dispose()
  }



}
