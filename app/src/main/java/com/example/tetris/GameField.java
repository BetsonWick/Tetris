package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameField extends SurfaceView implements SurfaceHolder.Callback {
    private final int TILES_X = 10;
    private final int TILES_Y = 20;

    Tile currentTile;

    private MainThread thread;
    private Field field;
    private int displayWidth;
    private int displayHeight;

    private int frameCounter = 0;

    public GameField(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    private void setupField() {
        field = new Field(TILES_X, TILES_Y);
        field.setWidth(displayWidth);
        field.setHeight(displayHeight);
        field.clearField();
    }

    private void setupTile(double random) {
        int type = (int) (random * 3);
        currentTile = new Tile(TileType.values()[type]);
        currentTile.setX(TILES_X / 2);
        currentTile.setY(0);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setupField();
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
        setupTile(Math.random());
    }

    public void update() {
        frameCounter++;
        if (currentTile != null && frameCounter % 4 == 0) {
            currentTile.setY(currentTile.getY() + 1);
            if (!field.update(currentTile)) {
                field.setTileIn(currentTile, Cell.FILLED);
                setupTile(Math.random());
            }
        }
        if (frameCounter == MainThread.MAX_FPS) {
            frameCounter = 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        field.draw(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN && currentTile != null) {
            boolean right = event.getX() > displayWidth / 2;
            if (right && currentTile.getX() + currentTile.getWidth() < TILES_X) {
                currentTile.setX(currentTile.getX() + 1);
            }
            if (!right && currentTile.getX() > 0) {
                currentTile.setX(currentTile.getX() - 1);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean isClosed = false;
        while (!isClosed) {
            try {
                thread.setRunning(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isClosed = true;
        }
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }
}
