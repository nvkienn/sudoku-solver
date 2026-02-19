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

    ArrayList<Board> queue = new ArrayList<>();

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

    void csvParseBoardCustom(String fileName) { // --<
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                storeBoards.add(new Board());
                for (int i = 0; i < 81; i++) {
                    storeBoards.getLast().board[i] = new Cell();
                    if (line.charAt(i) == '.') {
                        storeBoards.getLast().board[i].init();
                    } else {
                        storeBoards
                                .getLast()
                                .board[i]
                                .set(Integer.parseInt(Character.toString(line.charAt(i))));
                    }
                }
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

    void obviousPairs() {
        for (int[] group : Groups.groups) {
            for (int i = 1; i <= 9; i++) {
                for (int j = i + 1; j <= 9; j++) {
                    int counter = 0;
                    int index1 = -1;
                    int index2 = -1;

                    groupLoop:
                    for (int index : group) {
                        if (board[index].isSolved()) {
                            if (board[index].get() == i || board[index].get() == j) {
                                break groupLoop;
                            } else {
                                continue;
                            }
                        }
                        if (board[index].contains(i)
                                && board[index].contains(j)
                                && board[index].size() == 2) {
                            counter += 1;
                            if (counter == 1) {
                                index1 = index;
                            } else if (counter == 2) {
                                index2 = index;
                                break groupLoop;
                            }
                        }
                    }

                    if (counter == 2) {
                        for (int index : group) {
                            if (index == index1 || index == index2) {
                                continue;
                            } else if (board[index].contains(i) || board[index].contains(j)) {
                                board[index].remove(Tools.generatePairBin(i, j));
                                if (board[index].isSolved()) {
                                    updateNotes(index);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    void pointingPairs() {
        for (int[] box : Groups.groups) {
            for (int i = 1; i <= 9; i++) {
                int counter = 0;
                int index1 = -1;
                int index2 = -1;
                int index3 = -1;

                boxLoop:
                for (int index : box) {
                    if (board[index].isSolved()) {
                        if (board[index].get() == i) {
                            break boxLoop;
                        }
                        continue;
                    }
                    if (board[index].contains(i)) {
                        counter += 1;
                        switch (counter) {
                            case 1:
                                index1 = index;
                                break;
                            case 2:
                                index2 = index;
                                break;
                            case 3:
                                index3 = index;
                                break;
                            case 4:
                                break boxLoop;
                        }
                    }
                }
                if (counter == 2) {
                    if (Tools.isSameRow(index1, index2)) {
                        for (int index : Groups.rows[Tools.getRow(index1)]) {
                            if (index == index1 || index == index2) {
                                continue;
                            }
                            if (board[index].contains(i)) {
                                board[index].remove(Tools.numToBin(i));
                                if (board[index].isSolved()) {
                                    updateNotes(index);
                                }
                            }
                        }
                    } else if (Tools.isSameColumn(index1, index2)) {
                        for (int index : Groups.columns[Tools.getColumn(index1)]) {
                            if (index == index1 || index == index2) {
                                continue;
                            }
                            if (board[index].contains(i)) {
                                board[index].remove(Tools.numToBin(i));
                                if (board[index].isSolved()) {
                                    updateNotes(index);
                                }
                            }
                        }
                    }

                } else if (counter == 3) {
                    if (Tools.isSameRow(index1, index2, index3)) {
                        for (int index : Groups.rows[Tools.getRow(index1)]) {
                            if (index == index1 || index == index2 || index == index3) {
                                continue;
                            }
                            if (board[index].contains(i)) {
                                board[index].remove(Tools.numToBin(i));
                                if (board[index].isSolved()) {
                                    updateNotes(index);
                                }
                            }
                        }
                    } else if (Tools.isSameColumn(index1, index2, index3)) {
                        for (int index : Groups.columns[Tools.getColumn(index1)]) {
                            if (index == index1 || index == index2 || index == index3) {
                                continue;
                            }
                            if (board[index].contains(i)) {
                                board[index].remove(Tools.numToBin(i));
                                if (board[index].isSolved()) {
                                    updateNotes(index);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    void applyRules() {
        Board copyBoard = new Board();
        do {
            copyBoard.board = Tools.copyBoard(this.board);
            this.hiddenSingles();
            // applying these rules slows the solving down???
            // this.obviousPairs();
            // this.hiddenPairs();
            // this.pointingPairs();
        } while (Tools.isEqual(copyBoard.board, this.board) == false);
    }

    boolean isValid() { // --<
        for (int[] group : Groups.groups) {
            for (int i = 1; i <= 9; i++) {
                int counter = 0;
                for (int index : group) {
                    if (board[index].isSolved()) {
                        if (board[index].get() == i) {
                            counter += 1;
                            if (counter > 1) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    // -->

    boolean isFull() {
        for (Cell cell : board) {
            if (cell.isNotSolved()) {
                return false;
            }
        }
        return true;
    }

    int findLeastNotesIndex() {
        int index = 0;
        int size = 9;
        for (int i = 0; i < 81; i++) {
            if (board[i].isSolved()) {
                continue;
            }
            if (board[i].size() == 2) {
                return i;
            }
            if (board[i].size() < size) {
                index = i;
                size = board[i].size();
            }
        }
        return index;
    }

    void guessSolve() {
        queue.add(new Board());
        queue.getFirst().board = Tools.copyBoard(board);
        do {
            int index = queue.getFirst().findLeastNotesIndex();
            for (int i = 1; i <= 9; i++) {
                if (queue.getFirst().board[index].contains(i)) {

                    queue.add(new Board());
                    queue.getLast().board = Tools.copyBoard(queue.getFirst().board);
                    queue.getLast().board[index].set(i);
                    queue.getLast().updateNotes(index);
                    queue.getLast().applyRules();

                    if (queue.getLast().isFull() && queue.getLast().isValid()) {
                        board = Tools.copyBoard(queue.getLast().board);
                        return;
                    }
                }
            }
            queue.removeFirst();
        } while (queue.isEmpty() == false);
    }

    void main() {
        // csvParseBoardCustom("customBoards.csv");
        csvParseBoard("sortedBoards.csv");
        long totalTime = 0;
        long longestTime = 0;
        int difficulty = 0;
        int counter = 0;
        boolean allSolved = true;
        for (Board board : storeBoards) {
            counter += 1;
            System.out.println(
                    "------------------------------------------------------------------------------");
            // System.out.println("initial");
            // printBoard(board.board);
            long start = System.nanoTime();
            board.initNotes();
            board.applyRules();
            if (board.isFull() == false) {
                board.guessSolve();
            }
            long stop = System.nanoTime();
            long gap = stop - start;
            totalTime += gap;
            // System.out.println("after");
            // printBoard(board.board);
            // System.out.println(board.isValid());
            // Tools.printBoardIndex();
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
                difficulty = Integer.parseInt(board.csvDifficulty);
            }
        }
        System.out.println(
                "------------------------------------------------------------------------------");
        System.out.println("Average time taken: " + (totalTime / 1000 / 1_000_000.0) + "ms");
        System.out.println("Longest time taken: " + longestTime / 1_000_000.0 + "ms");
        System.out.println("Difficulty of longest to solve baord: " + difficulty);
        String message = (allSolved) ? "All solved" : "Failed to solve all";
        System.out.println(message);
    }
}
