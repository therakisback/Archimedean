package util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class to handle all the file IO of the game, 
 * for now this will read in the csv files for the levelling skills / abilites / whatever.
 * In future, could hold a way to save the current game state if game is closed in the middle of a run.
 */
public class GameIO {

    private static final GameIO instance = new GameIO();

    private List<Integer> passiveIDs = new ArrayList<>();
    private HashMap<Integer, String> passiveTitles = new HashMap<>();
    private HashMap<Integer, String> passiveDescriptions = new HashMap<>();
    private HashMap<Integer, List<Float>> passiveModifiers = new HashMap<>();
    private List<Integer> activeIDs = new ArrayList<>();
    private HashMap<Integer, String> activeTitles = new HashMap<>();
    private HashMap<Integer, String> activeDescriptions = new HashMap<>();
    private HashMap<Character, List<Integer>> activeIDsByKey = new HashMap<>();
    private HashMap<Integer, Character> activeKeys = new HashMap<>();

    /**
     * Class that is meant to handle the file IO of the game.
     * At this point in development, that is entirely the passive and active abilities list.
     * This makes it so Player doesnt have to hold all this information, and Model and Viewer can access
     * it as needed, like with descriptions.
     * 
     * It is also a singleton class, like player and the controllers, the data needs to be unified across
     * the various classes, and there is no point holding more than one GameIO in memory.
     */
    private GameIO() {
        try {
            FileReader passives = new FileReader("res/passives.csv");
            FileReader actives = new FileReader("res/actives.csv");
            activeIDsByKey.put('q', new ArrayList<>());
            activeIDsByKey.put('e', new ArrayList<>());
            activeIDsByKey.put('r', new ArrayList<>());

            // Passive parsing
            try (BufferedReader br = new BufferedReader(passives)) {
                String line = br.readLine();    // Reading in now just to get the title line out of the way
                while((line = br.readLine()) != null) {
                    String[] data = line.split(", "); // Unfortunately when I wrote the csv I did comma + space between data points, I'd rather just change it here.
                    Integer id = Integer.valueOf(data[0]);
                    passiveIDs.add(id);
                    passiveTitles.put(id,data[1]);
                    passiveDescriptions.put(id, data[2]);
                    
                    List<Float> modList = new ArrayList<>();
                    for (int i = 3; i < data.length; i++) {
                        modList.add(Float.valueOf(data[i]));
                    }
                    passiveModifiers.put(id, modList);
                }
            } 
            // Active parsing
            try (BufferedReader br = new BufferedReader(actives)) {
                String line = br.readLine();
                while((line = br.readLine()) != null) {
                    String[] data = line.split(", ");
                    Integer id = Integer.valueOf(data[0]);
                    activeIDs.add(id);
                    activeTitles.put(id, data[1]);
                    activeDescriptions.put(id,data[2]);
                    activeIDsByKey.get(data[4].charAt(0)).add(id);
                    activeKeys.put(id, data[4].charAt(0));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading in abilities file, please verify game files.");
        }
    }

    public static GameIO getInstance() {return instance;}

    // Passives

    public List<Integer> getPassiveIDs() {return passiveIDs;} 

    public String getPassiveTitle(int ID) {return passiveTitles.get(ID);}

    public String getPassiveDescription(int ID) {return passiveDescriptions.get(ID);}

    public List<Float> getPassiveModifiers(int ID) {return passiveModifiers.get(ID);}

    // Actives

    public Character getKeyByID(int ID) {return activeKeys.get(ID);}

    public List<Integer> getActiveIDs() {return activeIDs;}

    public String getActiveTitle(int ID) {return activeTitles.get(ID);}

    public String getActiveDescrition(int ID) {return activeDescriptions.get(ID);}

    public List<Integer> getActiveIDsByKey(Character key) {return activeIDsByKey.get(key);}

}