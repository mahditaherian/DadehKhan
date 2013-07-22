package structure;

import base.applicator.RequestRule;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.lang.reflect.Field;

/**
 * @author Mahdi Taherian
 */
public class ReferenceManager {

    public ReferenceManager() {

    }

    public static void main(String[] args) {
        makeXML(new RequestRule());
    }

    public static String makeXML(RequestRule requestRule) {


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
                    Object val = field.get(requestRule);
                    if (val == null || val.toString().trim().isEmpty()) {
                        continue;
                    }
                    string = doc.createElement(field.getType().getSimpleName().toLowerCase());
                    attr = doc.createAttribute("name");
                    attr.setValue(field.getName());
                    string.setAttributeNode(attr);
                    attr = doc.createAttribute("value");
                    attr.setValue(val.toString());
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
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File("data\\reference\\rule\\rule.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

//            transformer.transform(source, result);


//            TransformerFactory tf = TransformerFactory.newInstance();
//            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString().replaceAll("\n|\r", "");
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
