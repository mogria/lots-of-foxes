/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
public class ClientRemoteTurnHandlerTest {
    
    ClientRemoteTurnHandler clientHandler;
    Player player;
    Board board;
    Line line;
    int sizeX = 9;
    int sizeY = 9;
    int lineID = 70;
    Box box7;
    Box box8;
    InetAddress ip;
    String testIP = "192.168.1.1";
    
    public ClientRemoteTurnHandlerTest() {
        try{
            ip = InetAddress.getByName(testIP);
        }
        catch(UnknownHostException ex){
            System.out.println("Host not found: " + ex);
        }
        player = new Player("Marcel", Color.yellow);
        board = new Board(sizeX, sizeY);
        clientHandler = new ClientRemoteTurnHandler(board, player, ip, sizeX);
        box7 = new Box(7);
        box8 = new Box(8);
        line = new Line(lineID, box7, box8);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        clientHandler.closeConnection();
    }
    
    @Test
    public void receiveTurnTest(){
        line = clientHandler.receiveTurn();
        assertEquals(70, line.getId());
    }
    
    @Test
    public void sendTurnTest(){
        clientHandler.sendTurn(line);
    }
    
}
