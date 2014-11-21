package com.mapdigit.game.drop.actor

import com.guidebee.game.scene.{Actor, Group}
import com.guidebee.game.scene.actions.{TweenAction, ScaleToAction, RotateToAction, MoveToAction}
import com.guidebee.game.tween.Tween


class JetGroup extends Group("Jets"){

  private val jet1 = new Jet()
  private val jet2 = new Jet()

  jet1.setPosition(800 / 2 - 512 / 2, 240)
  jet2.setPosition(800 / 2 - 512 / 2, 140)
  addActor(jet1)
  addActor(jet2)


  val moveAction = new MoveToAction()
  val rotateAction = new RotateToAction()
  val scaleAction = new ScaleToAction()

  moveAction.setPosition(300f, 0f)
  moveAction.setDuration(5f)
  rotateAction.setRotation(90f)
  rotateAction.setDuration(5f)
  scaleAction.setScale(0.5f)
  scaleAction.setDuration(5f)

  //addAction(moveAction)
  //addAction(rotateAction)
  //addAction(scaleAction)

  //setPosition(800 / 2 - 512 / 2, 240)
  //val tween=Tween.to(this,TweenAction.POSITION_XY,5.0f ).target(300,480)
  //val tweenAction=new TweenAction(tween)

  //this.addAction(tweenAction)



}
