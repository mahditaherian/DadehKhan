package base.grabber;

import base.applicator.ReferenceProvider;
import base.applicator.StuffProvider;
import base.applicator.object.Stuff;
import base.applicator.object.StuffType;
import base.grabber.handler.HtmlRequestHandler;
import base.util.MySqlConnector;
import base.util.Page;
import org.jsoup.nodes.Element;

/**
 * @author Mahdi Taherian
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
        for (StuffType type : stuffProvider.getStuffTypes()) {
            grabKindOfStuff(type);
        }
    }

    @Override
    public void grabKindOfStuff(StuffType type) {
        for (Stuff stuff : type.getStuffs()) {
            grab(stuff);
        }
    }

    @Override
    public void grabReferences() {
//        throw new NotImplementedException();
    }

    public void grab(Stuff stuff) {
        Element element;
        for (Page page : stuff.getReferences()) {
            if (page != null && page.getDocument() != null) {
                if (page.getDataElement() == null || page.isDataChanged()) {
                    element = htmlRequestHandler.getElementByRule(page.getDocument(), page.getRequestRule());
                    page.setDataElement(element);
                } else {
                    element = page.getDataElement();
                }
                stuff.setProperties(page, htmlRequestHandler.convert(element, stuff.getConvertRules(page)));
            } else {
                System.out.println("page is null");
            }
        }
    }
}
