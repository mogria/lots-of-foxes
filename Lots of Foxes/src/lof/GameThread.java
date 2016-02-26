/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lof;

import javax.swing.JOptionPane;

import lof.model.GameConfig;
import lof.model.GameType;
import lof.model.RemoteGameConfig;

/**
 *
 * @author Adrian
 */
public class GameThread implements Runnable {

    GameConfig config;

    public GameThread(GameConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        GameCreator creator;
        ServerBroadcast serverBroadcast = null;

        try {
            creator = new GameCreator(config);

            if (config.getGameType() == GameType.REMOTE_HOST) {
                RemoteGameConfig remoteConfig = (RemoteGameConfig) config;
                serverBroadcast
                        = new ServerBroadcast(remoteConfig.getGameName(), remoteConfig.getGameVersion(), remoteConfig.getBoardSizeX(), remoteConfig.getBoardSizeY(), remoteConfig.getPort());

            }

            Thread thread = new Thread(creator.buildGameController(serverBroadcast));
            thread.start();
        } catch (GameCreator.GameCreationException ex) {
            JOptionPane.showMessageDialog(config.getMainFrame(), ex, "Error on creating Game", JOptionPane.ERROR_MESSAGE);
        }

    }

}
