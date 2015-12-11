package lots.of.foxes.ui;

import java.awt.Dialog;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lots.of.foxes.GameCreator;
import lots.of.foxes.model.AIDifficulty;
import lots.of.foxes.model.GameType;
import lots.of.foxes.model.LocalGameConfig;
import lots.of.foxes.model.RemoteGameConfig;

/**
 * Responsible for creating a LocalGameConfig
 *
 * @author Moritz
 */
public class LocalGameDialog extends JDialog {

    boolean startGramePressed;

    /**
     * Creates new form LocalGameFrame
     *
     * @param owner
     */
    public LocalGameDialog(JFrame owner) {
        super(owner, "Create a new Local Game againt the Computer", Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gameNameTextBox.setText("localgame1");
        startGramePressed = false;
    }

    public LocalGameDialog(JFrame owner, GameType gameType) {
        super(owner, gameType == GameType.LOCAL_AI ? "Create a new Local Game againt the Computer" : "", Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gameNameTextBox.setText(gameType == GameType.LOCAL_AI ? "localeGame" : "RemoteGame");
        startGramePressed = false;

        if (gameType == GameType.REMOTE_HOST) {
            enemyDifficultyLabel.setVisible(false);
            enemyDifficultyComboBox.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        gameNameLabel = new javax.swing.JLabel();
        gameNameTextBox = new javax.swing.JTextField();
        enemyDifficultyLabel = new javax.swing.JLabel();
        enemyDifficultyComboBox = new javax.swing.JComboBox();
        sizeXLabel = new javax.swing.JLabel();
        sizeXTextBox = new javax.swing.JTextField();
        sizeYLabel = new javax.swing.JLabel();
        sizeYTextBox = new javax.swing.JTextField();
        abortButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 20, 80, 20, 80, 20, 80, 20, 0};
        layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        getContentPane().setLayout(layout);

        gameNameLabel.setText("Game Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(gameNameLabel, gridBagConstraints);

        gameNameTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameNameTextBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(gameNameTextBox, gridBagConstraints);

        enemyDifficultyLabel.setText("Enemy Difficulty");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(enemyDifficultyLabel, gridBagConstraints);

        enemyDifficultyComboBox.setModel(new DefaultComboBoxModel(AIDifficulty.values()));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${selectedDifficulty}"), enemyDifficultyComboBox, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"), "selectedDifficulty");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(enemyDifficultyComboBox, gridBagConstraints);

        sizeXLabel.setText("Board Width");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(sizeXLabel, gridBagConstraints);

        sizeXTextBox.setText("3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(sizeXTextBox, gridBagConstraints);

        sizeYLabel.setText("Board Height");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(sizeYLabel, gridBagConstraints);

        sizeYTextBox.setText("3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(sizeYTextBox, gridBagConstraints);

        abortButton.setText("Abort");
        abortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abortButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(abortButton, gridBagConstraints);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(startButton, gridBagConstraints);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gameNameTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameNameTextBoxActionPerformed

    }//GEN-LAST:event_gameNameTextBoxActionPerformed

    private void abortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abortButtonActionPerformed
        startGramePressed = false;
        this.dispose();
    }//GEN-LAST:event_abortButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        startGramePressed = true;
        this.dispose();
    }//GEN-LAST:event_startButtonActionPerformed

    public LocalGameConfig getLocalGameConfig() {
        LocalGameConfig localGameConfig = new LocalGameConfig(
                Integer.parseInt(sizeXTextBox.getText()),
                Integer.parseInt(sizeYTextBox.getText()),
                gameNameTextBox.getText(),
                false,
                (AIDifficulty)enemyDifficultyComboBox.getSelectedItem());
        localGameConfig.setGameType(GameType.LOCAL_AI);
        return localGameConfig;
    }
    public RemoteGameConfig getRemoteGameConfig(){
        RemoteGameConfig remoteGameConfig = new RemoteGameConfig(gameNameTextBox.getText(), "1.0", Integer.parseInt(sizeXTextBox.getText()), Integer.parseInt(sizeYTextBox.getText()), "127.0.0.1", 12345);
        remoteGameConfig.setGameType(GameType.REMOTE_HOST);
        return remoteGameConfig;
    }

    public boolean isStartGramePressed() {
        return startGramePressed;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abortButton;
    private javax.swing.JComboBox enemyDifficultyComboBox;
    private javax.swing.JLabel enemyDifficultyLabel;
    private javax.swing.JLabel gameNameLabel;
    private javax.swing.JTextField gameNameTextBox;
    private javax.swing.JLabel sizeXLabel;
    private javax.swing.JTextField sizeXTextBox;
    private javax.swing.JLabel sizeYLabel;
    private javax.swing.JTextField sizeYTextBox;
    private javax.swing.JButton startButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
