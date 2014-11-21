package com.mapdigit.game.box2d.actor

import com.guidebee.game.GameEngine._
import com.guidebee.game.Input.Keys
import com.guidebee.game.graphics.Texture
import com.guidebee.math.Vector3
import com.guidebee.game.physics.BodyDef.BodyType
import com.guidebee.game.scene.Actor

class Bucket extends Actor("Bucket") {

  setTexture(assetManager.get("bucket.png", classOf[Texture]))
  setPosition(800 / 2 - 64 / 2, 20)
  initBody()
  getBody.setGravityScale(0)
  setSelfControl(true)


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
  }
}
