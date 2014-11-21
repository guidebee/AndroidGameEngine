package com.mapdigit.game.microedition.drop.actor;


import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.Pixmap;
import com.guidebee.game.graphics.SVGImage;
import com.guidebee.game.graphics.Texture;

/**
 * Created by james on 28/09/2014.
 */
public class Lion {


    private Texture texture;

    public Lion(String filename) {
        SVGImage svgImage = GameEngine.assetManager.get(filename, SVGImage.class);
        Pixmap pixmap=svgImage.getPixmap(0.5f);
        texture = new Texture(pixmap);
       pixmap.dispose();
    }

    public Texture getTexture() {
        return texture;
    }
}
