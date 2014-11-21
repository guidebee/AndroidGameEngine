package com.mapdigit.game.box2d

import com.guidebee.game.GameEngine._
import com.guidebee.game.GamePlay
import com.guidebee.game.audio.{Music, Sound}
import com.guidebee.game.graphics.Texture


/**
 * Created by james on 5/10/2014.
 */
class Box2dGamePlay extends GamePlay {

  override def create() {
    // load the assets

    assetManager.load("droplet.png", classOf[Texture])
    assetManager.load("jet.png", classOf[Texture])
    assetManager.load("bucket.png", classOf[Texture])

    assetManager.load("drop.wav", classOf[Sound])
    assetManager.load("rain.mp3", classOf[Music])

    assetManager.finishLoading()

    val scene = new MainScene()
    setScreen(scene)
  }

  override def dispose(): Unit ={
    assetManager.dispose()
  }

}
