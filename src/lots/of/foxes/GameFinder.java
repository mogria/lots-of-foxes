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
    private static final String BROADCAST_IP = "255.255.255.255";
    private static final String DISCOVER_MESSAGE = "LOF_DISCOVER";
    private static final String RESPONSE_MESSAGE = "LOF_RESPONSE";
    private static final int RESPONSE_OFFSET = 12;
    
    Thread thread;
    private int port;
    private byte[] sendData = new byte[BYTE_DATA_SIZE];
    private InetAddress group;
    private DatagramPacket sendPacket;
    private ArrayList<String> games = new ArrayList<>();
    private DatagramSocket socket;
    
    public GameFinder(int port){
        this.port = port;
        sendData = DISCOVER_MESSAGE.getBytes();
        try{
            group = InetAddress.getByName(BROADCAST_IP);
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
        try{
            socket = new DatagramSocket(port);
            socket.setBroadcast(true);
            AnswerHandler answer = new AnswerHandler();
            new Thread(answer).start();
            while(true){
                socket.send(sendPacket);
                System.out.println("Broadcast sent to " + group.getHostAddress());
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
    
    public class AnswerHandler implements Runnable{
        
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
                    //if(message[0].equals(RESPONSE_MESSAGE)){
                        System.out.println("received: ");
                        addGame(new String(receivePacket.getData()));
                    //}
                } catch(IOException ex) {
                    ex.printStackTrace();
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
        
        if(!games.isEmpty()){
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
        
        if(!games.isEmpty()){
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
    }
    
    public static void main(String[] args) {
        GameFinder testFinder = new GameFinder(6969);
    }
    
}
