package com.example.tetris;

import java.util.HashMap;

public class Tile {
    public static final int SIZE = 4;
    private static HashMap<TileType, int[][][]> tileToShape;

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
}
