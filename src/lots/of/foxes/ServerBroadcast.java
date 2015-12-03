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
 *
 * @author Diego
 */
public class ServerBroadcast implements Runnable{
    private static final String DISCOVER_MESSAGE = "LOF_DISCOVER";
    
    Thread thread;
    private int port;
    private byte[] sendData = new byte[1024];
    private byte[] receiveData = new byte[1024];
    private InetAddress group;
    private InetAddress myIP;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;
    private String gameName;
    private String gameVersion;
    private String gameField;
    
    public ServerBroadcast(String gameName, String gameVersion, 
                String gameField, int port){
        
        this.gameName = gameName;
        this.gameVersion = gameVersion;
        this.gameField = gameField;
        this.port = port;
        try{
            group = InetAddress.getByName("203.0.113.0");
        }
        catch(UnknownHostException ex){
            System.out.println("Could not resolve Multicast-IP: " + ex);
        }
        try{
            myIP = InetAddress.getLocalHost();
        }
        catch(UnknownHostException ex){
            System.out.println("Could not find localhost: " + ex);
        }
        sendData = ("LOF_RESPONSE;" + gameName + ";" + gameVersion + ";" 
                    + gameField + ";" + myIP).getBytes();
        sendPacket = new DatagramPacket(sendData, sendData.length, group, this.port);
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        if(this.thread == null){
            this.thread = new Thread(this);
            thread.start();
        }
    }
    
    @Override
    public void run(){
        try(DatagramSocket socket = new DatagramSocket(port)){
            while(true){
                socket.receive(receivePacket);
                if(receivePacket.toString().equals(DISCOVER_MESSAGE)){
                    socket.send(sendPacket);
                }
            }
        }
        catch(Exception ex){
            System.out.println("Error while starting socket: " + ex);
        }
    }
    
}
