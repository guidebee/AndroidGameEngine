package com.mapdigit.game.box2d.actor

import com.guidebee.game.GameEngine._
import com.guidebee.game.audio.Sound
import com.guidebee.game.physics.{Shape, BodyDef}
import com.guidebee.math.geometry.Rectangle
import com.guidebee.math.MathUtils
import com.guidebee.game.scene.{Actor, Group}
import com.guidebee.utils.TimeUtils


class RainDropGroup extends Group("RainDropGroup") {
  private var lastDropTime: Long = 0
  private val dropSound = assetManager.get("drop.wav", classOf[Sound])



  spawnRaindrop

  private def spawnRaindrop {
    val raindrop = new RainDrop()
    raindrop.setPosition(MathUtils.random(0, 800 - 64),480)
    val dropRect=new Rectangle(12,0,40,40)
    raindrop.initBody(BodyDef.BodyType.DynamicBody,Shape.Type.Circle, dropRect,1.0f,0,0f)
    addActor(raindrop)
    lastDropTime = TimeUtils.nanoTime()
  }


  def collisionDetected(actor:Actor): Unit ={
    dropSound.play()
    removeActor(actor)

  }

  override def act(delta: Float): Unit = {
    // check if we need to create a new raindrop
    if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop


    val rainDrops=getChildren().toArray[RainDrop](classOf[RainDrop])
    for(raindrop <- rainDrops){
     // val y=raindrop.getY() -200 * graphics.getDeltaTime()
     // raindrop.setY(y)
      val y=raindrop.getY()
      if (y + 64 < 0) {
        removeActor(raindrop)

      }

    }
  }

}