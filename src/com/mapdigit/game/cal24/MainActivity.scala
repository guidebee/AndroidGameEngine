package com.mapdigit.game.cal24

import android.os.Bundle
import com.guidebee.game.Configuration
import com.guidebee.game.activity.GameActivity

/**
 * Created by james on 26/10/2014.
 */
class MainActivity extends GameActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    val config = new Configuration()
    config.useAccelerometer = false
    config.useCompass = false

    initialize(new Cal24GamePlay(), config)
  }
}