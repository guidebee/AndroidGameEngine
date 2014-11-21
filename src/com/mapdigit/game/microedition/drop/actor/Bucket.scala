package com.mapdigit.game.microedition.drop.actor

import com.guidebee.game.graphics.{Texture, Batch}
import com.guidebee.math.Vector3
import com.guidebee.game.{Input, GameEngine}
import GameEngine._
import Input.Keys
import com.guidebee.game.audio.Sound
import com.guidebee.game.microedition.Sprite

class Bucket extends Sprite(new Texture(files.internal("bucket.png"))) {

  setName("Bucket")
  setBounds(getX, getY, getWidth, getHeight)
  setPosition(800 / 2 - 64 / 2, 20)
  private val flySound = assetManager.get("fly.wav", classOf[Sound])

  override def draw(batch: Batch, alpha: Float): Unit = {
    paint(batch)
  }

  override def act(delta: Float): Unit = {
    // process user input
    if (input.isTouched()) {
      val touchPos = new Vector3()
      touchPos.set(input.getX(), input.getY(), 0)
      getStage().getCamera().unproject(touchPos)
      setX(touchPos.x - 64 / 2)
    }
    if (input.isKeyPressed(Keys.LEFT))
      setX(getX() - 200 * graphics.getDeltaTime())
    if (input.isKeyPressed(Keys.RIGHT))
      setX(getX() + 200 * graphics.getDeltaTime())

    // make sure the bucket stays within the screen bounds

    if (getX() < 0) setX(0)
    if (getX() > 800 - 64) setX(800 - 64)

    val fly = getStage.findActor("Fly").asInstanceOf[Fly]

    if(fly!=null){
      if(this.collidesWith(fly)){
        flySound.stop()
        flySound.play()
      }
    }
  }
}
