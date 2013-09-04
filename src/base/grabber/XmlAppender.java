package base.grabber;

import base.applicator.ConvertRule;
import base.applicator.Parameter;
import base.applicator.Property;
import base.applicator.RequestRule;
import base.applicator.object.StandardEntity;
import base.applicator.object.Stuff;
import base.panel.RuleMaker;
import base.util.Page;
import base.util.Word;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * @author Mahdi
 */
public class XmlAppender {
    XmlGrabber xmlGrabber;

    public XmlAppender(XmlGrabber xmlGrabber) {
        this.xmlGrabber = xmlGrabber;
    }

    public void append(RequestRule requestRule) {
        Document document = xmlGrabber.getDocument(RequestRule.class,RequestRule.class.getSimpleName());
        appendRule(requestRule, document);
        File file = xmlGrabber.fileHolder.getFile(RequestRule.class.getSimpleName());
        saveDocument(document, file);
    }

    public void append(Stuff stuff) {
        Document document = xmlGrabber.getDocument(stuff.getClass(),stuff.getClass().getSimpleName());
        appendStuff(stuff, document);
        File file = xmlGrabber.fileHolder.getFile(stuff.getClass().getSimpleName());
        saveDocument(document, file);
    }

    public void appendStuff(Stuff stuff, Document doc) {
        Element stuffElement = doc.createElement(stuff.getClass().getSimpleName().toLowerCase());
        // set attribute to rule element
        Attr attr = doc.createAttribute("id");
        attr.setValue(stuff.getId().toString());
        stuffElement.setAttributeNode(attr);

        attr = doc.createAttribute("type");
        attr.setValue("stuff");
        stuffElement.setAttributeNode(attr);

        for (Parameter property : stuff.getParameters()) {
            appendParameter(doc, stuffElement, stuff, (Property) property);
        }

        doc.getElementsByTagName("stuffs").item(0).appendChild(stuffElement);

    }

    public void appendParameter(Document doc, Element element, StandardEntity object, Property property) {
        Element child;
        StandardEntity entity;
        Attr attr;
        switch (property.getType()) {
            case WORD:
                Word word = (Word) property.getValue();
                child = doc.createElement(property.getName());
                appendAttribute(doc, child, "type", property.getType().key);
                //todo oooooooooooooooooooooooooooooooooooooooooooooo
//                appendAttribute(doc, child, "pe", word.getFarsi());
//                appendAttribute(doc, child, "en", word.getEnglish());
//                appendAttribute(doc, child, "fi", word.getFarsiInEnglish());
                element.appendChild(child);
                break;
            case LIST:
                child = doc.createElement(property.getName());
                attr = doc.createAttribute("type");
                attr.setValue(property.getType().key);
                child.setAttributeNode(attr);
                for (Object obj : (List<?>) property.getValue()) {
                    if (obj instanceof StandardEntity) {
                        entity = (StandardEntity) obj;
                        appendParameter(doc, child, object, entity.getProperty());
                    } else {
                        System.out.println("WTF? AAA");
                    }
                }
                element.appendChild(child);
                break;
            case PAGE:
                child = doc.createElement("refer");
                Page page = (Page) property.getValue();
                appendAttribute(doc, child, "type", property.getType().key);
                appendAttribute(doc, child, "id", page.getId().toString());

                String crs = "";
                Stuff stuff = (Stuff) object;
                List<ConvertRule> convertRules = stuff.getConvertRules(page);
                if (convertRules != null && !convertRules.isEmpty()) {
                    for (int i = 0; i < convertRules.size(); i++) {
                        ConvertRule convertRule = convertRules.get(i);
                        crs += convertRule.getId().toString();
                        if (i < convertRules.size() - 1) {
                            crs += ",";
                        }
                    }
                    appendAttribute(doc, child, "convert_rule", crs);
                }
                element.appendChild(child);
                break;

        }
    }

    public void appendAttribute(Document doc, Element element, String key, String value) {
        Attr attr = doc.createAttribute(key);
        attr.setValue(value);
        element.setAttributeNode(attr);
    }

    public static void appendRule(RequestRule requestRule, Document doc) {
        Element rule = doc.createElement("requestrule");

        // set attribute to rule element
        Attr attr = doc.createAttribute("id");
        attr.setValue(requestRule.getId().toString());
        rule.setAttributeNode(attr);

        Element string;
        for (Parameter property : requestRule.getParameters()) {
            Object val = ((Property) property).getValue();
            if (val == null || val.toString().trim().isEmpty() || !RuleMaker.isValidType(property.getType().clazz)) {
                continue;
            }

            string = doc.createElement(property.getName());
            attr = doc.createAttribute("type");
            attr.setValue(property.getType().clazz.getSimpleName().toLowerCase());
            string.setAttributeNode(attr);
            attr = doc.createAttribute("value");
            attr.setValue(val.toString());
            string.setAttributeNode(attr);
            rule.appendChild(string);
        }
        doc.getElementsByTagName("rules").item(0).appendChild(rule);
    }

    public static void saveDocument(Document doc, File file) {
        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);
//            TransformerFactory tf = TransformerFactory.newInstance();
//            Transformer transformer = tf.newTransformer();
//            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//            StringWriter writer = new StringWriter();
//            transformer.transform(new DOMSource(doc), new StreamResult(writer));
//            return writer.getBuffer().toString().replaceAll("\n|\r", "");
        } catch (TransformerException e) {
            e.printStackTrace();
        }
//        return "";
    }
}
