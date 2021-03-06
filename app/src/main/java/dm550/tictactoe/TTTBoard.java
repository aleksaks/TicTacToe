package dm550.tictactoe;

/** represents a tic tac toe board of a given size */
public class TTTBoard {
    
    /** 2-dimensional array representing the board
     * coordinates are counted from top-left (0,0) to bottom-right (size-1, size-1)
     * board[x][y] == 0   signifies free at position (x,y)
     * board[x][y] == i   for i > 0 signifies that Player i made a move on (x,y)
     */
    private int[][] board;
    
    /** size of the (quadratic) board */
    private int size;
    
    /** constructor for creating a copy of the board
     * not needed in Part 1 - can be viewed as an example 
     */
    public TTTBoard(TTTBoard original) {
        this.size = original.size;
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.board[x][y] = original.board[x][y];
            }
        }
    }
    
    /** constructor for creating an empty board for a given number of players */
    public TTTBoard(int numPlayers) {
        this.size = numPlayers+1;
        this.board = new int[this.getSize()][this.getSize()];
    }
    
    /** checks whether the board is free at the given position */
    public boolean isFree(Coordinate c) {
        if (board[c.getX()][c.getY()]==0)
            return true;
        else
            return false;
    }
    
    /** returns the players that made a move on (x,y) or 0 if the positon is free */
    public int getPlayer(Coordinate c) {
        if (!isFree(c))
            return board[c.getX()][c.getY()];
        else
            return 0;
    }
    
    /** record that a given player made a move at the given position
     * checks that the given positions is on the board
     * checks that the player number is valid 
     */
    public void addMove(Coordinate c, int player) {
        try {
            if (c.checkBoundaries(size, size) ) {
                if (player > 0)
                    board[c.getX()][c.getY()] = player;
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Illegal argument exception: " + e.toString());
        }
    }

    /** returns true if, and only if, there are no more free positions on the board */
    public boolean checkFull() {
        for(int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
            {
                if(board[x][y]==0)
                    return false;
            }
        return true;
    }

    /** returns 0 if no player has won (yet)
     * otherwise returns the number of the player that has three in a row
     */
    public int checkWinning() {
        int result = 0;
        //checking all column and diagonal sequences (they all can only start from a particular segment of the board
        for(int x = 0; x<size;x++) {
            for (int y = 0; y < size-2; y++) {
                Coordinate start = new XYCoordinate(x, y);
                //We are interested in checking cells that are not free
                if(!isFree(start)) {
                     result = checkSequence(start, 0, 1);
                    if (result > 0)
                        return result;
                    //Checking diagonal only for x smaller than size-2
                    if(x<(size-2)) {
                        result = checkSequence(start, 1, 1);
                        if (result > 0)
                            return result;
                    }
                    //Checking backward diagonal only for x larger than 2
                    if(x>1) {
                        result = checkSequence(start, -1, 1);
                        if (result > 0)
                            return result;
                    }
                }
            }
        }
        //Checking all rows sequences
        for(int x = 0; x<size-2;x++) {
            for (int y = 0; y < size; y++) {
                Coordinate start = new XYCoordinate(x, y);
                //We are interested in checking cells that are not free
                if(!isFree(start)) {
                    result = checkSequence(start, 1, 0);
                    if (result > 0)
                        return result;
                }
            }
        }
        return result;
    }
    
    /** internal helper function checking one row, column, or diagonal */
    private int checkSequence(Coordinate start, int dx, int dy) {
        //The
        int count = 1;
        int currentPlayer = getPlayer(start);
        for (int i = 1; i < 3; i++)
        {
            start = start.shift(dx,dy);
            if(start.checkBoundaries(size,size)) {
                if (getPlayer(start) == currentPlayer)
                    count++;
            }
            //If 2nd neighbouring cell is not marked by current player - there is no point to continue the checking process
            else
                break;
        }
        if (count ==3)
            return currentPlayer;
        else
            return 0;


        // TODO
    }
    
    /** getter for size of the board */
    public int getSize() {
        return this.size;
    }
    
    /** pretty printing of the board
     * usefule for debugging purposes
     */
    public String toString() {
        String result = "";
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                result += this.board[x][y]+" ";
            }
            result += "\n";
        }
        return result;
    }

}
