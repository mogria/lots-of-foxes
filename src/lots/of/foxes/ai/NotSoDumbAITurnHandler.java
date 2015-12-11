package lots.of.foxes.ai;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lots.of.foxes.AbstractTurnHandler;
import lots.of.foxes.ITurnHandler;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Moritz
 */
public class NotSoDumbAITurnHandler extends AbstractTurnHandler {
    
    ITurnHandler dumbAIHandler;
    
    public NotSoDumbAITurnHandler(Board board, Player player) {
        super(board, player);
        dumbAIHandler = new DumbAITurnHandler(board, player);
    }
    
    private List<Box> getCloseableBoxes() {
        return board.getBoxes().stream().filter(box -> {
           return box.getLineCount() == 3;
        }).collect(Collectors.toList());
    }
    
    private List<Line> getLinesSurroundingBox(Box box) {
        int x = box.getColumn();
        int y = box.getRow();
        
        int surroundingLines[][] = {
            {x - 1, y},
            {x + 1, y},
            {x, y - 1},
            {x, y + 1}
        };
        
        return Arrays.stream(surroundingLines)
                .map(coords -> board.getLineByCoordinate(coords[0], coords[1]))
                .filter(line -> line != null)
                .collect(Collectors.toList());
    }
    

    @Override
    public void sendTurn(Line line) {
        dumbAIHandler.sendTurn(line);
    }

    @Override
    public Line receiveTurn() {
        List<Box> closableBoxes = getCloseableBoxes();
        if(closableBoxes.size() > 0) {
            List<Line> closingLines = getLinesSurroundingBox(closableBoxes.get(0));
            if(closingLines.size() > 0) {
                return closingLines.get(0);
            }
        }
        return dumbAIHandler.receiveTurn();
    }
    
}
