package com.mapdigit.game.drop.actor

import com.guidebee.game.GameEngine._
import com.guidebee.game.graphics.Texture
import com.guidebee.game.scene.Actor

/**
 * Created by james on 5/10/2014.
 */
class RainDrop extends Actor("RainDrop"){
  setTexture(assetManager.get("droplet.png", classOf[Texture]))
}