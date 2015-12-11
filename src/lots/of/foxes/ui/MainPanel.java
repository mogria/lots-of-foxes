package lots.of.foxes.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import lots.of.foxes.GameCreator;
import lots.of.foxes.GameController;
import lots.of.foxes.GameFinder;
import lots.of.foxes.GameThread;
import lots.of.foxes.ServerBroadcast;
import lots.of.foxes.model.GameType;
import lots.of.foxes.model.GameConfig;
import lots.of.foxes.model.LocalGameConfig;
import lots.of.foxes.model.RemoteGameConfig;
import sun.swing.SwingUtilities2;

/**
 *
 * @author Marcel
 */
public class MainPanel extends JPanel implements Runnable {

    private static final int UDP_PORT = 6969;
    private static final int TCP_PORT = 6969;

    DefaultTableModel dtm;
    Thread thread;
    GameFinder gf;
    JTable gameTable;
    boolean shouldSearchforClients = true;

    JButton createLocalGameButton = new JButton("Create Local Game");

    public MainPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = initButtons();
        add(buttonPanel, BorderLayout.NORTH);

        JScrollPane gameTableScroll = initGameTable();
        add(gameTableScroll, BorderLayout.CENTER);

        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        gf = new GameFinder(UDP_PORT);
        while (shouldSearchforClients) {
            try {
                thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = dtm.getRowCount() - 1; i > -1; i--) {
                dtm.removeRow(i);
            }
            for (RemoteGameConfig game : gf.getGames()) {
                Object[] row = {game.getGameName(), game.getFieldSize(), game.getServerIP(), game.getGameVersion()};
                dtm.addRow(row);
            }
        }
        try {
            gf.stop();
            while (!gf.canContinue()){
                thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private JPanel initButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);

        // JButton startButton = new JButton("Start Game");
        JButton loadButton = new JButton("Load Game");
        JButton settingsButton = new JButton("Settings");
        JButton createRemoteGame = new JButton("Host Remote Game");

        //   buttonPanel.add(startButton);
        buttonPanel.add(createLocalGameButton);
        buttonPanel.add(createRemoteGame);
        MainPanel that = this;
        createRemoteGame.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               that.shouldSearchforClients = false;
                java.awt.EventQueue.invokeLater(() -> {
                    JFrame mainFrame = (JFrame) SwingUtilities.getRoot(that);
                    LocalGameDialog newLocalGameDialog = new LocalGameDialog(mainFrame, GameType.REMOTE_HOST);
                    newLocalGameDialog.setVisible(true);
                    if (newLocalGameDialog.isStartGramePressed()) {
                        
                        RemoteGameConfig remoteConfig = newLocalGameDialog.getRemoteGameConfig();
                        remoteConfig.setMainFrame(mainFrame);
                        remoteConfig.setDoIStartFirst(true);
                        mainFrame.remove(that);
                        mainFrame.repaint();
                        Thread thread = new Thread(new GameThread(remoteConfig));
                        thread.start();
                        /*
                         GameCreator creator;
                         try {
                         creator = new GameCreator(remoteConfig);
                         ServerBroadcast serverBroadcast = 
                         new ServerBroadcast(remoteConfig.getGameName(), remoteConfig.getGameVersion(),remoteConfig.getBoardSizeX(),remoteConfig.getBoardSizeY(), UDP_PORT);
                           
                         Thread thread = new Thread(creator.buildGameController(serverBroadcast));
                         thread.start();
                             
                         } catch (GameCreator.GameCreationException ex) {
                         JOptionPane.showMessageDialog(mainFrame, ex, "Error on creating local Game", JOptionPane.ERROR_MESSAGE);
                         }*/
                    }
                });
            }

        });

        createLocalGameButton.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                java.awt.EventQueue.invokeLater(() -> {
                    JFrame mainFrame = (JFrame) SwingUtilities.getRoot(that);
                    LocalGameDialog newLocalGameDialog = new LocalGameDialog(mainFrame);
                    newLocalGameDialog.setVisible(true);
                    if (newLocalGameDialog.isStartGramePressed()) {
                        LocalGameConfig localConfig = newLocalGameDialog.getLocalGameConfig();
                        localConfig.setMainFrame(mainFrame);
                        mainFrame.remove(that);
                        mainFrame.repaint();
                        GameCreator creator;
                        try {
                            creator = new GameCreator(localConfig);
                            Thread thread = new Thread(creator.buildGameController());
                            thread.start();
                        } catch (GameCreator.GameCreationException ex) {
                            JOptionPane.showMessageDialog(mainFrame, ex, "Error on creating local Game", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }

        });
        buttonPanel.add(loadButton);
        buttonPanel.add(settingsButton);

        return buttonPanel;
    }

    private JScrollPane initGameTable() {
        String[] columnNames = {"Server",
            "Fieldsize",
            "IP",
            "Version"};

        dtm = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        gameTable = new JTable(dtm);
        MainPanel that = this;
        gameTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    that.shouldSearchforClients = false;
                    
                    JFrame mainFrame = (JFrame) SwingUtilities.getRoot(that);
                    mainFrame.remove(that);
                    mainFrame.repaint();
                    int row = gameTable.rowAtPoint(evt.getPoint());
                    // TODO: let's hope the row index doesn't change :-)
                    GameConfig cfg = gf.getGames().get(row);
                    cfg.setGameType(GameType.REMOTE_CLIENT);
                    cfg.setMainFrame(mainFrame);
                    Thread thread = new Thread(new GameThread(cfg));
                    thread.start();

                    /*GameCreator creator = new GameCreator(cfg);
                     GameController controller = creator.buildGameController();
                     controller.run();*/
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gameTable.getParent(), ex, "Couldn't create game", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return new JScrollPane(gameTable);
    }
}
