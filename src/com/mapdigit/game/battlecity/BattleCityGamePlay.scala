package com.mapdigit.game.battlecity

import com.guidebee.game.GameEngine._
import com.guidebee.game.GamePlay

class BattleCityGamePlay extends GamePlay {

  override def create() {
    // load the assets

    ResourceManager.loadResources()

    val scene = new GameScene()

    setScreen(scene)
  }

  override def dispose(): Unit = {
    assetManager.dispose()
  }

}
