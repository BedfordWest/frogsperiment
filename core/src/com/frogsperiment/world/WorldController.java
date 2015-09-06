package com.frogsperiment.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.frogsperiment.levels.Level;
import com.frogsperiment.objects.AbstractGameObject;
import com.frogsperiment.objects.AbstractMovingObject;
import com.frogsperiment.objects.Player;
import com.frogsperiment.objects.Weapon;
import com.frogsperiment.util.CameraHelper;
import com.frogsperiment.util.Constants;
import com.frogsperiment.util.InputController;

/**
 * Created by bedford on 9/2/15.
 */
public class WorldController {


    // Set the TAG for logging purposes
    private static final String TAG = WorldController.class.getName();

    private CameraHelper cameraHelper;
    private Level level;
    Player player;
    private boolean initRenderState = false;
    private Stage uiStage, gameStage;
    private InputController inputController;
    private float accumulator = 0f;
    private Array<AbstractMovingObject> objects =
            new Array<AbstractMovingObject>();

    // Rectangles for collision detection
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    public WorldController () {
        init();
    }

    public void reset() {
        init();
    }

    // This init is used whenever the object needs to be reset without
    //   completely deleting and re-instantiating it.
    private void init () {
        cameraHelper = new CameraHelper();
        initLevel();
        player = this.getLevel().getPlayer();
        cameraHelper.setTarget(player);
        inputController = new InputController(this);
    }

    public void update (float deltaTime) {
        inputController.handleDebugInput(deltaTime);
        //Put collision handling here
        updateMove(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void initLevel() {
        level = new Level();
    }


    // Handle collisions
    private void onCollisionPlayerWithWeapon(Weapon weapon) {};

    private void testCollisions() {
    }

    // For any objects derived from AbstractMovingObject, make sure they move
    private void updateMove(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        double frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;

        // Perform full movement for each TIME_STEP
        while (accumulator >= Constants.TIME_STEP) {

            accumulator -= Constants.TIME_STEP;
            for (AbstractMovingObject o : objects) {
                if (o != null) { o.update(Constants.TIME_STEP); }
            }

        }

        // Interpolate for anything "left over" after the time steps
        for (AbstractMovingObject o : objects) {
            if (o != null) { o.interpolate(accumulator/ Constants.TIME_STEP); }
        }

    }


    // Getters
    public Level getLevel() { return this.level; }
    public Stage getUiStage() { return this.uiStage; }
    public Stage getGameStage() { return this.gameStage; }
    public CameraHelper getCameraHelper() { return this.cameraHelper; }

    public boolean getInitRenderState() { return this.initRenderState; }

    // Setters
    public void setInitRenderState(boolean state) {
        this.initRenderState = state;
    }


}
