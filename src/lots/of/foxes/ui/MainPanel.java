/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lots.of.foxes.GameFinder;

/**
 *
 * @author Dethrall
 */
public class MainPanel extends JPanel implements Runnable{
    
    private static final int GF_PORT = 6969;
    
    DefaultTableModel dfm;
    Thread thread;
    GameFinder gf;
    
    public MainPanel(){
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = initButtons();
        add(buttonPanel, BorderLayout.NORTH);
        
        JScrollPane gameTableScroll = iniGameTable();
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
            // TODO: add Games to table
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
    
    private JScrollPane iniGameTable(){
        String[] columnNames = {"Server",
                                "Fieldsize",
                                "Version",
                                "Join"};
        
        dfm = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        JTable gameTable = new JTable(dfm);
                
        return new JScrollPane(gameTable);
    }
}