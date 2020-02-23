package com.example.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Field {
    private static final int SHIFT = 4;
    private CellUnit[][] field;
    private int width;
    private int height;
    private int tilesX;
    private int tilesY;

    public Field(int width, int height) {
        field = new CellUnit[width + SHIFT * 2][height + SHIFT];
        for (int i = 0; i < width + SHIFT * 2; i++) {
            for (int j = 0; j < height + SHIFT; j++) {
                field[i][j] = new CellUnit(Cell.BLANK, CellColor.WHITE);
            }
        }
        for (int i = 0; i <= height; i++) {
            field[SHIFT - 1][i] = new CellUnit(Cell.FILLED, CellColor.WHITE);
            field[width + SHIFT][i] = new CellUnit(Cell.FILLED, CellColor.WHITE);
        }
        for (int i = SHIFT - 1; i <= width + SHIFT; i++) {
            field[i][height] = new CellUnit(Cell.FILLED, CellColor.WHITE);
        }
        tilesX = width;
        tilesY = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean checkTileSide(Tile tile, int dir) {
        int[][] state = tile.getCurrentShape();
        for (int x = 0; x < Tile.SIZE; x++) {
            for (int y = 0; y < Tile.SIZE; y++) {
                int posX = tile.getX() + x + SHIFT;
                int posY = tile.getY() + y;
                if (field[posX + dir][posY].cell == Cell.FILLED &&
                        state[y][x] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void update(Tile tile) {
        for (int i = 0; i < tilesY; i++) {
            int counter = 0;
            for (int j = SHIFT; j < tilesX + SHIFT; j++) {
                if (field[j][i].cell == Cell.MOVING) {
                    field[j][i].cell = Cell.BLANK;
                }
                if (field[j][i].cell == Cell.FILLED) {
                    counter++;
                }
            }
            if (counter == tilesX && i > 0) {
                for (int k = i; k > 0; k--) {
                    for (int j = SHIFT; j < tilesX + SHIFT; j++) {
                        field[j][k].cell = field[j][k - 1].cell;
                    }
                }
            }
        }
        setTileIn(tile, Cell.MOVING);
    }

    public void setTileIn(Tile tile, Cell toFill) {
        for (int i = 0; i < Tile.SIZE; i++) {
            for (int j = 0; j < Tile.SIZE; j++) {
                if (tile.getCurrentShape()[i][j] != 0) {
                    field[j + tile.getX() + SHIFT][i + tile.getY()].cell = toFill;
                }
            }
        }
    }

    public boolean checkCollision(Tile tile) {
        int[][] state = tile.getCurrentShape();
        for (int x = 0; x < Tile.SIZE; x++) {
            for (int y = 0; y < Tile.SIZE; y++) {
                int posX = tile.getX() + x + SHIFT;
                int posY = tile.getY() + y;
                if (field[posX][posY + 1].cell == Cell.FILLED &&
                        state[y][x] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clearField() {
        for (int i = 0; i < tilesX; i++) {
            for (int j = 0; j < tilesY; j++) {
                field[i + SHIFT][j].cell = Cell.BLANK;
            }
        }
    }

    public void draw(Canvas canvas, double tileShift) {
        int tileSize = width / tilesX;
        Paint paint = new Paint();
        for (int i = 0; i < tilesX; i++) {
            for (int j = 0; j < tilesY; j++) {
                double shift = 0;
                int cellColor;

                switch (field[i + SHIFT][j].cell) {
                    case BLANK:
                        continue;
                    case FILLED:
                        cellColor = Color.GRAY;
                        break;
                    case MOVING:
                        cellColor = Color.RED;
                        shift = tileSize * tileShift / GameField.FPS_SPLIT;
                        break;
                    default:
                        cellColor = Color.BLACK;
                }
                int heightDifference = tilesY * tileSize - height;
                int leftX = i * tileSize;
                int leftY = j * tileSize - heightDifference + (int) (shift);
                Rect rect = new Rect(leftX, leftY, leftX + tileSize, leftY + tileSize);
                paint.setColor(cellColor);
                canvas.drawRect(rect, paint);
            }
        }
    }
}
