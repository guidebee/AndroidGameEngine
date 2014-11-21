package com.mapdigit.game.box2d.actor

import com.guidebee.game.GameEngine._
import com.guidebee.game.graphics.Texture
import com.guidebee.game.scene.Actor


class RainDrop extends Actor("RainDrop"){
  setTexture(assetManager.get("droplet.png", classOf[Texture]))
  //setPosition(100,400)



}