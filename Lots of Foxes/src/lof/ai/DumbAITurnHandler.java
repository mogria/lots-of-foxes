package lof.ai;

import lof.model.Board;
import lof.model.Line;
import lof.model.Player;

/**
 *
 * @author Moritz
 */
public class DumbAITurnHandler extends AbstractAITurnHandler {

    /**
     * Constructor.
     * @param board the board the player plays on
     * @param player the player associated with this ITurnHandler
     */
    public DumbAITurnHandler(Board board, Player player) {
        super(board, player);
    }
    
    /**
     * Called when a turn is requested of this ITurnHandler
     * @return the Line made by this TurnHandler
     */
    @Override
    public Line receiveTurn() {
        return BoardUtil.getRandomAvailableLine(board);
    }


    
}
