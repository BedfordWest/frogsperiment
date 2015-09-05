package com.frogsperiment.assets;

import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by bedford on 9/2/15.
 */
public class Assets implements Disposable, AssetErrorListener {

    // Set the TAG constant for logging
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    public AssetPlayer player;
    public AssetBot bot;
    public AssetFloor floor;
    public AssetWall wall;

}
