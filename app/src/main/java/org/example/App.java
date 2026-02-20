package org.example;

import java.util.ArrayList;

public class App {

    static ArrayList<Board> storeBoards = new ArrayList<>();

    public static void main(String[] args) {

        // to create sortedBoards.csv
        // csvTools.csvCreateSortedCsv(numOfBoards, asc(true/false));

        int numOfBoardToSolve = 1000;
        boolean printBoard = false;

        // csvParseBoardCustom("customBoards.csv");
        csvTools.csvParseBoard("sortedBoards.csv");

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
            System.out.println(
                    counter
                            + ": "
                            + Tools.isEqual(board.board, board.solution)
                            + ", time taken: "
                            + (gap) / 1_000_000.0
                            + "ms");
            System.out.println(
                    "Rating: " + board.difficultyRating + ", own rating: " + board.numRulesApplied);
            if (Tools.isEqual(board.board, board.solution) == false) {
                allSolved = false;
            }
            if (gap > longestTime) {
                longestTime = gap;
                difficulty = board.difficultyRating;
            }
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
}
