package com.mapdigit.game.drop.actor

import com.guidebee.game.GameEngine._
import com.guidebee.game.graphics.Texture
import com.guidebee.game.scene.Actor


class Jet extends Actor("Jet")  {

  private val jetImage: Texture = assetManager.get("jet.png", classOf[Texture])

  setTexture(jetImage)





}
