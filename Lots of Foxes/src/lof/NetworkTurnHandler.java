/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lof;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import lof.model.Board;
import lof.model.Line;
import lof.model.Player;

/**
 * The abstract class NetworkTurnHandler handles communication between the two players. 
 * Sending and receiving a turn is the same for host and client.
 * 
 * @author Diego
 */
public abstract class NetworkTurnHandler extends AbstractTurnHandler{

    /**
     * socket for the TCP connection. Used from client AND server
     */
    protected Socket clientSocket;
    
    /**
     * portnumber for the TCP connection
     */
    protected int portNumber;
    
    /**
     * 
     * @param board
     * @param player 
     */
    public NetworkTurnHandler(Board board, Player player) {
        super(board, player);
    }
    
    /**
     * This method sends a line object to the other player.
     * 
     * @param line 
     */
    @Override
    public void sendTurn(Line line) {
        try
        {
            ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            //line object will be serialized and sent
            outStream.writeObject(line);
        }
        catch(IOException ex){
            System.out.println("Error in Method sendTurn: " + ex);
        }
    }
    
    /**
     * This method receives a line object to the other player.
     * 
     * @return Line-object
     */
    @Override
    public Line receiveTurn() {
        Line line = new Line(0, null, null);
        try
        {
            ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
            //line object will be received
            line = (Line)inStream.readObject();
            System.out.println("Received Turn: " + line);
        }
        catch(IOException ex){
            System.out.println("Error in Method receiveTurn: " + ex);
        } 
        catch (ClassNotFoundException ex) {
            System.out.println("Error in Method receiveTurn: " + ex);
        }
        return line;
    }
    
}
