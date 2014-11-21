package com.mapdigit.game.box2d

import com.guidebee.game.GameEngine._
import com.guidebee.game.audio.Music
import com.guidebee.game.camera.viewports.ScalingViewport
import com.guidebee.math.Matrix4
import com.guidebee.game.physics.Box2DDebugRenderer
import com.guidebee.game.scene.Stage
import com.guidebee.game.{Collidable, GameEngine, ScreenAdapter}
import com.mapdigit.game.box2d.actor.{Bucket, RainDropGroup, Platform}
import com.mapdigit.game.box2d.director.CollisionDirector


class MainScene extends ScreenAdapter {


  private val rainMusic: Music = assetManager.get("rain.mp3", classOf[Music])

  private val stage = new Stage(new ScalingViewport(800, 480))


  // start the playback of the background music immediately
  rainMusic.setLooping(true)
  val debugRenderer = new Box2DDebugRenderer()

  stage.initWorld()
  private val rainDrop = new RainDropGroup()


  stage.addActor(rainDrop)



  private val bucket = new Bucket()

  stage.addActor(bucket)

  stage.setCollisionListener(CollisionDirector,Collidable.BOX2D_CONTACT);


  val debugMatrix = new Matrix4(stage.getCamera.combined)
  debugMatrix.scale(GameEngine.pixelToBox2DUnit, GameEngine.pixelToBox2DUnit, 0)

  val platform = new Platform()

  platform.initEdgeBody(10, 250f, 490f, 50f, 1.0f, 0f, 1f)
  stage.addActor(platform)


  override def render(delta: Float): Unit = {
    // clear the screen with a dark blue color. The
    // arguments to glClearColor are the red, green
    // blue and alpha component in the range [0,1]
    // of the color to be used to clear the screen.
    graphics.clearScreen(0, 0, 0.2f, 1)
    stage.act(delta)
    stage.draw()
    debugRenderer.render(world, debugMatrix)
    //GameEngine.app.log("World",GameEngine.world.getBodyCount().toString)
  }


  override def dispose(): Unit = {
    // dispose of all the native resources
    stage.dispose()
  }

  override def pause(): Unit = {

  }

  override def show() {

  }

}
