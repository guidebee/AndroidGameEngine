package com.mapdigit.game.box2d.director

import com.guidebee.game.GameEngine._
import com.guidebee.game.audio.Sound
import com.guidebee.game.scene.Actor

import com.guidebee.game.scene.collision.{Collision, CollisionListener}


/**
 * Created by james on 7/10/2014.
 */
object CollisionDirector extends CollisionListener  {
  private val dropSound = assetManager.get("drop.wav", classOf[Sound])

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
