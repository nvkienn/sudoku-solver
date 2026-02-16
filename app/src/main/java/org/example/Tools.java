package org.example;

import java.util.HashMap;

class Tools {
    private static int[] cells = {
        0b1, 0b10, 0b100, 0b1000, 0b10000, 0b100000, 0b1000000, 0b10000000, 0b100000000,
    };
    private static HashMap<Integer, Integer> cellToNum = initCellToNum();

    private static HashMap<Integer, Integer> initCellToNum() {
        cellToNum = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            cellToNum.put(0b1 << i, i + 1);
        }
        return cellToNum;
    }

    static int numToCell(int num) {
        return cells[num - 1];
    }

    static int cellToNum(int cell) {
        return cellToNum.get(cell);
    }
}
