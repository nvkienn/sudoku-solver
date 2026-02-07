package org.example;

public class App {
    void solve(String FileName) {
        FileUtils parser = new FileUtils();
        Board board = new Board();
        parser.parseBoard(FileName);
        board.gameBoard = parser.getGameBoard();
        board.solution = parser.getSolution();
        board.solve();
        // boolean r = Arrays.deepEquals(board.gameBoard, board.solution);
        // if (r == true) {
        //    System.out.println(FileName + " was solved.");
        // } else {
        //    System.out.println(FileName + " failed to solve.");
        // }
    }

    public static void main(String[] args) {
        App app = new App();
        app.solve("Board8.json");

        // Tests:
        // int x = 5;
        // int y = x;
        // x = 6;
        // System.out.println(y);

        // ArrayList<Integer> x = new ArrayList<Integer>();
        // ArrayList<Integer> y = new ArrayList<Integer>();
        // x.add(1);
        // x.add(2);
        // x.add(3);
        // x.add(4);
        // System.out.println(x);
        // System.out.println(y);

        // complication: checkIfSquareIsSolved right now only accounts for if there can only be one
        // possible number in that square, but does not account for the fact that the number cannot
        // be anywhere else in the box/row/column. Meaning that if for the box/row/column, if there
        // is only one occurrence of that number in the possible numbers list, then that number must
        // be in that box.
        // HashMap<Integer, Integer> numCounter = new HashMap<>();
        // numCounter.put(1, 0);
        // numCounter.put(1, numCounter.get(1) + 1);
        // System.out.println(numCounter.get(1));
        //
        //
        // Try:
        // Put the first avail number and try.
        // Solve from here. If another road block is hit, try the next avail number and so on.
        // This continues until an invalid state is reached, or the board is completely solved.
        // if an invalid state is reached, reload the save state and try again.
        // use a 4 dimensional arraylist to store possibleBoard
        // use a 3 dimensional array to store gameBoard
        // for each layer of trying a number, add a save state, refernces each save state by the
        // layer number of each try. might have to do recursion.
    }
}
