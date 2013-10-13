package base.grabber;

import base.applicator.object.Car;
import base.applicator.object.detail.Detail;
import base.applicator.object.Stuff;
import base.applicator.object.detail.DetailField;
import base.applicator.object.detail.FieldType;
import base.classification.Category;
import base.classification.Icon;
import base.unit.Unit;
import base.util.*;
import base.util.datetime.WeeklyDuration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Mahdi Taherian
 */
public enum PropertyType {
    REFER("refer", null),
    LIST("list", ArrayList.class),
    WORD("word", Word.class),
    REFERENCE("reference", Reference.class),
    FACTORY("factory", null),
    STUFF("stuff", Stuff.class),
    INTEGER("int", Integer.class),
    STRING("string", String.class),
    TYPE("type", PropertyType.class),
    CAR("car", Car.class),
    CLAZZ("clazz", Class.class),
    //    CURRENCY("currency", Currency.class),
//    IRRIAL("IRRial", IRRial.class),
//    MILLION_RIAL("MillionRial", IRRial.class),
//    MILLION_TOMAN("MillionToman", IRRial.class),
//    TOMAN("toman", IRRial.class),
//    THOUSAND_RIAL("ThousandRial", IRRial.class),
//    THOUSAND_TOMAN("ThousandToman", IRRial.class),
//    USDOLLAR("USDollar", USDollar.class),
    FIELD("field", DetailField.class),
    FIELD_TYPE("fieldType", FieldType.class),
    UNIT("unit", Unit.class),
    ID("id", EntityID.class),
    RELY("rely_rate", RelyRate.class),
    CATEGORY("category", Category.class),
    ICON("icon", Icon.class),
    DETAIL("detail", Detail.class),
    DURATION("duration", WeeklyDuration.class),
    DURATIONS("durations", HashMap.class),

    PAGE("page", Page.class),
    HTML("html", Page.class),
    HOST("host", HostType.class);
    

    public String key;
    public Class<?> clazz;


    PropertyType(String str, Class<?> clazz) {
        this.key = str;
        this.clazz = clazz;
    }

    public static PropertyType getValue(String str) {
        for (PropertyType type : PropertyType.values()) {
            if (type.key.equalsIgnoreCase(str)) {
                return type;
            }
        }
        return null;
    }
}
