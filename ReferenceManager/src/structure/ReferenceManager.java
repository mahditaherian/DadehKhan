package structure;

import base.applicator.RequestRule;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Field;

/**
 * @author Mahdi Taherian
 */
public class ReferenceManager {

    public ReferenceManager() {

    }

    public static void main(String[] args) {
        ReferenceManager manager = new ReferenceManager();
        manager.makeXML(new RequestRule());
    }

    public String makeXML(RequestRule requestRule) {


        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("rules");
            doc.appendChild(rootElement);

            Element rule = doc.createElement("rule");

            // set attribute to rule element
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            rule.setAttributeNode(attr);

            Class<RequestRule> clazz = RequestRule.class;
            Element string;
            for (Field field : clazz.getFields()) {
                try {
                    field.get(requestRule);
                    string = doc.createElement(field.getType().getSimpleName().toLowerCase());
                    attr = doc.createAttribute("name");
                    attr.setValue(field.getName());
                    string.setAttributeNode(attr);
                    attr = doc.createAttribute("value");
                    attr.setValue(field.get(requestRule).toString());
                    string.setAttributeNode(attr);
                    rule.appendChild(string);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rootElement.appendChild(rule);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("data\\reference\\rule\\rule.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return "";
    }
}
