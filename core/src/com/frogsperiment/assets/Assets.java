package com.frogsperiment.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.frogsperiment.util.Constants;

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

    // singleton: prevent instantiation from other classes
    private Assets () {}

    public void init (AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
                TextureAtlas.class);
        // start loading assets and wait until finished
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " +
                assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + a);
        }

        TextureAtlas atlas =
                assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear,
                    Texture.TextureFilter.Linear);
        }

        // create game resource objects
        player = new AssetPlayer(atlas);
        bot = new AssetBot(atlas);
        floor = new AssetFloor(atlas);
        wall = new AssetWall(atlas);
    }

    @Override
    public void dispose () {
        assetManager.dispose();
    }

    @Override
    public void error (AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'",
                (Exception)throwable);
    }

    /* The following code contains the inner asset classes for Assets */
    /* -------------------------------------------------------------- */

    // Asset inner class for the player assets
    public class AssetPlayer {
        public final TextureAtlas.AtlasRegion walking;

        public AssetPlayer (TextureAtlas atlas) {
            walking = atlas.findRegion("character");
        }
    }

    // Asset inner class for the player assets
    public class AssetBot {
        public final TextureAtlas.AtlasRegion walking;

        public AssetBot (TextureAtlas atlas) {
            walking = atlas.findRegion("bot");
        }
    }

    // Asset inner class for the player assets
    public class AssetFloor {
        public final TextureAtlas.AtlasRegion floor1;

        public AssetFloor (TextureAtlas atlas) {
            floor1 = atlas.findRegion("bot");
        }
    }

    // Asset inner class for the player assets
    public class AssetWall {
        public final TextureAtlas.AtlasRegion wall1;

        public AssetWall (TextureAtlas atlas) {
            wall1 = atlas.findRegion("bot");
        }
    }

}
