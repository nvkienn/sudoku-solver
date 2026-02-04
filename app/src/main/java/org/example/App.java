package org.example;

public class App {
    public static void main(String[] args) {
        // FileUtils.parseBoard("Board.json");
        // Board.printBoard("game");
        Board.initPossibleBoard();
        System.out.println(Board.possibleBoard);
    }
}
