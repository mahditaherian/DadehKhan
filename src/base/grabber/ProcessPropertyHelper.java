package base.grabber;

import base.applicator.object.detail.Detail;
import base.applicator.ConvertRule;
import base.applicator.Parameter;
import base.applicator.ReferenceProvider;
import base.applicator.RequestRule;
import base.applicator.object.Entity;
import base.applicator.object.Stuff;
import base.applicator.object.detail.DetailField;
import base.applicator.object.detail.DetailValue;
import base.applicator.object.detail.FieldType;
import base.applicator.object.detail.StringValue;
import base.classification.Category;
import base.classification.Icon;
import base.unit.Amount;
import base.unit.Unit;
import base.unit.UnitKind;
import base.util.*;
import base.util.datetime.DayOfWeek;
import base.util.datetime.WeeklyDuration;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.joox.JOOX.$;
import org.w3c.dom.NodeList;

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
        EntityID updateRuleID = new EntityID(Util.convertToInt(node.getAttributes().getNamedItem("update_rule").getNodeValue()));
        UpdateRule updateRule = grabManager.getUpdateManager().getUpdateRule(updateRuleID);
        page.setUpdateRule(updateRule);
        String host = node.getAttributes().getNamedItem("host").getNodeValue();
        page.setHost(HostType.valueOf(host.toUpperCase()));
        return page;
    }

    public Word processWord(Node node) {
        String idStr = node.getAttributes().getNamedItem("id").getNodeValue();
        EntityID id = new EntityID(Util.convertToInt(idStr));
//        String en = node.getAttributes().getNamedItem("en").getNodeValue();
//        String fi = node.getAttributes().getNamedItem("fi").getNodeValue();
        return grabManager.getWordManager().getWord(id);
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
                List<Object> clsList = new ArrayList<>();
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
                Word word = processWord(node);
                return word;

//            case REFERENCE:
//            case FACTORY:
//
//                return processKind(obj, kindType, node);
            case CATEGORY:
                return processCategory(obj, node);

            case DURATIONS:
                return processDurations(node);

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
            case UNIT: {
                return processUnit(node);
            }
//            case USDOLLAR:
//            case IRRIAL: {
//                return processCurrency(node, propertyType);
//            }
            case DETAIL: {
                return processDetail(node);
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

//    private Currency processCurrency(Node node, PropertyType propertyType) {
//        Currency cur = null;
//        try {
//            cur = (Currency) propertyType.clazz.newInstance();
//            double value = Util.convertToDouble(node.getAttributes().getNamedItem("value").getNodeValue());
//
//            cur.setValue(value);
//        } catch (InstantiationException | NullPointerException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        assert cur != null;
//        return cur;
//    }
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
            case FIELD:
                return this.grabManager.getEntityClassifier().getDetailField(id);
        }
        return null;
    }

    public Detail processDetail(Node node) {
        //<param type="detail" field="1" value="8" isnumeric="no" isrefer="no" />
        Node nameNode = node.getAttributes().getNamedItem("field");
        DetailField field = null;
        DetailValue value = null;
        if (nameNode != null) {
            EntityID fieldID = new EntityID(Util.convertToInt(nameNode.getNodeValue()));
            field = grabManager.getEntityClassifier().getDetailField(fieldID);
        }
        if (field == null) {
            throw new IllegalStateException();
        }

        boolean isValueRefer = false;

        Node referNode = node.getAttributes().getNamedItem("isrefer");
        if (referNode != null) {
            String val = referNode.getNodeValue().trim();
            if (val.equalsIgnoreCase("yes")) {
                isValueRefer = true;
            }
        }

        //determining type of value
//        Node numericNode = node.getAttributes().getNamedItem("isnumeric");
//        Util.NumberType numType = Util.NumberType.INT;
////        boolean isNumeric = false;
//        if (numericNode != null) {
//            String val = numericNode.getNodeValue().trim();
//            if (val.equalsIgnoreCase("yes")) {
//                isNumeric = true;
//            }
//        }


        //process detail value
        Node valNode = node.getAttributes().getNamedItem("value");
        if (valNode != null) {
            if (isValueRefer) {
                Word word = grabManager.getWordManager().getWord(new EntityID(Util.convertToInt(valNode.getNodeValue())));
                value = new StringValue(word);

            } else {
//                Util.convert(valNode.getNodeValue(), field.getFieldType());
                value = DetailValue.newInstance(valNode.getNodeValue(), field.getFieldType());
            }
        }

        return new Detail(field, value);
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

    public Unit processUnit(Node node) {
        String kindStr = node.getAttributes().getNamedItem("kind").getNodeValue();
        String amountStr = node.getAttributes().getNamedItem("amount").getNodeValue();

        UnitKind unitKind = UnitKind.valueOf(kindStr.toUpperCase());
        Unit unit = null;
        try {
            unit = unitKind.clazz.newInstance();
            unit.setKind(unitKind);
            if (amountStr != null) {
                unit.setAmount(Amount.valueOf(amountStr.toUpperCase()));
            }

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return unit;
    }

    public void process(Entity entity, Element objElement) {
        List<Parameter> parameters = new ArrayList<>(entity.getParameters());
        for (Parameter property : parameters) {
            Node node = objElement.getElementsByTagName(property.getName()).item(0);
            if (node == null) {
                //it means, no such parameter in element children were found.
                //so looking for it in attributes
                String attr = objElement.getAttribute(property.getName().toLowerCase());
                if (!attr.isEmpty()) {
                    Object val = processAttribute(AttributeType.getValue(property.getName().toUpperCase()), attr);
                    Method method = Util.getSetter(property.getName(), property.getType().clazz, entity.getClass());
                    Util.invoke(method, entity, val);
                } else {
                    //no such parameters were found in this element
//                    System.out.println(property + " not found for " + entity);
                }
            } else {
                //it means, this parameter were found in child attributes
                Object val = processProperty(entity, node);
                Method method = Util.getSetter(property.getName(), property.getType().clazz, entity.getClass());
                Util.invoke(method, entity, val);
            }
        }
    }

    private Object processAttribute(AttributeType attribute, String value) {
        int id;
        switch (attribute) {
            case ID:
                id = Util.convertToInt(value);
                return new EntityID(id);
            case NAME:
                id = Util.convertToInt(value);
                return grabManager.getWordManager().getWord(new EntityID(id));
            case VALUE:
                return value;
            case FIELD: {
                return FieldType.getValue(value);
            }
            case CATEGORY:{
                id = Util.convertToInt(value);
                return grabManager.getEntityClassifier().getCategory(new EntityID(id));
            }

        }
        return null;
    }

    private Map<WeeklyDuration, Integer> processDurations(Node node) {
        Map<WeeklyDuration, Integer> durations = new HashMap<>();
        NodeList childNodes = node.getChildNodes();


        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node child = node.getChildNodes().item(i);
            if (child.getNodeName().equals("time")) {
                Pair<WeeklyDuration, Integer> duration = processDuration(child);
                durations.put(duration.getKey(), duration.getValue());
            }
        }
        return durations;
    }

    private Pair<WeeklyDuration, Integer> processDuration(Node node) {
        Node nameNode = node.getAttributes().getNamedItem("start");
        int start = Util.convertToInt(nameNode.getNodeValue());
        nameNode = node.getAttributes().getNamedItem("end");
        int end = Util.convertToInt(nameNode.getNodeValue());
//        nameNode = node.getAttributes().getNamedItem("period");
//        int period = Util.convertToInt(nameNode.getNodeValue());
        nameNode = node.getAttributes().getNamedItem("interval");
        int interval = Util.convertToInt(nameNode.getNodeValue());
        WeeklyDuration duration = new WeeklyDuration();
        duration.setStartSecondOfDay(start);
        duration.setEndSecondOfDay(end);
        duration.setDayOfWeek(DayOfWeek.EVERY_DAY);//todo should get from xml file instead of constant declaration
        return new Pair<>(duration, interval);
    }
}
