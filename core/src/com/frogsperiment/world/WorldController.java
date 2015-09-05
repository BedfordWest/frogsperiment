package com.frogsperiment.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.frogsperiment.levels.Level;
import com.frogsperiment.objects.Player;
import com.frogsperiment.objects.Weapon;
import com.frogsperiment.util.CameraHelper;
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
        cameraHelper.update(deltaTime);
    }

    private void initLevel() {
        level = new Level();
    }


    // Handle collisions
    private void onCollisionPlayerWithWeapon(Weapon weapon) {};

    private void testCollisions() {
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
