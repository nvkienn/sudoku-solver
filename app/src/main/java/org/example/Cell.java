package org.example;

class Cell {
    int data;

    void init() {
        this.data = 0b111111111;
    }

    void set(int num) {
        this.data = Tools.numToBin(num);
    }

    void set(int num, boolean integer) {
        if (integer == true) {
            this.data = Tools.numToBin(num);
        } else {
            this.data = num;
        }
    }

    int get() {
        return Tools.binToNum(data);
    }

    String getFullBin() {
        return Integer.toBinaryString(data);
    }

    boolean isSolved() {
        boolean result = Integer.bitCount(data) == 1 ? true : false;
        return result;
    }

    boolean isNotSolved() {
        boolean result = Integer.bitCount(data) == 1 ? false : true;
        return result;
    }

    boolean contains(int num) {
        int testBin = Tools.numToBin(num);
        boolean result = (testBin & this.data) > 0 ? true : false;
        return result;
    }

    int size() {
        return Integer.bitCount(data);
    }

    void remove(Cell cell) {
        this.data = this.data & ~cell.data;
    }

    void remove(int data) {
        this.data = this.data & ~data;
    }
}
