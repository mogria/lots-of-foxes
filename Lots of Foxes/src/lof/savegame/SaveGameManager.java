package lof.savegame;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import lof.model.Board;

/**
 * manages all the save files
 * @author Moritz
 */
public final class SaveGameManager {
    
    /**
     * a map of all the SaveFile's in the SaveGameDir
     * the key is the saveGameName
     * the value is an instance of SaveFile
     */
    private HashMap<String, SaveFile> saveFiles;
    
    public SaveGameManager() {
        refresh();
    }
    
    /**
     * read the save game dir and create a SaveFile instance for each file
     */
    public void refresh() {
        saveFiles = new HashMap<>();
        String[] saveFileNames = new File(SaveFile.getSaveGameDir())
                .list((dir, name) -> name.endsWith("." + SaveFile.EXTENSION));
        Arrays.stream(saveFileNames).forEach(fileName -> {
            String saveGameName = fileName.split(".")[0]; // remove extension
            SaveFile saveFile = new SaveFile(saveGameName);
            saveFiles.put(saveGameName, saveFile);
        });
    }
    
    /**
     * Get a list of all the SaveFile objects
     */
    public Collection<SaveFile> getSaveFiles() {
        return saveFiles.values();
    }
    
    
    /**
     * save a game Board
     * @param gameName the name of the save file / the name of the game
     * @param boardToSave the Board to save
     * @throws IOException 
     */
    public void saveGame(String gameName, Board boardToSave) throws IOException {
        SaveFile saveFile = saveFiles.get(gameName);
        if(saveFile == null) {
            saveFile = new SaveFile(gameName);
            saveFiles.put(gameName, saveFile);
        }
        saveFile.save(boardToSave);
    }
    
    /**
     * load a game Board
     * @param gameName the name of the game to load
     * @return the board loaded
     * @throws IOException 
     */
    public Board loadGame(String gameName) throws IOException {
        return saveFiles.get(gameName).load();
    }
}
