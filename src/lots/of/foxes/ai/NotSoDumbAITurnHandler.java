package lots.of.foxes.ai;

import java.util.ArrayList;
import java.util.List;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Moritz
 */
public class NotSoDumbAITurnHandler extends AbstractAITurnHandler {
    
    public NotSoDumbAITurnHandler(Board board, Player player) {
        super(board, player);
    }

    @Override
    public Line receiveTurn() {
        // see if any boxes can be closes
        List<Box> closableBoxes = BoardUtil.filterCloseableBoxes(board);
        if(closableBoxes.size() > 0) {
            Box random3LineBox = BoardUtil.getRandomFromList(closableBoxes);
            List<Line> surroundingLines = BoardUtil.getLinesSurroundingBox(board, random3LineBox);
            return BoardUtil.getRandomAvailableLine(surroundingLines);
        }
                    
        // play boxes with 1 or 0 lines
        List<Box> suboptimalBoxes = BoardUtil.filterBoxes(new ArrayList<>(board.getBoxes()), box -> box.getLineCount() <= 1);
        if(suboptimalBoxes.size() > 0) {
            Box randomBox = BoardUtil.getRandomFromList(suboptimalBoxes);
            return BoardUtil.getRandomAvailableLine(BoardUtil.getLinesSurroundingBox(board, randomBox));
        }
        
        // play something random
        return BoardUtil.getRandomAvailableLine(board);
    }
    
}
