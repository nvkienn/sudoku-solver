package org.example;

import java.util.ArrayList;
import java.util.HashMap;

class Board {
    // attributes --<
    Cell[] board = new Cell[81];
    Cell[] solution = new Cell[81];

    int difficultyRating;
    int numRulesApplied = 0;
    boolean requiredToGuess = false;

    // attributes to assist in creating sortedBoards.csv
    String csvGameBoard;
    String csvSolution;
    String csvDifficulty;

    // -->

    void updateNotes(int currIndex) { // --<
        for (int index : Groups.getRelated(currIndex)) {
            if (board[index].isNotSolved()) {
                board[index].remove(board[currIndex]);
                if (board[index].isSolved()) {
                    updateNotes(index);
                }
            }
        }
    }

    // -->

    void initNotes() { // --<
        for (int i = 0; i < 81; i++) {
            if (board[i].isSolved()) {
                updateNotes(i);
            }
        }
    }

    // -->

    void hiddenSingles() { // --<
        for (int[] group : Groups.groups) {
            int[] counter = new int[9];
            for (int index : group) {
                if (board[index].isSolved()) {
                    continue;
                }
                for (int i = 1; i <= 9; i++) {
                    if (board[index].contains(i)) {
                        counter[i - 1] += 1;
                    }
                }
            }
            for (int i = 1; i <= 9; i++) {
                if (counter[i - 1] == 1) {
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

    // -->

    void hiddenPairs() { // --<
        for (int[] group : Groups.groups) {
            HashMap<Integer, ArrayList<Integer>> counter = new HashMap<>();
            for (int i = 1; i <= 9; i++) {
                counter.put(i, new ArrayList<>());
            }
            for (int index : group) {
                if (board[index].isSolved()) {
                    continue;
                }
                for (int i = 1; i <= 9; i++) {
                    if (board[index].contains(i)) {
                        counter.get(i).add(index);
                    }
                }
            }
            for (int i = 1; i <= 9; i++) {
                if (counter.get(i).size() != 2) {
                    counter.remove(i);
                }
            }
            for (int i : counter.keySet()) {
                for (int j : counter.keySet()) {
                    if (i == j) {
                        continue;
                    }
                    if (counter.get(i).containsAll(counter.get(j))) {
                        for (int index : counter.get(i)) {
                            if (board[index].contains(i)
                                    && board[index].contains(j)
                                    && board[index].size() > 2) {
                                board[index].set(Tools.generatePairBin(i, j), false);
                            }
                        }
                    }
                }
            }
        }
    }

    // -->

    void hiddenSinglesAndPairs() { // --<
        for (int[] group : Groups.groups) {
            HashMap<Integer, ArrayList<Integer>> counter = new HashMap<>();
            for (int i = 1; i <= 9; i++) {
                counter.put(i, new ArrayList<>());
            }
            for (int index : group) {
                if (board[index].isSolved()) {
                    continue;
                }
                for (int i = 1; i <= 9; i++) {
                    if (board[index].contains(i)) {
                        counter.get(i).add(index);
                    }
                }
            }
            for (int i = 1; i <= 9; i++) {
                if (counter.get(i).size() == 1) {
                    int index = counter.get(i).getFirst();
                    board[index].set(i);
                    updateNotes(index);
                    counter.remove(i);
                } else if (counter.get(i).size() > 2) {
                    counter.remove(i);
                } else if (counter.get(i).isEmpty()) {
                    counter.remove(i);
                }
            }
            for (int i : counter.keySet()) {
                for (int j : counter.keySet()) {
                    if (i == j) {
                        continue;
                    }
                    if (counter.get(i).containsAll(counter.get(j))) {
                        for (int index : counter.get(i)) {
                            if (board[index].contains(i)
                                    && board[index].contains(j)
                                    && board[index].size() > 2) {
                                board[index].set(Tools.generatePairBin(i, j), false);
                            }
                        }
                    }
                }
            }
        }
    }

    // -->

    void obviousPairs() { // --<
        for (int[] group : Groups.groups) {
            ArrayList<Integer> sizeTwoDataArr = new ArrayList<>();
            ArrayList<Integer> obviousPairs = new ArrayList<>();
            for (int index : group) {
                if (board[index].size() == 2) {
                    int data = board[index].data;
                    if (sizeTwoDataArr.contains(data)) {
                        obviousPairs.add(data);
                    } else {
                        sizeTwoDataArr.add(data);
                    }
                }
            }
            for (int currData : obviousPairs) {
                for (int index : group) {
                    if (board[index].isSolved()) {
                        continue;
                    }
                    if (board[index].data == currData) {
                        continue;
                    }
                    board[index].remove(currData);
                    if (board[index].isSolved()) {
                        updateNotes(index);
                    }
                }
            }
        }
    }

    // -->

    void pointingPairs() { // --<
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

    // -->

    void applyRules() { // --<
        // stats: --<
        // average time taken for 1000 boards:
        // 1. No rules applied: 43ms
        // 2. hiddenSinglesOld: 8.3ms, average rating: 814
        // 3. obviousPairsOld: 166ms
        // The others are so bad individually that it is not worth waiting.
        // 4. All combined: 86ms, average rating: 518
        //
        // new obviousPairs: 12.7ms
        // hiddenSinglesOld + new obviousPairs: 7.7ms, average rating: 632
        // hiddenSingles: 6.3ms, average rating: 835
        // hiddenSingles + obviousPairs: 5.7ms, average rating: 640
        // -->
        Board copyBoard = new Board();
        do {
            copyBoard.board = Tools.copyBoard(this.board);
            numRulesApplied += 1;
            this.hiddenSingles();
            this.obviousPairs();
            // this.hiddenSinglesAndPairs();
            // this.hiddenPairs();
        } while (Tools.isEqual(copyBoard.board, this.board) == false);
    }

    // -->

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

    boolean isFull() { // --<
        for (Cell cell : board) {
            if (cell.isNotSolved()) {
                return false;
            }
        }
        return true;
    }

    // -->

    int findLeastNotesIndex() { // --<
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

    // -->

    void guessSolve() { // --<
        ArrayList<Board> queue = new ArrayList<>();
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
                    this.numRulesApplied += queue.getLast().numRulesApplied;

                    if (queue.getLast().isFull() && queue.getLast().isValid()) {
                        board = Tools.copyBoard(queue.getLast().board);
                        return;
                    }

                    if (queue.getLast().isValid() == false) {
                        queue.removeLast();
                    }
                }
            }
            queue.removeFirst();
        } while (queue.isEmpty() == false);
    }

    // -->

    void solve() {
        initNotes();
        applyRules();
        if (isFull() == false) {
            requiredToGuess = true;
            guessSolve();
        }
    }
}
