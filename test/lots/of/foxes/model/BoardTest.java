/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mogria
 */
public class BoardTest {
    
    private Board board;
    private Player player;
    
    private int[][] validLineCoordinates = new int[][]{
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
        {5, 4},
        {0, 5},
        {2, 5},
        {4, 5},
        {6, 5},
    };
    
    private int[][] validBoxCoordinates = new int[][]{
        {1, 1},
        {3, 1},
        {5, 1},
        {1, 3},
        {3, 3},
        {5, 3},
    };
    
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
        boolean result = board.playLine(player, line.getLineId());
        assertEquals(true, result);
        assertNotNull(line.getOwner());
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
        int x = 0;
        int y = 0;
        Board instance = null;
        Box expResult = null;
        Box result = instance.getBoxByCoordinate(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLineByCoordinate method, of class Board.
     */
    @Test
    public void testGetLineByCoordinate() {
        System.out.println("getLineByCoordinate");
        int x = 0;
        int y = 0;
        Board instance = null;
        Line expResult = null;
        Line result = instance.getLineByCoordinate(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}
