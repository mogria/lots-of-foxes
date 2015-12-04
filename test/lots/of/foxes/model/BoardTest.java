package lots.of.foxes.model;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for the Board model.
 * 
 * @author Moritz
 */
public class BoardTest {
    
    
    private Board board;
    private Player player;

    /**
     * list of all valid line coordinates for lines on a 3x2 board
     */
    private final int[][] validLineCoordinates = new int[][]{
        {1, 0},
        {3, 0},
        {5, 0},
        {0, 1},
        {2, 1},
        {4, 1},
        {6, 1},
        {1, 2},
        {3, 2},
        {5, 2},
        {0, 3},
        {2, 3},
        {4, 3},
        {6, 3},
        {1, 4},
        {3, 4},
        {5, 4}
    };

    /**
     * list of all valid box coordinates for lines on a 3x2 board
     */
    private final int[][] validBoxCoordinates = new int[][]{
        {1, 1},
        {3, 1},
        {5, 1},
        {1, 3},
        {3, 3},
        {5, 3},};

    @Before
    public void setUp() {
        board = new Board(3, 2);
        player = new Player("TestPlayer", Color.BLACK);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getLines method, of class Board.
     */
    @Test
    public void testGetLines() {
        System.out.println("getLines");
        Collection<Line> lines = board.getLines();

        assertEquals((3 * 2) * 2 + 3 + 2, lines.size());

        lines.stream().forEach(line -> {
            assertTrue(
                    Arrays.stream(validLineCoordinates).anyMatch(coords -> coords[0] == line.getColumn()
                            && coords[1] == line.getRow())
            );
        });
    }

    /**
     * Test of getBoxes method, of class Board.
     */
    @Test
    public void testGetBoxes() {
        System.out.println("getBoxes");
        Collection<Box> lines = board.getBoxes();

        assertEquals((3 * 2), lines.size());
    }

    /**
     * Test of playLine method, of class Board.
     */
    @Test
    public void testPlayLine() {
        System.out.println("playLine");
        Line line = board.getLines().stream().findAny().get();
        boolean result = board.playLine(player, line.getId());
        assertEquals(true, result);
        assertNotNull(line.getOwner());
    }
    
    /**
     * Test of playLine method, of class Board.
     */
    @Test
    public void testPlayAllLinesForABox() {
        System.out.println("playLine");
        Line[] lines = {
            board.getLineByCoordinate(0, 1),
            board.getLineByCoordinate(1, 0),
            board.getLineByCoordinate(2, 1),
            board.getLineByCoordinate(1, 2)
        };
        
        Arrays.stream(lines).forEach(line -> {
            assertEquals(true, board.playLine(player, line.getId()));
            assertNotNull(line.getOwner());
        });
        
        Box box = Arrays.stream(lines[0].getAdjacentBoxes())
                .filter(line -> line != null)
                .findFirst().get();
        box.getOwner();
    }

    /**
     * Test of isBoardFull method, of class Board.
     */
    @Test
    public void testIsBoardFull() {
        System.out.println("isBoardFull");
        assertEquals(false, board.isBoardFull());
        Random rand = new Random();
        board.getLines().stream().filter(line -> rand.nextBoolean()).forEach(line -> {
            board.playLine(player, line);
        });
        assertEquals(false, board.isBoardFull());
        board.getLines().stream().filter(line -> line.getOwner() != null).forEach(line -> {
            board.playLine(player, line);
        });

        assertEquals(false, board.isBoardFull());
    }

    /**
     * Test of getBoxByCoordinate method, of class Board.
     */
    @Test
    public void testGetBoxByCoordinate() {
        System.out.println("getBoxByCoordinate");
        Arrays.stream(validBoxCoordinates).forEach(coords -> {
            Box box = board.getBoxByCoordinate(coords[0], coords[1]);
            assertNotNull(box);
            assertEquals(coords[0], box.getColumn());
            assertEquals(coords[1], box.getRow());
        });
        assertNull(board.getBoxByCoordinate(0, 0));
    }

    /**
     * Test of getLineByCoordinate method, of class Board.
     */
    @Test
    public void testGetLineByCoordinate() {
        System.out.println("getLineByCoordinate");
        Arrays.stream(validLineCoordinates).forEach(coords -> {
            Line line = board.getLineByCoordinate(coords[0], coords[1]);
            assertNotNull(line);
            assertEquals(coords[0], line.getColumn());
            assertEquals(coords[1], line.getRow());
        });
        assertNull(board.getBoxByCoordinate(0, 0));
    }

}
