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
 *
 * @author Diego
 */
public class GameFinder implements Runnable{
    
    private static final int BROADCAST_SLEEP_TIME = 1000; //Transmit broadcast every x mili-seconds
    private static final int GAME_TTL = 5;
    private static final int BYTE_DATA_SIZE = 1024;
    private static final String MULTICAST_IP = "203.0.113.0";
    private static final String DISCOVER_MESSAGE = "LOF_DISCOVER";
    
    Thread thread;
    private int port;
    private byte[] sendData = new byte[BYTE_DATA_SIZE];
    private InetAddress group;
    private DatagramPacket sendPacket;
    private ArrayList<String> games = new ArrayList<>();
    
    public GameFinder(int port){
        this.port = port;
        sendData = DISCOVER_MESSAGE.getBytes();
        try{
            group = InetAddress.getByName(MULTICAST_IP);
        }
        catch(UnknownHostException ex){
            System.out.println("Could not resolve Multicast-IP: " + ex);
        }
        sendPacket = new DatagramPacket(sendData, sendData.length, group, this.port);
        if(this.thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        try(DatagramSocket socket = new DatagramSocket(port)){
            AnswerHandler answer = new AnswerHandler(socket);
            new Thread(answer).start();
            while(true){
                socket.send(sendPacket);
                System.out.println("Multicast sent to group: " + group.getHostAddress());
                Thread.sleep(BROADCAST_SLEEP_TIME);
                updateList();
            }
        }
        catch(Exception ex){
            System.out.println("Error while starting socket: " + ex);
        }
    }
    
    public class AnswerHandler implements Runnable{
        
        private DatagramPacket receivePacket;
        private byte[] receiveData = new byte[BYTE_DATA_SIZE];
        private DatagramSocket socket;
        
        public AnswerHandler(DatagramSocket socket){
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            this.socket = socket;
        }
        @Override
        public void run() {
            while(true){
                try {
                    System.out.println("Waiting for answer.");
                    socket.receive(receivePacket);
                    addGame(receivePacket.toString());
                } catch (IOException ex) {
                    System.out.println("Error while receiving UDP Packet: " + ex);
                }
            }
        }
    }
    
    public synchronized void addGame(String game){
        ListIterator itr = games.listIterator();
        boolean newGame = true;
        String currentEntry;
        String currentGame;
        int ttl;
        
        while(itr.hasNext()){
            currentEntry = (String)itr.next();
            ttl = Integer.parseInt("" + currentEntry.charAt
                    (currentEntry.length()-1));
            currentGame = currentEntry.replace(currentEntry
                    .substring(currentEntry.length()-2), "");
            
            if(currentGame.equals(game)){
                itr.set(currentGame + ";0");
                newGame = false;
            }
        }
        if(newGame){
            games.add(game + ";0");
        }
    }
    
    public synchronized void updateList(){
        ListIterator itr = games.listIterator();
        String currentEntry;
        String currentGame;
        int ttl;

        while(itr.hasNext()){
            currentEntry = (String)itr.next();
            ttl = Integer.parseInt("" + currentEntry.charAt
                    (currentEntry.length()-1));
            currentGame = ((String)itr.next()).replace(currentEntry
                    .substring(currentEntry.length()-2), "");
            
            if(ttl > GAME_TTL){
                itr.remove();
            }
            else{
                itr.set(currentGame + ";" + ttl++);
            }
        }
    }
    
    public static void main(String[] args) {
        GameFinder testFinder = new GameFinder(6969);
    }
    
}
