package com.example.tetris;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    private Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameField gameField = new GameField(this);
        display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        gameField.setDisplayWidth(size.x);
        gameField.setDisplayHeight(size.y);
        int full_screen = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(full_screen, full_screen);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(gameField);

    }

}
