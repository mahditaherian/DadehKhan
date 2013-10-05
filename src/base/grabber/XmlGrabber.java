package base.grabber;

import base.applicator.ConvertRule;
import base.applicator.IDManager;
import base.applicator.RequestRule;
import base.applicator.object.StandardEntity;
import base.applicator.object.Stuff;
import base.applicator.object.detail.DetailField;
import base.classification.Category;
import base.lang.WordManager;
import base.util.Page;
import base.util.Reference;
import base.util.UpdateRule;
import base.util.Word;
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

    private Map<String, Document> documentMap;//string
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
        documentMap = new HashMap<>();
        this.fileHolder = fileHolder;
        fileHolder.hold(Reference.class, Reference.class.getSimpleName(), "xml");
        File referenceFile = fileHolder.getFile(Reference.class.getSimpleName());
        putDocument(Reference.class.getSimpleName(), referenceFile);
        processPropertyHelper = new ProcessPropertyHelper(grabManager);
    }

    @Override
    public void grabKindOfStuff(Class<? extends Stuff> kind) {
        List<? extends Stuff> stuffs = new ArrayList<>(grabKind(kind));
        for (Stuff stuff : stuffs) {
            idManager.addStuffID(stuff.getId().getValue());
            stuffProvider.addStuff(stuff);
        }
    }

    @Override
    public void grabReferences() {
        List<Reference> references = new ArrayList<>(grabKind(Reference.class));
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
        List<RequestRule> requestRules = new ArrayList<>(grabKind(RequestRule.class));
        for (RequestRule rule : requestRules) {
            idManager.addRequestRuleID(rule.getId().getValue());
            referenceProvider.addRequestRule(rule.getId(), rule);
        }

        List<ConvertRule> convertRules = new ArrayList<>(grabKind(ConvertRule.class));
        for (ConvertRule rule : convertRules) {
            idManager.addConvertRuleID(rule.getId().getValue());
            referenceProvider.addConvertRule(rule.getId(), rule);
        }

        List<UpdateRule> updateRules = new ArrayList<>(grabKind(UpdateRule.class));
        for (UpdateRule rule : updateRules) {
            idManager.addUpdateRuleID(rule.getId().getValue());
            grabManager.getUpdateManager().addUpdateRule(rule.getId(), rule);
        }
    }

    public void grabCategories() {
        List<Category> categories = new ArrayList<>(grabKind(Category.class));
        for (Category category : categories) {
            idManager.addCategoryID(category.getId().getValue());
            grabManager.getEntityClassifier().register(category);
        }
    }

    void grabDetails() {
        List<DetailField> fields = new ArrayList<>(grabKind(DetailField.class));
        for (DetailField field : fields) {
            idManager.addFieldID(field.getId().getValue());
            grabManager.getEntityClassifier().register(field);
        }
    }

    public void grabWords() {
        Class<Word> kind = Word.class;
        WordManager wordManager = grabManager.getWordManager();
        Document doc = getDocument(kind, wordManager.getLanguage().fileName);
        if (doc == null) {
            System.err.println("xmlDocument is null");
            return;
        }
        List<Word> words = new ArrayList<>(grabKind(kind, doc));
        for (Word word : words) {
            idManager.addWordID(word.getId().getValue());
            wordManager.addWord(word);
        }
//        grabKind(kind,doc);
    }

    private <T extends StandardEntity> List<T> grabKind(Class<T> kind, Document doc) {
        List<T> objectList = new ArrayList<>();
//        Match objects = $(doc).find(kind.getSimpleName().toLowerCase());
        Match objects = $(doc).children();

        T entity;
        for (Element objElement : objects) {

            entity = processElement(kind, objElement);
            objectList.add(entity);
        }
        return objectList;

    }

    private <T extends StandardEntity> List<T> grabKind(Class<T> kind) {
        Document doc = getDocument(kind, kind.getSimpleName());
        if (doc == null) {
            System.err.println("xmlDocument is null");
            return null;
        }
        return grabKind(kind, doc);
    }

    private <T extends StandardEntity> T processElement(Class<T> kind, Element element) {
        T obj = null;
        try {
            obj = kind.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (obj != null) {
            processPropertyHelper.process(obj, element);
        }
        return obj;
    }

    protected Document getDocument(Class kind, String fileName) {
        if (documentMap.containsKey(fileName)) {
            return documentMap.get(fileName);
        } else {
            if (fileHolder.containsFile(fileName)) {
                File file = fileHolder.getFile(fileName);
                putDocument(fileName, file);
                return documentMap.get(fileName);
            } else {
                fileHolder.hold(kind, fileName, "xml");
                return getDocument(kind, fileName);
            }
        }
    }

    private void putDocument(String kind, File file) {
        try {
            documentMap.put(kind, $(file).document());
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
