package com.mapdigit.game.cal24.actor

import com.guidebee.game.GameEngine
import com.guidebee.game.graphics.{Pixmap, SVGImage, Texture}
import com.guidebee.game.scene.Actor
import com.guidebee.game.scene.actions.TweenAction
import com.guidebee.game.tween.Tween

/**
 * Created by james on 26/10/2014.
 */
class PokerCard extends Actor("PokerCard"){



  val svgImage: SVGImage = GameEngine.assetManager.get("pokercard.svg", classOf[SVGImage])
  val pixmap: Pixmap = svgImage.getPixmap(0.5f)
  setTexture(new Texture(pixmap))
  pixmap.dispose
  setPosition(0,0)
  setOrigin(0,0)

  val tween1=Tween.to(this,TweenAction.SCALE_XY,5f ).target(0.1f,0.1f).repeatYoyo(5,0f)
  val tweenAction1=new TweenAction(tween1)

  this.addAction(tweenAction1)


}
