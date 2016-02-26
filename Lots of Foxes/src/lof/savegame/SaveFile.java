package lof.savegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lof.model.Board;
import lof.model.Line;
import lof.model.Player;

/**
 * represents a save file on the hard disk
 * 
 * the save file
 * @author Moritz
 */
public class SaveFile {
    
    /**
     * directory name created inside the saveGameDir where all the
     * save games are stored in
     */
    static private final String SAVE_GAME_DIR_NAME = ".lots-of-foxes";
    
    static public final String EXTENSION = ".dat";
    
    /**
     * Full directory path to where the SaveFiles are stored in is created in.
     * This includes the SAVE_GAME_DIR_NAME as last directory.
     * This is different depending on which OS you're and and is determined
     * by the determineSaveGameDir() method.
     */ 
    static private String saveGameDir;
    
    static {
        makeSureSaveGameDirExists();
    }
    
    /**
     * makes sure the SAVE_GAME_DIR_NAME exists within the directory provided
     * by determineSaveGameDir() method
     */
    private static void makeSureSaveGameDirExists() {
        saveGameDir = determineSaveGameDir() + File.separator + SAVE_GAME_DIR_NAME;
        File dir = new File(saveGameDir);
        if(!dir.exists()) dir.mkdir();
    }
    
    /**
     * tries to find out where to best store save games.
     * This method tries to find the APPDATA directory on Windows.
     * On Unix system it tries to find the HOME directory of the user
     * @return String the directory where save games are best stored in
     */
    private static String determineSaveGameDir() {
        String[] dirs = {
            System.getenv("LOTS_OF_FOXES_SAVE_DIR"),
            System.getenv("APPDATA"),
            System.getProperty("user.home") + "\\Local Settings\\Application Data",
            System.getProperty("user.home"),
            System.getenv("HOME"),
            "."
        };
        
        return Arrays.stream(dirs).filter(dir -> {
            return dir != null && !dir.equals("") && new File(dir).isDirectory();
        }).findFirst().get();
    }
    
    public static String getSaveGameDir() {
        return saveGameDir;
    }
    
    /**
     * The file object on the file system for this save file
     */
    private final File file;
    
    /**
     * the name of the save game file without extension
     */
    private final String saveGameName;
    
    public SaveFile(String saveGameName) {
        this.saveGameName = saveGameName;
        this.file = new File(saveGameDir + File.separator + saveGameName + "." + EXTENSION);
    }
    
    /**
     * whether the SaveFile already exists on the Disk
     * @return true if the SaveFile already exists
     */
    public boolean exists() {
        return file.exists();
    }
    
    /**
     * Create a save file on the disk to store the board.
     * 
     * @param board the board to store
     * @throws IOException 
     */
    public void save(Board board) throws IOException {
        if(!exists()) file.createNewFile();
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            throw new IOException("could not create file", ex);
        }

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ArrayList<Line> linesToBeSaved = new ArrayList<>(board.getLines().stream()
                .filter(line -> line.getOwner() != null)
                .collect(Collectors.toList()));
        final int numLines = linesToBeSaved.size();
        
        objectOutputStream.writeObject(board.getPlayers()[0]);
        objectOutputStream.writeObject(board.getPlayers()[1]);
        objectOutputStream.writeInt(board.getSizeX());
        objectOutputStream.writeInt(board.getSizeY());
        objectOutputStream.writeInt(numLines);
        for(int i = 0; i < numLines; i++) {
            objectOutputStream.writeObject(linesToBeSaved.get(i));
        }
    }
    
    /**
     * Load a save file from the disk and load the board.
     * 
     * @return board the board to load
     * @throws IOException 
     * @throws FileNotFoundException
     */
    public Board load() throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            final Player player1 = (Player)objectInputStream.readObject();
            final Player player2 = (Player)objectInputStream.readObject();
            final int sizeX = objectInputStream.readInt();
            final int sizeY = objectInputStream.readInt();
            final int numLines = objectInputStream.readInt();
            ArrayList<Line> lines = new ArrayList<>();
            for(int i = 0; i < numLines; i++) {
                lines.add((Line)objectInputStream.readObject());
            }
            return new Board(sizeX, sizeY, player1, player2, lines);
        } catch (ClassNotFoundException | ClassCastException ex) {
            throw new IOException("could not read board", ex);
        }
    }
    
    /**
     * Delete a save file.
     * @return true if the save file could be deleted, false if not
     */
    public boolean delete() {
        return file.delete();
    }
}
