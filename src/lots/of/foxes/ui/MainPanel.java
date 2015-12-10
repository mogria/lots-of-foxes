/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lots.of.foxes.GameCreator;
import lots.of.foxes.GameController;
import lots.of.foxes.GameFinder;
import lots.of.foxes.GameType;
import lots.of.foxes.model.GameConfig;
import lots.of.foxes.model.RemoteGameConfig;

/**
 *
 * @author Marcel
 */
public class MainPanel extends JPanel implements Runnable{
    
    private static final int GF_PORT = 6969;
    
    DefaultTableModel dtm;
    Thread thread;
    GameFinder gf;
    JTable gameTable;
    
    public MainPanel(){
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = initButtons();
        add(buttonPanel, BorderLayout.NORTH);
        
        JScrollPane gameTableScroll = initGameTable();
        add(gameTableScroll, BorderLayout.CENTER);
        
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
    
    @Override
    public void run(){
        gf = new GameFinder(GF_PORT);
        while(true){
            try {
                thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int i = dtm.getRowCount() - 1; i > -1; i--) {
                dtm.removeRow(i);
            }
            for(RemoteGameConfig game : gf.getGames()){
                Object[] row = {game.getGameName(), game.getFieldSize(), game.getServerIP(), game.getGameVersion()};
                dtm.addRow(row);
            }
        }
    }
    
    private JPanel initButtons(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);
        
        JButton startButton = new JButton("Start Game");
        JButton loadButton = new JButton("Load Game");
        JButton settingsButton = new JButton("Settings");
        
        buttonPanel.add(startButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(settingsButton);
        
        return buttonPanel;
    }
    
    private JScrollPane initGameTable(){
        String[] columnNames = {"Server",
                                "Fieldsize",
                                "IP",
                                "Version"};
        
        dtm = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        gameTable = new JTable(dtm);
                
        gameTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = gameTable.rowAtPoint(evt.getPoint());
                // row 2 is IP Address, this may change if the columns change
                String[] fieldSize = ((String) dtm.getValueAt(row, 1)).split(" x ");
                String ip = (String) dtm.getValueAt(row, 2);
                
                GameConfig cfg = new GameConfig(Integer.valueOf(fieldSize[0]), Integer.valueOf(fieldSize[1]), ip);
                cfg.setGameType(GameType.REMOTE_CLIENT);
                GameCreator creator = new GameCreator(cfg, GF_PORT);
                GameController controller = creator.buildGameController();
                controller.run();
            }
        });
        return new JScrollPane(gameTable);
    }
}