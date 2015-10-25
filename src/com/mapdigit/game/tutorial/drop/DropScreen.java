package com.mapdigit.game.tutorial.drop;

import com.guidebee.game.ScreenAdapter;
import com.guidebee.game.audio.Music;
import com.guidebee.game.camera.viewports.ScalingViewport;
import com.guidebee.game.scene.Stage;
import com.mapdigit.game.tutorial.drop.actor.Bucket;
import com.mapdigit.game.tutorial.drop.actor.RainDropGroup;

import static com.guidebee.game.GameEngine.*;
/**
 * Created by root on 10/25/15.
 */
public class DropScreen extends ScreenAdapter {


    private Music rainMusic = assetManager.get("rain.mp3",Music.class);

    private Bucket bucket =new Bucket();

    private RainDropGroup rainDropGroup=new RainDropGroup();

    private Stage stage=new Stage(new ScalingViewport(800,480));

    public DropScreen(){
        //bucket.debug();
        rainMusic.setLooping(true);
        stage.addActor(bucket);
        stage.addActor(rainDropGroup);
        stage.setCollisionListener(rainDropGroup);


    }


    @Override
    public void render(float delta){
        graphics.clearScreen(0,0,0.2f,1);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose(){
        stage.dispose();
        assetManager.dispose();
    }

    @Override
    public void pause(){
        rainMusic.stop();
    }

    @Override
    public void show(){
        rainMusic.play();
    }
}
