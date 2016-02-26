package lof.ai;

import java.util.ArrayList;
import java.util.List;

import lof.AbstractTurnHandler;
import lof.model.Board;
import lof.model.Line;
import lof.model.Player;

/**
 * implements an empty sendTurn() method by making a list of all the enemy turns.
 * 
 * @author Moritz
 */
public abstract class AbstractAITurnHandler extends AbstractTurnHandler {
    private List<Line> enemyLines;

    public AbstractAITurnHandler(Board board, Player player) {
        super(board, player);
        enemyLines = new ArrayList<>();
    }

    public List<Line> getEnemyLines() {
        return enemyLines;
    }
    
    @Override
    public void sendTurn(Line line) {
        enemyLines.add(line);
    }
    
}
