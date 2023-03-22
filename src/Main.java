import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] arg) {
        openZip("C:/JD_Games/savegames/saves.zip", "C:/JD_Games/savegames");
        System.out.println(openProgress("C:/JD_Games/savegames/save_1.dat"));
        System.out.println(openProgress("C:/JD_Games/savegames/save_2.dat"));
        System.out.println(openProgress("C:/JD_Games/savegames/save_3.dat"));
    }

    public static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return gameProgress;
    }

    public static void openZip(String zipPath, String path) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry zipEntry;
            String zipEntryName;
            while ((zipEntry = zis.getNextEntry()) != null) {
                zipEntryName = zipEntry.getName();
                FileOutputStream fos = new FileOutputStream(path + "/" + zipEntryName);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
