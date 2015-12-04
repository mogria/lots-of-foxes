/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Player;

/**
 *
 * @author Diego
 */
public class ClientRemoteTurnHandler extends NetworkTurnHandler{

    /**
     * IP Address of the server (host)
     */
    private InetAddress serverIP;
    
    /**
     * The constructor builts the socket and connect to the server
     * 
     * @param board
     * @param player
     * @param serverIP
     * @param port 
     */
    public ClientRemoteTurnHandler(Board board, Player player, InetAddress serverIP, int port) {
        super(board, player);
        this.serverIP = serverIP;
        this.portNumber = port;
        try{
            clientSocket = new Socket(this.serverIP, this.portNumber);
            System.out.println("Connection established.");
        }
        catch(IOException e){
            System.out.println("Exception occured: " + e.getMessage());
        }
    }

    /**
     * Method to close the connection to the server
     */
    public void closeConnection(){
        try {
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Failed while closing sockets: " + ex);
        }
    }
    
}
