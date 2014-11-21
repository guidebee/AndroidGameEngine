package com.mapdigit.game.drop

import com.guidebee.game.GameEngine._
import com.guidebee.game.{Collidable, ScreenAdapter}
import com.guidebee.game.audio.Music
import com.guidebee.game.camera.viewports.ScalingViewport
import com.guidebee.game.scene.{Actor, Stage}
import com.guidebee.game.scene.collision.{Collision, CollisionListener}
import com.mapdigit.game.drop.actor.{Bucket, JetGroup, RainDropGroup}
import com.mapdigit.game.drop.director.CollisionDirector

class MainScene extends ScreenAdapter {

  private val rainMusic: Music = assetManager.get("rain.mp3", classOf[Music])
  private val bucket = new Bucket()
  private val rainDrop = new RainDropGroup()
  private val jetGroup = new JetGroup()
  private val stage = new Stage(new ScalingViewport(800,480))

  //private val collisionDirector =new CollisionDirector(stage)


  bucket.debug()
  // start the playback of the background music immediately
  rainMusic.setLooping(true)
  stage.addActor(bucket)
  stage.addActor(rainDrop)
  stage.addActor(jetGroup)
  stage.setCollisionListener(rainDrop)
  //stage.addDirector(collisionDirector)
  //collisionDirector.collisionDetected=rainDrop.collisionDetected

  override def render(delta: Float): Unit = {
    // clear the screen with a dark blue color. The
    // arguments to glClearColor are the red, green
    // blue and alpha component in the range [0,1]
    // of the color to be used to clear the screen.
    graphics.clearScreen(0, 0, 0.2f, 1)
    stage.act()

    stage.draw()
  }


  override def dispose(): Unit = {
    // dispose of all the native resources
    stage.dispose()
  }

  override def pause(): Unit = {
    rainMusic.stop()
  }

  override def show() {
    rainMusic.play()
  }


}
