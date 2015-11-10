package com.mapdigit.game.tutorial.drop.actor;

import com.guidebee.game.audio.Sound;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.scene.Group;
import com.guidebee.game.scene.collision.Collision;
import com.guidebee.game.scene.collision.CollisionListener;
import com.guidebee.math.MathUtils;
import com.guidebee.utils.TimeUtils;


import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.graphics;

public class RainDropGroup extends Group implements CollisionListener{

    private long lastDropTime =0;
    private Sound dropSound=assetManager.get("drop.wav",Sound.class);


    private void spawnRainDrop(){
        RainDrop rainDrop=new RainDrop();
        //rainDrop.rotateBy((float)(Math.random()*360));
        //rainDrop.debug();

        rainDrop.setPosition(MathUtils.random(0,800-64),480);
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
            float y = rainDrop.getY() - 200 * graphics.getDeltaTime();
            rainDrop.setY(y);
            if(y+64 <0){

                removeActor(rainDrop);
            }

        }
    }

    @Override
    public void collisionDetected(Collision collision) {
        if(collision!=null){
            Actor actor1=(Actor)collision.getObjectA();
            Actor actor2=(Actor)collision.getObjectB();
            if(actor1!=null && actor2!=null){
                if(actor1.getName()=="Mario" && actor2.getName()=="RainDrop"){

                    actor2.getParent().removeActor(actor2);
                    dropSound.play();
                }else if(actor2.getName()=="Mario" && actor1.getName()=="RainDrop"){

                    actor1.getParent().removeActor(actor1);
                    dropSound.play();
                }
            }
        }


    }
}
