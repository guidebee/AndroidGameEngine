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
package com.mapdigit.game.examples.camera

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.GameEngine
import com.guidebee.game.camera.viewports._
import com.guidebee.game.ui._
import com.guidebee.utils.Scaling
import com.mapdigit.game.examples.{ExampleGamePlay, ExampleScene}

//[------------------------------ MAIN CLASS ----------------------------------]
class CameraScene(override val gamePlay: ExampleGamePlay) extends ExampleScene(gamePlay) {
  private val background = new Toronto()

  val skin = new Skin(GameEngine.files.internal("skin/default/uiskin.json"))

  val viewButton = new TextButton("Change View", skin, "default")

  viewButton.setWidth(200f)
  viewButton.setHeight(60f)

  viewButton.setPosition(200, 0)

  val label = new Label("default", skin)

  label.setWidth(400f)
  label.setHeight(60f)

  label.setPosition(600, 0)

  var vIndex = 0

  viewButton.addListener(new ClickListener() {
    override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
      vIndex = (vIndex + 1) % names.size
      label.setText(names(vIndex))
      val viewPort = viewports(vIndex)
      stage.setViewport(viewPort)
      resize(GameEngine.graphics.getWidth, GameEngine.graphics.getHeight)
      GameEngine.app.log("Viewport", names(vIndex))
    }

  })


  private val names = Array("StretchViewport", "FitViewport",
    "ExtendViewport: no max", "ExtendViewport: max", "ScreenViewport: 1:1",
    "ScreenViewport: 0.75:1", "ScalingViewport: none", "Free move"
  )

  val minWorldWidth = 640
  val minWorldHeight = 480
  val maxWorldWidth = 800
  val maxWorldHeight = 480

  val camera = stage.getCamera()

  val screenViewport = new ScreenViewport(camera)
  screenViewport.setUnitsPerPixel(0.75f)
  private val viewports = Array(new StretchViewport(minWorldWidth, minWorldHeight, camera),
    new FitViewport(minWorldWidth, minWorldHeight, camera),
    new ExtendViewport(minWorldWidth, minWorldHeight, camera),
    new ExtendViewport(minWorldWidth, minWorldHeight, maxWorldWidth, maxWorldHeight, camera),
    new ScreenViewport(camera),
    screenViewport,
    new ScalingViewport(Scaling.none, minWorldWidth, minWorldHeight, camera),
    new ScalingViewport(Scaling.stretch, maxWorldWidth, maxWorldHeight, camera)
  )


  stage.addActor(background)

  private val bucket = new Bucket()
  stage.addActor(bucket)
  stage.addHUDComponent(viewButton)
  stage.addHUDComponent(label)

  override def dispose(): Unit = {
    super.dispose()
  }

}