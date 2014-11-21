package com.mapdigit.game.ui

import android.os.Bundle
import com.guidebee.game.Configuration
import com.guidebee.game.activity.GameActivity

/**
 * Created by james on 11/10/2014.
 */
class MainActivity extends GameActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    val config = new Configuration()
    config.useAccelerometer = false
    config.useCompass = false

    initialize(new UIGamePlay(), config)
  }
}
