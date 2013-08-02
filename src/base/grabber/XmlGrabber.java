package base.grabber;

import base.applicator.ConvertRule;
import base.applicator.IDManager;
import base.applicator.RequestRule;
import base.applicator.object.StandardEntity;
import base.applicator.object.Stuff;
import base.classification.Category;
import base.util.Page;
import base.util.Reference;
import org.joox.Match;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
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
    protected GrabManager grabManager;


    public XmlGrabber(FileHolder fileHolder, GrabManager grabManager, IDManager idManager) {
        super();
        this.idManager = idManager;
        this.grabManager = grabManager;
        this.stuffProvider = grabManager.getStuffProvider();
        this.referenceProvider = grabManager.getReferenceProvider();
        documentMap = new HashMap<Class, Document>();
        this.fileHolder = fileHolder;
        fileHolder.hold(Reference.class, "xml");
        File referenceFile = fileHolder.getFile(Reference.class);
        putDocument(Reference.class, referenceFile);
        processPropertyHelper = new ProcessPropertyHelper(grabManager);
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

    public void grabCategories() {
        List<Category> categories = new ArrayList<Category>(grabKind(Category.class));
        for (Category category : categories) {
            idManager.addCategoryID(category.getId().getValue());
            grabManager.getEntityClassifier().register(category);
        }
    }

    private <T extends StandardEntity> List<T> grabKind(Class<T> kind) {
        Document doc = getDocument(kind);
        if (doc == null) {
            System.err.println("xmlDocument is null");
            return null;
        }
        List<T> objectList = new ArrayList<T>();
//        Match objects = $(doc).find(kind.getSimpleName().toLowerCase());
        Match objects = $(doc).children();

        T entity;
        for (Element objElement : objects) {

            entity = processElement(kind, objElement);
            objectList.add(entity);
        }
        return objectList;
    }

    private <T extends StandardEntity> T processElement(Class<T> kind, Element element) {
        T obj = null;
        try {
            obj = kind.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (obj != null) {
            processPropertyHelper.process(obj, element);
        }
        return obj;
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
