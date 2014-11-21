package com.mapdigit.game.microedition.drop.actor;

import com.guidebee.drawing.*;
import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.Pixmap;
import com.guidebee.game.graphics.Texture;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by james on 11/10/2014.
 */
public class Texts {
    private Graphics2D graphics2D;

    private Texture texture;

    private int width=600;
    private int height=64;

    private Color color=Color.GREEN;
    VectorFont vectorFont;



    public Texts() {

        graphics2D=new Graphics2D(width,height);
        vectorFont=VectorFont.getSystemFont();


        graphics2D.clear();
        SolidBrush brush = new SolidBrush(color);
        Pen pen=new Pen(color);
        graphics2D.setDefaultPen(pen);
        graphics2D.setDefaultBrush(brush);



    }

    public void draw(){


        graphics2D.drawChars(vectorFont,height,new char[]{'h','e','l','l','o'},0,0);
        Pixmap pixMap=new Pixmap(width,height,Pixmap.Format.RGBA8888);
        pixMap.drawRGB(graphics2D.getRGB(),width,height);
        texture=new Texture(pixMap);

    }

    public Texture getTexture(){

        return texture;
    }
}
