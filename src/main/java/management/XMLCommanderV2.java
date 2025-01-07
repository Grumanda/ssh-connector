package management;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * This class controlls the xml file where the connections are saved.
 *
 * @author Grumanda
 */
public class XMLCommanderV2 {

    private final File XML_FILE = new File("config/sshList.xml");
    private Document document;

    /**
     * This is the constructor.
     */
    public XMLCommanderV2() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(XML_FILE);
        } catch (Exception e) {
            // If getting an exception, the whole program will not work, so it will cancel
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * This method reads all connections from the xml file and saves them to a List.
     */
    public void loadConnections() {
        NodeList nodeList = document.getElementsByTagName("connection");
        Main.datensatzList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element connectionElement = (Element) nodeList.item(i);
            String name = connectionElement.getElementsByTagName("name").item(0)
                    .getTextContent().trim();
            String command = connectionElement.getElementsByTagName("command").item(0)
                    .getTextContent().trim();
            String password = connectionElement.getElementsByTagName("password").item(0)
                    .getTextContent().trim();

            // Add connection to list in main method
            Datensatz datensatz = new Datensatz(name, command, password);
            Main.datensatzList.add(datensatz);
        }
    }

    /**
     * This method adds a connection to the xml file.
     *
     * @param name
     * @param command
     * @param password
     */
    public void addConnection(String name, String command, String password) {
        Element connectionElement = document.createElement("connection");
        Element nameElement = document.createElement("name");
        Element commandElement = document.createElement("command");
        Element passwordElement = document.createElement("password");

        nameElement.appendChild(document.createTextNode(name));
        commandElement.appendChild(document.createTextNode(command));
        passwordElement.appendChild(document.createTextNode(password));

        connectionElement.appendChild(nameElement);
        connectionElement.appendChild(commandElement);
        connectionElement.appendChild(passwordElement);

        document.getDocumentElement().appendChild(connectionElement);

        writeXML();
    }

    /**
     * This method deletes a connection from the xml file.
     *
     * @param name
     */
    public void deleteConnection(String name) {
        NodeList nodeList = document.getElementsByTagName("connection");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element connectionElement = (Element) nodeList.item(i);

            String connectionName = connectionElement.getElementsByTagName("name")
                    .item(0).getTextContent().trim();

            if (connectionName.equals(name)) {
                connectionElement.getParentNode().removeChild(connectionElement);
                writeXML();
                break;
            }
        }
        writeXML();
    }

    /**
     * This method writes into the xml file.
     */
    private void writeXML() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileWriter(XML_FILE));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "An error occured:\n" + e.getMessage(), "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}