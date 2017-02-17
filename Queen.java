public class Queen {
    private int row;
    private int column;

    public Queen(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean inConflict(Queen q){
        //  Check rows and columns
        if(row == q.getRow() || column == q.getCol())
            return true;
        //  Check diagonals
        else if(Math.abs(column-q.getCol()) == Math.abs(row-q.getRow()))
            return true;

        return false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return column;
    }


    public void down () {
        row++;
    }

    public String toString() {
        return row + "," + column;
    }
}
