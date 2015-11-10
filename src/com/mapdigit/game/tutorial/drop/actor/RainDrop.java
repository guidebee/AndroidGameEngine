package com.mapdigit.game.tutorial.drop.actor;

import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.Pixmap;
import com.guidebee.game.graphics.SVGImage;
import com.guidebee.game.graphics.Texture;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.scene.Actor;
import com.guidebee.utils.Disposable;

import static com.guidebee.game.GameEngine.assetManager;

public class RainDrop extends Actor  {



    public RainDrop(){
        super("RainDrop");
        setTexture(assetManager.get("coin.png",Texture.class));

    }



}
