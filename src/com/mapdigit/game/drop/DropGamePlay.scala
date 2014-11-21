package com.mapdigit.game.drop

import com.guidebee.game.graphics.Texture
import com.guidebee.game.{GamePlay, GameEngine}
import GameEngine._
import com.guidebee.game.audio.{Music, Sound}

class DropGamePlay extends GamePlay {

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