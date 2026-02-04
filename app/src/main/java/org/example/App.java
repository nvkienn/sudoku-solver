package org.example;

public class App {
    public static void main(String[] args) {
        FileUtils.parseBoard("Board.json");
        Board.printBoard("game");
        // System.out.println(Board.possibleNum[8][8][0]);
    }
}
