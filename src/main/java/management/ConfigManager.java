package management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class reads the file "settings.txt" and saves its values.
 *
 * @author Grumanda
 */
public class ConfigManager {

    private String design;
    private boolean autoCopyToClipboard;

    /**
     * This is the constructor.
     */
    public ConfigManager() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("saves\\settings.txt"));
            String s = "";
            while (scanner.hasNext()) {
                s = scanner.next();
                // Ignores lines beginning with '/' (call it comments ;D)
                if (s.charAt(0) == '/') {
                    continue;
                }
                // Split each line in "key and value"
                String[] parts = s.split("=");

                if (parts[0].equals("autoClipboardCopy")) {
                    //AutoCopyToClipboard
                    if (parts[1].equals("true")) {
                        autoCopyToClipboard = true;
                    } else {
                        autoCopyToClipboard = false;
                    }
                } else if (parts[0].equals("design")) {
                    //Design
                    design = parts[1];
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter method for design.
     *
     * @return String
     */
    public String getDesign() {
        return design;
    }

    /**
     * Getter method for autoCopyToClipboard.
     *
     * @return boolean
     */
    public boolean isAutoCopyToClipboard() {
        return autoCopyToClipboard;
    }
}