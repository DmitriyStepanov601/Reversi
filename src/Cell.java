/**
 * A class that describes the cell of the playing field
 * @author Dmitriy Stepanov
 */
public class Cell {
    private int corY;
    private char corX;
    private char ch;

    /**
     * Constructor - creating a new cell
     * @param x - X coordinate
     * @param y - Y coordinate
     * @param c - symbol
     * @see Cell#Cell(char,int,char)
     */
    public Cell(char x, int y, char c) {
        corY = y;
        corX = x;
        ch = c;  
    }

    public Cell() {}
    public char getCorX()
    {
        return corX; 
    }
    public int getCorY()
    { 
        return corY; 
    }
    public char getCh()
    {
        return ch; 
    }

    public void setPosition(char x, char c, int y) {
        corX = x;
        corY = y;
        ch = c;
    }
}