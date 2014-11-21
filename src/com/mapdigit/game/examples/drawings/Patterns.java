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

import com.guidebee.drawing.Graphics2D;
import com.guidebee.drawing.TextureBrush;
import com.guidebee.drawing.geometry.AffineTransform;
import com.guidebee.drawing.geometry.Rectangle;
import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.Pixmap;
import com.guidebee.game.graphics.Texture;
import com.guidebee.utils.Disposable;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * draw with image pattens.
 */
public class Patterns implements Disposable {

    TextureBrush brush1;
    TextureBrush brush2;
    TextureBrush brush3;

    AffineTransform matrix1 = new AffineTransform();
    AffineTransform matrix2 = new AffineTransform();


    private Graphics2D graphics2D;

    private Texture texture;

    private int width = 200;

    private int height = 200;


    public Patterns() {
        graphics2D = new Graphics2D(width, height);
        //Clear the canvas with black color.
        graphics2D.clear();
        Pixmap pixmap = new Pixmap(GameEngine.files.internal("brick.png"));
        int[] rgbData = pixmap.getRGB();
        brush1 = new TextureBrush(rgbData, pixmap.getWidth(), pixmap.getHeight());
        pixmap = new Pixmap(GameEngine.files.internal("bird.png"));
        rgbData = pixmap.getRGB();
        brush2 = new TextureBrush(rgbData, pixmap.getWidth(), pixmap.getHeight());
        brush3 = new TextureBrush(rgbData, pixmap.getWidth(), pixmap.getHeight(), 127);

        matrix2.translate(50, 50);

        graphics2D.setAffineTransform(matrix1);
        graphics2D.fillRectangle(brush1, new Rectangle(20, 50, 100, 100));
        graphics2D.fillOval(brush2, 10, 10, 80, 80);
        graphics2D.setAffineTransform(matrix2);
        graphics2D.fillOval(brush3, 10, 10, 80, 80);

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
