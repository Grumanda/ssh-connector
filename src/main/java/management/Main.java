package management;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.*;
import gui.GUI;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the main class of this program.
 *
 * @author Grumanda
 */
public class Main {

    public static final Datensatz EMPTY_DATENSATZ = new Datensatz("", "", "");
    public static List<Datensatz> datensatzList;
    public static XMLCommanderV2 commander;
    public static ConfigManager configManager;

    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        commander = new XMLCommanderV2();
        configManager = new ConfigManager();

        startSession();
    }

    /**
     * This method starts the session (needed to update everything when a new connection
     * have been added).
     */
    public static void startSession() {
        commander.loadConnections();
        applyDesign();
        new GUI();
    }

    /**
     * This method applies the design for the program (FlatLaf-Framework).
     */
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

    /**
     * This method takes the passed font and returns it in an underlined version.
     *
     * @param font
     * @return Font
     */
    public static Font underline(Font font) {
        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        return font.deriveFont(attributes);
    }
}