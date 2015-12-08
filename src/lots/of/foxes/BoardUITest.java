/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import lots.of.foxes.model.Board;


/**
 *
 * @author Adrian
 */
public class BoardUITest extends JFrame {

    public BoardUITest() throws HeadlessException {
        this.setLayout(new GridBagLayout());
        this.setSize(1000, 1000);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Board b = new Board(5, 5);
        

        //BoardUI bui = new BoardUI(b.getLines(), b.getBoxes(),b.getGridSizeX(),b.getGridSizeY(), 10, 50);
        BoardUI bui = new BoardUI(b,10 , 50);
        bui.setVisible(true);
        this.add(bui);
        
      // this.repaint();
    }

    public static void main(String args[]) {

        BoardUITest bt = new BoardUITest();
        bt.setVisible(true);
    }
}
