package com.mapdigit.game.ui



import com.guidebee.game.GameEngine
import com.guidebee.game.ui._

/**
 * Created by james on 11/10/2014.
 */
class MainWindow extends Window(800,480){

   val  skin=new Skin(GameEngine.files.internal("skin/default/uiskin.json"))


   val  button = new TextButton("Click me", skin, "default")

  button.setWidth(200f)
  button.setHeight(60f)
  button.setPosition(getWidth /2 - button.getWidth/2, getHeight/2 - button.getHeight/2)

  button.addListener(new ClickListener(){
     override def clicked( event:InputEvent,  x:Float, y:Float):Unit ={
      button.setText("You clicked the button")
    }

  })

  this.addComponent(button)


}
