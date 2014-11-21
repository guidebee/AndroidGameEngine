package com.mapdigit.game.cal24

import com.guidebee.game.GameEngine._
import com.guidebee.game.ScreenAdapter
import com.guidebee.game.camera.viewports.ScalingViewport
import com.guidebee.game.scene.Stage
import com.mapdigit.game.cal24.actor.PokerCard

/**
 * Created by james on 26/10/2014.
 */
class Cal24Scene extends ScreenAdapter{

  private val stage = new Stage(new ScalingViewport(800,480))

  private val pokerCard=new PokerCard()

  stage.addActor(pokerCard)

  override def render(delta: Float): Unit = {
    // clear the screen with a dark blue color. The
    // arguments to glClearColor are the red, green
    // blue and alpha component in the range [0,1]
    // of the color to be used to clear the screen.
    graphics.clearScreen(0, 0, 0.2f, 1)
    stage.act()

    stage.draw()
  }

}
