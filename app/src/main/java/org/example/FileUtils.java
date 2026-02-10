package org.example;

import com.google.gson.Gson;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class FileUtils {

    private int[][] gameBoard;
    private int[][] solution;

    /** Takes in a file name, returns file contents as String. */
    // readFile(fileName)
    // --<
    String readFile(String fileName) {

        File myObj = new File(fileName);

        try (Scanner myReader = new Scanner(myObj)) {
            String fileContents = myReader.nextLine();
            while (myReader.hasNextLine()) {
                fileContents += myReader.nextLine();
            }
            return fileContents;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return "";
        }
    }

    // -->

    /** Takes in a jsonString, fills the Board Class's attribtues. */
    // jsonParser(jsonString)
    // --<
    void jsonParser(String jsonString) {
        Gson gson = new Gson();
        ParsedJsonBoard parsedBoard = gson.fromJson(jsonString, ParsedJsonBoard.class);
        gameBoard = parsedBoard.newboard.grids[0].value;
        solution = parsedBoard.newboard.grids[0].solution;
    }

    // -->

    void parseBoard(String fileName) {
        String boardJsonString = readFile(fileName);
        jsonParser(boardJsonString);
    }

    int[][] getGameBoard() {
        return gameBoard;
    }

    int[][] getSolution() {
        return solution;
    }
}
