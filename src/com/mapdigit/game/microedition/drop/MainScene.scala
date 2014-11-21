package com.mapdigit.game.microedition.drop


import com.guidebee.game.{ScreenAdapter, GameEngine}
import GameEngine._
import com.guidebee.game.audio.Music
import com.guidebee.game.camera.viewports.ScalingViewport
import com.guidebee.game.microedition.LayerManager
import com.mapdigit.game.microedition.drop.actor._

class MainScene extends ScreenAdapter {

  private val rainMusic: Music = assetManager.get("rain.mp3", classOf[Music])
  private val bucket = new Bucket()
  private val rainDrop = new RainDrop()
  private val fly = new Fly()
  private val plane = new Plane()
  private val background = new Background()



  private val layerManager = new LayerManager(new ScalingViewport(800,480))




  // start the playback of the background music immediately
  rainMusic.setLooping(true)
  layerManager.append(background)
  layerManager.append(bucket)
  layerManager.append(rainDrop)
  //layerManager.append(fly)
  layerManager.append(plane)





  override def render(delta: Float): Unit = {
    // clear the screen with a dark blue color. The
    // arguments to glClearColor are the red, green
    // blue and alpha component in the range [0,1]
    // of the color to be used to clear the screen.
    graphics.clearScreen(0, 0, 0.2f, 1)
    layerManager.act()
    layerManager.draw()
  }


  override def dispose(): Unit = {
    // dispose of all the native resources
    layerManager.dispose()
  }

  override def pause(): Unit = {
    rainMusic.stop()
  }

  override def show() {
    rainMusic.play()
  }

}
