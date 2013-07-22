package base.grabber;

import base.applicator.IDManager;
import base.applicator.ReferenceProvider;
import base.applicator.RequestRule;
import base.applicator.StuffProvider;
import base.applicator.object.Car;
import base.applicator.object.Stuff;
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
    private List<Class<? extends Stuff>> stuffs;
    private UpdateManager updateManager;
    private IDManager idManager;

    public GrabManager() {
        connector = new MySqlConnector();
        connector.connect();
        idManager = new IDManager();
        this.fileHolder = new FileHolder();
        this.stuffProvider = new StuffProvider(connector);
        this.referenceProvider = new ReferenceProvider(xmlGrabber, connector, stuffProvider.getStuffs());
        this.xmlGrabber = new XmlGrabber(fileHolder, stuffProvider, referenceProvider, idManager);
        this.htmlGrabber = new HtmlGrabber(connector, referenceProvider, stuffProvider);
        this.stuffs = new ArrayList<Class<? extends Stuff>>();
        updateManager = new UpdateManager();
    }

    public IDManager getIdManager() {
        return idManager;
    }

    public void initializeData() {

        stuffs.add(Car.class);
        xmlGrabber.grabRules();
        xmlGrabber.grabReferences();
        for (Class<? extends Stuff> clazz : stuffs) {
            xmlGrabber.grabKindOfStuff(clazz);
        }
    }

    public void append(RequestRule requestRule) {
        if (requestRule != null) {
            xmlGrabber.append(requestRule);
            referenceProvider.addRequestRule(requestRule.getID(), requestRule);
        }
    }

    public void append(Stuff stuff) {
        if (stuff != null) {
            xmlGrabber.append(stuff);
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
}
