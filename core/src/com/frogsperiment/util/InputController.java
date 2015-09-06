package com.frogsperiment.util;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector2;
import com.frogsperiment.objects.AbstractGameObject;
import com.frogsperiment.objects.Player;
import com.frogsperiment.world.WorldController;

/**
 * Created by bedford on 9/3/15.
 */
public class InputController extends InputAdapter {

    private WorldController worldController;
    private Player player;
    private CameraHelper cameraHelper;
    private static final String TAG = InputController.class.getName();

    public InputController(WorldController worldController)
    {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(worldController.getUiStage());
        multiplexer.addProcessor(worldController.getGameStage());
        Gdx.input.setInputProcessor(multiplexer);
        this.worldController = worldController;
        this.player = worldController.getLevel().getPlayer();
        this.cameraHelper = worldController.getCameraHelper();
    }

    @Override
    public boolean keyUp (int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {
            worldController.reset();
            worldController.setInitRenderState(true);
            Gdx.app.debug(TAG, "Game world reset");
        }

        // Toggle camera follow
        else if (keycode == Input.Keys.ENTER) {
            worldController.getCameraHelper().setTarget(
                    cameraHelper.hasTarget() ? null : player);
            Gdx.app.debug(TAG, "Camera follow is enabled: " +
                    cameraHelper.hasTarget());
        }
        else if(keycode == Input.Keys.D)  {
            player.setMoveSpeed(new Vector2(player.getSpeed().x -
                    player.getMoveRate(), player.getSpeed().y));
            Gdx.app.debug(TAG, "D released, move speed: " +
                    player.getSpeed().x);
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.setDirection(Constants.DIRECTION_LEFT);
            }
        }
        else if (keycode == Input.Keys.A) {
            player.setMoveSpeed(new Vector2(player.getSpeed().x +
                    player.getMoveRate(), player.getSpeed().y));
            Gdx.app.debug(TAG, "A released, move speed: " +
                    player.getSpeed().x);
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.setDirection(Constants.DIRECTION_RIGHT);
            }
        }
        return true;
    }

    @Override
    public boolean keyDown (int keycode) {
        switch(keycode)
        {
            case Input.Keys.SPACE:
                player.jump();
                Gdx.app.debug(TAG, "Player jumped!");
                break;
            case Input.Keys.A:
                player.setMoveSpeed(new Vector2(player.getSpeed().x -
                        player.getMoveRate(), player.getSpeed().y));
                player.setDirection(Constants.DIRECTION_LEFT);
                Gdx.app.debug(TAG, "A pressed, move speed: " +
                        player.getSpeed().x);
                break;
            case Input.Keys.D:
                player.setMoveSpeed(new Vector2(player.getSpeed().x +
                        player.getMoveRate(), player.getSpeed().y));
                player.setDirection(Constants.DIRECTION_RIGHT);
                Gdx.app.debug(TAG, "D pressed, move speed " +
                        player.getSpeed().x);
                break;
            default:
        }
        return true;
    }

    public void handleDebugInput (float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setMoveState(AbstractGameObject.MoveState.MS_UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setMoveState(
                    AbstractGameObject.
                            MoveState.
                            MS_DOWN
            );
        }

        else {
            player.setMoveState(
                    AbstractGameObject.
                            MoveState.
                            MS_NONE
            );
        }

        // Camera Controls (move)
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camMoveSpeed *= camMoveSpeedAccelerationFactor;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveCamera(-camMoveSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveCamera(camMoveSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveCamera(0, camMoveSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveCamera(0, -camMoveSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            cameraHelper.setPosition(0,0);
        }

        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
            cameraHelper.addZoom(camZoomSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) {
            cameraHelper.addZoom(-camZoomSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) {
            cameraHelper.setZoom(1);
        }
    }

    private void moveCamera (float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

}
