package com.mapdigit.game.tutorial.drop.actor;

import com.guidebee.game.Collidable;
import com.guidebee.game.GameEngine;
import com.guidebee.game.graphics.Animation;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.scene.Stage;
import com.guidebee.game.ui.GameControllerListener;
import com.guidebee.game.ui.Touchpad;
import com.guidebee.utils.collections.Array;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.graphics;


public class Mario extends Actor implements GameControllerListener {

    private final Animation forwardAnimation;
    private final Animation backwardAnimation;
    private final Animation leftAnimation;
    private final Animation rightAnimation;
    private final TextureRegion marioTextureRegion;

    private final int SPRITE_HEIGHT=64;
    private final int SPRITE_WIDTH=47;
    private final int SPRITE_FRAME_SIZE=6;
    private float elapsedTime = 0;
    private float tick=0.05f;
    private Direction currentDirection=Direction.NONE;

    private float oldX;
    private float oldY;

    private Collidable treeArea=null;

    public Mario() {
        super("Mario");
        TextureAtlas textureAtlas=assetManager.get("raindrop.atlas",TextureAtlas.class);
        marioTextureRegion=textureAtlas.findRegion("mario2");
        Array<TextureRegion> keyFramesForward=new Array<TextureRegion>();
        Array<TextureRegion> keyFramesRight=new Array<TextureRegion>();
        Array<TextureRegion> keyFramesBackward=new Array<TextureRegion>();
        Array<TextureRegion> keyFramesLeft=new Array<TextureRegion>();
        int i=0;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(marioTextureRegion,
                    j*SPRITE_WIDTH,
                    i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesForward.add(textureRegion);

        }
        i++;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(marioTextureRegion,
                    j*SPRITE_WIDTH,i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesRight.add(textureRegion);

        }
        i++;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(marioTextureRegion,
                    j*SPRITE_WIDTH,i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesBackward.add(textureRegion);

        }
        i++;
        for(int j=0;j<SPRITE_FRAME_SIZE;j++){
            TextureRegion textureRegion=new TextureRegion(marioTextureRegion
                    ,j*SPRITE_WIDTH,i*SPRITE_HEIGHT,
                    SPRITE_WIDTH,SPRITE_HEIGHT );
            keyFramesLeft.add(textureRegion);

        }

        forwardAnimation=new Animation(tick,keyFramesForward);
        backwardAnimation=new Animation(tick,keyFramesBackward);
        rightAnimation=new Animation(tick,keyFramesRight);
        leftAnimation=new Animation(tick,keyFramesLeft);
        setTextureRegion(forwardAnimation.getKeyFrame(0));
        setPosition(800/2-64/2,20);
        initBody();
        getBody().setGravityScale(0);
        setSelfControl(true);


    }


    public void setTreeArea(Collidable treeArea){
        this.treeArea=treeArea;
    }

    @Override
    public void KnobMoved(Touchpad touchpad, Direction direction) {

        currentDirection=direction;
        handleKeyPress();

    }

    @Override
    public void ButtonPressed(GameButton button) {

    }


    private boolean isCollideWithTree(){
        if(treeArea!=null){
            return Stage.collisionQuery(this,treeArea);
        }
        return false;
    }

    public void stopMoving(){
        currentDirection=Direction.NONE;
        setX(oldX);
        setY(oldY);
    }


    private void handleKeyPress(){

        if(!isCollideWithTree()) {
            oldX=getX();
            oldY=getY();
            switch (currentDirection) {
                case WEST:
                    setTextureRegion(leftAnimation.getKeyFrame(elapsedTime, true));
                    setX(getX() - 200 * graphics.getDeltaTime());
                    break;
                case EAST:

                    setTextureRegion(rightAnimation.getKeyFrame(elapsedTime, true));
                    setX(getX() + 200 * graphics.getDeltaTime());
                    break;
                case NORTH:

                    setTextureRegion(forwardAnimation.getKeyFrame(elapsedTime, true));
                    setY(getY() + 200 * graphics.getDeltaTime());
                    break;
                case SOUTH:
                    setTextureRegion(backwardAnimation.getKeyFrame(elapsedTime, true));
                    setY(getY() - 200 * graphics.getDeltaTime());
                    break;

            }

            if (getX() < 0) setX(0);
            if (getY() < 0) setY(0);
            if (getX() > 800 - 64) setX(800 - 64);
            if (getY() > 480 - 64) setY(480 - 64);
        }else{

            stopMoving();
        }
    }

    @Override
    public void act(float delta){
        elapsedTime += GameEngine.graphics.getDeltaTime();
        handleKeyPress();

    }
}
