package management;

public class Datensatz {

    private String name;
    private String command;
    private String password;

    public Datensatz(String name, String command, String password) {
        this.name = name;
        this.command = command;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return name;
    }
}
