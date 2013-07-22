package base.grabber;

import base.applicator.*;
import base.applicator.object.StandardEntity;
import base.applicator.object.Stuff;
import base.panel.RuleMaker;
import base.util.*;
import org.joox.Match;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.joox.JOOX.$;

/**
 * @author Mahdi Taherian
 */
public class XmlGrabber extends Grabber {
    //    private HtmlRequestHandler htmlRequestHandler;
    private Map<Class, Document> documentMap;//string
    private FileHolder fileHolder;
    private ProcessPropertyHelper processPropertyHelper;
    private IDManager idManager;


    public XmlGrabber(FileHolder fileHolder, StuffProvider stuffProvider, ReferenceProvider referenceProvider, IDManager idManager) {
        super();
        this.idManager = idManager;
        this.stuffProvider = stuffProvider;
        this.referenceProvider = referenceProvider;
        documentMap = new HashMap<Class, Document>();
        this.fileHolder = fileHolder;
        fileHolder.hold(Reference.class, "xml");
        File referenceFile = fileHolder.getFile(Reference.class);
        putDocument(Reference.class, referenceFile);
        processPropertyHelper = new ProcessPropertyHelper(referenceProvider, stuffProvider);
    }

    @Override
    public void grabKindOfStuff(Class<? extends Stuff> kind) {
        List<? extends Stuff> stuffs = new ArrayList<Stuff>(grabKind(kind));
        for (Stuff stuff : stuffs) {
            idManager.addStuffID(stuff.getId().getValue());
            stuffProvider.addStuff(stuff);
        }
    }

    @Override
    public void grabReferences() {
        List<Reference> references = new ArrayList<Reference>(grabKind(Reference.class));
        for (Reference reference : references) {
            idManager.addReferenceID(reference.getId().getValue());
            for (Page page : reference.getPages()) {
                idManager.addPageID(page.getId().getValue());
                page.setParent(reference);
            }
            referenceProvider.addReference(reference);
        }
    }

    public void grabRules() {
        List<RequestRule> requestRules = new ArrayList<RequestRule>(grabKind(RequestRule.class));
        for (RequestRule rule : requestRules) {
            idManager.addRequestRuleID(rule.getID().getValue());
            referenceProvider.addRequestRule(rule.getID(), rule);
        }

        List<ConvertRule> convertRules = new ArrayList<ConvertRule>(grabKind(ConvertRule.class));
        for (ConvertRule rule : convertRules) {
            idManager.addConvertRuleID(rule.getID().getValue());
            referenceProvider.addConvertRule(rule.getID(), rule);
        }
    }

