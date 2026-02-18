package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Board {
    // variables --<
    Cell[] board = new Cell[81];
    Cell[] solution = new Cell[81];

    ArrayList<Board> storeBoards = new ArrayList<>();
    String csvGameBoard;
    String csvSolution;
    String csvDifficulty;

    final int ROWLENGTH = 41;

    // -->

    void csvParseBoard(String fileName) { // --<
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] csvBoardInfo = line.split(",");
                storeBoards.add(new Board());
                for (int i = 0; i < 81; i++) {
                    storeBoards.getLast().board[i] = new Cell();
                    storeBoards.getLast().solution[i] = new Cell();
                    if (csvBoardInfo[0].charAt(i) == '.') {
                        storeBoards.getLast().board[i].init();
                    } else {
                        storeBoards
                                .getLast()
                                .board[i]
                                .set(
                                        Integer.parseInt(
                                                Character.toString(csvBoardInfo[0].charAt(i))));
                    }
                    storeBoards
                            .getLast()
                            .solution[i]
                            .set(Integer.parseInt(Character.toString(csvBoardInfo[1].charAt(i))));
                }
                storeBoards.getLast().csvDifficulty = csvBoardInfo[2];
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    // -->

    // printBoard(boardType) --<
    void printHorizontalBorder(String borderType, String part) { // --<
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

    void printBoardLine(Cell cell, String borderType) { // --<
        if (cell.isNotSolved()) {
            System.out.print(" " + " " + " " + borderType);
        } else {
            System.out.print(" " + cell.get() + " " + borderType);
        }
    }

    // -->

    void printBoard(Cell[] board) { // --<
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

    void updateNotes(int currIndex) {
        for (int index : Groups.getRelated(currIndex)) {
            if (board[index].isNotSolved()) {
                board[index].remove(board[currIndex]);
                if (board[index].isSolved()) {
                    updateNotes(index);
                }
            }
        }
    }

    void initNotes() {
        for (int i = 0; i < 81; i++) {
            if (board[i].isSolved()) {
                updateNotes(i);
            }
        }
    }

    void hiddenSingles() {
        for (int[] group : Groups.groups) {
            for (int i = 1; i <= 9; i++) {
                int counter = 0;

                groupLoop:
                for (int index : group) {
                    // note that if solved with a diff number, it would not contain i anyways and
                    // will move on to the next index in the group
                    if (board[index].contains(i)) {
                        if (board[index].isSolved()) {
                            // if already solved, move on to next number
                            break groupLoop;
                        }
                        counter += 1;
                        if (counter > 1) {
                            break groupLoop;
                        }
                    }
                }

                if (counter == 1) {
                    for (int index : group) {
                        if (board[index].contains(i)) {
                            board[index].set(i);
                            updateNotes(index);
                            break;
                        }
                    }
                }
            }
        }
    }

    void hiddenPairs() {
        for (int[] group : Groups.groups) {
            for (int i = 1; i <= 9; i++) {
                for (int j = i + 1; j <= 9; j++) {
                    int counter = 0;
                    boolean error = false;

                    groupLoop:
                    for (int index : group) {
                        if (board[index].isSolved()) {
                            if (board[index].contains(i)) {
                                break groupLoop;
                            } else if (board[index].contains(j)) {
                                break groupLoop;
                            } else {
                                continue;
                            }
                        }
                        if (board[index].contains(i) && board[index].contains(j) == false) {
                            error = true;
                            break groupLoop;
                        }
                        if (board[index].contains(i) == false && board[index].contains(j)) {
                            error = true;
                            break groupLoop;
                        }
                        if (board[index].contains(i) && board[index].contains(j)) {
                            counter += 1;
                            if (counter > 2) {
                                break groupLoop;
                            }
                        }
                    }

                    if (counter == 2 && error == false) {
                        for (int index : group) {
                            counter = 0;
                            if (board[index].contains(i)) {
                                board[index].set(Tools.generatePairBin(i, j), false);
                                counter += 1;
                                if (counter == 2) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    void obviousPairs() {}

    boolean isValid() {
        return true;
    }

    void main() {
        csvParseBoard("sortedBoards.csv");
        for (Board obj : storeBoards) {
            printBoard(obj.board);
        }
    }
}
