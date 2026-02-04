package org.example;

import com.google.gson.Gson;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class FileUtils {

    /** Takes in a file name, returns file contents as String. */
    public static String readFile(String fileName) {
        // --<

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
        // -->
    }

    /** Takes in a jsonString, fills the Board Class's attribtues. */
    public static void jsonParser(String jsonString) {
        Gson gson = new Gson();
        ParsedBoard parsedBoard = gson.fromJson(jsonString, ParsedBoard.class);
        Board.boardGrid = parsedBoard.newboard.grids[0].value;
        Board.solution = parsedBoard.newboard.grids[0].solution;
    }

    public static void parseBoard(String fileName) {
        String boardJsonString = readFile(fileName);
        jsonParser(boardJsonString);
    }
}
