package com.mapdigit.game.microedition.drop


import android.os.Bundle
import com.guidebee.game.{GameEngine, Configuration}
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