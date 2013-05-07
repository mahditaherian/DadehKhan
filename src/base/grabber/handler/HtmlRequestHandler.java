package base.grabber.handler;

import base.applicator.ConvertRule;
import base.applicator.RequestRule;
import base.util.Property;
import base.util.Util;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Mahdi
 */
public class HtmlRequestHandler {

//    public RequestHandler() {
//    }

    public Element getElementByRule(Document doc, RequestRule rule) {
        return getElementByRule(doc.body(), rule);
    }

    public Element getElementByRule(Element elm, RequestRule rule) {
        String id = rule.getContainsID();
        Elements allElements;

        if (id != null && !id.isEmpty()) {
            Element element = elm.getElementById(id);
            allElements = new Elements(element);
        } else {
            allElements = elm.getAllElements();
        }

        //check elements tag
        allElements.retainAll(getElementsByTag(allElements, rule.getTagName()));

        //check elements class
        allElements.retainAll(getElementsByClass(allElements, rule.getContainsClass()));

        //check inner text
        allElements.retainAll(getElementsContainsText(allElements, rule.getContainsText()));

        //set parents instead
        if (rule.getRequiredParent() > 0) {
            Elements result = new Elements();
            for (Element element : allElements) {
                Element parent = element;
                for (int i = 0; i < rule.getRequiredParent(); i++) {
                    parent = parent.parent();
                }
                result.add(parent);
            }
            allElements.retainAll(result);
        }

        Element element = allElements.get(rule.getResultIndex());

        if (element == null) {
            System.out.println("no such elements found by this rule!");
        }
        return element;
    }

    public List<Element> getElementsByTag(Elements elements, String tag) {
        List<Element> result = new ArrayList<Element>();
        if (tag != null && !tag.equals("")) {
            for (Element element : elements) {
                if (element.tagName().equalsIgnoreCase(tag)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    public List<Element> getElementsByClass(Elements elements, String className) {
        List<Element> result = new ArrayList<Element>();
        if (className != null && !className.equals("")) {
            for (Element element : elements) {
                if (element.className().equalsIgnoreCase(className)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    public List<Property> convert(Element element, List<ConvertRule> rules) {
        List<Property> properties = new ArrayList<Property>();
        for (ConvertRule rule : rules) {
            properties.add(convert(element, rule));
        }
        return properties;
    }

    public Property convert(Element element, ConvertRule convertRule) {
        Object value;
        Elements elements = element.getElementsContainingText(convertRule.getExplosion());
        Element goalElement = elements.get(convertRule.getIndex());
        if (convertRule.getPropertyClass().equals(Integer.class)) {
            value = Util.convertToInt(goalElement.text());
        } else {
            value = Util.convertToInt(goalElement.text());
        }
        return new Property(convertRule.getPropertyName(), value, convertRule.getPropertyClass());
    }

    public List<Element> getElementsContainsText(Elements elements, String text) {
        List<Element> result = new ArrayList<Element>();
        if (text != null && !text.equals("")) {
            String textLowerCase = text.toLowerCase();
            for (Element element : elements) {
                if (element.text().toLowerCase().contains(textLowerCase)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

}
