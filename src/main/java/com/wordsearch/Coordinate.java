package com.wordsearch;

/**
 * Created by Nilay on 7/28/2017.
 */
public class Coordinate {

    private int row;
    private int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int goUp() {
        return this.row - 1;
    }

    public int goDown() {
        return this.row + 1;
    }

    public int goRight() {
        return this.col + 1;
    }

    public int goLeft() {
        return this.col - 1;
    }
}
