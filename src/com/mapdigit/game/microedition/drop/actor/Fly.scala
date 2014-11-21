package com.mapdigit.game.microedition.drop.actor

import com.guidebee.game.GameEngine
import GameEngine._
import com.guidebee.game.graphics.{Texture, Batch}
import com.guidebee.game.microedition.Sprite

/**
 * Created by james on 27/09/2014.
 */
class Fly extends Sprite(new Texture(files.internal("fly.png")),128,64){

  setName("Fly")
  setBounds(getX, getY, getWidth, getHeight)

  setPosition( 128 / 2, 200)

  setFrameSequence(Array(0,1,2,1,0,1,2,1,0,1,2,1,1,1,1,1,1))

  override def draw(batch: Batch, alpha: Float): Unit = {
    paint(batch)
  }

  override def act(delta: Float): Unit = {
    nextFrame()
  }
}
