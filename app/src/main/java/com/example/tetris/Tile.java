package com.example.tetris;

public class Tile {
    private Cell[][] shape;
    private int width;
    private int height;
    private int x;
    private int y;

    private void setupCell(Cell cell) {
        shape = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                shape[i][j] = cell;
            }
        }
    }

    private void setupZigZag() {
        shape[0][0] = Cell.MOVING;
        shape[0][1] = Cell.MOVING;
        shape[1][1] = Cell.MOVING;
        shape[1][2] = Cell.MOVING;
    }

    public Cell getCellAt(int i, int j) {
        return shape[i][j];
    }

    public Tile(TileType type) {
        switch (type) {
            case LONG:
                width = 1;
                height = 4;
                setupCell(Cell.MOVING);
                break;
            case SQUARE:
                width = 2;
                height = 2;
                setupCell(Cell.MOVING);
                break;
            case ZIG_ZAG:
                width = 2;
                height = 3;
                setupCell(Cell.BLANK);
                setupZigZag();
                break;
            default:
                width = 1;
                height = 2;
                setupCell(Cell.MOVING);
        }
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
