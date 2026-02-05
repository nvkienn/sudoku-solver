package org.example;

public class App {
    public static void main(String[] args) {
        FileUtils parser = new FileUtils();
        Board board = new Board();
        parser.parseBoard("Board.json");
        board.gameBoard = parser.getGameBoard();
        board.solution = parser.getSolution();
        board.solve();

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
