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

    // printBoard(boardType) --<

    static final int ROWLENGTH = 41;

    static void printHorizontalBorder(String borderType, String part) { // --<
        switch (part) {
            case "body":
                for (int i = 1; i <= ROWLENGTH; i++) {
                    if (i == 6 || i == 10 || i == 14 || i == 15 || i == 19 || i == 23 || i == 27
                            || i == 28 || i == 32 || i == 36) {
                        System.out.print("+");
                    } else {
                        System.out.print(borderType);
                    }
                }
                break;
            case "edge":
                for (int i = 1; i <= ROWLENGTH; i++) {
                    System.out.print(borderType);
                }
                break;
        }
    }

    // -->

    static void printBoardLine(Cell cell, String borderType) { // --<
        if (cell.isNotSolved()) {
            System.out.print(" " + " " + " " + borderType);
        } else {
            System.out.print(" " + cell.get() + " " + borderType);
        }
    }

    // -->

    static void printBoard(Cell[] board) { // --<
        printHorizontalBorder("=", "edge");
        for (int x = 0; x < 9; x++) {
            System.out.print("\n||");
            for (int y = 0; y < 9; y++) {
                if (y % 3 == 2) {
                    printBoardLine(board[x * 9 + y], "||");
                } else {
                    printBoardLine(board[x * 9 + y], "|");
                }
            }
            System.out.println();
            if (x == 8) {
                printHorizontalBorder("=", "edge");
            } else if (x % 3 == 2) {
                printHorizontalBorder("=", "body");
            } else {
                printHorizontalBorder("-", "body");
            }
        }
        System.out.println();
    }

    // -->
    // -->

    static void printBoardIndex() { // --<
        printHorizontalBorder("=", "edge");
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
                printHorizontalBorder("=", "edge");
            } else if (x % 3 == 2) {
                printHorizontalBorder("=", "body");
            } else {
                printHorizontalBorder("-", "body");
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

}
