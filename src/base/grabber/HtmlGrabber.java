package base.grabber;

import base.applicator.ReferenceProvider;
import base.applicator.StuffProvider;
import base.applicator.object.Stuff;
import base.grabber.handler.HtmlRequestHandler;
import base.util.MySqlConnector;
import base.util.Page;
import org.jsoup.nodes.Element;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        for (Class<? extends Stuff> kind : stuffProvider.getStuffKinds()) {
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

    @Override
    public void grabReferences() {
//        throw new NotImplementedException();
    }

    public void grab(Stuff stuff) {
        Element element;
        for (Page page : stuff.getReferences()) {
            if (page != null) {
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
