package management;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLCommander {

    private static final String SAVE_DIR = "saves";
    private static final String XML_FILE = SAVE_DIR + File.separator + "commands.xml";

    // Konstruktor
    public XMLCommander() {
        try {
            File dir = new File(SAVE_DIR);
            File xmlFile = new File(XML_FILE);
            if (!dir.exists()) {
                dir.mkdir(); // Erstelle den Save-Ordner, falls er nicht existiert
            }

            if (!xmlFile.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.newDocument();

                // Erstelle das Root-Element "Commands"
                Element rootElement = doc.createElement("Commands");
                doc.appendChild(rootElement);

                // Speichere die leere XML-Datei
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(source, result);
            }
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    // Methode zum Hinzufügen eines neuen Eintrags
    public void addCommand(String name, String sshCommand, String password) {
        try {
            File xmlFile = new File(XML_FILE);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = builder.parse(xmlFile);
            } else {
                doc = builder.newDocument();
                Element rootElement = doc.createElement("Commands");
                doc.appendChild(rootElement);
            }

            Element commandElement = doc.createElement("Command");
            Element nameElement = doc.createElement("Name");
            nameElement.appendChild(doc.createTextNode(name));

            Element commandElementDetail = doc.createElement("Command");
            commandElementDetail.appendChild(doc.createTextNode(sshCommand));

            Element passwordElement = doc.createElement("Password");
            passwordElement.appendChild(doc.createTextNode(password));

            commandElement.appendChild(nameElement);
            commandElement.appendChild(commandElementDetail);
            commandElement.appendChild(passwordElement);
            doc.getDocumentElement().appendChild(commandElement);

            // Speichern mit Formatierung
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileWriter(XML_FILE));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Methode zum Abrufen aller Einträge
    public void loadCommands() {
        Main.datensatzList = new ArrayList<>();
        List<String[]> commandList = new ArrayList<>();
        try {
            File xmlFile = new File(XML_FILE);
            if (!xmlFile.exists()) {
                System.out.println("Keine Befehle gefunden.");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList commandNodes = doc.getElementsByTagName("Command");

            if (commandNodes.getLength() == 0) {
                System.out.println("Keine Befehle vorhanden.");
            }

            for (int i = 0; i < commandNodes.getLength(); i++) {
                Element commandElement = (Element) commandNodes.item(i);

                if (commandElement == null) {
                    System.out.println("Fehler: commandElement ist null.");
                    continue;
                }

                String name = safeGetTextContent(commandElement, "Name");
                String sshCommand = safeGetTextContent(commandElement, "Command");
                String password = safeGetTextContent(commandElement, "Password");

                if (name != null && sshCommand != null && password != null) {
                    commandList.add(new String[]{name, sshCommand, password});
                } else {
                }
            }
            for (String[] stringArray : commandList) {
                Datensatz datensatz = new Datensatz(stringArray[0], stringArray[1], stringArray[2]);
                Main.datensatzList.add(datensatz);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hilfsmethode zur sicheren Abfrage von Textinhalten
    private String safeGetTextContent(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }

    public void deleteData(String name) {
        try {
            File xmlFile = new File(XML_FILE);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList commandNodes = doc.getElementsByTagName("Command");
            for (int i = 0; i < commandNodes.getLength(); i++) {
                Element commandElement = (Element) commandNodes.item(i);
                String nam = safeGetTextContent(commandElement, "Name");
                if (nam != null && nam.equals(name)) {
                    commandElement.getParentNode().removeChild(commandElement);
                    break;
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileWriter(XML_FILE));
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
