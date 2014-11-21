package com.mapdigit.game.microedition.drop.actor;

import com.guidebee.drawing.Color;
import com.guidebee.drawing.Graphics2D;
import com.guidebee.drawing.SolidBrush;
import com.guidebee.drawing.geometry.Path;
import com.guidebee.game.graphics.Pixmap;
import com.guidebee.game.graphics.Texture;

/**
 * Created by james on 28/09/2014.
 */
public class Beziers {

    public Beziers() {
        graphics2D=new Graphics2D(100,100);
        reset(100, 100);
    }

    private Graphics2D graphics2D;

    private Texture texture=null;

    /**
     * The random number generator.
     */
    static java.util.Random random = new java.util.Random();
    /**
     * The animated path
     */
    Path path = new Path();
    /**
     * Red brush used to fill the path.
     */
    SolidBrush brush = new SolidBrush(Color.RED);
    private static final int NUMPTS = 6;
    private int animpts[] = new int[NUMPTS * 2];
    private int deltas[] = new int[NUMPTS * 2];
    long startt, endt;

    /**
     * Generates new points for the path.
     */
    private void animate(int[] pts, int[] deltas, int i, int limit) {
        int newpt = pts[i] + deltas[i];
        if (newpt <= 0) {
            newpt = -newpt;
            deltas[i] = (random.nextInt() & 0x00000003) + 2;
        } else if (newpt >= limit) {
            newpt = 2 * limit - newpt;
            deltas[i] = -((random.nextInt() & 0x00000003) + 2);
        }
        pts[i] = newpt;
    }

    /**
     * Resets the animation data.
     */
    private void reset(int w, int h) {
        for (int i = 0; i < animpts.length; i += 2) {
            animpts[i + 0] = (random.nextInt() & 0x00000003) * w / 2;
            animpts[i + 1] = (random.nextInt() & 0x00000003) * h / 2;
            deltas[i + 0] = (random.nextInt() & 0x00000003) * 6 + 4;
            deltas[i + 1] = (random.nextInt() & 0x00000003) * 6 + 4;
            if (animpts[i + 0] > w / 2) {
                deltas[i + 0] = -deltas[i + 0];
            }
            if (animpts[i + 1] > h / 2) {
                deltas[i + 1] = -deltas[i + 1];
            }
        }
    }

    /**
     * Sets the points of the path and draws and fills the path.
     */
    public void drawDemo(int w, int h) {
        for (int i = 0; i < animpts.length; i += 2) {
            animate(animpts, deltas, i + 0, w);
            animate(animpts, deltas, i + 1, h);
        }
        //Generates the new pata data.
        path.reset();
        int[] ctrlpts = animpts;
        int len = ctrlpts.length;
        int prevx = ctrlpts[len - 2];
        int prevy = ctrlpts[len - 1];
        int curx = ctrlpts[0];
        int cury = ctrlpts[1];
        int midx = (curx + prevx) / 2;
        int midy = (cury + prevy) / 2;
        path.moveTo(midx, midy);
        for (int i = 2; i <= ctrlpts.length; i += 2) {
            int x1 = (curx + midx) / 2;
            int y1 = (cury + midy) / 2;
            prevx = curx;
            prevy = cury;
            if (i < ctrlpts.length) {
                curx = ctrlpts[i + 0];
                cury = ctrlpts[i + 1];
            } else {
                curx = ctrlpts[0];
                cury = ctrlpts[1];
            }
            midx = (curx + prevx) / 2;
            midy = (cury + prevy) / 2;
            int x2 = (prevx + midx) / 2;
            int y2 = (prevy + midy) / 2;
            path.curveTo(x1, y1, x2, y2, midx, midy);
        }
        path.closePath();
        // clear the clipRect area before production

        System.gc();
        graphics2D.clear();
        graphics2D.fill(brush, path);

        Pixmap pixMap=new Pixmap(100,100,Pixmap.Format.RGBA8888);
        pixMap.drawRGB(graphics2D.getRGB(),100,100);
        if(texture!=null) texture.dispose();
        texture=new Texture(pixMap);



    }

    public Texture getTexture(){

        return texture;
    }

}
