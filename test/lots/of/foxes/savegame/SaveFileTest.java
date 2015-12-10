package lots.of.foxes.savegame;

import java.awt.Color;
import java.io.IOException;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for a SaveFile
 * @author Moritz
 */
public class SaveFileTest {
    
    private SaveFile saveFile;
    private Board board;
    private Player player = new Player(0, "test player", Color.BLACK);
    
    @Before
    public void setUp() {
        saveFile = new SaveFile("test-save-game");
        board = new Board(3, 3);
        board.setPlayer(player);
    }
    
    @After
    public void tearDown() {
        saveFile.delete();
    }

    /**
     * Test of exists method, of class SaveFile.
     * @throws java.io.IOException
     */
    @Test
    public void testExists() throws IOException {
        System.out.println("exists");
        assertFalse(saveFile.exists());
    }

    /**
     * Test of save method, of class SaveFile.
     */
    @Test
    public void testSave() throws IOException {
        System.out.println("save");
        saveFile.save(board);
        assertTrue(saveFile.exists());
    }

    /**
     * Test of load method, of class SaveFile.
     * @throws java.io.IOException
     */
    @Test
    public void testLoad() throws IOException {
        System.out.println("load");
        board.playLine(player, board.getLineByCoordinate(0, 1));
        saveFile.save(board);
        Board loadedBoard = saveFile.load();
        assertNull(loadedBoard.getLineByCoordinate(0, 0));
        assertNotNull(loadedBoard.getLineByCoordinate(0, 1));
        assertNotNull(loadedBoard.getLineByCoordinate(0, 1).getOwner());
    }

    /**
     * Test of delete method, of class SaveFile.
     * @throws java.io.IOException
     */
    @Test
    public void testDelete() throws IOException {
        System.out.println("delete");
        
        saveFile.save(board);
        assertTrue(saveFile.delete());
        assertFalse(saveFile.exists());
    }
    
}
