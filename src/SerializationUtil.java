import java.io.*;

public class SerializationUtil{


    /**
     * Saves the state of a game to a specified file path using serialization.
     * The game object is serialized and written to the specified file path,
     * allowing for later retrieval and restoration of the game state.
     *
     * @param game The Game object to be saved.
     * @param filePath The file path where the serialized game will be stored.
     *
     * @throws IOException If an I/O error occurs during the saving process.
     */
    public static void saveGame(Game game, String filePath){
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(game);
                System.out.println("Game Saved Successfully.\n");
            } catch (IOException e) {
                System.out.println("ERROR! Game couldn't save.\n" + e.getClass() + ": " + e.getMessage() + "\n");
            }
        }

    /**
     * Loads a serialized Game object from the specified file path.
     * The serialized Game object is deserialized from the file,
     * allowing for the restoration of a previously saved game state.
     *
     * @param filePath The file path from which the serialized game will be loaded.
     *
     * @return The loaded Game object, or null if an error occurs during the loading process.
     *
     * @throws IOException If an I/O error occurs during the loading process.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    public static Game loadGame(String filePath){
            Game loadedGame = null;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                System.out.println("\n---Game Loaded.---\n");
                loadedGame =  (Game) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error! Can't load data.");
                System.out.println(e.getClass() + ": " + e.getMessage() + "\n");
                return null;
            }
            return loadedGame;
        }
}
