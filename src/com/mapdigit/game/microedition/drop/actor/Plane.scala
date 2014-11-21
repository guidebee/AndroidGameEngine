package com.mapdigit.game.microedition.drop.actor

import com.guidebee.game.GameEngine
import GameEngine._
import com.guidebee.game.graphics.{Texture, Batch}
import com.guidebee.game.microedition.Sprite

/**
 * Created by james on 27/09/2014.
 */
class Plane extends Sprite(new Texture(files.internal("plane.png"))){

  setName("Plane")
  setBounds(getX, getY, getWidth, getHeight)

  setPosition( 800 / 2 - 256 / 2, 240)
  setOrigin(getWidth/2,getHeight/2)
  this.setTransform(Sprite.TRANS_MIRROR_ROT180)

  override def draw(batch: Batch, alpha: Float): Unit = {
    paint(batch)
  }


}