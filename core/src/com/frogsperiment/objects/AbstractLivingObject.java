package com.frogsperiment.objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by bedford on 9/2/15.
 */
public abstract class AbstractLivingObject extends AbstractGameObject {

    protected Vector2 moveSpeed;
    protected float moveRate;
    protected boolean isMoving;
    protected int maxJumps, currentJumps;

    public AbstractLivingObject() {
        moveRate = 5;
        moveSpeed = new Vector2(0,0);
        isMoving = false;
        maxJumps = 1;
        currentJumps = 0;
    }

    public void jump() {

    }

    // This function is called whenever damage is taken by a living object
    // The strike_direction determines the direction the object is pushed
    // back after being struck.
    public void takeDamage(int amount) {

    }


    // Getters
    public Vector2 getSpeed() { return moveSpeed; }
    public float getMoveRate() {
        return moveRate;
    }
    public boolean isMoving() { return isMoving; }

    // Setters
    public void setMoveSpeed (Vector2 speed) { moveSpeed = speed; }
    public void setMoving (boolean moving) { isMoving = moving; }

}
