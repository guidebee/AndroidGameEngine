/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
//--------------------------------- PACKAGE ------------------------------------
package com.mapdigit.game.examples.drawings;

//--------------------------------- IMPORTS ------------------------------------
import com.guidebee.drawing.Color;
import com.guidebee.drawing.Graphics2D;
import com.guidebee.drawing.SolidBrush;
import com.guidebee.drawing.geometry.Area;
import com.guidebee.drawing.geometry.Ellipse;
import com.guidebee.game.graphics.Pixmap;
import com.guidebee.game.graphics.Texture;
import com.guidebee.utils.Disposable;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * draw a pear with path.
 */
public class Pear implements Disposable {

    private Ellipse circle, oval, leaf, stem;
    private Area circ, ov, leaf1, leaf2, st1, st2;

    private Graphics2D graphics2D;

    private Texture texture;

    public Pear(){
        draw();
    }

    public void init() {
        graphics2D=new Graphics2D(100,100);
        graphics2D.clear();
        circle = new Ellipse();
        oval = new Ellipse();
        leaf = new Ellipse();
        stem = new Ellipse();
        circ = new Area(circle);
        ov = new Area(oval);
        leaf1 = new Area(leaf);
        leaf2 = new Area(leaf);
        st1 = new Area(stem);
        st2 = new Area(stem);
    }

    private void draw(){
        init();
        int w = 100;
        int h = 100;
        int ew = w / 2;
        int eh = h / 2;
        SolidBrush brush = new SolidBrush(Color.GREEN);
        graphics2D.setDefaultBrush(brush);
        // Creates the first leaf by filling the intersection of two Area
        //objects created from an ellipse.
        leaf.setFrame(ew - 16, eh - 29, 15, 15);
        leaf1 = new Area(leaf);
        leaf.setFrame(ew - 14, eh - 47, 30, 30);
        leaf2 = new Area(leaf);
        leaf1.intersect(leaf2);
        graphics2D.fill(null, leaf1);

        // Creates the second leaf.
        leaf.setFrame(ew + 1, eh - 29, 15, 15);
        leaf1 = new Area(leaf);
        leaf2.intersect(leaf1);
        graphics2D.fill(null, leaf2);

        brush = new SolidBrush(Color.BLACK);
        graphics2D.setDefaultBrush(brush);

        // Creates the stem by filling the Area resulting from the
        //subtraction of two Area objects created from an ellipse.
        stem.setFrame(ew, eh - 42, 40, 40);
        st1 = new Area(stem);
        stem.setFrame(ew + 3, eh - 47, 50, 50);
        st2 = new Area(stem);
        st1.subtract(st2);
        graphics2D.fill(null, st1);

        brush = new SolidBrush(Color.YELLOW);
        graphics2D.setDefaultBrush(brush);

        // Creates the pear itself by filling the Area resulting from the
        //union of two Area objects created by two different ellipses.
        circle.setFrame(ew - 25, eh, 50, 50);
        oval.setFrame(ew - 19, eh - 20, 40, 70);
        circ = new Area(circle);
        ov = new Area(oval);
        circ.add(ov);
        graphics2D.fill(null, circ);
        Pixmap pixMap=new Pixmap(100,100,Pixmap.Format.RGBA8888);
        pixMap.drawRGB(graphics2D.getRGB(),100,100);
        if(texture!=null) texture.dispose();
        texture=new Texture(pixMap);

    }

    public Texture getTexture(){
        return texture;
    }

    @Override
    public void dispose(){
        texture.dispose();
    }


}