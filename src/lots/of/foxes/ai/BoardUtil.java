package lots.of.foxes.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
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
    
    public static List<Box> filterAvailableBoxes(List<Box> boxes) {
        return boxes.stream()
                .filter(box -> box.getOwner() == null)
                .collect(Collectors.toList());
    }
    
    public static <T> List<T> filterOutElements(List<T> listToFilter, List<T> elementsToRemove) {
        return listToFilter.stream()
                .filter(e -> !elementsToRemove.contains(e))
                .collect(Collectors.toList());
    }
        
    
    public static Line getRandomAvailableLine(Board board) {
        return BoardUtil.getRandomFromList(
            BoardUtil.filterAvailableLines(new ArrayList<>(board.getLines()))
        );
    }

    public static List<Box> filterBoxes(List<Box> boxes, Predicate<Box> predicate) {
        return boxes.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
    
    public static List<Box> getBoxesWithLineCount(Board board, int numberOfLines) {
        return filterBoxes(new ArrayList<>(board.getBoxes()), box -> box.getLineCount() == numberOfLines);
    }
    
    public static List<Box> getCloseableBoxes(Board board) {
        return getBoxesWithLineCount(board, 3);
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
    
    public static List<Box> getSurroundingBoxes(Board board, Box box) {
        int x = box.getColumn();
        int y = box.getRow();
        
        int surroundingBoxes[][] = {
            {x - 2, y},
            {x + 2, y},
            {x, y - 2},
            {x, y + 2}
        };
        
        return Arrays.stream(surroundingBoxes)
                .map(coords -> board.getBoxByCoordinate(coords[0], coords[1]))
                .filter(b -> b != null)
                .collect(Collectors.toList());
    }
    
    public static List<Box> getChainableSurroundingBoxes(Board board, Box box) {
        return getSurroundingBoxes(board, box).stream()
                .filter(neighbor -> isLineAvailableBetweenBoxes(board, box, neighbor))
                .collect(Collectors.toList());
    }
    
    public static boolean areNeighborBoxes(Box box1, Box box2) {
        return (box1.getRow()    == box2.getRow()    && Math.abs(box1.getColumn() - box2.getColumn()) == 2)
            || (box1.getColumn() == box2.getColumn() && Math.abs(box1.getRow()    - box2.getRow()   ) == 2);
    }
    
    public static boolean isLineAvailableBetweenBoxes(Board board, Box box1, Box box2) {
        if(!areNeighborBoxes(box1, box2)) return false;
        int x = (box1.getColumn() + box2.getColumn()) / 2;
        int y = (box1.getRow() + box2.getRow()) / 2;
        Line line = board.getLineByCoordinate(x, y);
        return line != null && line.getOwner() == null;
    }
    
    
    public static List<Box> getBorderBoxes(Board board) {
        return board.getBoxes().stream()
                .filter(box -> box.getColumn() == 1 || box.getColumn() == board.getGridSizeX() - 2
                            || box.getRow()    == 1 || box.getRow()    == board.getGridSizeY() - 2)
                .collect(Collectors.toList());
    }
    
    public static List<List<Box>> getChains(Board board) {
        /* List<Box> boxes = filterAvailableBoxes(getBorderBoxes(board));
        List<List<Box>> chains = new ArrayList<>();
        while(boxes.size() > 0) {
            Deque<Box> chainBoxes = new LinkedList<>();
            chainBoxes.()
                    
                    
            List<Chain> = new ArrayList<
            chains.add(boxes)
            boxes = filterOutElements(boxes,);
        }
        getBorderBoxes(board).stream()
                .forEach(box -> {
                    box.
                }); */
        return new ArrayList<>();
    }
    

}
