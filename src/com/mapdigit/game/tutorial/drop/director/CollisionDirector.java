package com.mapdigit.game.tutorial.drop.director;

import com.guidebee.game.Collidable;
import com.guidebee.game.audio.Sound;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.scene.collision.Collision;
import com.guidebee.game.scene.collision.CollisionListener;
import com.mapdigit.game.tutorial.drop.actor.Mario;
import com.mapdigit.game.tutorial.drop.actor.RainDrop;

import static com.guidebee.game.GameEngine.assetManager;


public class CollisionDirector implements CollisionListener {

    private Sound dropSound=assetManager.get("drop.wav",Sound.class);

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
