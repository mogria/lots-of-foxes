package lots.of.foxes.model;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 * @author Moritz
 */
public class Player implements Serializable {
    
    /**
     * the number of the player (either 0, or 1)
     */
    private final int playerNum;

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

    public static final String PROP_BOXCOUNT = "boxCount";


    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Player(int playerNum, String name, Color color) {
        if(playerNum < 0 || playerNum > 1) throw new IllegalArgumentException("playerNum must either be 0 or 1");
        if(name == null) throw new IllegalArgumentException("name cannot be null");
        if(color == null) throw new IllegalArgumentException("color cannot be null");
        this.playerNum = playerNum;
        this.name = name;
        this.color = color;
    }

    /**
     * Get the number of this player
     * @return the number of this player
     */
    public int getPlayerNum() {
        return playerNum;
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
     * Get the value of boxCount
     *
     * @return the value of boxCount
     */
    public int getBoxCount() {
        return boxCount;
    }

    /**
     * increases boxCount by 1
     */
    public void increaseBoxCount() {
        int oldBoxCount = this.boxCount;
        this.boxCount = boxCount + 1;
        propertyChangeSupport.firePropertyChange(PROP_BOXCOUNT, oldBoxCount, boxCount);
    }
    
    
    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }


}
