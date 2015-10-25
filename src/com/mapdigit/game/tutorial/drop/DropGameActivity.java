package com.mapdigit.game.tutorial.drop;

import android.os.Bundle;
import com.guidebee.game.Configuration;
import com.guidebee.game.activity.GameActivity;


/**
 * Created by root on 10/25/15.
 */
public class DropGameActivity extends GameActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Configuration config = new Configuration();

        config.useAccelerometer = false;
        config.useCompass = false;

        initialize(new DropGamePlay(),config);
    }
}
