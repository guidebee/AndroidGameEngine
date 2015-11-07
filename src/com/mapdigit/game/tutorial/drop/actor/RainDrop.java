package com.mapdigit.game.tutorial.drop.actor;

import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.scene.Actor;

import static com.guidebee.game.GameEngine.assetManager;

public class RainDrop extends Actor {


    public RainDrop(){
        super("RainDrop");
        TextureAtlas textureAtlas=assetManager.get("raindrop.atlas",TextureAtlas.class);
        setTextureRegion(textureAtlas.findRegion("droplet"));

    }
}
