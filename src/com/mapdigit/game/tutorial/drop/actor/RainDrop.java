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

    private static Texture rainDropTexture;

    static{
        if(rainDropTexture==null){
            SVGImage svgImage = GameEngine.assetManager.get("raindrop.svg", SVGImage.class);
            Pixmap pixmap=svgImage.getPixmap(0.25f);
            rainDropTexture=new Texture(pixmap);
            pixmap.dispose();
        }
    }

    public RainDrop(){
        super("RainDrop");
        if(rainDropTexture!=null) {
            setTexture(rainDropTexture);
        }

    }

    public static void reloadTexture(){
        if(rainDropTexture==null){
            SVGImage svgImage = GameEngine.assetManager.get("raindrop.svg", SVGImage.class);
            Pixmap pixmap=svgImage.getPixmap(0.5f);
            rainDropTexture=new Texture(pixmap);
            pixmap.dispose();
        }
    }

    public static void unloadTexture(){
        if(rainDropTexture!=null){
            rainDropTexture.dispose();
            rainDropTexture=null;
        }
    }


}
