package lots.of.foxes;

import lots.of.foxes.model.Player;
import lots.of.foxes.model.Board;

/**
 * Partially implements the ITurnHandler interface.
 * This includes stuff which every implementation of the ITurnHandler most likely
 * needs
 * @author Moritz
 */
public abstract class AbstractTurnHandler implements ITurnHandler {
    
    /**
     * the board the player plays on
     */
    protected Board board;
    
    /**
     * the player associated with this ITurnHandler
     */
    protected Player player;
    
    /**
     * Constructor.
     * @param board the board the player plays on
     * @param player the player associated with this ITurnHandler
     */
    public AbstractTurnHandler(Board board, Player player) {
        this.board = board;
        this.player = player;
    }
    
    /**
     * returns the player associated with this ITurnHandler
     * @return 
     */
    @Override
    public Player getPlayer() {
        return board.getPlayer(player.getPlayerNum());
    }
    
}
