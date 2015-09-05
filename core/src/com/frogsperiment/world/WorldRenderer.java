package com.frogsperiment.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.frogsperiment.util.Constants;

import java.util.List;

/**
 * Created by bedford on 9/2/15.
 */
public class WorldRenderer implements Disposable {

    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private OrthogonalTiledMapRenderer renderer;
    private WorldController worldController;
    private Box2DDebugRenderer box2DDebugRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    // Rendering constants/switches
    private static final boolean DEBUG_DRAW_BOX2D_WORLD = true;

    public WorldRenderer (WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();

        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,
                Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(false);
        cameraGUI.update();

        renderer = new OrthogonalTiledMapRenderer(
                worldController.getLevel().getTiledMap(),
                1/Constants.WORLD_SCALE);
        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    public void render () {
        if(worldController.getInitRenderState())
        {
            worldController.setInitRenderState(false);
            renderer = new OrthogonalTiledMapRenderer(worldController.
                    getLevel().
                    getTiledMap(),
                    1/Constants.WORLD_SCALE
            );
        }
        renderWorld();
        renderGUI();
    }

    public void renderWorld() {
        camera.update();
        worldController.getCameraHelper().applyTo(camera);
        renderer.setView(camera);
        renderMap();
        if(DEBUG_DRAW_BOX2D_WORLD) { renderPhysicsDebugLines(); }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderPlayer();
        renderEnemies();
        batch.end();
    }

    public void renderGUI()
    {
        batch.setProjectionMatrix(cameraGUI.combined);

        batch.begin();
        renderFPS();
        batch.end();
    }

    public void resize (int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT /
                (float)height) * (float)width;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2,
                cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();
    }

    @Override public void dispose () {
        renderer.dispose();
    }

    private void renderPlayer()
    {
        worldController.getLevel()
                .getPlayer()
                .render(batch);
    }

    private void renderBots()
    {
        List<Bot> bots = worldController.getLevel().getBots();
        for (Bot bot : bots) {
            bot.render(batch);
        }
    }

    private void renderMap()
    {
        renderer.render();
    }

    private void renderFPS()
    {
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        font.draw(batch, "Player Velocity: " +
                        this
                                .worldController
                                .getLevel()
                                .getPlayer()
                                .getBody()
                                .getLinearVelocity(),
                10,
                40
        );
        font.draw(batch, "Move: <-A S-> D\\/ ^W , Jump: <Space>", 10, 60);
        font.draw(batch, "Reset Level: <R>", 10, 80);
    }

}
