package org.example;

import java.util.HashMap;

class Tools {
    private static int[] bins = {
        0b1, 0b10, 0b100, 0b1000, 0b10000, 0b100000, 0b1000000, 0b10000000, 0b100000000,
    };
    private static HashMap<Integer, Integer> binToNum = initBinToNum();

    private static HashMap<Integer, Integer> initBinToNum() {
        binToNum = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            binToNum.put(0b1 << i, i + 1);
        }
        return binToNum;
    }

    static int numToBin(int num) {
        return bins[num - 1];
    }

    static int binToNum(int bin) {
        return binToNum.get(bin);
    }

    static int generatePairBin(int num1, int num2) {
        return numToBin(num1) | numToBin(num2);
    }

    static void printBoardIndex() { // --<
        Board board = new Board();
        board.printHorizontalBorder("=", "edge");
        for (int x = 0; x < 9; x++) {
            System.out.print("\n||");
            for (int y = 0; y < 9; y++) {
                if (y % 3 == 2) {
                    if (x * 9 + y > 9) {
                        System.out.print(" " + (x * 9 + y) + "||");
                    } else {
                        System.out.print(" " + (x * 9 + y) + " " + "||");
                    }
                } else {
                    if (x * 9 + y > 9) {
                        System.out.print(" " + (x * 9 + y) + "|");
                    } else {
                        System.out.print(" " + (x * 9 + y) + " " + "|");
                    }
                }
            }
            System.out.println();
            if (x == 8) {
                board.printHorizontalBorder("=", "edge");
            } else if (x % 3 == 2) {
                board.printHorizontalBorder("=", "body");
            } else {
                board.printHorizontalBorder("-", "body");
            }
        }
        System.out.println();
    }

    // -->

    static Cell[] copyBoard(Cell[] original) { // --<
        Cell[] copy = new Cell[81];
        for (int i = 0; i < 81; i++) {
            copy[i] = new Cell();
            copy[i].data = original[i].data;
        }
        return copy;
    }

    // -->

    static boolean isEqual(Cell[] board1, Cell[] board2) {
        for (int i = 0; i < 81; i++) {
            if (board1[i].data != board2[i].data) {
                return false;
            }
        }
        return true;
    }

    static boolean isSameRow(int index1, int index2) {
        int row1 = index1 / 9;
        int row2 = index2 / 9;
        return row1 == row2;
    }

    static boolean isSameRow(int index1, int index2, int index3) {
        int row1 = index1 / 9;
        int row2 = index2 / 9;
        int row3 = index3 / 9;
        return row1 == row2 && row2 == row3;
    }

    static int getRow(int index) {
        return index / 9;
    }

    static boolean isSameColumn(int index1, int index2) {
        int column1 = index1 % 9;
        int column2 = index2 % 9;
        return column1 == column2;
    }

    static boolean isSameColumn(int index1, int index2, int index3) {
        int column1 = index1 % 9;
        int column2 = index2 % 9;
        int column3 = index3 % 9;
        return column1 == column2 && column2 == column3;
    }

    static int getColumn(int index) {
        return index % 9;
    }
}
