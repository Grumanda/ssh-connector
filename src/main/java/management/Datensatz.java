package management;

/**
 * This class saves the data of a connection. Every connection gets an object
 * of this class.
 *
 * @author Grumanda
 */
public class Datensatz {

    private String name;
    private String command;
    private String password;

    /**
     * This is the constructor for this class.
     *
     * @param name
     * @param command
     * @param password
     */
    public Datensatz(String name, String command, String password) {
        this.name = name;
        this.command = command;
        this.password = password;
    }

    /**
     * Getter method for name.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for command.
     *
     * @return String
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter method for password.
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method overrides the toString method and returns the name of the
     * connection (This is uses to display the names in the comboBoxes).
     *
     * @return String
     */
    @Override
    public String toString() {
        return name;
    }
}