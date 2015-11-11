package com.mapdigit.game.tutorial.drop.actor;

import com.guidebee.game.physics.BodyDef;
import com.guidebee.game.physics.Shape;
import com.guidebee.game.scene.Group;
import com.guidebee.math.MathUtils;
import com.guidebee.math.geometry.Rectangle;
import com.guidebee.utils.TimeUtils;

public class RainDropGroup extends Group {

    private long lastDropTime =0;



    private void spawnRainDrop(){
        RainDrop rainDrop=new RainDrop();
        //rainDrop.rotateBy((float)(Math.random()*360));
        //rainDrop.debug();

        rainDrop.setPosition(MathUtils.random(0,800-64),480);
        Rectangle dropRect=new Rectangle(0,0,32,32);
        rainDrop.initBody(BodyDef.BodyType.DynamicBody, Shape.Type.Circle, dropRect,1.0f,0,0.1f);
        addActor(rainDrop);
        lastDropTime= TimeUtils.nanoTime();
    }




    @Override
    public void act(float delta){
        if(TimeUtils.nanoTime()-lastDropTime>1000000000) {
            spawnRainDrop();
        }

        RainDrop [] rainDrops=getChildren().toArray(RainDrop.class);
        for(RainDrop rainDrop: rainDrops){
            float y = rainDrop.getY();

            if(y+64 <0){

                removeActor(rainDrop);
            }

        }
    }


}
