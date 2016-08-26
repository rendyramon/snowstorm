package com.calendars.advent.domain;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andras on 2016.07.13..
 */
public class SnowStorm {

    private final List<SnowFlake> snowFlakes;
    private final List<Drawable> snowFlakeDrawables;

    public SnowStorm(final List<Drawable> snowFlakes) {
        this.snowFlakes = new ArrayList<>();
        this.snowFlakeDrawables = snowFlakes;
    }

    public List<SnowFlake> getSnowFlakes() {
        return snowFlakes;
    }

    public void move(final int height) {
        final List<SnowFlake> visible = new ArrayList<>();
        for (final SnowFlake snowFlake : snowFlakes) {
            if (snowFlake.getY() < height) {
                visible.add(snowFlake);
            }
        }

        snowFlakes.clear();
        snowFlakes.addAll(visible);

        for (final SnowFlake snowFlake : snowFlakes) {
            snowFlake.fall();
        }
    }

    public void populate(final int width) {

        boolean startToSnow = true;

        for (final SnowFlake snowFlake : snowFlakes) {
            if (snowFlake.getY() < SnowFlake.MAX_SIZE) {
                startToSnow = false;
            }
        }

        if (startToSnow) {
            final int numberOfSnowFlakesToSpawn = random(3, 1);
            for (int i = 0; i < numberOfSnowFlakesToSpawn; i++) {
                final SnowFlake snowFlake = SnowFlake.newSnowFlake()
                        .setY(-1)
                        .setX(random(width, 0))
                        .setR(random(SnowFlake.MAX_SIZE, SnowFlake.MIN_SIZE));

                if( random( 100, 0 ) < 30) {
                    snowFlake.setDrawable(getSnowFlakeDrawable());
                }

                snowFlakes.add(snowFlake);
            }
        }
    }

    private int random(final int max, final int min) {
        final Random rand = new Random();
        int randomNumber = rand.nextInt(max) + 1; //so max is inclusive
        return randomNumber < min ? min : randomNumber;
    }

    private Drawable getSnowFlakeDrawable() {
        final Random random = new Random();
        return snowFlakeDrawables.get(random.nextInt(snowFlakeDrawables.size() - 1));
    }
}
