package base.grabber;

import base.applicator.ReferenceProvider;
import base.applicator.StuffProvider;
import base.applicator.object.Car;
import base.util.MySqlConnector;

/**
 * @author Mahdi Taherian
 */
public class GrabManager {
    private MySqlConnector connector;
    private Grabber grabber;
    private ReferenceProvider referenceProvider;
    private StuffProvider stuffProvider;

    public GrabManager() {
        connector = new MySqlConnector();
        connector.connect();

        this.stuffProvider = new StuffProvider(connector);
        this.referenceProvider = new ReferenceProvider(connector, stuffProvider.getStuffs());
        this.grabber = new HtmlGrabber(connector, referenceProvider, stuffProvider);
    }

    public void initializeData() {

    }

    public void execute() {
        if (stuffProvider.needUpdate()) {
            stuffProvider.update();
        }
        if (referenceProvider.needUpdate()) {
            referenceProvider.update();
        }
        grabber.grabKindOfStuff(Car.class);
    }
}
