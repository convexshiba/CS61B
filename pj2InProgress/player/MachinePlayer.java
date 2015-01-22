/* MachinePlayer.java */

package pj2.player;

import java.util.Scanner;

/**
 * An implementation of an automatic Network player.  Keeps track of moves
 * made by both players.  Can select a move for itself.
 */

class Board {
    int[][] board = new int[8][8];
    boolean[][] visited = new boolean[8][8];

    void initVisit() {
        visited = new boolean[8][8];
    }
}

class Position {
    int y;
    int x;
    int item;

    Position(int y, int x) {
        this.y = y;
        this.x = x;
        item =
    }
}

public class MachinePlayer extends Player {
    private final int BLACK = -1;
    private final int WHITE = 1;
    private final int UNOCCUPIED = 0;

    private Board board = new Board();

    private int playerColor;
    private int oppoColor;
    private int searchDepth = 2;

    // Creates a machine player with the given color.  Color is either 0 (black)
    // or 1 (white).  (White has the first move.)
    public MachinePlayer(int color) {
        if (color == 0) {
            playerColor = BLACK;
            oppoColor = WHITE;
        } else if (color == 1) {
            playerColor = WHITE;
            oppoColor = BLACK;
        } else {
            System.out.println("Wrong Input Color");
        }

    }

    // Creates a machine player with the given color and search depth.  Color is
    // either 0 (black) or 1 (white).  (White has the first move.)
    public MachinePlayer(int color, int searchDepth) {
        this(color);
        this.searchDepth = searchDepth;
    }

    // Returns a new move by "this" player.  Internally records the move (updates
    // the internal game board) as a move by "this" player.
    public Move chooseMove() {
        return new Move();
    }

    // If the Move m is legal, records the move as a move by the opponent
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method allows your opponents to inform you of their moves.
    public boolean opponentMove(Move m) {
        if (!legalMove(m, oppoColor)) {
            System.out.println("Illegal Move");
            return false;
        }

        switch (m.moveKind) {
            case 0:
                System.out.println(m);
                System.exit(0);
            case 1:
                System.out.println(m);
                board.board[m.y1][m.x1] = oppoColor;
                return true;
            case 2:
                System.out.println(m);
                board.board[m.y2][m.x2] = UNOCCUPIED;
                board.board[m.y1][m.x1] = oppoColor;
                return true;
            default:
                System.out.println("Error. No moveKind Match");
                return false;
        }
    }

    // If the Move m is legal, records the move as a move by "this" player
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method is used to help set up "Network problems" for your
    // player to solve.
    public boolean forceMove(Move m) {
        if (!legalMove(m, playerColor)) {
            System.out.println("Illegal Move");
            return false;
        }

        switch (m.moveKind) {
            case 0:
                System.out.println(m);
                System.exit(0);
            case 1:
                System.out.println(m);
                board.board[m.y1][m.x1] = playerColor;
                return true;
            case 2:
                System.out.println(m);
                board.board[m.y2][m.x2] = UNOCCUPIED;
                board.board[m.y1][m.x1] = playerColor;
                return true;
            default:
                System.out.println("Error. No moveKind Match");
                return false;
        }
    }

    private boolean legalMove(Move m, int moveColor) {
        switch (m.moveKind) {
            case Move.QUIT:
                return true;
            case Move.ADD:
                return checkAddMove(m, moveColor);
            case Move.STEP:
                return checkStepMove(m, moveColor);
            default:
                System.out.println("Error. No moveKind Match");
                return false;
        }
    }

