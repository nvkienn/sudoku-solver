package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Board {
    int[][] gameBoard;
    int[][] solution;
    ArrayList<ArrayList<Integer>> squaresToBeChecked = new ArrayList<>();
    ArrayList<ArrayList<Integer>> squaresToBeCheckedTemp = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<Integer>>> possibleBoard = new ArrayList<>();
    HashMap<Integer, Integer> numCounter = new HashMap<>();
    final int empty = 0;
    final int rowLength = 41;

    // UpdateBoard
    // --<
    // updateBoardGrid(row,column)
    // for if size of possibleNum for the square is 1
    // --<
    void updateBoardGrid(int row, int column) {
        gameBoard[row][column] = possibleBoard.get(row).get(column).getFirst();
        squaresToBeChecked.add(new ArrayList<>());
        squaresToBeChecked.getLast().add(row);
        squaresToBeChecked.getLast().add(column);
        possibleBoard.get(row).get(column).clear();
    }

    // -->

    // updateBoardGrid(num,int,row)
    // for if the square is the only square where the number can be
    // --<
    void updateBoardGrid(int num, int row, int column) {
        gameBoard[row][column] = num;
        squaresToBeChecked.add(new ArrayList<>());
        squaresToBeChecked.getLast().add(row);
        squaresToBeChecked.getLast().add(column);
        possibleBoard.get(row).get(column).clear();
    }

    // -->
    // -->

    // solveRow
    // --<
    void solveRow(int num, int row) {

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

    // -->

    // solveColumn
    // --<
    void solveColumn(int num, int column) {

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

    // -->

    // solveBoxMethod
    // --<
    void solveBoxMethod(int num, int xRightCoord, int yTopCoord) {
        for (int xCoord = xRightCoord - 3; xCoord < xRightCoord; xCoord++) {
            for (int yCoord = yTopCoord - 3; yCoord < yTopCoord; yCoord++) {
                Iterator<Integer> it = possibleBoard.get(xCoord).get(yCoord).iterator();
                while (it.hasNext()) {
                    Integer possibleNum = it.next();
                    if (possibleNum == num) {
                        it.remove();
                        checkIfSquareIsSolved(xCoord, yCoord);
                        break;
                    }
                }
            }
        }
    }

    // -->

    // solveBox
    // --<
    void solveBox(int num, int row, int column) {

        // possibleBoard.get(row).get(square = column num).get(possibleNum)
        if (row < 3) {
            if (column < 3) {
                solveBoxMethod(num, 3, 3);
            } else if (column < 6) {
                solveBoxMethod(num, 3, 6);
            } else {
                solveBoxMethod(num, 3, 9);
            }
        } else if (row < 6) {
            if (column < 3) {
                solveBoxMethod(num, 6, 3);
            } else if (column < 6) {
                solveBoxMethod(num, 6, 6);
            } else {
                solveBoxMethod(num, 6, 9);
            }
        } else {
            if (column < 3) {
                solveBoxMethod(num, 9, 3);

            } else if (column < 6) {
                solveBoxMethod(num, 9, 6);

            } else {
                solveBoxMethod(num, 9, 9);
            }
        }
    }

    // -->

    // firstRoundSolve
    // --<
    void firstRoundSolve() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int square = gameBoard[row][column];
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

    // -->

    // GuaranteeSolves
    // --<
    // GuaranteedRow
    // --<
    void guaranteedSolveForRow(int num, int row) {
        for (int column = 0; column < 9; column++) {
            Iterator<Integer> it = possibleBoard.get(row).get(column).iterator();
            while (it.hasNext()) {
                Integer possibleNum = it.next();
                if (possibleNum == num) {
                    updateBoardGrid(num, row, column);
                    return;
                } else if (possibleNum > num) {
                    break;
                }
            }
        }
    }

    // -->

    // GuaranteedColumn
    // --<
    void guaranteedSolveForColumn(int num, int column) {
        for (int row = 0; row < 9; row++) {
            Iterator<Integer> it = possibleBoard.get(row).get(column).iterator();
            while (it.hasNext()) {
                Integer possibleNum = it.next();
                if (possibleNum == num) {
                    updateBoardGrid(num, row, column);
                    return;
                } else if (possibleNum > num) {
                    break;
                }
            }
        }
    }

    // -->

    // GuaranteedBox
    // --<
    void guaranteedSolveForBox(int num, int xRightCoord, int yTopCoord) {
        for (int xCoord = xRightCoord - 3; xCoord < xRightCoord; xCoord++) {
            for (int yCoord = yTopCoord - 3; yCoord < yTopCoord; yCoord++) {
                Iterator<Integer> it = possibleBoard.get(xCoord).get(yCoord).iterator();
                while (it.hasNext()) {
                    Integer possibleNum = it.next();
                    if (possibleNum == num) {
                        updateBoardGrid(num, xCoord, yCoord);
                        return;
                    } else if (possibleNum > num) {
                        break;
                    }
                }
            }
        }
    }

    // -->
    // -->

    // Check If Solved
    // --<
    // initNumCounter
    // --<
    void initNumCounter() {
        numCounter.clear();
        for (int i = 1; i <= 9; i++) {
            numCounter.put(i, 0);
        }
    }

    // -->

    // CheckRow
    // --<
    void checkIfRowIsSolved(int row) {
        initNumCounter();
        for (int column = 0; column < 9; column++) {
            Iterator<Integer> it = possibleBoard.get(row).get(column).iterator();
            while (it.hasNext()) {
                Integer possibleNum = it.next();
                // adds 1 to the count of possibleNum
                numCounter.put(possibleNum, numCounter.get(possibleNum) + 1);
            }
        }
        for (int num : numCounter.keySet()) {
            if (numCounter.get(num) == 1) {
                guaranteedSolveForRow(num, row);
            }
        }
    }

    // -->

    // CheckColumn
    // --<
    void checkIfColumnIsSolved(int column) {
        initNumCounter();
        for (int row = 0; row < 9; row++) {
            Iterator<Integer> it = possibleBoard.get(row).get(column).iterator();
            while (it.hasNext()) {
                Integer possibleNum = it.next();
                // adds 1 to the count of possibleNum
                numCounter.put(possibleNum, numCounter.get(possibleNum) + 1);
            }
        }
        for (int num : numCounter.keySet()) {
            if (numCounter.get(num) == 1) {
                guaranteedSolveForColumn(num, column);
            }
        }
    }

    // -->

    // CheckBox
    // --<
    void checkIfBoxIsSolved(int xRightCoord, int yTopCoord) {
        initNumCounter();
        for (int xCoord = xRightCoord - 3; xCoord < xRightCoord; xCoord++) {
            for (int yCoord = yTopCoord - 3; yCoord < yTopCoord; yCoord++) {
                Iterator<Integer> it = possibleBoard.get(xCoord).get(yCoord).iterator();
                while (it.hasNext()) {
                    Integer possibleNum = it.next();
                    numCounter.put(possibleNum, numCounter.get(possibleNum) + 1);
                }
            }
        }
        for (int num : numCounter.keySet()) {
            if (numCounter.get(num) == 1) {
                guaranteedSolveForBox(num, xRightCoord, yTopCoord);
            }
        }
    }

    // -->

    // CheckSquare
    // --<
    void checkIfSquareIsSolved(int row, int column) {
        if (possibleBoard.get(row).get(column).size() == 1) {
            updateBoardGrid(row, column);
        }
    }

    // -->
    // -->

    // checkAllforSolved
    // --<
    void checkAllRowsIfSolved() {
        for (int row = 0; row < 9; row++) {
            checkIfRowIsSolved(row);
        }
    }

    void checkAllColumnsIfSolved() {
        for (int column = 0; column < 9; column++) {
            checkIfColumnIsSolved(column);
        }
    }

    void checkAllBoxesIfSolved() {
        for (int x = 3; x <= 9; x += 3) {
            for (int y = 3; y <= 9; y += 3) {
                checkIfBoxIsSolved(x, y);
            }
        }
    }

    void checkBoardForSolves() {
        checkAllRowsIfSolved();
        checkAllColumnsIfSolved();
        checkAllBoxesIfSolved();
    }

    // -->

    // goThroughSquaresToBeChecked
    // --<
    void goThroughSquaresToBeChecked() {
        squaresToBeCheckedTemp = squaresToBeChecked;
        squaresToBeChecked.clear();
        Iterator<ArrayList<Integer>> it = squaresToBeCheckedTemp.iterator();
        while (it.hasNext()) {
            ArrayList<Integer> coordinates = it.next();
            int row = coordinates.get(0);
            int column = coordinates.get(1);
            int num = gameBoard[row][column];
            solveRow(num, row);
            solveColumn(num, column);
            solveBox(num, row, column);
            it.remove();
        }
    }

    // -->

    void attemptFullSolve() {
        while (squaresToBeChecked.size() > 0) {
            goThroughSquaresToBeChecked();
            checkBoardForSolves();
        }
    }

    void solve() {
        // init solve
        printBoard("game");
        initPossibleBoard();
        firstRoundSolve();
        printBoard("game");
        printBoard("solution");
        checkAllColumnsIfSolved();

        printBoard("game");
        printBoard("solution");

        // iterative solve
        // attemptFullSolve();
        // printBoard("game");
    }

    // initPossibleBoard()
    // --<
    void initPossibleBoard() {
        for (int row = 0; row < 9; row++) {
            possibleBoard.add(new ArrayList<>());
            for (int column = 0; column < 9; column++) {
                possibleBoard.get(row).add(new ArrayList<>());
                int square = gameBoard[row][column];
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
    void printHorizontalBorder(String borderType, String part) {
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

    void printBoardLine(int square, String borderType) {
        if (square == empty) {
            System.out.print(" " + " " + " " + borderType);
        } else {
            System.out.print(" " + square + " " + borderType);
        }
    }

    void printBoard(String boardType) {
        int[][] board;
        if (boardType.equals("solution")) {
            board = solution;
        } else if (boardType.equals("game")) {
            board = gameBoard;
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

}
