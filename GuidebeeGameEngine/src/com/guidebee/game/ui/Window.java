/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
//--------------------------------- PACKAGE ------------------------------------
package com.guidebee.game.ui;

//--------------------------------- IMPORTS ------------------------------------

import com.guidebee.game.GameEngine;
import com.guidebee.game.InputProcessor;
import com.guidebee.game.ScreenAdapter;
import com.guidebee.game.camera.viewports.ScalingViewport;
import com.guidebee.game.camera.viewports.ScreenViewport;
import com.guidebee.game.engine.scene.Stage;
import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.SpriteBatch;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Window screen.
 */
public class Window extends ScreenAdapter {
    private SpriteBatch batch;
    private Stage stage;
    private Table table = new Table();

    private InputProcessor savedInputProcessor = null;


    /**
     * add UI component
     * @param actor
     */
    public void addComponent(Widget actor) {
        table.addActor(actor);
    }

    /**
     * add UI component
     * @param actor
     */
    public void addComponent(WidgetGroup actor) {
        table.addActor(actor);
    }


    /**
     * Constructor.
     * @param width
     * @param height
     */
    public Window(int width, int height) {

        stage = new Stage(new ScalingViewport(width, height));
        table.setFillParent(true);
        stage.addActor(table);
        batch = new SpriteBatch();
    }

    /**
     * Constructor.
     */
    public Window() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();

    }

    @Override
    public void show() {
        savedInputProcessor = GameEngine.input.getInputProcessor();
        GameEngine.input.setInputProcessor(stage);

    }

    @Override
    public void hide() {
        GameEngine.input.setInputProcessor(savedInputProcessor);
    }


    public Batch getBatch() {
        return stage.getBatch();
    }

    /**
     * get width of the window.
     * @return
     */
    public float getWidth() {
        return stage.getWidth();
    }


    /**
     * get height of the window.
     * @return
     */
    public float getHeight() {
        return stage.getHeight();
    }


    @Override
    public void render(float delta) {
        GameEngine.graphics.clearScreen(0, 0, 0, 1f);
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();

    }
}
