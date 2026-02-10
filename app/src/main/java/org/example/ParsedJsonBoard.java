package org.example;

public class ParsedJsonBoard {
    NewBoard newboard = new NewBoard();
}

class NewBoard {
    Grid[] grids = {new Grid()};
    String results;
    String message;
}

class Grid {
    int[][] value;
    int[][] solution;
    String difficulty;
}
