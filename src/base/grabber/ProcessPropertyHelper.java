package base.grabber;

import base.applicator.ConvertRule;
import base.applicator.ReferenceProvider;
import base.applicator.RequestRule;
import base.applicator.StuffProvider;
import base.applicator.object.Currency;
import base.applicator.object.Stuff;
import base.util.*;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi
 */
public class ProcessPropertyHelper {

    private ReferenceProvider referenceProvider;
    private StuffProvider stuffProvider;

    public ProcessPropertyHelper(ReferenceProvider referenceProvider, StuffProvider stuffProvider) {
        this.referenceProvider = referenceProvider;
        this.stuffProvider = stuffProvider;
    }

    public Page processPage(Node node) {
        Page page = new Page();
        int rate = Util.convertToInt(node.getAttributes().getNamedItem("rate").getNodeValue());
        page.setRate(RelyRate.valueOf(rate));
        String url = node.getAttributes().getNamedItem("link").getNodeValue();
        page.setUrl(url);
        String idStr = node.getAttributes().getNamedItem("id").getNodeValue();
        int id = Util.convertToInt(idStr);
        page.setId(new EntityID(id));
        EntityID requestRuleID = new EntityID(Util.convertToInt(node.getAttributes().getNamedItem("request_rule").getNodeValue()));
        RequestRule requestRule = referenceProvider.getRequestRuleByID(requestRuleID);
        page.setRequestRule(requestRule);
        return page;
    }

    public Word processWord(Node node) {
        String pe = node.getAttributes().getNamedItem("pe").getNodeValue();
        String en = node.getAttributes().getNamedItem("en").getNodeValue();
        String fi = node.getAttributes().getNamedItem("fi").getNodeValue();
        return new Word(pe, en, fi);
    }

    public List processList(Node node) {
        List<Object> clsList = new ArrayList<Object>();
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            clsList.add(node.getChildNodes().item(i));
        }
        return clsList;
    }

    public Object processProperty(Object obj, Node node) {
        if (node == null) {
            return null;
        }
        String type = "";
        String kind = node.getNodeName();
        String content = node.getTextContent();
        if (content.isEmpty() && node.getAttributes() != null) {
            Node attr = node.getAttributes().getNamedItem("value");
            if (attr != null) {
                content = attr.getNodeValue();
            }
        }
        if (kind == null || kind.equalsIgnoreCase("#text")) {
            return content;
        }
        PropertyType propertyType = PropertyType.STRING;
        KindType kindType;
        try {
            kindType = KindType.valueOf(kind.toUpperCase());
        } catch (IllegalArgumentException ex) {
            kindType = null;
        }
        if (node.getAttributes() != null) {
            Node namedItem = node.getAttributes().getNamedItem("type");
            if (namedItem != null) {
                type = namedItem.getNodeValue();
                if (type == null) {
                    type = "";
                }
            }
            propertyType = PropertyType.getValue(type);
            if (propertyType == null) {
                propertyType = PropertyType.getValue(node.getNodeName());

                if (propertyType == null) {
                    propertyType = PropertyType.STRING;
                }
            }
        }
        switch (propertyType) {
            case LIST:
                List<Object> clsList = new ArrayList<Object>();
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    Object val = processProperty(obj, node.getChildNodes().item(i));
                    if (val != null) {
                        clsList.add(val);
                    }
                }
                return clsList;
            case WORD:
                return processWord(node);

            case REFERENCE:
            case FACTORY:

                return processKind(obj, propertyType, kindType, node);

            case INTEGER:
                return Util.convertToInt(content);
            case STRING:
                return content;
            case TYPE: {
                return processType(node);
            }
            case HTML:
            case PAGE: {
                return processPage(node);
            }
            case USDOLLAR:
            case IRRIAL: {
                return processCurrency(node, propertyType);
            }
        }
        return null;
    }

    private Currency processCurrency(Node node, PropertyType propertyType) {
        Currency cur = null;
        try {
            cur = (Currency) propertyType.clazz.newInstance();
            double value = Util.convertToDouble(node.getAttributes().getNamedItem("value").getNodeValue());

            cur.setValue(value);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        assert cur != null;
        return cur;
    }

    private PropertyType processType(Node node) {
        PropertyType type;
        if (node == null || node.getAttributes() == null || node.getAttributes().getLength() == 0) {
            type = null;
        } else {
            String val = node.getAttributes().getNamedItem("value").getNodeValue();
            type = PropertyType.getValue(val);
        }
        return type;
    }

    public Object processKind(Object obj, PropertyType propertyType, KindType kindType, Node node) {
        if (kindType == null) {
            return null;
        }
        if (kindType.equals(KindType.REFER)) {
            String idString = node.getAttributes().getNamedItem("id").getNodeValue();
            switch (propertyType) {
                case REFERENCE:
                    Page page = referenceProvider.getPageByID(new EntityID(Util.convertToInt(idString)));
                    if (obj instanceof Stuff && page !=null) {
                        Stuff stuff = (Stuff) obj;

                        String[] ids = node.getAttributes().getNamedItem("convert_rule").getNodeValue().split(",");
                        ConvertRule convertRule;
                        EntityID convertRuleID;
                        for (String id : ids) {
                            convertRuleID = new EntityID(Util.convertToInt(id));
                            convertRule = referenceProvider.getConvertRuleByID(convertRuleID);
                            stuff.addRule(page, convertRule);
                        }
                    }
                    return page;
                case FACTORY:
                    return idString;
                default:
                    return null;
            }
        }
        return null;
    }
}
