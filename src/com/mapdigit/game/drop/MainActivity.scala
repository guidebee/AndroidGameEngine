package com.mapdigit.game.drop


import android.os.Bundle
import com.guidebee.game.Configuration
import com.guidebee.game.activity.GameActivity

class MainActivity extends GameActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    val config = new Configuration()
    config.useAccelerometer = false
    config.useCompass = false

    initialize(new DropGamePlay(), config)
  }
}