package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class Board {
    int[][] gameBoard;
    int[][] gameBoardSaveState = new int[9][9];
    ArrayList<int[][]> gameBoardTrySaveState = new ArrayList<int[][]>();
    int[][] solution;
    ArrayList<ArrayList<ArrayList<Integer>>> possibleBoard = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> possibleBoardTrySaveState =
            new ArrayList<>();
    HashMap<Integer, Integer> numCounter = new HashMap<>();
    final int empty = 0;
    final int rowLength = 41;

    // updateBoardGrid(num,int,row)
    // --<
    void updateBoardGrid(int num, int row, int column) {
        gameBoard[row][column] = num;
        possibleBoard.get(row).get(column).clear();
    }

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
            int num = possibleBoard.get(row).get(column).getFirst();
            updateBoardGrid(num, row, column);
            updatePossibleBoard(num, row, column);
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
                    updatePossibleBoard(num, row, column);
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
                    updatePossibleBoard(num, row, column);
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
                        updatePossibleBoard(num, xCoord, yCoord);
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

    // attemptFullSolve
    // --<
    void attemptFullSolve() {
        do {
            copyTwoDimensionalArray(gameBoard, gameBoardSaveState);
            checkBoardForSolves();
        } while (Arrays.deepEquals(gameBoard, gameBoardSaveState) == false);
    }

    // -->

    // checkBoardState
    // --<

    // boardIsFullySolved
    // --<
    boolean boardIsFullySolved() {
        if (boardIsFull() && boardStateIsValid()) {
            return true;
        } else {
            return false;
        }
    }

    // -->

    // boardIsFull
    // --<
    boolean boardIsFull() {
        for (int[] row : gameBoard) {
            for (int num : row) {
                if (num == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // -->

    // checkIfRowsAreValid
    // --<
    boolean rowsAreValid() {
        for (int row = 0; row < 9; row++) {
            initNumCounter();
            for (int column = 0; column < 9; column++) {
                int num = gameBoard[row][column];
                if (num == 0) {
                    continue;
                } else {
                    numCounter.put(num, numCounter.get(num) + 1);
                    if (numCounter.get(num) == 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // -->

    // checkIfColumnsAreValid
    // --<
    boolean columnsAreValid() {
        for (int column = 0; column < 9; column++) {
            initNumCounter();
            for (int row = 0; row < 9; row++) {
                int num = gameBoard[row][column];
                if (num == 0) {
                    continue;
                } else {
                    numCounter.put(num, numCounter.get(num) + 1);
                    if (numCounter.get(num) == 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // -->

    // checkIfBoxIsValid
    // --<
    boolean boxIsValid(int xRightCoord, int yTopCoord) {
        initNumCounter();
        for (int xCoord = xRightCoord - 3; xCoord < xRightCoord; xCoord++) {
            for (int yCoord = yTopCoord - 3; yCoord < yTopCoord; yCoord++) {
                int num = gameBoard[xCoord][yCoord];
                if (num == 0) {
                    continue;
                } else {
                    numCounter.put(num, numCounter.get(num) + 1);
                    if (numCounter.get(num) == 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // -->

    // checkIfBoxesAreValid
    // --<
    boolean boxesAreValid() {
        for (int x = 3; x <= 9; x += 3) {
            for (int y = 3; y <= 9; y += 3) {
                if (boxIsValid(x, y) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    // -->

    // boardStateIsValid
    // --<
    boolean boardStateIsValid() {
        if (rowsAreValid() && columnsAreValid() && boxesAreValid()) {
            return true;
        } else {
            return false;
        }
    }

    // }
    // -->
    // -->
    //
    void saveState() {
        gameBoardTrySaveState.add(new int[9][9]);
        copyTwoDimensionalArray(gameBoard, gameBoardTrySaveState.getLast());
        possibleBoardTrySaveState.add(new ArrayList<>());
        copyThreeDimensionalArrayList(possibleBoard, possibleBoardTrySaveState.getLast());
    }

    // guessAndCheckSolve
    // --<
    void guessAndCheckSolve() {
        // appends the current gameBoard and possibleBoard to their Saves
        saveState();
        /**
         * trying the first possibleNum it comes across as the actual number in the gameBoard,
         * continues looping through possibleNum to try
         */
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                ArrayList<ArrayList<ArrayList<Integer>>> possibleBoardTemp = new ArrayList<>();
                copyThreeDimensionalArrayList(
                        possibleBoardTrySaveState.getLast(), possibleBoardTemp);
                Iterator<Integer> it = possibleBoardTemp.get(row).get(column).iterator();
                while (it.hasNext()) {
                    Integer possibleNum = it.next();

                    updateBoardGrid(possibleNum, row, column);
                    updatePossibleBoard(possibleNum, row, column);
                    attemptFullSolve();

                    /**
                     * consider the third recursion. board is fully solved. the guessAndCheck is now
                     * returned.
                     */
                    if (boardStateIsValid()) {
                        if (boardIsFull()) {
                            return;
                        } else {
                            guessAndCheckSolve();
                            /**
                             * we now land back here into the second recursion. if we do nothing, it
                             * will exit the if else, and then re-enter the for loop, which we dont
                             * want. so we need to find a way to go back to the first recursion. Of
                             * course if it's not fully solved in the third recursion and it
                             * reentered the first recursion, then we want it to enter the for loop
                             * again. If it's solved, to prevent it from entering the for loop, we
                             * need to return to escape the function.
                             */
                            if (boardIsFull()) {
                                return;
                            }
                            /**
                             * This sends us back to the first recursion, but also back to this
                             * point, at which it will return and the recursion will end. Also don't
                             * have to check that it is invalid, as the only 2 ways we reach here is
                             * one, it gets returned from a valid and full state, or two, the above
                             * recursion finishes, at which point the gameBoard will load an earlier
                             * state which will not be full.
                             */
                        }
                    } else {
                        gameBoard = gameBoardTrySaveState.getLast();
                        possibleBoard = possibleBoardTrySaveState.getLast();
                    }
                }
            }
        }
        /**
         * lets say this is the third recursion. all possibleNum has been tried in this state, and
         * they all lead to invalid states. now we need to backtrack. we need to load a save state
         * that is made in the second recursion. so we remove the last save state, and load the
         * second last one. And then the function ends, so it will push it back into the second
         * recursions's for loop and the cycle continues.
         */
        gameBoardTrySaveState.removeLast();
        possibleBoard = possibleBoardTrySaveState.removeLast();
        gameBoard = gameBoardTrySaveState.getLast();
        possibleBoard = possibleBoardTrySaveState.getLast();
    }

    // -->

    void solve() {
        // init solve
        System.out.println("This is the initial board.");
        printBoard("game");
        initPossibleBoard();
        firstRoundSolve();

        // iterative solve
        attemptFullSolve();

        // guessAndCheckSolve
        guessAndCheckSolve();
        printBoard("game");
    }

    // eliminate possibleNum due to a square being found
    // --<
    // updatePossibleBoard
    // --<
    void updatePossibleBoard(int num, int row, int column) {
        solveRow(num, row);
        solveColumn(num, column);
        solveBox(num, row, column);
    }

    // -->

    // solve(Row/Column/Box)
    // --<
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
    // -->
    // -->

    // firstRoundSolve
    // --<
    void firstRoundSolve() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int num = gameBoard[row][column];
                if (num == 0) {
                    continue;
                } else {
                    updatePossibleBoard(num, row, column);
                }
            }
        }
    }

    // -->

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
        } else if (boardType.equals("gameBoardSaveState")) {
            board = gameBoardSaveState;
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
    //

    // copyTwoDimensionalArray
    // --<
    void copyTwoDimensionalArray(int[][] original, int[][] copy) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                copy[row][column] = original[row][column];
            }
        }
    }

    // -->

    // copyThreeDimensionalArrayList
    // --<
    void copyThreeDimensionalArrayList(
            ArrayList<ArrayList<ArrayList<Integer>>> original,
            ArrayList<ArrayList<ArrayList<Integer>>> copy) {
        for (int row = 0; row < 9; row++) {
            copy.add(new ArrayList<>());
            for (int column = 0; column < 9; column++) {
                copy.get(row).add(new ArrayList<>());
                Iterator<Integer> it = original.get(row).get(column).iterator();
                while (it.hasNext()) {
                    Integer num = it.next();
                    copy.get(row).get(column).add(num);
                }
            }
        }
    }
    // -->
}