    private boolean checkAddMove(Move m, int moveColor) {

        if (board.board[m.y1][m.x1] == WHITE || board.board[m.y1][m.x1] == BLACK) {
            System.out.println(board.board[m.y1][m.x1]);
            System.out.println("Move Already Taken");
            return false;
        }

        switch (moveColor) {
            case BLACK:
                if (m.x1 == 0 || m.x1 == 7) {
                    System.out.println("Out of bound");
                    return false;
                }
                break;
            case WHITE:
                if (m.y1 == 0 || m.y1 == 7) {
                    System.out.println("Out of bound");
                    return false;
                }
        }

        board.board[m.y1][m.x1] = moveColor;


        for (int xShift = -1; xShift <= 1; xShift++) {
            for (int yShift = -1; yShift <= 1; yShift++) {
                if (m.y1 + yShift >= 0 && m.y1 + yShift <= 7) {
                    if (m.x1 + xShift >= 0 && m.x1 + xShift <= 7) {
                        if (formCluster(m.y1 + yShift, m.x1 + xShift, moveColor)) {
                            board.board[m.y1][m.x1] = UNOCCUPIED;
                            return false;
                        }
                    }
                }
            }
        }
        board.board[m.y1][m.x1] = UNOCCUPIED;
        return true;
    }

