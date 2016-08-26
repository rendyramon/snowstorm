package com.calendars.advent.domain;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by Andras on 2016.07.13..
 */
public class SnowFlake {

    private final String LOG_TAG = SnowFlake.class.getSimpleName();

    public static final int MAX_SIZE = 10;
    public static final int MIN_SIZE = 6;

    private static final float MIN_SPEED = (float) 0.1;
    private static final float MAX_SPEED = (float) 0.5;

    private static final float SPEED_PER_SIZE = (MAX_SPEED - MIN_SPEED) / (MAX_SIZE - MIN_SIZE);

    private Drawable drawable;

    private int x;
    private float y;
    private int r;

    private float speed;

    private SnowFlake() {
    }

    public static SnowFlake newSnowFlake() {
        return new SnowFlake();
    }

    public SnowFlake setX(int x) {
        this.x = x;
        return this;
    }

    public SnowFlake setY(float y) {
        this.y = y;
        return this;
    }

    public SnowFlake setR(int r) {
        this.speed = (r - MIN_SIZE + 1) * SPEED_PER_SIZE + 1;
        this.r = r;
        return this;
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void fall() {
        this.y += speed;
    }

    public Rect getBounds() {
        return new Rect(x, (int)y, x + 2 * r, (int)y + 2 * r);
    }
}
