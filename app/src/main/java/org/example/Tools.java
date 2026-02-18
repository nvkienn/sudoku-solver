package org.example;

import java.util.HashMap;

class Tools {
    private static int[] bins = {
        0b1, 0b10, 0b100, 0b1000, 0b10000, 0b100000, 0b1000000, 0b10000000, 0b100000000,
    };
    private static HashMap<Integer, Integer> binToNum = initBinToNum();

    private static HashMap<Integer, Integer> initBinToNum() {
        binToNum = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            binToNum.put(0b1 << i, i + 1);
        }
        return binToNum;
    }

    static int numToBin(int num) {
        return bins[num - 1];
    }

    static int binToNum(int bin) {
        return binToNum.get(bin);
    }
}