    private boolean formCluster(int y, int x, int moveColor) {
        int clusterNumber = 0;

        if (board.board[y][x] == UNOCCUPIED) {
            return false;
        }

        if (board.board[y][x] != moveColor) {
            return false;
        }

        for (int xShift = -1; xShift <= 1; xShift++) {
            for (int yShift = -1; yShift <= 1; yShift++) {
                if (y + yShift >= 0 && y + yShift <= 7) {
                    if (x + xShift >= 0 && x + xShift <= 7) {
                        if (board.board[y + yShift][x + xShift] == board.board[y][x]) {
                            clusterNumber++;
                        }
                        if (clusterNumber == 3) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkStepMove(Move m, int moveColor) {
        if (board.board[m.y2][m.x2] != moveColor) {
            System.out.println("Move does not exist");
            return false;
        }

        board.board[m.y2][m.x2] = UNOCCUPIED;

        if (board.board[m.y1][m.x1] == WHITE || board.board[m.y1][m.x1] == BLACK) {
            System.out.println(m);
            System.out.println(board.board[m.y1][m.x1]);
            System.out.println("Add Move Already Taken");
            return false;
        }

        switch (moveColor) {
            case BLACK:
                if (m.x1 == 0 || m.x1 == 7) {
                    System.out.println(m);
                    System.out.println("Out of bound");
                    return false;
                }
            case WHITE:
                if (m.y1 == 0 || m.y1 == 7) {
                    System.out.println(m);
                    System.out.println("Out of bound");
                    return false;
                }
        }

        board.board[m.y1][m.x1] = moveColor;


        for (int xShift = -1; xShift <= 1; xShift++) {
            for (int yShift = -1; yShift <= 1; yShift++) {
                if (formCluster(m.y1 + yShift, m.x1 + xShift, moveColor)) {
                    board.board[m.y2][m.x2] = moveColor;
                    board.board[m.y1][m.x1] = UNOCCUPIED;
                    return false;
                }
            }
        }
        board.board[m.y2][m.x2] = moveColor;
        board.board[m.y1][m.x1] = UNOCCUPIED;
        return true;
    }

    private int whoWin(int[][] board) {
        return;
    }

    private boolean dfs(int y, int x, int parentDirection) {
        return true;
    }

    private int[] neighbor(int y, int x, int direction) {
        int[] foo = new int[2];
        switch (direction) {
            case 1:

        }
    }


//    private boolean checkPossibleWin(int color) {
//        int blackScore = 0;
//        int whiteScore = 0;
//        for (int x = 1; x <= 6; x++) {
//            if (board.board[0][x] == BLACK) {
//                blackScore += 1;
//            }
//            if (board.board[7][x] == BLACK) {
//                blackScore += 10;
//            }
//        }
//
//        if (blackScore > 10 && blackScore % 10 != 0) {
//            return checkWin(BLACK);
//        }
//    }
//
//    private boolean checkWin(int color) {
//
//        if (color == BLACK) {
//            for (int x = 1; x <= 6; x++) {
//                if (board.board[0][x] == color) {
//                    board.initVisit();
//                    if (dfs(0,x,color)) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        } else {
//            for (int y = 1; y <= 6; y++) {
//                if (board.board[y][0] == color) {
//                    board.initVisit();
//                    if (dfs(y,0)) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }
//
//
//
//    private int[][] findLink(int y, int x) {
//        //up
//        for (int upShift = -7; upShift <= 7; upShift++) {
//            for (int downShift = -7; downShift <= 7; downShift++) {
//
//
//            }
//        }
//    }

    @Override
    public String toString() {
        String boardString = "";
        boardString += "     X0  X1  X2  X3  X4  X5  X6  X7\n";
        boardString += "    +---+---+---+---+---+---+---+---+\n";
        for (int y = 0; y <= 7; y++) {
            boardString += " Y" + y + " ";
            for (int x = 0; x <= 7; x++) {
                boardString += "| ";
                if (board.board[y][x] == WHITE) {
                    boardString += "W";
                } else if (board.board[y][x] == BLACK) {
                    boardString += "B";
                } else if (board.board[y][x] == UNOCCUPIED) {
                    boardString += " ";
                }
                boardString += " ";
            }
            boardString += "|\n";
            boardString += "    +---+---+---+---+---+---+---+---+\n";
        }
        return boardString;
    }

    private void play(String player) {
        System.out.println(toString());
        Scanner input = new Scanner(System.in);
        int x;
        int y;
        Move m;

        if (player.equals("player")) {
            System.out.println("player");
            printColor(playerColor);
            String move = input.nextLine();
            x = Integer.parseInt(move.substring(0, 1));
            y = Integer.parseInt(move.substring(2));
            m = new Move(x, y);
            while (!forceMove(m)) {
                System.out.println("player");
                printColor(playerColor);
                move = input.nextLine();
                x = Integer.parseInt(move.substring(0, 1));
                y = Integer.parseInt(move.substring(2));
                m = new Move(x, y);
                System.out.println(toString());
            }
        } else if (player.equals("oppo")) {
            System.out.println("oppo");
            printColor(oppoColor);
            String move = input.nextLine();
            x = Integer.parseInt(move.substring(0, 1));
            y = Integer.parseInt(move.substring(2));
            m = new Move(x, y);
            while (!opponentMove(m)) {
                System.out.println("oppo");
                printColor(oppoColor);
                move = input.nextLine();
                x = Integer.parseInt(move.substring(0, 1));
                y = Integer.parseInt(move.substring(2));
                m = new Move(x, y);
                System.out.println(toString());
            }
        }
    }

    private void printColor(int color) {
        if (color == BLACK) {
            System.out.println("Black: ");
        } else if (color == WHITE) {
            System.out.println("White: ");
        }
    }

    public static void main(String[] args) {

        System.out.println("Please Choose MachinePlayer Color, 0 for Black, 1 for White:");

        Scanner input = new Scanner(System.in);

        int color = input.nextInt();

        MachinePlayer testDriver = new MachinePlayer(color);
        if (color == 1) {
            testDriver.play("player");
        }

        try {
            while (true) {
                testDriver.play("oppo");
                testDriver.play("player");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        System.out.println("White");
//        String move = input.nextLine();
//
//        while (!move.equals("exit")) {
//
//            int x = Integer.parseInt(move.substring(0,1));
//            int y = Integer.parseInt(move.substring(2));
//            Move m = new Move(x,y);
//            while (!testDriver.opponentMove(m)) {
//                System.out.println("White");
//                move = input.nextLine();
//                x = Integer.parseInt(move.substring(0,1));
//                y = Integer.parseInt(move.substring(2));
//                m = new Move(x,y);
//            }
//            System.out.println(testDriver);
//
//            System.out.println("Black:");
//            move = input.nextLine();
//
//            x = Integer.parseInt(move.substring(0,1));
//            y = Integer.parseInt(move.substring(2));
//            m = new Move(x,y);
//            while (!testDriver.forceMove(m)) {
//                System.out.println("Black:");
//                move = input.nextLine();
//                x = Integer.parseInt(move.substring(0,1));
//                y = Integer.parseInt(move.substring(2));
//                m = new Move(x,y);
//            }
//            System.out.println(testDriver);
//
//            System.out.println("White:");
//            move = input.nextLine();
//        }
    }
}
