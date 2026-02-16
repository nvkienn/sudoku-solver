package org.example;

class Cell {
    int data;

    void init() {
        this.data = 0b111111111;
    }

    void set(int num) {
        this.data = Tools.numToCell(num);
    }

    int get() {
        return Tools.cellToNum(data);
    }

    boolean isSolved() {
        boolean result = (Integer.bitCount(data) == 1) ? true : false;
        return result;
    }

    boolean isNotSolved() {
        boolean result = (Integer.bitCount(data) == 1) ? false : true;
        return result;
    }

    void remove(Cell cell) {
        this.data = this.data & ~cell.data;
    }
}
