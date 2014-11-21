package com.mapdigit.game.drop.director

import com.guidebee.game.Collidable
import com.guidebee.game.scene.directors.IntervalDirector
import com.guidebee.game.scene.{Actor, Stage}
import com.mapdigit.game.drop.actor.{Bucket, RainDrop, RainDropGroup}

/**
 * Created by james on 5/10/2014.
 */
class CollisionDirector(val stage:Stage) extends IntervalDirector(1/60.0f){

  var collisionDetected = (actor:Actor)=>{}



  lazy val bucket=stage.findActor("Bucket").asInstanceOf[Bucket]

  override protected def updateInterval(): Unit = {
    val actors=stage.findActor("RainDropGroup").asInstanceOf[RainDropGroup]
      .getChildren.toArray[RainDrop](classOf[RainDrop])
    for(actor <-actors){
      if(actor.getName=="RainDrop"){

        if (actor.collidesWith(bucket,Collidable.BOUNDING_AREA)) {
          collisionDetected(actor)
        }

      }
    }
  }
}
