# sudoku-solver

## Running The Solver

```
$ ./gradlew build

$ ./gradlew run
```

or

```
$ ./gradlew build run
```

## Structure

Source code is in ./app/src/main/java/org/example/  

Cwd of program is in ./app and contains all boards  

Csv files containing boards are:  
- test.csv (400,000+ boards)
- sortedBoards.csv (generated)
- testBoards.csv (used for testing rules)
- custom.txt (manually added)

## Options

### App.java:

```java
public static void main(String[] args) {
    // printBoard = true -> print the initial and solved state of Board when solving  
    // custom = true -> read Boards from custom.txt instead of sortedBoards.csv
    solve(int numOfBoardToSolve, boolean printBoard, boolean custom)  

    // Uncomment to test whether the rules work
    // System.out.println(testHiddenSingles());
    // System.out.println(testobviousPairs());
    // System.out.println(testHiddenPairs());
    // System.out.println(testPointingPairs());
}


```
### Board.java:

```java
void applyRules() {
    Board copyBoard = new Board();
    do {
        copyBoard.board = Tools.copyBoard(this.board);
        numRulesApplied += 1;
        this.hiddenSingles();
        this.obviousPairs();
        // this.hiddenPairs();
    	// this.pointingPairs();
        // this.hiddenSinglesAndPairs();
    } while (Tools.isEqual(copyBoard.board, this.board) == false);
}
```

#### Current rules being applied are only:  

1. Hidden Singles  
2. Obvious Pairs

#### Rules not applied:

1. Hidden Pairs (note that `hiddenSinglesAndPairs()` applies both rules hidden singles and hidden pairs)
2. Pointing Pairs (the method also accounts for pointing triples)

## Measuring Solver Effectiveness

### Solver Speed

Solver speed becomes more apparent when solving a large number of boards at once.  

Applying `hiddenSingles()` yields the fastest solving speed compared to the rest of the rules.

Applying `obviousPairs()` on top negligibly increases solving speed, but reduces the number of times the rules are applied.

Applying `hiddenPairs()` or `pointingPairs()` reduces solving speed. Although the number of times rules are applied decreases, the need to run through the board another time offsets the speed benefits.


### Number Of Times Rules Are Applied

Generally, the more rules used, the less the number of times the rules have to be applied. However, different rules have different magnitude of impact on this metric.

For example, applying `hiddenSingles()` has a larger effect on reducing the number of times rules are applied, whereas applying `hiddenPairs()` does not make much of a difference as there are usually less hidden pairs found in a game.

## How It Works

Check out [sudoku.com/sudoku-rules](https://sudoku.com/sudoku-rules/) for sudoku related phrases and how the rules work.

### Board.java

```java
void solve() {
    initNotes();
    applyRules();
    if (isFull() == false) {
        // At this point, the solver assumes an unsolved cell to be solved
        // by trying a possible number from the Notes. Then the solver applies the rules
        // and checks whether the board is solved. This process repeats itself until the
        // board is solved.
        guessSolve();
    }
}
```


