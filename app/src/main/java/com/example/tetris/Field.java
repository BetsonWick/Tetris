package com.example.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Field {
    private Cell[][] field;
    private int width;
    private int height;
    private int tilesX;
    private int tilesY;

    public Field(int width, int height) {
        field = new Cell[width][height];
        tilesX = width;
        tilesY = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isEmpty(int x, int y) {
        if (x < 0 || x >= tilesX || y < 0 || y >= tilesY) {
            return false;
        }
        return field[x][y] == Cell.BLANK;
    }

    public boolean update(Tile tile) {
        for (int i = tilesY - 1; i >= 0; i--) {
            int counter = 0;
            for (int j = 0; j < tilesX; j++) {
                if (field[j][i] == Cell.MOVING) {
                    field[j][i] = Cell.BLANK;
                }
                if (field[j][i] == Cell.FILLED) {
                    counter++;
                }
            }
            if (counter == tilesX) {
                for (int j = 0; j < tilesX; j++) {
                    field[j][i] = Cell.BLANK;
                }
            }
        }
        setTileIn(tile, Cell.MOVING);
        return !checkCollision(tile);
    }

    public void setTileIn(Tile tile, Cell toFill) {
        for (int i = 0; i < tile.getWidth(); i++) {
            for (int j = 0; j < tile.getHeight(); j++) {
                Cell currentCell = tile.getCellAt(i, j);
                if (currentCell != Cell.BLANK) {
                    field[i + tile.getX()][j + tile.getY()] = toFill;
                }
            }
        }
    }

    private boolean checkCollision(Tile tile) {
        if (tile.getY() + tile.getHeight() == tilesY) {
            return true;
        }
        for (int i = 0; i < tile.getWidth(); i++) {
            if (field[i + tile.getX()][tile.getY() + tile.getHeight()] == Cell.FILLED &&
                    tile.getCellAt(i, tile.getHeight() - 1) == Cell.MOVING) {
                return true;
            }
        }
        return false;
    }

    public void clearField() {
        for (int i = 0; i < tilesX; i++) {
            for (int j = 0; j < tilesY; j++) {
                field[i][j] = Cell.BLANK;
            }
        }
    }

    public void draw(Canvas canvas) {
        int tileSize = width / tilesX;
        Paint paint = new Paint();
        for (int i = 0; i < tilesX; i++) {
            for (int j = 0; j < tilesY; j++) {
                int heightDifference = tilesY * tileSize - height;
                int leftX = i * tileSize;
                int leftY = j * tileSize - heightDifference;
                int cellColor;
                switch (field[i][j]) {
                    case BLANK:
                        cellColor = Color.WHITE;
                        break;
                    case FILLED:
                        cellColor = Color.GRAY;
                        break;
                    case MOVING:
                        cellColor = Color.RED;
                        break;
                    default:
                        cellColor = Color.BLACK;
                }
                paint.setColor(cellColor);
                Rect rect = new Rect(leftX, leftY, leftX + tileSize, leftY + tileSize);
                canvas.drawRect(rect, paint);
            }
        }
    }
}
