package base.grabber;

import base.applicator.*;
import base.applicator.object.StandardEntity;
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
    private Map<Class, Document> documentMap;//string
    protected FileHolder fileHolder;
    private ProcessPropertyHelper processPropertyHelper;
    protected IDManager idManager;


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
            idManager.addRequestRuleID(rule.getId().getValue());
            referenceProvider.addRequestRule(rule.getId(), rule);
        }

        List<ConvertRule> convertRules = new ArrayList<ConvertRule>(grabKind(ConvertRule.class));
        for (ConvertRule rule : convertRules) {
            idManager.addConvertRuleID(rule.getId().getValue());
            referenceProvider.addConvertRule(rule.getId(), rule);
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

            List<Parameter> parameters = new ArrayList<Parameter>(obj.getParameters());
            for (Parameter property : parameters) {
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

    protected Document getDocument(Class kind) {
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
}
