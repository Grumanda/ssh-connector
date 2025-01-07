package management;

public class SSHConnector {

    public static void connect(String command) {
        try {
            // Befehl ausführen
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "cmd.exe", "/C", "start " + command);
            Process process = processBuilder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
