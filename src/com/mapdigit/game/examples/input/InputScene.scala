package com.mapdigit.game.examples.input

import com.guidebee.game.GameEngine
import com.guidebee.game.graphics.Texture
import com.guidebee.game.ui.GameControllerListener.{GameButton, Direction}
import com.guidebee.game.ui.drawable.TextureRegionDrawable
import com.guidebee.game.ui.{Touchpad, GameControllerListener, Skin, GameController}
import com.mapdigit.game.examples.camera.Toronto
import com.mapdigit.game.examples.{ExampleGamePlay, ExampleScene}

/**
 * Created by james on 20/10/2014.
 */
class InputScene (override val gamePlay: ExampleGamePlay) extends ExampleScene(gamePlay) with GameControllerListener {


  val touchpadSkin = new Skin()
  val backgroundTexture = GameEngine.assetManager.get("Back_08.png", classOf[Texture])
  touchpadSkin.add("touchBackground", backgroundTexture)
  touchpadSkin.add("touchKnob", GameEngine.assetManager.get("Joystick_08.png", classOf[Texture]))

  touchpadSkin.add("shoot", GameEngine.assetManager.get("Button_08_Normal_Shoot.png", classOf[Texture]))
  touchpadSkin.add("shoot_pressed", GameEngine.assetManager.get("Button_08_Pressed_Shoot.png", classOf[Texture]))
  touchpadSkin.add("virgin", GameEngine.assetManager.get("Button_08_Normal_Virgin.png", classOf[Texture]))
  touchpadSkin.add("virgin_pressed", GameEngine.assetManager.get("Button_08_Pressed_Virgin.png", classOf[Texture]))



  val gameController = new GameController(touchpadSkin.getDrawable("touchBackground").asInstanceOf[TextureRegionDrawable],
    touchpadSkin.getDrawable("touchKnob").asInstanceOf[TextureRegionDrawable],
    touchpadSkin.getDrawable("shoot").asInstanceOf[TextureRegionDrawable],
    touchpadSkin.getDrawable("shoot_pressed").asInstanceOf[TextureRegionDrawable],
    touchpadSkin.getDrawable("virgin").asInstanceOf[TextureRegionDrawable],
    touchpadSkin.getDrawable("virgin_pressed").asInstanceOf[TextureRegionDrawable]
    )

  gameController.addGameControllerListener(this)

  stage.setGameController(gameController)

  private val background = new Toronto()

  stage.addActor(background)

  private val bucket = new Bucket()
  stage.addActor(bucket)

  override def render(delta: Float): Unit = {
    super.render(delta)
    bucket.setX(bucket.getX()+ gameController.getKnobPercentX*10)
    bucket.setY(bucket.getY()+ gameController.getKnobPercentY*10)
  }

  override def KnobMoved(touchpad: Touchpad, direction: Direction): Unit ={
    GameEngine.app.log("KnobMoved",direction.toString)
  }

  override def ButtonPressed(button: GameButton): Unit = {
    GameEngine.app.log("buttonPressed",button.toString())
  }
}
