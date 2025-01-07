package management;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.*;
import gui.GUI;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static final Datensatz EMPTY_DATENSATZ = new Datensatz("", "", "");
    public static List<Datensatz> datensatzList;
    public static XMLCommander commander;
    public static ConfigManager configManager;

    public static void main(String[] args) {
        commander = new XMLCommander();
        configManager = new ConfigManager();

        startSession();
    }

    public static void startSession() {
        commander.loadCommands();
        commander.deleteData("Test Connection");
        applyDesign();
        new GUI();
    }

    private static void applyDesign() {
        String design = configManager.getDesign();
        switch (design) {
            case "FlatArcIJTheme" -> FlatArcIJTheme.setup();
            case "FlatCyanLightIJTheme" -> FlatCyanLightIJTheme.setup();
            case "FlatSolarizedDarkIJTheme" -> FlatSolarizedDarkIJTheme.setup();
            case "FlatSolarizedLightIJTheme" -> FlatSolarizedLightIJTheme.setup();
            case "FlatCobalt2IJTheme" -> FlatCobalt2IJTheme.setup();
            case "FlatArcDarkIJTheme" -> FlatArcDarkIJTheme.setup();
            case "FlatMaterialDesignDarkIJTheme" -> FlatMaterialDesignDarkIJTheme.setup();
            case "FlatCarbonIJTheme" -> FlatCarbonIJTheme.setup();
            case "FlatVuesionIJTheme" -> FlatVuesionIJTheme.setup();
            case "FlatXcodeDarkIJTheme" -> FlatXcodeDarkIJTheme.setup();
            default -> FlatLightLaf.setup();
        }
    }

    public static Font underline(Font font) {
        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        return font.deriveFont(attributes);
    }
}
