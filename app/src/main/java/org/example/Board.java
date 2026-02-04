package org.example;

import java.util.ArrayList;

class Board {
    static int[][] boardGrid;
    static int[][] solution;
    static ArrayList<ArrayList<ArrayList<Integer>>> possibleBoard =
            new ArrayList<ArrayList<ArrayList<Integer>>>();
    static final int empty = 0;
    static final int rowLength = 41;

    // static void initPossibleBoard() {
    //	for {row:possibleBoard} {
    //		for {square:row}{
    //			for {i}
    //		}
    //	}
    // }

    // printBoard
    // --<
    static void printHorizontalBorder(String borderType, String part) {
        switch (part) {
            case "body":
                for (int i = 1; i <= rowLength; i++) {
                    if (i == 6 || i == 10 || i == 14 || i == 15 || i == 19 || i == 23 || i == 27
                            || i == 28 || i == 32 || i == 36) {
                        System.out.print("+");
                    } else {
                        System.out.print(borderType);
                    }
                }
                break;
            case "edge":
                for (int i = 1; i <= rowLength; i++) {
                    System.out.print(borderType);
                }
                break;
        }
    }

    static void printBoardLine(int square, String borderType) {
        if (square == empty) {
            System.out.print(" " + " " + " " + borderType);
        } else {
            System.out.print(" " + square + " " + borderType);
        }
    }

    static void printBoard(String boardType) {
        // print main body
        int[][] board;
        if (boardType.equals("solution")) {
            board = solution;
        } else if (boardType.equals("game")) {
            board = boardGrid;
        } else {
            System.out.println("Invalid boardType given.");
            return;
        }
        printHorizontalBorder("=", "edge");
        for (int x = 0; x < board.length; x++) {
            System.out.print("\n||");
            for (int y = 0; y < 9; y++) {
                if (y % 3 == 2) {
                    printBoardLine(board[x][y], "||");
                } else {
                    printBoardLine(board[x][y], "|");
                }
            }
            System.out.print("\n");
            if (x == 8) {
                printHorizontalBorder("=", "edge");
            } else if (x % 3 == 2) {
                printHorizontalBorder("=", "body");
            } else {
                printHorizontalBorder("-", "body");
            }
        }
    }
    // -->

}
