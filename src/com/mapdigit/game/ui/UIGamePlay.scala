package com.mapdigit.game.ui

import com.guidebee.game.GameEngine._
import com.guidebee.game.GamePlay

/**
 * Created by james on 11/10/2014.
 */
class UIGamePlay extends GamePlay {

  override def create() {
    // load the assets


    val scene = new MainWindow()
    setScreen(scene)
  }

  override def dispose(): Unit ={
    assetManager.dispose()
  }

}
