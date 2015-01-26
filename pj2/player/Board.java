package pj2.player;

/**
 * This file is created by @Muffin_C on 1/26/15 21:05.
 * This file is part of Project CS61B.
 */
class Board {
    int[][] board = new int[8][8];
    boolean[][] visited = new boolean[8][8];

    void initVisit() {
        visited = new boolean[8][8];
    }
}
