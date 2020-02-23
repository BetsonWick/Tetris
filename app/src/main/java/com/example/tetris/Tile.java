package com.example.tetris;

import android.graphics.Color;

import java.util.HashMap;

public class Tile {
    public static final int SIZE = 4;
    private static HashMap<TileType, int[][][]> tileToShape;

    private int tileColor;
    private int movingShift;
    private int[][][] shape;
    private int x;
    private int y;
    private int rotation;

    static {
        tileToShape = new HashMap<>();
        int[][][] shape = new int[4][4][4];
        shape[0] = shape[1] = shape[2] = shape[3] =
                new int[][]{{0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        tileToShape.put(TileType.SQUARE, shape);
        shape = new int[4][4][4];
        shape[0] = new int[][]{{0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}};
        shape[1] = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}};
        shape[2] = new int[][]{{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}};
        shape[3] = new int[][]{{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        tileToShape.put(TileType.LONG, shape);
        shape = new int[4][4][4];
        shape[0] = new int[][]{{1, 0, 0, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        shape[1] = new int[][]{{0, 1, 1, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        shape[2] = new int[][]{{0, 0, 0, 0}, {1, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}};
        shape[3] = new int[][]{{0, 1, 0, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}};
        tileToShape.put(TileType.CORNER, shape);
        shape = new int[4][4][4];
        shape[0] = new int[][]{{0, 0, 1, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        shape[1] = new int[][]{{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        shape[2] = new int[][]{{0, 0, 0, 0}, {1, 1, 1, 0}, {1, 0, 0, 0}, {0, 0, 0, 0}};
        shape[3] = new int[][]{{1, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        tileToShape.put(TileType.CORNER2, shape);
        shape = new int[4][4][4];
        shape[0] = new int[][]{{0, 1, 1, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        shape[1] = new int[][]{{0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}};
        shape[2] = new int[][]{{0, 0, 0, 0}, {0, 1, 1, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}};
        shape[3] = new int[][]{{1, 0, 0, 0}, {1, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        tileToShape.put(TileType.ZIG_ZAG, shape);
        shape = new int[4][4][4];
        shape[0] = new int[][]{{1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        shape[1] = new int[][]{{0, 0, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        shape[2] = new int[][]{{0, 0, 0, 0}, {1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        shape[3] = new int[][]{{0, 1,  0, 0}, {1, 1, 0, 0}, {1, 0, 0, 0}, {0, 0, 0, 0}};
        tileToShape.put(TileType.ZIG_ZAG2, shape);
        shape = new int[4][4][4];
        shape[0] = new int[][]{{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        shape[1] = new int[][]{{0, 1, 0, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        shape[2] = new int[][]{{0, 0, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        shape[3] = new int[][]{{0, 1,  0, 0}, {1, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        tileToShape.put(TileType.MIDDLE, shape);

    }


    public Tile(TileType type) {
        rotation = 0;
        shape = tileToShape.get(type);
    }

    public int getMovingShift() {
        return movingShift;
    }

    public void setMovingShift(int movingShift) {
        this.movingShift = movingShift;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getCurrentShape() {
        return shape[rotation];
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation % SIZE;
    }

    public void changeRotation() {
        rotation++;
        rotation %= SIZE;
    }

    public int getTileColor() {
        return tileColor;
    }

    public void setRandomColor(double randomValue) {
        int val = (int) (randomValue * 6);
        switch (val) {
            case 0:
                tileColor = Color.BLUE;
                break;
            case 1:
                tileColor = Color.RED;
                break;
            case 2:
                tileColor = Color.YELLOW;
                break;
            case 3:
                tileColor = Color.GREEN;
                break;
            case 4:
                tileColor = Color.MAGENTA;
                break;
            case 5:
                tileColor = Color.CYAN;
                break;
        }
    }

    public void setTileColor(int tileColor) {
        this.tileColor = tileColor;
    }
}
