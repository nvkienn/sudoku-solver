# sudoku-solver

### Running The Solver

> $ ./gradlew build
> 
> $ ./gradlew run

OR

> $ ./gradlew build run

### Structure

Source code is in ./app/src/main/java/org/example/<br>
Cwd of program is in ./app<br>
Csv files containing boards are:<br>
- test.csv (400,000+ boards)
- sortedBoards.csv (generated)
- customBoards.csv (manually added

### Options

# App.java:

> solve(int numOfBoardToSolve, boolean printBoard, boolean custom)
> printBoard = true // print the initial and solved state of Board when solving
> custom = true // read Boards from customBoards.csv instead of sortedBoards.csv



