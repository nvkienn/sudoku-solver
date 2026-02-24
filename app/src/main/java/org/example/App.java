package org.example;

import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        solve(1000, false, false);
        // System.out.println(testHiddenSingles());
        // System.out.println(testobviousPairs());
        // System.out.println(testHiddenPairs());
    }

    static ArrayList<Board> storeBoards = new ArrayList<>();

    /**
     * printBoard = true // print inital and final state of board, custom = true // read Boards from
     * customBoards.csv instead of sortedBoards.csv
     */
    static void solve(int numOfBoardToSolve) {
        solve(numOfBoardToSolve, false, false);
    }

    /**
     * printBoard = true // print inital and final state of board, custom = true // read Boards from
     * customBoards.csv instead of sortedBoards.csv
     */
    static void solve(int numOfBoardToSolve, boolean printBoard, boolean custom) {

        // to create sortedBoards.csv
        // csvTools.csvCreateSortedCsv(numOfBoards, asc(true/false));

        if (custom == true) {
            csvTools.csvParseBoardCustom("customBoards.csv");
            numOfBoardToSolve = 1;
        } else {
            csvTools.csvParseBoard("sortedBoards.csv");
        }

        long totalTime = 0;
        long longestTime = 0;
        int difficulty = 0;
        int ownDifficulty = 0;
        int counter = 0;
        int numberNeededToGuess = 0;
        int totalRating = 0;
        boolean allSolved = true;

        for (Board board : storeBoards) {
            counter += 1;
            System.out.println(
                    "------------------------------------------------------------------------------");
            if (printBoard == true) {
                Tools.printBoard(board.board);
            }
            long start = System.nanoTime();

            board.solve();

            long stop = System.nanoTime();
            long gap = stop - start;
            totalTime += gap;
            totalRating += board.numRulesApplied;
            if (printBoard == true) {
                Tools.printBoard(board.board);
            }
            if (custom == true) {
                System.out.println(
                        counter
                                + ": "
                                + board.isValid()
                                + ", time taken: "
                                + (gap) / 1_000_000.0
                                + "ms");
                if (board.isValid() == false) {
                    allSolved = false;
                }
            } else {
                System.out.println(
                        counter
                                + ": "
                                + Tools.isEqual(board.board, board.solution)
                                + ", time taken: "
                                + (gap) / 1_000_000.0
                                + "ms");
                if (Tools.isEqual(board.board, board.solution) == false) {
                    allSolved = false;
                }
                if (gap > longestTime) {
                    longestTime = gap;
                    difficulty = board.difficultyRating;
                }
            }
            System.out.println(
                    "Rating: " + board.difficultyRating + ", own rating: " + board.numRulesApplied);
            if (board.requiredToGuess == true) {
                numberNeededToGuess += 1;
            }
            if (board.numRulesApplied > ownDifficulty) {
                ownDifficulty = board.numRulesApplied;
            }
            if (counter >= numOfBoardToSolve) {
                System.out.println(
                        "------------------------------------------------------------------------------");
                System.out.println(
                        "Average time taken: "
                                + (totalTime / numOfBoardToSolve / 1_000_000.0)
                                + "ms");
                System.out.println("Longest time taken: " + longestTime / 1_000_000.0 + "ms");
                System.out.println("Difficulty of longest to solve baord: " + difficulty);
                System.out.println(
                        "Average number of times rules applied: "
                                + totalRating / numOfBoardToSolve);
                System.out.println("Maximum number of rules applied: " + ownDifficulty);
                System.out.println(
                        "Number of boards that required guessing: " + numberNeededToGuess);
                String message = (allSolved) ? "All solved" : "Failed to solve all";
                System.out.println(message);
                return;
            }
        }
    }

    static String testHiddenSingles() {
        csvTools.csvParseBoardCustom("customBoards.csv");
        Board board = storeBoards.get(0);
        board.initNotes();
        board.hiddenSingles();
        Cell cell1 = board.board[56];
        if (cell1.isSolved() && cell1.contains(1)) {
            return "hiddenSingles() works";
        }
        return "hiddenSingles() does not work";
    }

    static String testobviousPairs() {
        csvTools.csvParseBoardCustom("customBoards.csv");
        Board board = storeBoards.get(1);
        board.initNotes();
        board.obviousPairs();
        Cell cell1 = board.board[3];
        Cell cell2 = board.board[14];
        if (cell1.isSolved() && cell1.contains(6) && cell2.isSolved() && cell2.contains(4)) {
            return "obviousPairs() works";
        }
        return "obviousPairs() does not work";
    }

    static String testHiddenPairs() {
        csvTools.csvParseBoardCustom("customBoards.csv");
        Board board = storeBoards.get(0);
        board.initNotes();
        board.hiddenPairs();
        if (board.board[45].data == Tools.generatePairBin(2, 9)
                && board.board[52].data == Tools.generatePairBin(2, 9)) {
            return "HiddenPairs() works";
        }
        return "HiddenPairs() does not work";
    }
}
