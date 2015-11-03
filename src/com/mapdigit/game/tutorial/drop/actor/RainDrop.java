package com.mapdigit.game.tutorial.drop.actor;

import com.guidebee.game.scene.Actor;
import com.mapdigit.game.tutorial.drop.drawing.Pear;

public class RainDrop extends Actor {
    static Pear pear=new Pear();

    public RainDrop(){
        super("RainDrop");
        setTexture(pear.getTexture());
    }
}
