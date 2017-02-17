import java.util.Arrays;
import java.util.Random;

public class Driver {
    private static int tempHeuristic = 0;
    private static int neighbors = 0;
    private static int resets = 0;
    private static int stateChanges = 0;

    public static void main(String[] args) {
        Queen[] currentBoard;
        int currentH = 0;

        Queen[] initBoard = createBoard();

        currentBoard = Arrays.copyOf(initBoard, 8);
        tempHeuristic = checkHeuristic(initBoard);
        currentH = tempHeuristic;

        // test other states
        while (currentH != 0) {
            currentBoard = testBoards(currentBoard, currentH);  //  Sets the best board as current
            currentH = tempHeuristic;
        }
        System.out.println();
        printBoard(currentBoard, currentH, neighbors);  //  Print the last one
        System.out.println("\nState changes: " + stateChanges);
        System.out.println("Number of resets: " + resets);
    }

    /* Tests heuristic of each potential state */
    public static Queen[] testBoards (Queen[] currentBoard, int currentH) { //  Tests other baords
        Queen[] bestBoard = new Queen[8];
        Queen[] tempBoard = new Queen[8];
        int bestH = currentH;
        int tempH;
        int numNeighbors = 0;

        for (int i=0; i<8; i++) {   //  Copies over board states
            bestBoard[i] = new Queen(currentBoard[i].getRow(), currentBoard[i].getCol());
            tempBoard[i] = new Queen(bestBoard[i].getRow(), bestBoard[i].getCol());
        }

        //  Iterate each column
        for (int i=0; i<8; i++) {
            if (i>0)    //  Reset the board
                tempBoard[i-1] = new Queen (currentBoard[i-1].getRow(), currentBoard[i-1].getCol());
            tempBoard[i] = new Queen (0, tempBoard[i].getCol());

            //  Iterate each row
            for (int j=0; j<8; j++) {

                tempH = checkHeuristic(tempBoard);  //  Check Heuristic

                if (tempH < bestH) {    //  If there is another neighbor with a lower heuristic
                    numNeighbors++;   //  Reset the number with that heuristic to 1
                    bestH = tempH;

                    for (int g=0; g<8; g++) {   //  Copy over the board with the best heuristic
                        bestBoard[g] = new Queen(tempBoard[g].getRow(), tempBoard[g].getCol());
                    }
                }
                if (tempBoard[i].getRow()!=7)   //  Moves the queen down
                    tempBoard[i].down();
            }
        }

        System.out.println();
        printBoard(currentBoard, currentH, numNeighbors);   //  Print the previous board
        System.out.println("Setting next state...");

        if (bestH == currentH) {
            System.out.println("\nNo better board found. Resetting...");
            bestBoard = createBoard();
            tempHeuristic = checkHeuristic(bestBoard);
            resets++;
        } else
            tempHeuristic = bestH;

        stateChanges++;
        return bestBoard;
    }

    /* Check Heuristics of state */
    public static int checkHeuristic (Queen[] board) {
        int h = 0;

        for (int i = 0; i< board.length; i++) {
            for (int k=i+1; k<board.length; k++ ) {
                if (board[i].inConflict(board[k])) {
                    h++;
                }
            }
        }
        return h;
    }

    /* Creates a new board */
    public static Queen[] createBoard() {
        Queen[] startPos = new Queen[8];
        Random rd = new Random();

        for(int i=0; i<8; i++){
            startPos[i] = new Queen(rd.nextInt(8), i);
        }
        return startPos;
    }

    /* Prints board */
    private static void printBoard (Queen[] state, int h, int neighbors) {
        int[][] board = new int[8][8];

        for (int i=0; i<8; i++) {
            for (int k=0; k<8; k++) {
                board[i][k]=0;
            }
        }
        for (int i=0; i<8; i++) {
            board[state[i].getRow()][state[i].getCol()]=1;
        }

        System.out.println("Current heuristic: " + h);
        System.out.println("Current state... ");
        for (int i=0; i<8; i++) {
            for (int k = 0; k < 8; k++) {
                System.out.print(board[i][k] + " ");
            }
            System.out.println();
        }
        System.out.println("Neighbors with lower heuristic: " + neighbors);
    }
}


