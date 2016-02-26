/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lof;

import java.io.IOException;
import java.net.ServerSocket;

import lof.model.Board;
import lof.model.Player;

/**
 *
 * @author Diego
 */
public class HostRemoteTurnHandler extends NetworkTurnHandler {

    /**
     * Server socket, used to host the connection
     */
    private ServerSocket serverSocket;

    /**
     * The constructor builts the server socket and waits for the connection try of the client.
     * 
     * @param port
     * @param board
     * @param player 
     */
    public HostRemoteTurnHandler(int port, Board board, Player player,ServerBroadcast sb) {
        super(board, player);
        this.portNumber = port;
        try{
            serverSocket = new ServerSocket(this.portNumber);
            System.out.println("Server startet.");
            clientSocket = serverSocket.accept();
            sb.stop();
            System.out.println("Connection established.");
        }
        catch(IOException e){
            System.out.println("Exception occured: " + e.getMessage());
        }
    }

    /**
     * Method to close the connection to the client
     */
    public void closeConnection(){
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Failed while closing sockets: " + ex);
        }
    }

}
