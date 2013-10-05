package base.grabber;

import base.Config;
import base.applicator.IDManager;
import base.applicator.ReferenceProvider;
import base.applicator.RequestRule;
import base.applicator.StuffProvider;
import base.applicator.object.Car;
import base.applicator.object.Stuff;
import base.classification.EntityClassifier;
import base.lang.WordManager;
import base.util.MySqlConnector;
import base.util.UpdateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi Taherian
 */
public class GrabManager {

    private MySqlConnector connector;
    private HtmlGrabber htmlGrabber;
    private ReferenceProvider referenceProvider;
    private FileHolder fileHolder;
    private StuffProvider stuffProvider;
    private XmlGrabber xmlGrabber;
    private XmlAppender xmlAppender;
    private List<Class<? extends Stuff>> stuffs;
    private UpdateManager updateManager;
    private IDManager idManager;
    private EntityClassifier entityClassifier;
    private WordManager wordManager;

    public GrabManager() {
        connector = new MySqlConnector();
        connector.connect();
        idManager = new IDManager();
        this.fileHolder = new FileHolder();
        this.stuffProvider = new StuffProvider(connector);
        this.referenceProvider = new ReferenceProvider(xmlGrabber, connector, stuffProvider.getStuffs());
        this.entityClassifier = new EntityClassifier();
        this.wordManager = new WordManager(Config.DEFAULT_LANGUAGE);
        this.xmlGrabber = new XmlGrabber(fileHolder, this, idManager);
        this.xmlAppender = new XmlAppender(xmlGrabber);
        this.htmlGrabber = new HtmlGrabber(connector, referenceProvider, stuffProvider);
        this.stuffs = new ArrayList<>();
        updateManager = new UpdateManager();
    }

    public IDManager getIdManager() {
        return idManager;
    }

    public void initializeData() {

        stuffs.add(Car.class);
        xmlGrabber.grabWords();
        xmlGrabber.grabCategories();
        xmlGrabber.grabDetails();
        xmlGrabber.grabRules();
        xmlGrabber.grabReferences();
        for (Class<? extends Stuff> clazz : stuffs) {
            xmlGrabber.grabKindOfStuff(clazz);
        }
        stuffProvider.setStuffKinds(stuffs);
    }

    public void append(RequestRule requestRule) {
        if (requestRule != null) {
            xmlAppender.append(requestRule);
            referenceProvider.addRequestRule(requestRule.getId(), requestRule);
        }
    }

    public void append(Stuff stuff) {
        if (stuff != null) {
            xmlAppender.append(stuff);
            stuffProvider.addStuff(stuff);
        }
    }

    public void execute() {
        if (stuffProvider.needUpdate()) {
            stuffProvider.update();
        }
        if (referenceProvider.needUpdate()) {
            referenceProvider.update();
        }

        for (Class<? extends Stuff> clazz : stuffs) {
//                htmlGrabber.grabKindOfStuff(clazz);
            for (Stuff stuff : stuffProvider.getStuffs(clazz)) {
                updateManager.updateStuffReferences(stuff);
                htmlGrabber.grab(stuff);
            }
        }
    }

    public StuffProvider getStuffProvider() {
        return stuffProvider;
    }

    public ReferenceProvider getReferenceProvider() {
        return referenceProvider;
    }

    /**
     * entity classifier getter. entity classifier is a object which manage
     * categories of objects
     *
     * @return entity classifier
     */
    public EntityClassifier getEntityClassifier() {
        return entityClassifier;
    }

    /**
     * word manager reserve words by id of them.
     *
     * @return WordManager
     */
    public WordManager getWordManager() {
        return wordManager;
    }

    public UpdateManager getUpdateManager() {
        return updateManager;
    }
}
