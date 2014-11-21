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
import com.guidebee.drawing.geometry.Point;
import com.guidebee.drawing.geometry.Polygon;
import com.guidebee.drawing.geometry.Polyline;
import com.guidebee.game.graphics.Pixmap;
import com.guidebee.game.graphics.Texture;
import com.guidebee.utils.Disposable;
//--------------------------------- PACKAGE ------------------------------------

/**
 * draw ploylines and polygons.
 */
public class Polys implements Disposable {

    private Graphics2D graphics2D;

    private Texture texture;

    private int width = 200;

    private int height = 200;

    AffineTransform mat1;


    /**
     * Colors
     */
    Color redColor = new Color(0x96ff0000, true);
    Color greenColor = new Color(0xff00ff00);
    Color blueColor = new Color(0x750000ff, true);

    Polyline polyline;
    Polygon polygon;
    Polygon polygon1;

    static String pointsdata1 = "59,45,95,63,108,105,82,139,39,140,11,107,19,65";

    public Polys() {
        graphics2D = new Graphics2D(width, height);
        //Clear the canvas with black color.
        graphics2D.clear();


        mat1 = new AffineTransform();
        mat1.translate(30, 40);
        mat1.rotate(-30 * Math.PI / 180.0);
        polyline = new Polyline();
        polygon = new Polygon();
        polygon1 = new Polygon();
        Point[] points = Point.fromString(pointsdata1);
        for (int i = 0; i < points.length; i++) {
            polyline.addPoint(points[i].x, points[i].y);
            polygon.addPoint(points[i].x, points[i].y);
            polygon1.addPoint(points[i].x, points[i].y);
        }


        graphics2D.setAffineTransform(new AffineTransform());
        SolidBrush brush = new SolidBrush(greenColor);
        graphics2D.fillPolygon(brush, polygon);
        graphics2D.setAffineTransform(mat1);

        brush = new SolidBrush(blueColor);
        Pen pen = new Pen(redColor, 5);
        graphics2D.setPenAndBrush(pen, brush);
        graphics2D.fillPolygon(null, polygon1);
        graphics2D.drawPolyline(null, polyline);

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
