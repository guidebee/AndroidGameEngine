package com.mapdigit.game.microedition.drop.actor

import com.guidebee.game.GameEngine._
import com.guidebee.game.audio.Sound
import com.guidebee.game.graphics.{Batch, Texture}
import com.guidebee.game.microedition.Sprite
import com.guidebee.math.MathUtils
import com.guidebee.math.geometry.Rectangle
import com.guidebee.utils.collections.Array
import com.guidebee.utils.{Pools, TimeUtils}

class RainDrop extends Sprite(new Texture(files.internal("droplet.png"))) {
  // create the raindrops array and spawn the first raindrop
  private val raindrops = new Array[Rectangle]()

  private var lastDropTime: Long = 0

  private val dropSound = assetManager.get("drop.wav", classOf[Sound])

  private val flySound = assetManager.get("fly.wav", classOf[Sound])

  private val bucketRect = new Rectangle(0, 0, 64, 64)

  private val flyRect = new Rectangle(0, 0, 128, 64)

  private val rectPool = Pools.get(classOf[Rectangle])
  spawnRaindrop

  override def draw(batch: Batch, alpha: Float): Unit = {
    val len = raindrops.size
    for (index <- 0 to len - 1) {
      setPosition(raindrops.get(index).x, raindrops.get(index).y)
      paint(batch)
    }
  }

  private def spawnRaindrop {
    val raindrop = rectPool.obtain()
    raindrop.x = MathUtils.random(0, 800 - 64)
    raindrop.y = 480
    raindrop.width = 64
    raindrop.height = 64
    raindrops.add(raindrop)
    lastDropTime = TimeUtils.nanoTime()
  }


  override def act(delta: Float): Unit = {
    // check if we need to create a new raindrop
    if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop
    val bucket = getStage.findActor("Bucket").asInstanceOf[Bucket]
    val fly = getStage.findActor("Fly").asInstanceOf[Fly]

    val iter = raindrops.iterator()
    while (iter.hasNext()) {
      val raindrop = iter.next()
      var removed = false
      raindrop.y -= 200 * graphics.getDeltaTime()
      if (raindrop.y + 64 < 0) iter.remove()
      if (bucket != null) {
        bucketRect.x = bucket.getX()
        bucketRect.y = bucket.getY()
        if (raindrop.overlaps(bucketRect)) {
          dropSound.play()
          iter.remove()
          rectPool.free(raindrop)
          removed = true
        }
      }
      if (!removed && fly != null) {
        flyRect.x = fly.getX()
        flyRect.y = fly.getY()
        if (raindrop.overlaps(flyRect)) {
          flySound.play()
          iter.remove()
          rectPool.free(raindrop)
        }
      }
    }

  }

}


