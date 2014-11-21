package com.mapdigit.game.drop.actor

import com.guidebee.game.GameEngine._
import com.guidebee.game.audio.Sound
import com.guidebee.game.graphics.Texture
import com.guidebee.game.scene.collision.{Collision, CollisionListener}
import com.guidebee.math.MathUtils
import com.guidebee.game.scene.{Actor, Group}
import com.guidebee.utils.TimeUtils


class RainDropGroup extends Group("RainDropGroup") with CollisionListener{
  private var lastDropTime: Long = 0
  private val dropSound = assetManager.get("drop.wav", classOf[Sound])
  spawnRaindrop

  private def spawnRaindrop {
    val raindrop = new RainDrop()
    raindrop.rotateBy((Math.random()*360).toFloat)
    raindrop.debug()

    raindrop.setPosition(MathUtils.random(0, 800 - 64),480)
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
      val y=raindrop.getY() -200 * graphics.getDeltaTime()
      raindrop.setY(y)
      if (y + 64 < 0) {
        removeActor(raindrop)
      }

    }
  }

  override def CollisionDetected(collision: Collision): Unit = {
    if(collision!=null) {
      val actor1 = collision.getObjectA().asInstanceOf[Actor]
      val actor2 = collision.getObjectB().asInstanceOf[Actor]

      if (actor1 != null && actor2 != null) {

        if(actor1.getName=="Bucket" && actor2.getName=="RainDrop"){
          actor2.getParent.removeActor(actor2)
          dropSound.play()
        }else if(actor2.getName=="Bucket" && actor1.getName=="RainDrop"){
          actor1.getParent.removeActor(actor1)
          dropSound.play()
        }

      }
    }
  }
}


