/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.model;

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
    public RemoteGameConfig(String gameName, String gameVersion, int boardSizeX, int boardSizeY, String serverIP){
        super(boardSizeX, boardSizeY, gameName);
        this.gameVersion = gameVersion;
        this.serverIP = serverIP;
        this.ttl = 0;
    }

    @Override
    public String toString() {
        return getGameName() + " - " + getServerIP() + " - " + getTtl();
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
}
