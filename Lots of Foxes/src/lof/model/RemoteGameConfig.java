/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lof.model;

import java.util.Objects;

/**
 * Represents a Game for the broadcast/discovery and game list/join functionality
 * @author Marcel
 */
public class RemoteGameConfig extends GameConfig{
    
    /**
     * version of the game
     */
    private String gameVersion;
    
    /**
     * ip address under which the server is reachable
     */
    private String serverIP;
    
    /**
     * the port under which the server is reachable
     */
    private int port;
    
    /**
     * gets increased automatically by the game finder, if there is no further response from the server
     */
    private int ttl;
    
    
    /**
     * Constructor
     * 
     * @param boardSizeX
     * @param boardSizeY
     * @param gameName
     * @param gameVersion
     * @param serverIP
     */
    public RemoteGameConfig(String gameName, String gameVersion, int boardSizeX, int boardSizeY, String serverIP, int port){
        super(boardSizeX, boardSizeY, gameName);
        this.gameVersion = gameVersion;
        this.serverIP = serverIP;
        this.port = port;
        this.ttl = 0;
    }

    /**
     * returns a readable String representation of the game
     * @return 
     */
    @Override
    public String toString() {
        return getGameName() + " - " + getServerIP() + " - " + getTtl();
    }
   
    /**
     * returns the field dimensions as String
     * @return 
     */
    public String getFieldSize(){
        return getBoardSizeX() + " x " + getBoardSizeY();
    }
    
    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
