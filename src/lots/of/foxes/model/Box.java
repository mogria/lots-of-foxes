package lots.of.foxes.model;

/**
 * Represents a single box on the Board
 * 
 * @author Moritz
 */
public class Box {
    
    /**
     * an unique id for this box
     */
    private int boxId;
    
    /**
     * the number of lines used around this box
     */
    private int lineCount = 0;
    
    /**
     * the player which used the last line.
     */
    private Player owner = null;
    
    public Box(int boxId) {
        this.boxId = boxId;
    }

    /**
     * Get the value of lineCount
     *
     * @return the value of lineCount
     */
    public int getLineCount() {
        return lineCount;
    }

    /**
     * Get the value of owner
     *
     * @return the value of owner
     */
    public Player getOwner() {
        return owner;
    }
    
    /**
     * Increases the number of lines in use around this Box.
     * if the fourth line is being taken the player passed is declared owner
     * of the Box.
     * 
     * @param player the player adding the line
     */
    public void addLine(Player player) {
        lineCount++;
        if(lineCount >= 4) {
            owner = player;
        }
            
    }
    
    /**
     * get the row this line lies in the grid.
     * @return the row this line lies in the grid.
     */
    public int getRow() {
        return boxId & 0xFFFF;
    }
    
    /**
     * get the column this line lies in the grid.
     * @return the column this line lies in the grid.
     */
    public int getColumn() {
        return (boxId >> 16) & 0xFFFF;
    }
}
