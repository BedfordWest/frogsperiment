package com.frogsperiment.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.frogsperiment.assets.Assets;
import com.frogsperiment.util.Constants;

/**
 * Created by bedford on 9/5/15.
 */
public class Weapon extends AbstractMovingObject {

    // Set the TAG for logging purposes
    private static final String TAG = Player.class.getName();

    private TextureRegion regWalking;
    private TextureRegion currentFrame;
    TextureRegion[] walkFrames;

    // Properties for walking animation
    private static final int FRAME_COLS = 6;
    private static final int FRAME_ROWS = 5;
    Animation walkAnimation;

    public Weapon() {
        init();
    }

    private void init () {
        this.dimension.set(1.0f, 1.75f);
        this.rotation = 0.0f;
        this.direction = Constants.DIRECTION_RIGHT;
        this.origin.set(dimension.x / 2, dimension.y / 2);

        Texture walkSheet = Assets.instance.player.walking.getTexture();
        TextureRegion[][] tmp =
                TextureRegion.split(walkSheet,
                        walkSheet.getWidth()/FRAME_COLS,
                        walkSheet.getHeight()/FRAME_ROWS
                );
        this.walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        this.walkAnimation = new Animation(0.025f, walkFrames);
        this.stateTime = 0f;


    }

    @Override
    public void render (SpriteBatch batch) {
        TextureRegion reg;

        if (this.direction == Constants.DIRECTION_RIGHT)
        {
            this.stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = walkAnimation.getKeyFrame(stateTime,true);
            batch.draw(currentFrame, position.x, position.y,
                    origin.x, origin.y, dimension.x, dimension.y, scale.x,
                    scale.y, rotation);
        }

        else if (this.direction == Constants.DIRECTION_LEFT)
        {

            this.stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = walkAnimation.getKeyFrame(stateTime,true);
            batch.draw(currentFrame, position.x, position.y,
                    origin.x, origin.y, dimension.x, dimension.y, scale.x,
                    scale.y, rotation);
        }

        else {
            Gdx.app.debug(TAG, "Problem rendering the player!");
        }
    }

}
