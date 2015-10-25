package com.mapdigit.game.tutorial.drop;

import com.guidebee.game.GamePlay;
import com.guidebee.game.audio.Music;
import com.guidebee.game.audio.Sound;
import com.guidebee.game.graphics.Texture;

import static com.guidebee.game.GameEngine.*;
/**
 * Created by root on 10/25/15.
 */
public class DropGamePlay extends GamePlay{
    @Override
    public void create() {
        assetManager.load("droplet.png",Texture.class);
        assetManager.load("bucket.png",Texture.class);
        assetManager.load("drop.wav",Sound.class);
        assetManager.load("rain.mp3",Music.class);
        assetManager.finishLoading();

        DropScene screen=new DropScene();
        setScreen(screen);
    }

    @Override
    public void dispose(){
        assetManager.dispose();
    }
}
