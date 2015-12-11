package lots.of.foxes.ai;

import java.util.ArrayList;
import java.util.List;
import lots.of.foxes.AbstractTurnHandler;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 * implements an empty sendTurn() method by making a list of all the enemy turns.
 * 
 * @author Moritz
 */
public abstract class AbstractAITurnHandler extends AbstractTurnHandler {
    private List<Line> enemyLines;

    public AbstractAITurnHandler(Board board, Player player) {
        super(board, player);
        enemyLines = new ArrayList<>(enemyLines);
    }

    public List<Line> getEnemyLines() {
        return enemyLines;
    }
    
    @Override
    public void sendTurn(Line line) {
        enemyLines.add(line);
    }
    
}
