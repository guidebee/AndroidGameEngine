package com.mapdigit.game.tutorial.common;

import com.guidebee.game.ScreenAdapter;

import static com.guidebee.game.GameEngine.*;

/**
 * Created by root on 10/17/15.
 */
public class MainScreen extends ScreenAdapter{

    @Override
    public void render(float delta){
        graphics.clearScreen(0, 0, 0.2f, 1);
    }
}
