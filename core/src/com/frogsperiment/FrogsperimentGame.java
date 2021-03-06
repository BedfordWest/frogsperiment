package com.frogsperiment;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.frogsperiment.assets.Assets;
import com.frogsperiment.world.WorldController;
import com.frogsperiment.world.WorldRenderer;

public class FrogsperimentGame implements ApplicationListener {

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    // We will use this variable to track whether the game has been paused
    private boolean paused;

	@Override
	public void create () {
        // Set LibGDX log level to DEBUG
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        // Load assets
        // Initialize controller and renderer
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);
        // Game world is active on start
        paused = false;

	}

	@Override
	public void render () {

        // Do not update the game world when paused.
        if (!paused) {
            // Update the game world by the time that has passed since the last
            // rendered frame
            worldController.update(Gdx.graphics.getDeltaTime());
        }

        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f,
                0xed/255.0f, 0xff/255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the game world to screen
        worldRenderer.render();

	}

	@Override public void resize (int width, int height) {
		// Resizing is a render event, therefore we want WorldRenderer to handle it
		worldRenderer.resize(width, height);
	}

	@Override public void pause () {
		paused = true;
	}
	@Override public void resume () {
		Assets.instance.init(new AssetManager());
		paused = false;
	}
	@Override public void dispose () {
		// Disposal is a render event, therefore we want WorldRenderer to handle it
		worldRenderer.dispose();
		Assets.instance.dispose();
	}

}
