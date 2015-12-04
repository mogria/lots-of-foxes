/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.model;

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
     * Constructor which sets all attributes except ttl
     * @param name
     * @param boardSizeX
     * @param boardSizeY
     * @param gameVersion 
     */
    public RemoteGameConfig(String name, int boardSizeX, int boardSizeY, String gameName, String gameVersion){
        super(boardSizeX, boardSizeY, gameName);
        this.gameVersion = gameVersion;
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
