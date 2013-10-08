package base.grabber;

import base.applicator.ReferenceProvider;
import base.applicator.StuffProvider;
import base.applicator.object.StuffType;
import base.util.MySqlConnector;

/**
 * Created by: Mahdi Taherian
 */
public abstract class Grabber {
    protected ReferenceProvider referenceProvider;
    protected MySqlConnector connector;
    protected StuffProvider stuffProvider;

    protected Grabber(/*MySqlConnector connector, ReferenceProvider referenceProvider, StuffProvider stuffProvider*/) {
//        this.referenceProvider = referenceProvider;
//        this.stuffProvider = stuffProvider;
//        this.connector = connector;
    }

    public abstract void grabKindOfStuff(StuffType type);

    public abstract void grabReferences();
}
