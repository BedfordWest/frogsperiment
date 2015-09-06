package com.frogsperiment.util;

/**
 * Created by bedford on 9/2/15.
 */
public class Constants {

    /** Desktop Client Constants **/
    public static final int DESKTOP_HEIGHT = 600;
    public static final int DESKTOP_WIDTH = 800;

    /** World Constants **/
    // Visible game world is 5 meters wide
    public static final float VIEWPORT_WIDTH = 5.0f;
    // Visible game world is 5 meters tall
    public static final float VIEWPORT_HEIGHT = 5.0f;
    // GUI Width
    public static final float VIEWPORT_GUI_WIDTH = 800.0f;
    // GUI Height
    public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
    // This corresponds to how many pixels are in a meter
    public static final float WORLD_SCALE = 16.0f;
    // Length of time in a frame step
    public static final float TIME_STEP = 1/120f;

    /** Asset Constants **/
    // Location of description file for texture atlas
    public static final String TEXTURE_ATLAS_OBJECTS =
            "android/assets/images/objecttextures.atlas";

    /** Level Tile Constants **/
    // Width and Height of level tiles
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    // Width of game levels in tiles
    public static final int LEVEL_X_TILES = 30;
    // Height of game levels in tiles
    public static final int LEVEL_Y_TILES = 30;

    /** Player Constants **/
    // Base starting player health pool
    public static final int BASE_PLAYER_HEALTH = 50;

    /** AbstractMovingObject Constants **/
    // Types of enemy weapons in the game
    public enum WeaponType { LASER, GAS, POISON, NEEDLE, VIAL }

    /** General constants for code readability **/
    public static final float DIRECTION_RIGHT = 0.0f;
    public static final float DIRECTION_LEFT = 180.0f;
    public static final float DIRECTION_UP = 270.0f;
    public static final float DIRECTION_DOWN = 90.0f;

}
