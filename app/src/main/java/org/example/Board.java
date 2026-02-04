package org.example;

import java.util.ArrayList;
import java.util.Iterator;

class Board {
    static int[][] boardGrid;
    static int[][] solution;
    static ArrayList<ArrayList<Integer>> squaresToBeChecked = new ArrayList<>();
    static ArrayList<ArrayList<ArrayList<Integer>>> possibleBoard = new ArrayList<>();
    static final int empty = 0;
    static final int rowLength = 41;

    static void checkIfSquareIsSolved(int row, int column) {
        if (possibleBoard.get(row).get(column).size() == 1) {
            boardGrid[row][column] = possibleBoard.get(row).get(column).getFirst();
            squaresToBeChecked.add(new ArrayList<>());
            squaresToBeChecked.getLast().add(row);
            squaresToBeChecked.getLast().add(column);
            possibleBoard.get(row).get(column).removeFirst();
        }
    }

    static void solveRow(int num, int row) {

        // possibleBoard.get(row).get(square = column num).get(possibleNum)
        // get row from possibleBoard
        // iterate through squares in row
        // check if the possibleNum of the square has the number in the actual board
        // if that number is there, remove the number

        for (int column = 0; column < 9; column++) {
            Iterator<Integer> it = possibleBoard.get(row).get(column).iterator();
            while (it.hasNext()) {
                Integer possibleNum = it.next();
                if (possibleNum == num) {
                    it.remove();
                    checkIfSquareIsSolved(row, column);
                    break;
                }
            }
        }
    }

    static void solveColumn(int num, int column) {

        // possibleBoard.get(row).get(square = column num).get(possibleNum)

        for (int row = 0; row < 9; row++) {
            Iterator<Integer> it = possibleBoard.get(row).get(column).iterator();
            while (it.hasNext()) {
                Integer possibleNum = it.next();
                if (possibleNum == num) {
                    it.remove();
                    checkIfSquareIsSolved(row, column);
                    break;
                }
            }
        }
    }

    static void solveBox(int num, int row, int column) {

        // possibleBoard.get(row).get(square = column num).get(possibleNum)
        // if (row < 3) {
        //    if (column < 3) {
        //		for (int)

        //    } else if (column < 6) {

        //    } else {

        //    }

        // } else if (row < 6) {
        //    if (column < 3) {

        //    } else if (column < 6) {

        //    } else {

        //    }
        // } else {
        //    if (column < 3) {

        //    } else if (column < 6) {

        //    } else {

        //    }
        // }
    }

    static void Solve() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int square = boardGrid[row][column];
                if (square == empty) {
                    continue;
                } else {
                    solveRow(square, row);
                    solveColumn(square, column);
                    solveBox(square, row, column);
                }
            }
        }
    }

    // initPossibleBoard()
    // --<
    static void initPossibleBoard() {
        for (int row = 0; row < 9; row++) {
            possibleBoard.add(new ArrayList<>());
            for (int column = 0; column < 9; column++) {
                possibleBoard.get(row).add(new ArrayList<>());
                int square = boardGrid[row][column];
                if (square == empty) {
                    for (int possibleNum = 1; possibleNum <= 9; possibleNum++) {
                        possibleBoard.get(row).get(column).add(possibleNum);
                    }
                }
            }
        }
    }

    // -->

    // printBoard(boardType)
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
