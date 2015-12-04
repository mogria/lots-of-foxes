package lots.of.foxes;

import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 * Interface for objects who make turns and receive turns
 * @author Moritz
 */
public interface ITurnHandler {
    void sendTurn(Line line);
    Line receiveTurn();
    
    Player getPlayer();
}
