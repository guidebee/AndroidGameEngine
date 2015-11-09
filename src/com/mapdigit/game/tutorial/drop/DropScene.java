package com.mapdigit.game.tutorial.drop;

import com.guidebee.game.GameEngine;
import com.guidebee.game.InputProcessor;
import com.guidebee.game.audio.Music;
import com.guidebee.game.camera.viewports.*;
import com.guidebee.game.graphics.Texture;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.maps.tiled.TiledMap;
import com.guidebee.game.scene.Scene;
import com.guidebee.game.scene.Scenery;
import com.guidebee.game.ui.GameController;
import com.guidebee.game.ui.GameControllerListener;
import com.guidebee.game.ui.Skin;
import com.guidebee.game.ui.Touchpad;
import com.guidebee.game.ui.drawable.TextureRegionDrawable;
import com.mapdigit.game.tutorial.drop.actor.Bucket;
import com.mapdigit.game.tutorial.drop.actor.Mario;
import com.mapdigit.game.tutorial.drop.actor.RainDropGroup;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.graphics;

public class DropScene extends Scene  {

    private Music rainMusic = assetManager.get("rain.mp3", Music.class);
    private Bucket bucket = new Bucket();
    private Mario mario = new Mario();
    private RainDropGroup rainDropGroup = new RainDropGroup();
    private InputProcessor savedInputProcessor;
    private TiledMap background= assetManager.get("tiledmap/forest.tmx", TiledMap.class);

    private Scenery scenery=new Scenery(background);


    public DropScene() {
        super(new ScalingViewport(800,480));
        TextureAtlas textureAtlas=assetManager.get("raindrop.atlas",TextureAtlas.class);



        GameController gameController
                = new GameController(new TextureRegionDrawable(textureAtlas.findRegion("Back")),
                new TextureRegionDrawable(textureAtlas.findRegion("Joystick")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Normal_Shoot")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Pressed_Shoot")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Normal_Virgin")),
                new TextureRegionDrawable(textureAtlas.findRegion("Button_08_Pressed_Virgin"))
        );
        gameController.addGameControllerListener(mario);



        rainMusic.setLooping(true);
        scenery.setBackGroundLayers(new int[]{0,1,2});
        scenery.setForeGroundLayers(new int[]{3,4});

        sceneStage.addActor(mario);
        sceneStage.addActor(rainDropGroup);
        sceneStage.setScenery(scenery);
        sceneStage.setCollisionListener(rainDropGroup);
        sceneStage.setGameController(gameController);


    }



    @Override
    public void hide() {
        GameEngine.input.setInputProcessor(savedInputProcessor);
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
        savedInputProcessor = GameEngine.input.getInputProcessor();
        GameEngine.input.setInputProcessor(sceneStage);
        rainMusic.play();
    }


}
