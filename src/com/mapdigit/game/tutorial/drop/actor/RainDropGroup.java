package com.mapdigit.game.tutorial.drop.actor;

import android.util.Log;
import com.guidebee.game.Collidable;
import com.guidebee.game.audio.Sound;
import com.guidebee.game.maps.objects.RectangleMapObject;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.scene.Group;
import com.guidebee.game.scene.collision.Collision;
import com.guidebee.game.scene.collision.CollisionListener;
import com.guidebee.game.ui.GameControllerListener;
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
            Collidable objectA=collision.getObjectA();
            Collidable objectB=collision.getObjectB();
            if(objectA instanceof Mario){
                 if(objectB instanceof RainDrop){
                    ((Actor)objectB).getParent().removeActor((Actor) objectB);
                    dropSound.play();
                }
            }else if(objectA instanceof RainDrop){
                if(objectB instanceof Mario){
                    ((Actor)objectA).getParent().removeActor((Actor) objectA);
                    dropSound.play();
                }
            }

        }


    }
}
