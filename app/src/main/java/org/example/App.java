package org.example;

import java.util.Arrays;

public class App {
    void solve(String FileName) {
        FileUtils parser = new FileUtils();
        Board board = new Board();
        parser.parseBoard(FileName);
        board.gameBoard = parser.getGameBoard();
        board.solution = parser.getSolution();
        board.solve();
        boolean r = Arrays.deepEquals(board.gameBoard, board.solution);
        if (r == true) {
            System.out.println(FileName + " was solved.");
        } else {
            System.out.println(FileName + " failed to solve.");
        }
    }

    public static void main(String[] args) {
        // App app = new App();
        App app1 = new App();
        // App app2 = new App();
        // App app3 = new App();
        // App app4 = new App();
        app1.solve("Board1.json");
        // app2.solve("Board2.json");
        // app3.solve("Board3.json");
        // app4.solve("Board4.json");
        // app.solve("Board.json");
        // App app5 = new App();
        // app5.solve("Board5.json");
        int x = 5;
        int y = x;
        x = 6;
        System.out.println(y);

        // ArrayList<Integer> x = new ArrayList<Integer>();
        // x.add(1);
        // x.add(2);
        // x.add(3);
        // System.out.println(x.getLast());

        // complication: checkIfSquareIsSolved right now only accounts for if there can only be one
        // possible number in that square, but does not account for the fact that the number cannot
        // be anywhere else in the box/row/column. Meaning that if for the box/row/column, if there
        // is only one occurrence of that number in the possible numbers list, then that number must
        // be in that box.
        // HashMap<Integer, Integer> numCounter = new HashMap<>();
        // numCounter.put(1, 0);
        // numCounter.put(1, numCounter.get(1) + 1);
        // System.out.println(numCounter.get(1));
    }
}
