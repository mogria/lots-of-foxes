/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.awt.Color;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Diego
 */
public class ClientRemoteTurnHandler implements ITurnHandler{
    
    @Override
    public Player getPlayer() {
        return new Player("Homo", Color.yellow);
    }

    @Override
    public void sendTurn(Line line) {
        
    }

    @Override
    public Line receiveTurn() {
        return new Line(1, null, null);
    }
    
}
