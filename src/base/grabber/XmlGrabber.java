package base.grabber;

import base.applicator.ReferenceProvider;
import base.applicator.StuffProvider;
import base.applicator.object.Stuff;
import base.grabber.handler.HtmlRequestHandler;
import base.util.MySqlConnector;
import org.joox.Match;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.joox.JOOX.$;

/**
 * @author Mahdi Taherian
 */
public class XmlGrabber extends Grabber {
    private HtmlRequestHandler htmlRequestHandler;
    private Document xmlDocument = null;

    protected XmlGrabber(File xmlFile, MySqlConnector connector, ReferenceProvider referenceProvider, StuffProvider stuffProvider) {
        super(connector, referenceProvider, stuffProvider);
        htmlRequestHandler = new HtmlRequestHandler();
        try {
            xmlDocument = $(xmlFile).document();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void grabKindOfStuff(Class<? extends Stuff> kind) {
        if (xmlDocument == null) {
            System.err.println("xmlDocument is null");
            return;
        }
        Stuff stuff = null;
        PropertyType propertyType;

// Wrap the document with the jOOX API
        Match stuffs = $(xmlDocument).find(kind.getName().toLowerCase());
        Field[] fields;
        for (Element stuffElement : stuffs) {
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
                if (field.isAccessible()) {
                    Node node = stuffElement.getElementsByTagName(field.getName()).item(0);
                    String type = node.getAttributes().getNamedItem("type").getNodeName();
                    propertyType = PropertyType.valueOf(type.toUpperCase());
                    try {
                        field.set(stuff, processProperty(propertyType, node));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object processProperty(PropertyType type, Node node) {
        String kind = node.getAttributes().getNamedItem("kind").getNodeValue();
        switch (type) {
            case LIST:
                Class cls = null;

                try {
                    cls = Class.forName(kind);
                    List<? extends Object> clsList = new ArrayList<Object>();
                    for (int i = 0; i < node.getChildNodes().getLength(); i++) {
//                        clsList.add((Object)node.getChildNodes().item(i)); todo
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                break;
        }
        return null;
    }

    public void catchElements(Stuff stuff) {
        if (xmlDocument == null) {
            System.err.println("xmlDocument is null");
            return;
        }
// Wrap the document with the jOOX API
        Match x1 = $(xmlDocument).find(stuff.getTypeName().getEnglish());

        for (org.w3c.dom.Element element : x1) {

        }


    }
}
