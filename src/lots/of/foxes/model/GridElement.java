package lots.of.foxes.model;

/**
 * Represents an element in the Grid.
 * @author Moritz
 */
public class GridElement {
    /**
     * the unique id of this GridElement
     */
    private int id;
    
    public GridElement(int id) {
        this.id = id;
    }

    /**
     * get the unique id of this GridElement
     * @return the unique id of this GridElement
     */
    public int getId() {
        return id;
    }
    
        
    /**
     * get the row this GridElement lies in the grid.
     * @return the row this GridElement lies in the grid.
     */
    public int getRow() {
        return id & 0xFFFF;
    }
    
    /**
     * get the column this GridElement lies in the grid.
     * @return the column this GridElement lies in the grid.
     */
    public int getColumn() {
        return (id >> 16) & 0xFFFF;
    }
}
