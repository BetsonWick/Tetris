package com.example.tetris;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    public static final int MAX_FPS = 32;
    public static final int secNano = 1000_000_000;

    private GameField field;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning;

    public MainThread(SurfaceHolder surfaceHolder, GameField field) {
        this.surfaceHolder = surfaceHolder;
        this.field = field;
        isRunning = false;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        long startTime;
        long targetTime = secNano / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        while (isRunning) {
            startTime = System.nanoTime();
            Canvas canvas = null;
            try {

                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    field.update();
                    field.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            waitTime = (targetTime + startTime - System.nanoTime()) / 1000_000;
            try {
                if (waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalTime += (System.nanoTime() - startTime);
            frameCount++;
            if (totalTime >= secNano) {
                System.out.println(frameCount);
                field.setCurrentFPS(frameCount);
                totalTime = 0;
                frameCount = 0;
            }
        }
    }
}
