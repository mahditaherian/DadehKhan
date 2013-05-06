package base.grabber;

import base.applicator.ReferenceProvider;
import base.applicator.RequestHandler;
import base.applicator.RequestRule;
import base.applicator.StuffProvider;
import base.applicator.object.Stuff;
import base.util.MySqlConnector;
import base.util.Pair;
import base.util.Reference;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by: Mahdi Taherian
 */
public class HtmlGrabber extends Grabber {
    private RequestHandler requestHandler;

    public HtmlGrabber(MySqlConnector connector, ReferenceProvider referenceProvider, StuffProvider stuffProvider) {
        super(connector, referenceProvider, stuffProvider);
        requestHandler = new RequestHandler();
    }


    public void grabAllStuff() {
        for (Class<? extends Stuff> kind : stuffProvider.getStuffKins()) {
            grabKindOfStuff(kind);
        }
    }

    public void grabKindOfStuff(Class<? extends Stuff> kind) {
        List<Stuff> stuffs = stuffProvider.getStuffs(kind);
        Element element;
        for (Stuff stuff : stuffs) {
            for (Pair<Reference, RequestRule> reference : stuff.getReferences()) {
                element = requestHandler.getElementByRule(reference.getKey().getDocument(), reference.getValue());
                stuff.setProperties(requestHandler.convert(element, stuff.getConvertRules(reference.getKey())));
            }
        }
    }


}
