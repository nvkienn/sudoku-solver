package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class csvTools {

    static ArrayList<Board> storeBoards = new ArrayList<>();

    // csvCreateSortedCsv --<
    static void csvReadFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] csvBoardInfo = line.split(",");
                storeBoards.add(new Board());
                storeBoards.getLast().csvGameBoard = csvBoardInfo[1];
                storeBoards.getLast().csvSolution = csvBoardInfo[2];
                storeBoards.getLast().csvDifficulty = csvBoardInfo[3];
            }
            storeBoards.removeFirst();
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    static void csvSortStoreBoardsAsc(boolean asc) {
        if (asc == true) {
            Collections.sort(
                    storeBoards,
                    (obj1, obj2) -> {
                        Board board1 = (Board) obj1;
                        Board board2 = (Board) obj2;
                        int boardDifficulty1;
                        int boardDifficulty2;
                        try {
                            boardDifficulty1 = Integer.parseInt(board1.csvDifficulty);
                            boardDifficulty2 = Integer.parseInt(board2.csvDifficulty);
                        } catch (NumberFormatException e) {
                            boardDifficulty1 = 0;
                            boardDifficulty2 = 0;
                        }
                        return boardDifficulty1 - boardDifficulty2;
                    });

        } else {
            Collections.sort(
                    storeBoards,
                    (obj1, obj2) -> {
                        Board board1 = (Board) obj1;
                        Board board2 = (Board) obj2;
                        int boardDifficulty1;
                        int boardDifficulty2;
                        try {
                            boardDifficulty1 = Integer.parseInt(board1.csvDifficulty);
                            boardDifficulty2 = Integer.parseInt(board2.csvDifficulty);
                        } catch (NumberFormatException e) {
                            boardDifficulty1 = 0;
                            boardDifficulty2 = 0;
                        }
                        return boardDifficulty2 - boardDifficulty1;
                    });
        }
    }

    static void csvWriteSortedBoard(int numOfBoards) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sortedBoards.csv"))) {
            for (int i = 0; i < numOfBoards; i++) {
                bw.write(
                        storeBoards.get(i).csvGameBoard
                                + ","
                                + storeBoards.get(i).csvSolution
                                + ","
                                + storeBoards.get(i).csvDifficulty);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    static void csvCreateSortedCsv(int numOfBoards, boolean asc) {
        csvReadFile("test.csv");
        csvSortStoreBoardsAsc(asc);
        csvWriteSortedBoard(numOfBoards);
    }

    // -->

    static void csvParseBoard(String fileName) { // --<
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] csvBoardInfo = line.split(",");
                App.storeBoards.add(new Board());
                for (int i = 0; i < 81; i++) {
                    App.storeBoards.getLast().board[i] = new Cell();
                    App.storeBoards.getLast().solution[i] = new Cell();
                    if (csvBoardInfo[0].charAt(i) == '.') {
                        App.storeBoards.getLast().board[i].init();
                    } else {
                        App.storeBoards
                                .getLast()
                                .board[i]
                                .set(
                                        Integer.parseInt(
                                                Character.toString(csvBoardInfo[0].charAt(i))));
                    }
                    App.storeBoards
                            .getLast()
                            .solution[i]
                            .set(Integer.parseInt(Character.toString(csvBoardInfo[1].charAt(i))));
                }
                App.storeBoards.getLast().difficultyRating = Integer.parseInt(csvBoardInfo[2]);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    // -->

    static void csvParseBoardCustom(String fileName) { // --<
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                App.storeBoards.add(new Board());
                for (int i = 0; i < 81; i++) {
                    App.storeBoards.getLast().board[i] = new Cell();
                    if (line.charAt(i) == '.') {
                        App.storeBoards.getLast().board[i].init();
                    } else {
                        App.storeBoards
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

}
