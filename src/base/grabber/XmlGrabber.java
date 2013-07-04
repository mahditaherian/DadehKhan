package base.grabber;

import base.applicator.ConvertRule;
import base.applicator.ReferenceProvider;
import base.applicator.RequestRule;
import base.applicator.StuffProvider;
import base.applicator.object.Stuff;
import base.util.EntityID;
import base.util.Page;
import base.util.Reference;
import base.util.Util;
import org.joox.Match;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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


    public XmlGrabber(FileHolder fileHolder, StuffProvider stuffProvider, ReferenceProvider referenceProvider) {
        super();
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
            stuffProvider.addStuff(stuff);
        }
    }

    @Override
    public void grabReferences() {
        List<Reference> references = new ArrayList<Reference>(grabKind(Reference.class));
        for (Reference reference : references) {
            for (Page page : reference.getPages()) {
                page.setParent(reference);
            }
            referenceProvider.addReference(reference);
        }
    }

    public void grabRules() {
        List<RequestRule> requestRules = new ArrayList<RequestRule>(grabKind(RequestRule.class));
        for (RequestRule rule : requestRules) {
            referenceProvider.addRequestRule(rule.getID(), rule);
        }

        List<ConvertRule> convertRules = new ArrayList<ConvertRule>(grabKind(ConvertRule.class));
        for (ConvertRule rule : convertRules) {
            referenceProvider.addConvertRule(rule.getID(), rule);
        }
    }

    private <T> List<T> grabKind(Class<T> kind) {
        Document doc = getDocument(kind);
        if (doc == null) {
            System.err.println("xmlDocument is null");
            return null;
        }
        List<T> objectList = new ArrayList<T>();
        T obj;
// Wrap the document with the jOOX API
        Match objects = $(doc).find(kind.getSimpleName().toLowerCase());
        Field[] fields;
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

            fields = kind.getFields();
            for (Field field : fields) {
                Node node = objElement.getElementsByTagName(field.getName()).item(0);
                if (node == null) {
                    String attr = objElement.getAttribute(field.getName().toLowerCase());
                    if (!attr.isEmpty()) {
                        try {
//                            field.set(obj, processAttribute(AttributeType.valueOf(field.getName().toUpperCase()), attr));
                            Object val = processAttribute(AttributeType.valueOf(field.getName().toUpperCase()), attr);
                            Method method = Util.getSetter(field, kind);
                            method.invoke(obj, val);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
//                        field.set(obj, processPropertyHelper.processProperty(node));
                        Object val = processPropertyHelper.processProperty(obj,node);
                        Method method = Util.getSetter(field, kind);
                        method.invoke(obj, val);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
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

//    private void processAttributes(Object obj, Node node) {
//        NamedNodeMap attributes = node.getAttributes();
//        Node attr;
//        Class<?> kind = obj.getClass();
//        for (int i = 0; i < attributes.getLength(); i++) {
//            attr = attributes.item(i);
//            AttributeType type = AttributeType.get(attr.getNodeName());
//            switch (type) {
//                case CONVERT_RULE: {
//                    if (!Util.isInstance(kind, Stuff.class)) {
//                        throw new ClassCastException();
//                    }
//                    Stuff stuff = (Stuff) obj;
//
//                    EntityID id = new EntityID(Util.convertToInt(node.getNodeValue()));
//                    ConvertRule rule = referenceProvider.getConvertRuleByID(id);
//                    id = new EntityID(Util.convertToInt(node.getNodeValue()));
//                    stuff.addConvertRule(, rule);
//                }
//            }
//
//        }
//    }

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


//    public void setProperty(Object object, Node node) {
//        if (node.getAttributes() == null) {
//            return;
//        }
//        Class kind = object.getClass();
//        Field[] fields = kind.getFields();
//        for (Field field : fields) {
////            Object val = processProperty(node);
////            Node value = node.getAttributes().getNamedItem(field.getName());
////            if (value != null) {
//            try {
//                field.set(object, processProperty(node));
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            break;
////            }
//        }
//    }

//    public Object processProperty(Node node) {
//        if (node == null) {
//            return null;
//        }
//        String type = "";
//        String kind = node.getNodeName();
//        if (kind == null || kind.equalsIgnoreCase("#text")) {
//            return node.getTextContent();
//        }
//        PropertyType type = PropertyType.STRING;
//        KindType kindType = KindType.valueOf(kind.toUpperCase());
//        if (node.getAttributes() != null) {
//            Node namedItem = node.getAttributes().getNamedItem("type");
//            if (namedItem != null) {
//                type = namedItem.getNodeValue();
//                if (type == null) {
//                    type = "";
//                }
//            }
//            type = PropertyType.getValue(type);
//            if (type == null) {
//                type = PropertyType.getValue(node.getNodeName());
//
//                if (type == null) {
//                    type = PropertyType.STRING;
//                }
//            }
//        }
//        switch (type) {
//            case LIST:
//                List<Object> clsList = new ArrayList<Object>();
//                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
//                    clsList.add(processProperty(node.getChildNodes().item(i)));
//                }
//                return clsList;
//            case WORD:
//                return processPropertyHelper.processWord(node);
//
//            case REFERENCE:
//            case FACTORY:
//                return processKind(type, kindType, node);
//
//            case INTEGER:
//                return Util.convertToInt(node.getTextContent());
//            case STRING:
//                return node.getTextContent();
//
//            case HTML:
//            case PAGE: {
//                return processPropertyHelper.processPage(node);
//            }
//        }
//        return null;
//    }
//
//    public Object processKind(PropertyType type, KindType kindType, Node node) {
//        if (kindType.equals(KindType.REFER)) {
//            String idString = node.getAttributes().getNamedItem("id").getNodeValue();
//            switch (type) {
//                case REFERENCE:
//                    return referenceProvider.getReferenceByID(new EntityID(Util.convertToInt(idString)));
//                case FACTORY:
//                    return idString;
//                default:
//                    return null;
//            }
//        }
//        return null;
//    }

}
