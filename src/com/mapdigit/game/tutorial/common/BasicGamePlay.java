package com.mapdigit.game.tutorial.common;

import com.guidebee.game.GamePlay;
import com.guidebee.game.Screen;

/**
 * Created by root on 10/17/15.
 */
public class BasicGamePlay extends GamePlay{

    @Override
    public void create() {
       Screen main=new MainScreen();
        setScreen(main);
    }

}

