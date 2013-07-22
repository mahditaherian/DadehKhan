package base.grabber.handler;

import base.applicator.ConvertRule;
import base.applicator.Property;
import base.applicator.RequestRule;
import base.applicator.object.Currency;
import base.grabber.PropertyType;
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
        if (rule.getTagName() != null && !rule.getTagName().isEmpty()) {
            List<Element> byTag = getElementsByTag(allElements, rule.getTagName());
            allElements.retainAll(byTag);
        }

        //check elements class
        if (rule.getContainsClass() != null && !rule.getContainsClass().isEmpty()) {
            allElements.retainAll(getElementsByClass(allElements, rule.getContainsClass()));
        }

        //check inner text
        if (rule.getContainsText() != null && !rule.getContainsText().isEmpty()) {
            allElements.retainAll(getElementsContainsText(allElements, rule.getContainsText()));
        }

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
        Object value = null;
        Elements e = element.getElementsContainingOwnText(convertRule.getKeyword());
        Element el = e.first();
        while (!el.tagName().equalsIgnoreCase(convertRule.getParent())) {
            el = el.parent();
            assert el != null;
        }

//        Elements elements = element.getElementsByTag(convertRule.getExplosion());
        Elements elements = el.children();
        Element goalElement = elements.get(convertRule.getIndex());
//        if (convertRule.getType().equals(PropertyType.INTEGER)) {
//            value = Util.convertToInt(goalElement.text());
//        } else {
//            value = goalElement.text();
//        }

        switch (convertRule.getType()) {
            case THOUSAND_TOMAN:
            case MILLION_TOMAN:
            case TOMAN:
            case MILLION_RIAL:
            case THOUSAND_RIAL:
            case USDOLLAR:
            case IRRIAL: {

                try {
                    Currency currency = (Currency) convertRule.getType().clazz.newInstance();
                    double val = Util.convertToDouble(goalElement.text());
                    if (convertRule.getType().equals(PropertyType.TOMAN)) {
                        val *= 10;
                    } else if (convertRule.getType().equals(PropertyType.THOUSAND_RIAL)) {
                        val *= 1000;
                    } else if (convertRule.getType().equals(PropertyType.MILLION_RIAL)) {
                        val *= 1000000;
                    } else if (convertRule.getType().equals(PropertyType.MILLION_TOMAN)) {
                        val *= 10000000;
                    } else if (convertRule.getType().equals(PropertyType.THOUSAND_TOMAN)) {
                        val *= 10000;
                    }
                    currency.setValue(val);
                    value = currency;
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                break;
            }
            case INTEGER: {
                value = Util.convertToInt(goalElement.text());
                break;
            }
            case STRING:
            default: {
                value = goalElement.text();
                break;
            }
        }
        return new Property(convertRule.getName(), value, convertRule.getType());
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
