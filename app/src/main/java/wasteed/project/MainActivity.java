package wasteed.project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {
    private Display display;
    private GameField gameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameField = new GameField(this);
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

    @Override
    protected void onPause() {
        super.onPause();
        try {
            FileOutputStream stream = getApplicationContext().
                    openFileOutput("info", Context.MODE_PRIVATE);
            stream.write(GameField.points);
            GameField.points = 0;
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameField.isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            FileInputStream stream = getApplicationContext().openFileInput("info");
            int points = stream.read();
            GameField.points = points;
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameField.isPaused = false;
    }

}
