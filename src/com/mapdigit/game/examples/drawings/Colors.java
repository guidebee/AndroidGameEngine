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
import com.guidebee.drawing.Pen;
import com.guidebee.drawing.SolidBrush;
import com.guidebee.drawing.geometry.AffineTransform;
import com.guidebee.game.graphics.Pixmap;
import com.guidebee.game.graphics.Texture;
import com.guidebee.utils.Disposable;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Colors demo.
 */
public class Colors implements Disposable {
    /* Colors */
    /**
     * The solid (full opaque) red color in the ARGB space
     */
    Color redColor = new Color(0xffff0000);
    /**
     * The semi-opaque green color in the ARGB space (alpha is 0x78)
     */
    Color greenColor = new Color(0x7800ff00, true);
    /**
     * The semi-opaque blue color in the ARGB space (alpha is 0x78)
     */
    Color blueColor = new Color(0x780000ff, true);
    /**
     * The semi-opaque yellow color in the ARGB space ( alpha is 0x78)
     */
    Color yellowColor = new Color(0x78ffff00, true);
    /**
     * The dash array
     */
    int dashArray[] = {20, 8};

    private Graphics2D graphics2D;

    private Texture texture;

    private int width = 200;

    private int height = 200;

    public Colors() {
        graphics2D = new Graphics2D(width, height);
        //Clear the canvas with black color.
        graphics2D.clear();

        AffineTransform matrix = new AffineTransform();
        graphics2D.setAffineTransform(matrix);

        SolidBrush brush = new SolidBrush(redColor);
        graphics2D.fillOval(brush, 30, 60, 80, 80);

        brush = new SolidBrush(greenColor);
        graphics2D.fillOval(brush, 60, 30, 80, 80);

        Pen pen = new Pen(yellowColor, 10, Pen.CAP_BUTT, Pen.JOIN_MITER, dashArray, 0);

        brush = new SolidBrush(blueColor);
        graphics2D.setPenAndBrush(pen, brush);

        graphics2D.fillOval(null, 90, 60, 80, 80);
        graphics2D.drawOval(null, 90, 60, 80, 80);

        Pixmap pixMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixMap.drawRGB(graphics2D.getRGB(), width, height);
        if (texture != null) texture.dispose();
        texture = new Texture(pixMap);
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
