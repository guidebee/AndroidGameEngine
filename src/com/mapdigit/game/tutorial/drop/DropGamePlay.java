package com.mapdigit.game.tutorial.drop;

import com.guidebee.game.GamePlay;
import com.guidebee.game.audio.Music;
import com.guidebee.game.audio.Sound;
import com.guidebee.game.graphics.Texture;

import static com.guidebee.game.GameEngine.assetManager;

public class DropGamePlay extends GamePlay{
    @Override
    public void create() {
        loadAssets();
        DropScene screen=new DropScene();
        setScreen(screen);
    }

    @Override
    public void dispose(){
        assetManager.dispose();
    }

    private void loadAssets(){
        assetManager.load("droplet.png",Texture.class);
        assetManager.load("mario2.png",Texture.class);
        assetManager.load("bucket.png",Texture.class);
        assetManager.load("drop.wav",Sound.class);
        assetManager.load("rain.mp3",Music.class);
        assetManager.finishLoading();
    }
}
