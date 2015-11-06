package com.mapdigit.game.tutorial.drop.actor;

import com.guidebee.game.GameEngine;
import com.guidebee.game.Input;
import com.guidebee.game.graphics.Animation;
import com.guidebee.game.graphics.Texture;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.math.Vector3;
import com.guidebee.utils.collections.Array;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.graphics;
import static com.guidebee.game.GameEngine.input;


public class Mario extends Actor {

    private final Animation forwardAnimation;
    private final Animation backwardAnimation;
    private final Animation leftAnimation;
    private final Animation rightAnimation;
    private final Texture marioTexture;

    private final int SPRITE_HEIGHT=64;
    private final int SPRITE_WIDTH=47;
    private final int SPRITE_FRAME_SIZE=6;
    private float elapsedTime = 0;

    public Mario() {
        super("Mario");
        marioTexture=assetManager.get("mario2.png",Texture.class);
        Array<TextureRegion> keyFramesForward=new Array<TextureRegion>();
        Array<TextureRegion> keyFramesRight=new Array<TextureRegion>();
        Array<TextureRegion> keyFramesBackward=new Array<TextureRegion>();
        Array<TextureRegion> keyFramesLeft=new Array<TextureRegion>();
        int i=0;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(marioTexture,
                    j*SPRITE_WIDTH,
                    i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesForward.add(textureRegion);

        }
        i++;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(marioTexture,
                    j*SPRITE_WIDTH,i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesRight.add(textureRegion);

        }
        i++;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(marioTexture,
                    j*SPRITE_WIDTH,i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesBackward.add(textureRegion);

        }
        i++;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(marioTexture
                    ,j*SPRITE_WIDTH,i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesLeft.add(textureRegion);

        }

        forwardAnimation=new Animation(0.1f,keyFramesForward);
        backwardAnimation=new Animation(0.1f,keyFramesBackward);
        rightAnimation=new Animation(0.1f,keyFramesRight);
        leftAnimation=new Animation(0.1f,keyFramesLeft);
        setTextureRegion(leftAnimation.getKeyFrame(0));
        setPosition(800/2-64/2,20);


    }


    @Override
    public void act(float delta){
        elapsedTime += GameEngine.graphics.getDeltaTime();
        setTextureRegion(forwardAnimation.getKeyFrame(elapsedTime,true));
        if(input.isTouched()){
            Vector3 touchPos=new Vector3();
            touchPos.set(input.getX(),input.getY(),0);
            getStage().getCamera().unproject(touchPos);
            setX(touchPos.x-64/2);
        }
        if(input.isKeyPressed(Input.Keys.LEFT)){

            setTextureRegion(leftAnimation.getKeyFrame(elapsedTime,true));
            setX(getX() - 200 * graphics.getDeltaTime());
        }
        if(input.isKeyPressed(Input.Keys.RIGHT)){

            setTextureRegion(rightAnimation.getKeyFrame(elapsedTime,true));
            setX(getX() + 200*graphics.getDeltaTime());
        }
        if(getX()<0)  setX(0);
        if(getX() > 800- 64) setX(800-64);
    }
}
