package management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigManager {

    private String design;
    private boolean autoCopyToClipboard;

    public ConfigManager() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("saves\\settings.txt"));
            String s = "";
            while (scanner.hasNext()) {
                s = scanner.next();
                if (s.charAt(0) == '/') {
                    continue;
                }
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

    public String getDesign() {
        return design;
    }

    public boolean isAutoCopyToClipboard() {
        return autoCopyToClipboard;
    }
}
