package lof;

import lof.model.Line;
import lof.model.Player;

/**
 * Interface for objects who make turns and receive turns
 * @author Moritz
 */
public interface ITurnHandler {
    void sendTurn(Line line);
    Line receiveTurn();
    
    Player getPlayer();
}
