package com.mapdigit.game.tutorial.drop.actor;

import com.guidebee.game.graphics.Texture;
import com.guidebee.game.scene.Actor;
import static com.guidebee.game.GameEngine.assetManager;
/**
 * Created by root on 10/25/15.
 */
public class RainDrop extends Actor {
    public RainDrop(){
        super("RainDrop");
        setTexture(assetManager.get("droplet.png",Texture.class));
    }
}
