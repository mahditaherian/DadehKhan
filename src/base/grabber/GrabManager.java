package base.grabber;

import base.applicator.ReferenceProvider;
import base.applicator.StuffProvider;
import base.applicator.object.Car;
import base.applicator.object.Stuff;
import base.util.MySqlConnector;

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

    public GrabManager() {
        connector = new MySqlConnector();
        connector.connect();
        this.fileHolder = new FileHolder();
        this.stuffProvider = new StuffProvider(connector);
        this.referenceProvider = new ReferenceProvider(xmlGrabber, connector, stuffProvider.getStuffs());
        this.xmlGrabber = new XmlGrabber(fileHolder, stuffProvider, referenceProvider);
        this.htmlGrabber = new HtmlGrabber(connector, referenceProvider, stuffProvider);
        this.stuffs = new ArrayList<Class<? extends Stuff>>();
    }

    public void initializeData() {

        stuffs.add(Car.class);
        xmlGrabber.grabRules();
        xmlGrabber.grabReferences();
        for (Class<? extends Stuff> clazz : stuffs) {
            xmlGrabber.grabKindOfStuff(clazz);
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
            htmlGrabber.grabKindOfStuff(clazz);
        }
    }
}
