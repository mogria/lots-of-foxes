package lots.of.foxes.ai;

import java.util.Random;
import lots.of.foxes.AbstractTurnHandler;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Moritz
 */
public class DumbAITurnHandler extends AbstractTurnHandler {

    /**
     * Constructor.
     * @param board the board the player plays on
     * @param player the player associated with this ITurnHandler
     */
    public DumbAITurnHandler(Board board, Player player) {
        super(board, player);
    }
    
    /**
     * Called when the enemy made a turn
     * @param line the line the enemy made
     */
    @Override
    public void sendTurn(Line line) {
        // do nothing, the board is already updated by the game controller
    }

    /**
     * Called when a turn is requested of this ITurnHandler
     * @return the Line made by this TurnHandler
     */
    @Override
    public Line receiveTurn() {
        Line[] availableLines = (Line[])board.getLines().stream()
                .filter(line -> line.getOwner() != null).toArray();
        return availableLines[new Random().nextInt(availableLines.length)];
    }


    
}
