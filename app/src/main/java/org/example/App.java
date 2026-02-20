package org.example;

public class App {
    public static void main(String[] args) {

        Board board = new Board();
        board.main(1000);

        // Obvious Pairs
        // Go through a group.
        // Identify those with size == 2
        // Add to HashMap with an associated counter.
        // Afterwards, go through HashMap
        // If counter == 2,
        // go through group again and remove that pair.
        // Hidden Pairs
        // Go through a group.
        // Have a num Counter [] with arrays holding the indexes of the occurrence of these numbers.
        // if no. of occurence == 2,
        // check if they coincide in same cell by looking at indexes.
        // if they do then go through group and remove

        // csvTools csv = new csvTools();
        // csv.csvCreateSortedCsv(false);
        // String x = "";
        // x += (5 + "hello");
        // x = x.substring(0, x.length() - 1);
        // System.out.println(x);
        // board.main();

        //	App app = new App();
        //	app.solve();

        // Board board = new Board();
        // board.csvCreateSortedCsv();

        // Tests:
        // for (int i = 1; i <= 9; i++) {
        //    for (int j = i + 1; j <= 9; j++) {
        //        System.out.println(i + "," + j);
        //    }
        // }
        // short x = 0b1 << 5;
        // System.out.println(x);
        // System.out.println(Integer.toBinaryString(x));
        // System.out.println(Short.MIN_VALUE);
        // System.out.println(Integer.toBinaryString(0xFFFF));
        // System.out.println(0xFF);
        // File file = new File("test.csv");
        // System.out.println(file.exists());
        //
        // ArrayList<Hello> x = new ArrayList<>();
        // String[] y = {"2", "1", "3"};
        // for (String num : y) {
        //    x.add(new Hello());
        //    x.getLast().myString[0] = num;
        // }
        // for (Hello item : x) {
        //    System.out.println(item.myString[0]);
        // }
        // Collections.sort(
        //        x,
        //        (hello1, hello2) -> {
        //            Hello a = (Hello) hello1;
        //            Hello b = (Hello) hello2;
        //            int aNum;
        //            int bNum;
        //            try {
        //                aNum = Integer.parseInt(a.myString[0]);
        //                bNum = Integer.parseInt(b.myString[0]);
        //            } catch (NumberFormatException e) {
        //                aNum = 0;
        //                bNum = 0;
        //            }
        //            return bNum - aNum;
        //        });
        // System.out.println();

        // for (Hello item : x) {
        //    System.out.println(item.myString[0]);
        // }
        //
        //
        //
        //
        // Hello hello = new Hello();
        // hello.y = hello.increase();
        // hello.increase();
        // for (int[] row : hello.y) {
        //     for (int num : row) {
        //         System.out.println(num);
        //     }
        // }

        //
        //
        //
        // Hello hello = new Hello();
        // hello.x = 2;
        // hello.decrease();
        // Hypothesis.
        // 1. this belongs to the current obj, so x first belongs to hello, then to newHellos
        // subsequently.
        // "2,1,0"
        // 2. this belongs to hello and hello only.
        // "2,2,2"
        //
        // Result: hypothesis 1 is true! it belongs to the current object that the method belongs
        // to. That's dope and exactly what i need that is very noice.

        //
        // int x = 5;
        // int y = x;
        // x = 6;
        // System.out.println(y);

        // ArrayList<ArrayList<Integer>> x = new ArrayList<>();
        // ArrayList<Integer> y = new ArrayList<Integer>();
        // x.add(new ArrayList<>());
        // x.get(0).add(1);
        // y = x.get(0);
        // y.add(2);

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
        //
        //
        // Start with the original board, then make a copy of it using a new object. This way, you
        // always have a original copy, and you can copy back if an invalid result is reached. this
        // copy should be made just after the iterator, so for every new number that is tested, you
        // will be trying from the original board. Then, this baord will be solved, and if the state
        // is still valid, will be passed into another round of recursion where it will be tested.
        // This board should now become the original board, and another one will be made to make a
        // copy.
        // We are not taking in the current board state. we tried to do that, there is too much code
        // to change, because we have to edit all our code to be able to take in a board of our
        // choice. instead, we are using the object's own method to act on its own attribute. a new
        // object is created every recursion.
    }
}

class Hello { // --<
    int x;
    int[][] y = {{1}};
    String[] myString = new String[1];

    void decrease() {
        Hello newHello = new Hello();

        System.out.println(this.x);
        newHello.x = this.x;
        if (newHello.x > 0) {
            newHello.x -= 1;
            System.out.println("A recursor has been called.");
            newHello.decrease();
        }
    }

    int[][] increase() {
        Hello newHello = new Hello();
        newHello.y[0][0] = y[0][0] + 1;
        return newHello.y;
    }
} // -->
