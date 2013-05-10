package base.grabber;

import base.grabber.handler.HtmlRequestHandler;
import base.applicator.ReferenceProvider;
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
    private HtmlRequestHandler htmlRequestHandler;

    public HtmlGrabber(MySqlConnector connector, ReferenceProvider referenceProvider, StuffProvider stuffProvider) {
        super();
        this.referenceProvider = referenceProvider;
        this.stuffProvider = stuffProvider;
        this.connector = connector;
        htmlRequestHandler = new HtmlRequestHandler();
    }


    public void grabAllStuff() {
        for (Class<? extends Stuff> kind : stuffProvider.getStuffKins()) {
            grabKindOfStuff(kind);
        }
    }

    @Override
    public void grabKindOfStuff(Class<? extends Stuff> kind) {
        List<Stuff> stuffs = stuffProvider.getStuffs(kind);
        for (Stuff stuff : stuffs) {
            grab(stuff);
        }
    }

    public void grab(Stuff stuff) {
        Element element;
        for (Pair<Reference, RequestRule> reference : stuff.getReferences()) {
            element = htmlRequestHandler.getElementByRule(reference.getKey().getDocument(), reference.getValue());
            stuff.setProperties(htmlRequestHandler.convert(element, stuff.getConvertRules(reference.getKey())));
        }
    }
}
