package com.frogsperiment.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.frogsperiment.assets.Assets;
import com.frogsperiment.util.Constants;

/**
 * Created by bedford on 9/2/15.
 */
public class Player extends AbstractLivingObject {

    // Set the TAG for logging purposes
    private static final String TAG = Player.class.getName();

    private TextureRegion regTurned;

    public Player() {
        init();
    }

    private void init () {
        this.dimension.set(1.0f, 1.75f);
        this.rotation = 0.0f;
        this.direction = Constants.DIRECTION_RIGHT;
        this.regTurned = Assets.instance.player.turned;
        this.origin.set(dimension.x/2, dimension.y/2);
    }

    @Override
    public void render (SpriteBatch batch) {
        TextureRegion reg;

        if (this.direction == Constants.DIRECTION_RIGHT)
        {
            reg = regTurned;
            batch.draw(reg.getTexture(), position.x, position.y,
                    origin.x, origin.y, dimension.x, dimension.y, scale.x,
                    scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(), false, false);
        }

        else if (this.direction == Constants.DIRECTION_LEFT)
        {
            reg = regTurned;
            batch.draw(reg.getTexture(), position.x, position.y,
                    origin.x, origin.y, dimension.x, dimension.y, scale.x,
                    scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(), true, false);
        }

        else {
            Gdx.app.debug(TAG, "Problem rendering the player!");
        }
    }

}
