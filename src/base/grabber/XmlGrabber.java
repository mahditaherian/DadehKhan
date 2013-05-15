package base.grabber;

import base.applicator.ReferenceProvider;
import base.applicator.StuffProvider;
import base.applicator.object.Stuff;
import base.util.EntityID;
import base.util.Reference;
import base.util.Util;
import base.util.Word;
import org.joox.Match;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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


    public XmlGrabber(FileHolder fileHolder, StuffProvider stuffProvider, ReferenceProvider referenceProvider) {
        super();
        this.stuffProvider = stuffProvider;
        this.referenceProvider = referenceProvider;
        documentMap = new HashMap<Class, Document>();
        this.fileHolder = fileHolder;
        fileHolder.hold(Reference.class, "xml");
        File referenceFile = fileHolder.getFile(Reference.class);
        putDocument(Reference.class, referenceFile);
    }

    @Override
    public void grabKindOfStuff(Class<? extends Stuff> kind) {
        Document doc = getDocument(kind);
        if (doc == null) {
            System.err.println("xmlDocument is null");
            return;
        }
        Stuff stuff;
// Wrap the document with the jOOX API
        Match stuffs = $(doc).find(kind.getSimpleName().toLowerCase());
        Field[] fields;
        for (Element stuffElement : stuffs) {
            stuff = null;
            try {
                stuff = kind.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (stuff == null) {
                continue;
            }

            fields = kind.getFields();
            for (Field field : fields) {
//                if (field.isAccessible()) {
                Node node = stuffElement.getElementsByTagName(field.getName()).item(0);
                if (node == null) {
                    continue;
                }
                try {
                    field.set(stuff, processProperty(node));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            stuffProvider.addStuff(stuff);
        }
    }

    @Override
    public void grabReferences() {

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

    public Object processProperty(Node node) {
        if (node == null) {
            return null;
        }
        String type = "";
        PropertyType propertyType = PropertyType.STRING;
        String kind = "";
        if (node.getAttributes() != null) {
            Node namedItem = node.getAttributes().getNamedItem("type");
            if (namedItem != null) {
                type = namedItem.getNodeValue();
                if (type == null) {
                    type = "";
                }
            }
            propertyType = PropertyType.valueOf(type.toUpperCase(), PropertyType.STRING);
            namedItem = node.getAttributes().getNamedItem("kind");
            if (namedItem != null) {
                kind = namedItem.getNodeValue();
                if (kind == null) {
                    kind = "";
                }
            }
        }
        if (node.getChildNodes().getLength() == 0) {
            switch (propertyType) {
                case INTEGER:
                    return Util.convertToInt(node.getTextContent());
                case STRING:
                    return node.getTextContent();
            }
        }
        switch (propertyType) {
            case LIST:
                List<Object> clsList = new ArrayList<Object>();
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    clsList.add(processProperty(node.getChildNodes().item(i)));
                }
                return clsList;
            case WORD:
                String pe = node.getAttributes().getNamedItem("pe").getNodeValue();
                String en = node.getAttributes().getNamedItem("en").getNodeValue();
                String fi = node.getAttributes().getNamedItem("fi").getNodeValue();
                return new Word(pe, en, fi);
            case REFER:
                KindType kindType = KindType.valueOf(kind.toUpperCase());
                return processKind(kindType, node);
        }
        return null;
    }

    public Object processKind(KindType kindType, Node node) {
        String idString = node.getAttributes().getNamedItem("id").getNodeValue();
        switch (kindType) {
            case REFERENCE:
                return referenceProvider.getReferenceByID(new EntityID(Util.convertToInt(idString)));
            case FACTORY:
                return idString;
            default:
                return null;
        }
    }

}