    private <T extends StandardEntity> List<T> grabKind(Class<T> kind) {
        Document doc = getDocument(kind);
        if (doc == null) {
            System.err.println("xmlDocument is null");
            return null;
        }
        List<T> objectList = new ArrayList<T>();
        T obj;
// Wrap the document with the jOOX API
        Match objects = $(doc).find(kind.getSimpleName().toLowerCase());
        for (Element objElement : objects) {
            obj = null;
            try {
                obj = kind.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (obj == null) {
                continue;
            }

//            fields = kind.getFields();
            for (Parameter property : obj.getParameters()) {
                Node node = objElement.getElementsByTagName(property.getName()).item(0);
                if (node == null) {
                    String attr = objElement.getAttribute(property.getName().toLowerCase());
                    if (!attr.isEmpty()) {
//                            field.set(obj, processAttribute(AttributeType.valueOf(field.getName().toUpperCase()), attr));
                        Object val = processAttribute(AttributeType.valueOf(property.getName().toUpperCase()), attr);
                        Method method = Util.getSetter(property.getName(), property.getType().clazz, kind);
                        Util.invoke(method, obj, val);
                    }
                } else {
//                        field.set(obj, processPropertyHelper.processProperty(node));
                    Object val = processPropertyHelper.processProperty(obj, node);
                    Method method = Util.getSetter(property.getName(), property.getType().clazz, kind);
                    Util.invoke(method, obj, val);
                }
            }
            objectList.add(obj);
        }
        return objectList;
    }

    private Object processAttribute(AttributeType attribute, String value) {
        switch (attribute) {
            case ID:
                int id = Util.convertToInt(value);
                return new EntityID(id);
        }
        return null;
    }

    private Document getDocument(Class kind) {
        if (documentMap.containsKey(kind)) {
            return documentMap.get(kind);
        } else {
            if (fileHolder.containsFile(kind)) {
                File file = fileHolder.getFile(kind);
                putDocument(kind, file);
                return documentMap.get(kind);
            } else {
                fileHolder.hold(kind, "xml");
                return getDocument(kind);
            }
        }
    }

    private void putDocument(Class kind, File file) {
        try {
            documentMap.put(kind, $(file).document());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void append(RequestRule requestRule) {
        Document document = getDocument(RequestRule.class);
        appendRule(requestRule, document);
        File file = fileHolder.getFile(RequestRule.class);
        saveDocument(document, file);
    }


    ///****************************************appender****************************************///
    public void append(Stuff stuff) {
        Document document = getDocument(RequestRule.class);
//        appendStuff(stuff);
        appendStuff(stuff, document);
        File file = fileHolder.getFile(RequestRule.class);
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


//        Element child = doc.createElement("name");
//        attr = doc.createAttribute("pe");
//        attr.setValue(stuff.getName().getFarsi());
//        child.setAttributeNode(attr);
//        attr = doc.createAttribute("en");
//        attr.setValue(stuff.getName().getEnglish());
//        child.setAttributeNode(attr);
//        attr = doc.createAttribute("fi");
//        attr.setValue(stuff.getName().getFarsiInEnglish());
//        child.setAttributeNode(attr);
//        stuffElement.appendChild(child);

//        child = doc.createElement("factories");
//        attr = doc.createAttribute("type");
//        attr.setValue(stuff.getName().getFarsi());
//        child.setAttributeNode(attr);
//        attr = doc.createAttribute("en");
//        attr.setValue(stuff.getName().getEnglish());
//        child.setAttributeNode(attr);
//        attr = doc.createAttribute("fi");
//        attr.setValue(stuff.getName().getFarsiInEnglish());
//        child.setAttributeNode(attr);
//        stuffElement.appendChild(child);


    }

    public void appendParameter(Document doc, Element element, StandardEntity object, Property property) {
        Element child;
        StandardEntity entity;
        Attr attr;
        switch (property.getType()) {
            case WORD:
                Word word = (Word) property.getValue();
                child = doc.createElement(property.getName());
                attr = doc.createAttribute("type");
                attr.setValue(property.getType().key);
                child.setAttributeNode(attr);
                attr = doc.createAttribute("pe");
                attr.setValue(word.getFarsi());
                child.setAttributeNode(attr);
                attr = doc.createAttribute("en");
                attr.setValue(word.getEnglish());
                child.setAttributeNode(attr);
                attr = doc.createAttribute("fi");
                attr.setValue(word.getFarsiInEnglish());
                child.setAttributeNode(attr);
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
                        appendParameter(doc, child, entity, entity.getProperty());
                    } else {
                        System.out.println("WTF? AAA");
                    }
                }
                break;
            case REFERENCE:
                child = doc.createElement("refer");
                attr = doc.createAttribute("type");
                attr.setValue(property.getType().key);
                child.setAttributeNode(attr);
                Page page = (Page) property.getValue();
                attr = doc.createAttribute("id");
                attr.setValue(page.getId().toString());
                child.setAttributeNode(attr);

                String crs = "";
                Stuff stuff = (Stuff) object;
                List<ConvertRule> convertRules = stuff.getConvertRules(page);
                for (int i = 0; i < convertRules.size(); i++) {
                    ConvertRule convertRule = convertRules.get(i);
                    crs += convertRule.getID().toString();
                    if (i < convertRules.size() - 1) {
                        crs += ",";
                    }
                }
                attr = doc.createAttribute("convert_rule");
                attr.setValue(crs);
                child.setAttributeNode(attr);
                element.appendChild(child);
                break;

        }
    }

    public static void appendRule(RequestRule requestRule, Document doc) {
        Element rule = doc.createElement("requestrule");

        // set attribute to rule element
        Attr attr = doc.createAttribute("id");
        attr.setValue(requestRule.getID().toString());
        rule.setAttributeNode(attr);

        Element string;
        for (Parameter property : requestRule.getParameters()) {
//        for (Field field : clazz.getFields()) {
            Object val = ((Property)property).getValue();
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
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
//        return "";
    }
}
