package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class csvTools {

    ArrayList<Board> storeBoards = new ArrayList<>();

    // csvCreateSortedCsv --<
    void csvReadFile(String fileName) {
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

    void csvSortStoreBoards() {
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
    }

    void csvCreateFile() {
        try {
            File file = new File("sortedBoards.csv");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void csvWriteSortedBoard() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sortedBoards.csv"))) {
            for (int i = 0; i < 20; i++) {
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

    void csvCreateSortedCsv() {
        csvReadFile("test.csv");
        csvSortStoreBoards();
        csvCreateFile();
        csvWriteSortedBoard();
    }

    // -->

}
