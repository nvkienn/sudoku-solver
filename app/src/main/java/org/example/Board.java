package org.example;

import java.util.ArrayList;

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

    void hiddenSinglesOld() { // --<
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

    void hiddenPairsOld() { // --<
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

    // -->

    void hiddenPairs() {}

    void obviousPairsOld() { // --<
        for (int[] group : Groups.groups) {
            for (int i = 1; i <= 9; i++) {
                loop:
                for (int j = i + 1; j <= 9; j++) {
                    int counter = 0;
                    int index1 = -1;
                    int index2 = -1;

                    groupLoop:
                    for (int index : group) {
                        if (board[index].isSolved()) {
                            if (board[index].get() == i || board[index].get() == j) {
                                continue loop;
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

    void mergeHiddenSinglesAndObviousPairs() {
        for (int[] group : Groups.groups) {
            ArrayList<Integer> sizeTwoDataArr = new ArrayList<>();
            ArrayList<Integer> obviousPairs = new ArrayList<>();
            int[] singlesCounter = new int[9];
            for (int index : group) {
                if (board[index].isSolved()) {
                    continue;
                }

                // hiddenSingles
                for (int i = 1; i <= 9; i++) {
                    if (board[index].contains(i)) {
                        singlesCounter[i - 1] += 1;
                    }
                }

                // obviousPairs
                if (board[index].size() == 2) {
                    int data = board[index].data;
                    if (sizeTwoDataArr.contains(data)) {
                        obviousPairs.add(data);
                    } else {
                        sizeTwoDataArr.add(data);
                    }
                }
            }

            // hiddenSingles
            for (int i = 1; i <= 9; i++) {
                if (singlesCounter[i - 1] == 1) {
                    for (int index : group) {
                        if (board[index].contains(i)) {
                            board[index].set(i);
                            updateNotes(index);
                            break;
                        }
                    }
                }
            }

            // obviousPairs
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

    void applyRules() { // --<
        // stats:
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
        Board copyBoard = new Board();
        do {
            copyBoard.board = Tools.copyBoard(this.board);
            numRulesApplied += 1;
            // this.hiddenSinglesOld();
            // this.hiddenSingles();
            // this.obviousPairsOld();
            // this.obviousPairs();
            // this.hiddenPairsOld();
            // this.pointingPairs();
            mergeHiddenSinglesAndObviousPairs();
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
