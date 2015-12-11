package lots.of.foxes.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.Line;

/**
 * 
 * @author Moritz
 */
public class BoardUtil {
    
    public static <T> T getRandomFromList(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }
    
    public static List<Line> filterAvailableLines(List<Line> lines) {
        return lines.stream()
                .filter(line -> line.getOwner() == null)
                .collect(Collectors.toList());
    }
    
    public static Line getRandomAvailableLine(Board board) {
        return BoardUtil.getRandomFromList(
            BoardUtil.filterAvailableLines(new ArrayList<>(board.getLines()))
        );
    }
    
    public static List<Box> getCloseableBoxes(Board board) {
        return board.getBoxes().stream().filter(box -> {
           return box.getLineCount() == 3;
        }).collect(Collectors.toList());
    }
    
    public static List<Line> getLinesSurroundingBox(Board board, Box box) {
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
}
