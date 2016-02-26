/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Marcel
 */
public final class LofFrame extends JFrame{
    private static final String VERSION = "6.9";
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    
    private JPanel contentPanel;
    
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LofFrame().setVisible(true);
            }
        });
    }
    
    public LofFrame(){
        super("Lots Of Foxes v" + VERSION);
        this.contentPanel = null;
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
                
        setContentPanel(new MainPanel());
    }
    
    public void setContentPanel(JPanel newPanel) {
        if(contentPanel != null) {
            remove(contentPanel);
        }
        contentPanel = newPanel;
        add(contentPanel);
    }
}
