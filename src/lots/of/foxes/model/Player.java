package lots.of.foxes.model;

import java.awt.Color;

/**
 *
 * @author Moritz
 */
public class Player {
       
    /**
     * name of the player
     */
    private String name;
    
    /**
     * the color of this player
     */
    private Color color;
    
    /**
     * the number of boxes owned by this player
     */
    private int boxCount = 0;

    public Player(String name, Color color) {
        if(name == null) throw new IllegalArgumentException("name cannot be null");
        if(color == null) throw new IllegalArgumentException("color cannot be null");
        this.name = name;
        this.color = color;
    }

    /**
     * Get the name of this player
     *
     * @return the name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * Get the color of this player
     * @return the color of this player
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Get the number of boxes owned by this player
     *
     * @returnthe number of boxes owned by this player
     */
    public int getBoxCount() {
        return boxCount;
    }

    /**
     * increases boxCount by 1
     */
    public void increaseBoxCount() {
        boxCount++;
    } 

}
