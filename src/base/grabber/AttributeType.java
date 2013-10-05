package base.grabber;

import base.applicator.ConvertRule;
import base.applicator.RequestRule;
import base.util.EntityID;
import base.util.Word;

/**
 * @author Mahdi
 */
public enum AttributeType {
    ID("id", EntityID.class),
    CONVERT_RULE("convert_rule", ConvertRule.class),
    REQUEST_RULE("request_rule", RequestRule.class),
    NAME("name", Word.class),
    VALUE("value", String.class),
    FIELD("field",null),
    CATEGORY("category",null),
    TYPE("type", null),;

    String name;
    Class<?> clazz;


    AttributeType(String type, Class<?> clazz) {
        this.name = type;
        this.clazz = clazz;
    }

    public static AttributeType getValue(String str) {
        for (AttributeType type : AttributeType.values()) {
            if (type.name.equalsIgnoreCase(str)) {
                return type;
            }
        }
        return null;
    }
}
