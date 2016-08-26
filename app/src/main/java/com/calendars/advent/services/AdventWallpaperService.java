package com.calendars.advent.services;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import com.calendars.advent.R;
import com.calendars.advent.domain.SnowFlake;
import com.calendars.advent.domain.SnowStorm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andras on 2016.07.13..
 */
public class AdventWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new AdventEngine();
    }

    private class AdventEngine extends Engine {

        private final String LOG_TAG = AdventEngine.class.getSimpleName();

        private List<Drawable> snowFlakes;

        private final Handler handler = new Handler();
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                drawFrame();
            }

        };

        private final SnowStorm snowStorm;


        private int width;
        private int height;

        {
            snowFlakes = new ArrayList<>();
            snowFlakes.add(getResources().getDrawable(R.drawable.snowflake1));
            snowFlakes.add(getResources().getDrawable(R.drawable.snowflake2));
            snowFlakes.add(getResources().getDrawable(R.drawable.snowflake3));
            snowFlakes.add(getResources().getDrawable(R.drawable.snowflake4));
            snowFlakes.add(getResources().getDrawable(R.drawable.snowflake5));
            snowFlakes.add(getResources().getDrawable(R.drawable.snowflake6));
            snowFlakes.add(getResources().getDrawable(R.drawable.snowflake7));
        }

        AdventEngine() {
            snowStorm = new SnowStorm(snowFlakes);
        }


        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            // By default we don't get touch events, so enable them.
            setTouchEventsEnabled(true);
            drawFrame();
            Log.v(LOG_TAG, "Frame drawn!");
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.width = width;
            this.height = height;
            super.onSurfaceChanged(holder, format, width, height);
        }

        private void drawFrame() {

            final SurfaceHolder holder = getSurfaceHolder();
            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {

                    snowStorm.move(height);
                    snowStorm.populate(width);

                    final Paint paint = new Paint();
                    paint.setColor(0xffffffff);
                    paint.setAntiAlias(true);
                    paint.setStrokeWidth(2);
                    paint.setStrokeCap(Paint.Cap.ROUND);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStyle(Paint.Style.FILL);

                    c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                    for (final SnowFlake snowFlake : snowStorm.getSnowFlakes()) {

                        if (snowFlake.getDrawable() == null) {

                            c.drawCircle(snowFlake.getX(), snowFlake.getY(), snowFlake.getR(), paint);

                        } else {
                            final Drawable drawable = snowFlake.getDrawable();
                            drawable.setBounds(snowFlake.getBounds());
                            drawable.draw(c);
                        }


                    }

                    handler.postDelayed(drawRunner, 10);
                }
            } finally {
                if (c != null)
                    holder.unlockCanvasAndPost(c);
            }
        }
    }

}
