package com.mapdigit.game.microedition.drop.actor

import com.guidebee.game.GameEngine
import GameEngine._
import com.guidebee.game.graphics.{TextureRegion, Texture, Batch}
import com.guidebee.game.microedition.TiledLayer

/**
 * Created by james on 28/09/2014.
 */
class Background extends TiledLayer(12,4,new TextureRegion(new Texture(files.internal("tiles.png"))),32,32){

  private val pear=new Pear()
  pear.draw()

  //private val beziers=new Beziers()

  private val lion =new Lion("lion.svg")

  private val club2 =new Club2()

  //beziers.drawDemo(100,100)

  private val texts = new Texts()
  texts.draw()

  val cells= List(0,0,1,3,0,0,0,0,0,0,0,0,
                    0,1,4,4,3,0,0,0,0,1,2,2,
                    1,4,4,4,4,3,0,0,1,4,4,4)


  setPosition(32,0)
  for(index <- 0 to cells.length-1){
    val c = index % 12
    val r = 3-index / 12
    setCell(c,r,cells(index))

  }

  val animatedIndex=createAnimatedTile(5)
  for(i <-0 to 11){
    setCell(i,0,animatedIndex)
  }

  var animatedCount=0

  override def draw(batch: Batch, alpha: Float): Unit = {

    paint(batch)
    batch.draw(pear.getTexture,200,20)
    //batch.draw(beziers.getTexture,200,300)
    batch.draw(lion.getTexture,450,0)
    //batch.draw(club2.getTexture,500,0)
    batch.draw(texts.getTexture,0,300)

  }

  override def act(delta: Float): Unit = {
    animatedCount=animatedCount+1
    if(animatedCount>10) {
      if (getAnimatedTile(animatedIndex) == 5) {
        setAnimatedTile(animatedIndex, 7)
      } else {
        setAnimatedTile(animatedIndex, 5)
      }

      animatedCount=0
    }
    //beziers.drawDemo(100,100)
  }
}
