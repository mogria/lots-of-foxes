/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.awt.Color;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Diego
 */
public class HostRemoteTurnHandlerTest {
    
    HostRemoteTurnHandler hostHandler;
    Player player;
    Board board;
    Line line;
    int sizeX = 9;
    int sizeY = 9;
    int lineID = 60;
    Box box1;
    Box box2;
    
    public HostRemoteTurnHandlerTest() {
        player = new Player("Diego", Color.yellow);
        board = new Board(sizeX, sizeY);
        hostHandler = new HostRemoteTurnHandler(6969, board, player);
        box1 = new Box(1);
        box2 = new Box(2);
        line = new Line(lineID, box1, box2);
    }

    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        hostHandler.closeConnection();
    }
    
    @Test
    public void sendTurnTest(){
        hostHandler.sendTurn(line);
    }
    
    @Test
    public void receiveTurnTest(){
        line = hostHandler.receiveTurn();
        assertEquals(70, line.getId());
    }
}
