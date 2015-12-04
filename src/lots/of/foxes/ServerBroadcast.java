/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The class ServerBroadcast waits for requests from clients and answers with all needed IPs.
 * 
 * @author Diego
 */
public class ServerBroadcast implements Runnable{
    private static final String DISCOVER_MESSAGE = "LOF_DISCOVER                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    "; //Message for receiving broadcast from GameFinder (same as in Class GameFinder)
    private static final String RESPONSE_MESSAGE = "LOF_RESPONSE"; //Message for sending broadcast to GameFinder (same as in Class GameFinder)
    private static final String BROADCAST_IP = "255.255.255.255"; //Broadcast IP
    private static final int BYTE_DATA_SIZE = 1024; //size of the UDP packets
    
    Thread thread;
    private int port; //port for datagram socket
    private byte[] sendData = new byte[BYTE_DATA_SIZE]; //data of the UDP packet (sending)
    private byte[] receiveData = new byte[BYTE_DATA_SIZE]; //data of the UDP packet (receiving)
    private InetAddress broadcastIP; //IP variable
    private InetAddress myIP; //IP of the own client
    private DatagramPacket sendPacket; //Packet to send
    private DatagramPacket receivePacket; //Packet to receive
    private String gameName; //Name of the game
    private String gameVersion; //Version of the application
    private String gameField; //Size of the game field
    
    /**
     * The constructor sets all instance variables, build the packets and starts the thread.
     * 
     * @param gameName
     * @param gameVersion
     * @param gameField
     * @param port 
     */
    public ServerBroadcast(String gameName, String gameVersion, 
                String gameField, int port){
        
        this.gameName = gameName;
        this.gameVersion = gameVersion;
        this.gameField = gameField;
        this.port = port;
        try{
            broadcastIP = InetAddress.getByName(BROADCAST_IP);
        }
        catch(UnknownHostException ex){
            System.out.println("Could not resolve Broadcast: " + ex);
        }
        try{
            myIP = InetAddress.getLocalHost();
        }
        catch(UnknownHostException ex){
            System.out.println("Could not find localhost: " + ex);
        }
        sendData = (RESPONSE_MESSAGE + ";" + gameName + ";" + gameVersion + ";" + gameField + ";" + myIP).getBytes();
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        if(this.thread == null){
            this.thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * This method handles the Thread. The routine waits for a request from a client in the LAN and answers with the details.
     */
    @Override
    public void run(){
        try(DatagramSocket socket = new DatagramSocket(port)){
            socket.setBroadcast(true);
            while(true){
                System.out.println("Wait for Client.");
                socket.receive(receivePacket);
                String s = new String(receivePacket.getData());
                //Only answers to requests with the correct statement.
                if(s.equals(DISCOVER_MESSAGE)){
                    System.out.println("Got " + DISCOVER_MESSAGE + " from Client " + receivePacket.getAddress().getHostAddress());
                    sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    socket.send(sendPacket);
                    System.out.println("Sent " + RESPONSE_MESSAGE + " to Client " + receivePacket.getAddress().getHostAddress());
                }
            }
        }
        catch(Exception ex){
            System.out.println("Error while starting socket: " + ex);
        }
    }
    
    public static void main(String[] args) {
        ServerBroadcast server1 = new ServerBroadcast("Homo", "Homo", "Homo", 6969);
    }
    
}
