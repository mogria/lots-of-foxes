/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * The Class GameFinder sends Broadcasts every second to the local network to notify game servers. The embedded class AnswerHandler waits for the answers of every server.
 * 
 * @author Diego
 */
public class GameFinder implements Runnable{
    
    private static final int BROADCAST_SLEEP_TIME = 1000; //Time in ms to wait for the next broadcast
    private static final int GAME_TTL = 5; //Time to live, till the game will be deleted in the list. MAX: 9
    private static final int BYTE_DATA_SIZE = 1024; //size of the UDP packets
    private static final String BROADCAST_IP = "255.255.255.255"; //Broadcast IP
    private static final String DISCOVER_MESSAGE = "LOF_DISCOVER"; //Message for sending broadcast (same as in Class ServerBroadcast)
    private static final String RESPONSE_MESSAGE = "LOF_RESPONSE"; //Message for receiving broadcast (same as in Class ServerBroadcast)
    private static final int RESPONSE_OFFSET = 12; //length of 'LOF_RESPONSE'
    
    Thread thread;
    private int port; //port for datagram socket
    private byte[] sendData = new byte[BYTE_DATA_SIZE]; //data of the UDP packet
    private InetAddress broadcastIP; //IP variable
    private DatagramPacket sendPacket; //Packet to send
    private ArrayList<String> games = new ArrayList<>(); //list of available games
    // Entry structure: RESPONSE_MESSAGE;name of the game;version of the application;size of the gamefield;IP of the server;time to live
    private DatagramSocket socket; //Datagram socket for UDP communication
    
    /**
     * The constructor set the port, builds the datagram packet and starts the object as a thread.
     * 
     * @param port 
     */
    public GameFinder(int port){
        this.port = port;
        sendData = DISCOVER_MESSAGE.getBytes();
        try{
            broadcastIP = InetAddress.getByName(BROADCAST_IP);
        }
        catch(UnknownHostException ex){
            System.out.println("Could not resolve Multicast-IP: " + ex);
        }
        sendPacket = new DatagramPacket(sendData, sendData.length, broadcastIP, this.port);
        if(this.thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * This method handles the Thread. It starts also the a new Thread of the class AnswerHandler and then starts sending broadcasts to the network.
     */
    @Override
    public void run() {
        try{
            socket = new DatagramSocket(port);
            socket.setBroadcast(true);
            AnswerHandler answer = new AnswerHandler();
            new Thread(answer).start();
            while(true){
                //This loop sends a multicast, update the game list and waits defined time.
                socket.send(sendPacket);
                //System.out.println("Broadcast sent to " + group.getHostAddress());
                Thread.sleep(BROADCAST_SLEEP_TIME);
                updateList();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Error while starting socket: " + ex);
        }
        finally{             
            socket.close();
        }
    }
    
    /**
     * This class handles all answers receiving from other clients. The clients are saved in the ArrayList 'games'.
     */
    public class AnswerHandler implements Runnable{
        
        /**
         * The object runs a thread, which listens to the open datagram socket and safes all answers to the LOF_DISCOVER message.
         */
        @Override
        public void run() {
            byte[] receiveData;
            DatagramPacket receivePacket;
            while(true){
                receiveData = new byte[BYTE_DATA_SIZE];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    socket.receive(receivePacket);
                    String s = new String(receivePacket.getData());
                    String[] message = s.split(";");
                    //Only Messages with the correct answer statement will be safed in the ArrayList games.
                    if(message[0].equals(RESPONSE_MESSAGE)){
                        //System.out.println("received: " + message[0]);
                        addGame(new String(receivePacket.getData()));
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Error while receiving UDP Packet: " + ex);
                }
            }
        }
    }
    
    /**
     * This method adds or updates the ArrayList games with a given game.
     * 
     * @param game 
     */
    public synchronized void addGame(String game){
        ListIterator<String> itr = games.listIterator();
        boolean newGame = true;
        String currentEntry;
        String currentGame;
        int ttl;
        
        //if the list is empty, the new game can just be added without checkin, if it's already exists.
        if(!games.isEmpty()){
            while(itr.hasNext()){
                currentEntry = itr.next();
                //ttl is safed at the last position of the game entry and only one char. 
                ttl = Integer.parseInt("" + currentEntry.charAt
                        (currentEntry.length()-1));
                //currentGame is the entry without the ttl field
                currentGame = currentEntry.substring(0, (currentEntry.length()-2));
                
                //if the current entry is the same as the given game, the entry will be updated and ttl will be reseted.
                if(currentGame.equals(game)){
                    itr.set(currentGame + ";0");
                    newGame = false;
                }
            }
        }
        //if newGame is true, the game will be added at the end of the ArrayList games.
        if(newGame){
            games.add(game + ";0");
        }
    }
    
    /**
     * This method loops the ArrayList games and increments the ttl field.
     */
    public synchronized void updateList(){
        ListIterator<String> itr = games.listIterator();
        String currentEntry;
        String currentGame;
        int ttl;
        
        if(!games.isEmpty()){
            while(itr.hasNext()){
                currentEntry = itr.next();
                //ttl is safed at the last position of the game entry and only one char. 
                ttl = Integer.parseInt("" + currentEntry.charAt
                        (currentEntry.length()-1));
                //currentGame is the entry without the ttl field
                currentGame = currentEntry.substring(0, (currentEntry.length()-2));
                System.out.println(currentEntry);
                
                //if ttl reached the MAX ttl, the entry will be deleted
                if(ttl >= GAME_TTL){
                    itr.remove();
                }
                else{
                    itr.set(currentGame + ";" + (ttl+1));
                }
            }
        }
    }
    
    public static void main(String[] args) {
        GameFinder testFinder = new GameFinder(6969);
    }
    
}
