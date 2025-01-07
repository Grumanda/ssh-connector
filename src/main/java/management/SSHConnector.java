package management;

/**
 * This class creates the connection.
 *
 * @author Grumanda
 */
public class SSHConnector {

    /**
     * This method opens the command prompt and executes the command.
     *
     * @param command
     */
    public static void connect(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "cmd.exe", "/C", "start " + command);
            Process process = processBuilder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}