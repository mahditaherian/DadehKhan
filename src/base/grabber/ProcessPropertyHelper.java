package base.grabber;

import base.applicator.*;
import base.applicator.object.Currency;
import base.applicator.object.StandardEntity;
import base.applicator.object.Stuff;
import base.classification.Category;
import base.classification.Icon;
import base.util.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.joox.JOOX.$;

/**
 * @author Mahdi
 */
public class ProcessPropertyHelper {

    private ReferenceProvider referenceProvider;
    private GrabManager grabManager;

    public ProcessPropertyHelper(GrabManager grabManager) {
        this.grabManager = grabManager;
        this.referenceProvider = grabManager.getReferenceProvider();
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
        String host = node.getAttributes().getNamedItem("host").getNodeValue();
        page.setHost(HostType.valueOf(host.toUpperCase()));
        return page;
    }

    public Word processWord(Node node) {
        String pe = node.getAttributes().getNamedItem("pe").getNodeValue();
        String en = node.getAttributes().getNamedItem("en").getNodeValue();
        String fi = node.getAttributes().getNamedItem("fi").getNodeValue();
        return new Word(pe, en, fi);
    }

//    public List processList(Node node) {
//        List<Object> clsList = new ArrayList<Object>();
//        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
//            clsList.add(node.getChildNodes().item(i));
//        }
//        return clsList;
//    }

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
        PropertyType kindType;
        try {
            kindType = PropertyType.getValue(kind);
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
                Node lNode = node.getAttributes().getNamedItem("kind");
                PropertyType listKind = lNode != null ? PropertyType.getValue(lNode.getNodeValue()) : null;
                for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                    Object val = processProperty(obj, node.getChildNodes().item(i));
                    if (val != null) {
                        if (listKind == null || Util.isInstance(listKind.clazz, val.getClass())) {
                            clsList.add(val);
                        }
                    }
                }
                return clsList;
            case WORD:
                return processWord(node);

//            case REFERENCE:
//            case FACTORY:
//
//                return processKind(obj, kindType, node);
            case CATEGORY:
                return processCategory(obj, node);

            case ICON:
                return processIcon(node);
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

            case REFER: {
                return processKind(obj, kindType, node);
            }
        }
        return null;
    }

    private Icon processIcon(Node node) {
        Icon icon = new Icon();
        icon.setId(null);//todo
        String url = node.getAttributes().getNamedItem("url").getNodeValue();
        icon.setUrl(url);
        return icon;
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

    public Object processKind(Object obj, PropertyType kindType, Node node) {
        if (kindType == null) {
            return null;
        }
        String idString = node.getAttributes().getNamedItem("id").getNodeValue();
        EntityID id = new EntityID(Util.convertToInt(idString));
        switch (kindType) {
            case PAGE:
                Page page = referenceProvider.getPageByID(id);
                if (obj instanceof Stuff && page != null) {
                    Stuff stuff = (Stuff) obj;
                    String[] ids = node.getAttributes().getNamedItem("convert_rule").getNodeValue().split(",");
                    ConvertRule convertRule;
                    EntityID convertRuleID;
                    for (String i : ids) {
                        convertRuleID = new EntityID(Util.convertToInt(i));
                        convertRule = referenceProvider.getConvertRuleByID(convertRuleID);
                        stuff.addRule(page, convertRule);
                    }
                }
                return page;
            case FACTORY:
                return id;
            case CATEGORY:
                return this.grabManager.getEntityClassifier().getCategory(id);
        }
        return null;
    }

    public Category processCategory(Object obj, Node node) {
        Category category = new Category();
        category.setParent(obj == null ? null : (Category) obj);
        Element element = $(node).get(0);
        process(category, element);
        grabManager.getIdManager().addCategoryID(category.getId().getValue());
        grabManager.getEntityClassifier().register(category);
        return category;
    }

    public void process(StandardEntity entity, Element objElement) {
        List<Parameter> parameters = new ArrayList<Parameter>(entity.getParameters());
        for (Parameter property : parameters) {
            Node node = objElement.getElementsByTagName(property.getName()).item(0);
            if (node == null) {
                String attr = objElement.getAttribute(property.getName().toLowerCase());
                if (!attr.isEmpty()) {
                    Object val = processAttribute(AttributeType.valueOf(property.getName().toUpperCase()), attr);
                    Method method = Util.getSetter(property.getName(), property.getType().clazz, entity.getClass());
                    Util.invoke(method, entity, val);
                }
            } else {
                Object val = processProperty(entity, node);
                Method method = Util.getSetter(property.getName(), property.getType().clazz, entity.getClass());
                Util.invoke(method, entity, val);
            }
        }
    }

    private Object processAttribute(AttributeType attribute, String value) {
        switch (attribute) {
            case ID:
                int id = Util.convertToInt(value);
                return new EntityID(id);
        }
        return null;
    }
}
