import java.io.*;

public class SerializationUtil{


    public static void saveGame(Game game, String filePath){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(game);
            System.out.println("Game Saved Successfully.\n");
        } catch (IOException e) {
            System.out.println("ERROR! Game couldn't save.\n" + e.getClass() + ": " + e.getMessage() + "\n");
        }
    }

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
