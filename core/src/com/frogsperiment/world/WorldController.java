package com.frogsperiment.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.frogsperiment.levels.Level;
import com.frogsperiment.objects.AbstractGameObject;
import com.frogsperiment.objects.Player;
import com.frogsperiment.util.CameraHelper;
import com.frogsperiment.util.Constants;
import com.frogsperiment.util.InputController;

/**
 * Created by bedford on 9/2/15.
 */
public class WorldController {


    // Set the TAG for logging purposes
    private static final String TAG = WorldController.class.getName();

    private float accumulator = 0;
    private CameraHelper cameraHelper;
    private Level level;
    private PhysicsController physicsController;
    Player player;
    private Array<Body> bodies = new Array<Body>();
    private boolean initRenderState = false;
    private Stage uiStage, gameStage;
    private InputController inputController;

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
        initPhysics();
        player = this.getLevel().getPlayer();
        cameraHelper.setTarget(player);
        inputController = new InputController(this);
    }

    public void update (float deltaTime) {
        inputController.handleDebugInput(deltaTime);
        updatePhysics(deltaTime);
        doPhysicsStep(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void initLevel() {
        level = new Level();
    }

    private void initPhysics() {
        physicsController = new PhysicsController(this);
    }

    private void updatePhysics(float deltaTime) {
        this.physicsController.updatePhysics(deltaTime);
    }

    // Handle collisions
    private void onCollisionPlayerWithWeapon(Weapon weapon) {};

    private void testCollisions() {
    }

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        double frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;

        this.getPhysicsController().getB2World().getBodies(bodies);

        while (accumulator >= Constants.TIME_STEP) {
            this.physicsController.getB2World().step(
                    Constants.TIME_STEP,
                    Constants.VELOCITY_ITERATIONS,
                    Constants.POSITION_ITERATIONS
            );
            accumulator -= Constants.TIME_STEP;

            for (Body b : bodies) {
                // Get the body's user data - in this example, our user data
                //   is an instance of the Entity class
                AbstractGameObject e = (AbstractGameObject) b.getUserData();

                if (e != null) { e.update(Constants.TIME_STEP); }
            }
        }

        for (Body b : bodies) {
            // Get the body's user data - in this example, our user data
            //   is an instance of the Entity class
            AbstractGameObject e = (AbstractGameObject) b.getUserData();

            if (e != null) {
                e.interpolate(accumulator / Constants.TIME_STEP);
            }
        }
    }

    // Getters
    public Level getLevel() { return this.level; }
    public Stage getUiStage() { return this.uiStage; }
    public Stage getGameStage() { return this.gameStage; }
    public CameraHelper getCameraHelper() { return this.cameraHelper; }
    public PhysicsController getPhysicsController() {
        return this.physicsController;
    }
    public boolean getInitRenderState() { return this.initRenderState; }

    // Setters
    public void setInitRenderState(boolean state) {
        this.initRenderState = state;
    }


}
