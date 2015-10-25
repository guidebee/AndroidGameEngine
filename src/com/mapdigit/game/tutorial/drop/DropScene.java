package com.mapdigit.game.tutorial.drop;

import com.guidebee.game.audio.Music;
import com.guidebee.game.camera.viewports.ScalingViewport;
import com.guidebee.game.scene.Scene;
import com.mapdigit.game.tutorial.drop.actor.Bucket;
import com.mapdigit.game.tutorial.drop.actor.RainDropGroup;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.graphics;

public class DropScene extends Scene {

    private Music rainMusic = assetManager.get("rain.mp3", Music.class);
    private Bucket bucket = new Bucket();
    private RainDropGroup rainDropGroup = new RainDropGroup();

    public DropScene() {
        super(new ScalingViewport(800, 480));
        rainMusic.setLooping(true);
        sceneStage.addActor(bucket);
        sceneStage.addActor(rainDropGroup);
        sceneStage.setCollisionListener(rainDropGroup);

    }


    @Override
    public void render(float delta) {
        graphics.clearScreen(0, 0, 0.2f, 1);
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
    }

    @Override
    public void pause() {
        rainMusic.stop();
    }

    @Override
    public void show() {
        rainMusic.play();
    }
}
