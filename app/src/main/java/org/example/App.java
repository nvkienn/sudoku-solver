package org.example;

import java.util.ArrayList;

public class App {

    static String HELP_TEXT =
            "\nSudoku-Solver\n\n"
                    + "	USAGE: java -jar solver.jar <boards-file>\n\n"
                    + "	Note: solver.jar takes exactly one file.\n";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(HELP_TEXT);
        } else if (args.length == 1) {
            solve(args[0]);
        } else {
            System.out.println("ERROR: solver.jar only takes exactly one file.");
        }
        // testSolve();
        // System.out.println(testHiddenSingles());
        // System.out.println(testobviousPairs());
        // System.out.println(testHiddenPairs());
        // System.out.println(testPointingPairs());
    }

    static ArrayList<Board> storeBoards = new ArrayList<>();

    static void solve(String fileName) {
        solve(fileName, 0, true, true);
    }

    static void testSolve() {
        solve("", 1000, false, false);
    }

    static void solve(String fileName, int numOfBoardToSolve, boolean printBoard, boolean custom) {

        // to create sortedBoards.csv
        // csvTools.csvCreateSortedCsv(numOfBoards, asc(true/false));

        if (custom == true) {
            csvTools.csvParseBoardCustom(fileName);
            numOfBoardToSolve = storeBoards.size();
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
                System.out.println(counter + ": " + "time taken: " + (gap) / 1_000_000.0 + "ms");
                System.out.println("Number of times rules applied: " + board.numRulesApplied);
                if (gap > longestTime) {
                    longestTime = gap;
                }
            } else {
                String result = Tools.isEqual(board.board, board.solution) ? "solved" : "unsolved";
                System.out.println(
                        counter + ": " + result + ", time taken: " + (gap) / 1_000_000.0 + "ms");
                System.out.println(
                        "Rating: "
                                + board.difficultyRating
                                + ", own rating: "
                                + board.numRulesApplied);
                if (Tools.isEqual(board.board, board.solution) == false) {
                    allSolved = false;
                }
                if (gap > longestTime) {
                    longestTime = gap;
                    difficulty = board.difficultyRating;
                }
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
                System.out.println("SUMMARY:");
                System.out.println(
                        "Average time taken: "
                                + (totalTime / numOfBoardToSolve / 1_000_000.0)
                                + "ms");
                System.out.println("Longest time taken: " + longestTime / 1_000_000.0 + "ms");
                if (custom == false) {
                    System.out.println("Difficulty of longest to solve baord: " + difficulty);
                }
                System.out.println(
                        "Average number of times rules applied: "
                                + totalRating / numOfBoardToSolve);
                System.out.println("Maximum number of rules applied: " + ownDifficulty);
                System.out.println("Number of boards solved: " + numOfBoardToSolve);
                System.out.println(
                        "Number of boards that required guessing: " + numberNeededToGuess);
                String message = (allSolved) ? "All solved" : "Failed to solve all";
                System.out.println(message);
                return;
            }
        }
    }

    static String testHiddenSingles() {
        csvTools.csvParseBoardCustom("testBoards.csv");
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
        csvTools.csvParseBoardCustom("testBoards.csv");
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
        csvTools.csvParseBoardCustom("testBoards.csv");
        Board board = storeBoards.get(0);
        board.initNotes();
        board.hiddenPairs();
        if (board.board[45].data == Tools.generatePairBin(2, 9)
                && board.board[52].data == Tools.generatePairBin(2, 9)) {
            return "hiddenPairs() works";
        }
        return "hiddenPairs() does not work";
    }

    static String testPointingPairs() {
        csvTools.csvParseBoardCustom("testBoards.csv");
        Board board = storeBoards.get(2);
        board.initNotes();
        board.pointingPairs();
        if (board.board[63].contains(4) == false
                && board.board[66].contains(6) == false
                && board.board[67].contains(6) == false
                && board.board[68].contains(6) == false
                && board.board[57].contains(1) == false
                && board.board[59].contains(1) == false) {
            return "pointingPairs() works";
        }
        return "pointingPairs() does not work";
    }
}
