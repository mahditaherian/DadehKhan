package base.grabber;

import base.applicator.object.IRRial;
import base.applicator.object.Stuff;
import base.applicator.object.USDollar;
import base.classification.Category;
import base.classification.Icon;
import base.util.*;

import java.util.ArrayList;

/**
 * @author Mahdi Taherian
 */
public enum PropertyType {
    REFER("refer",null),
    LIST("list", ArrayList.class),
    WORD("word", Word.class),
    REFERENCE("reference", Reference.class),
    FACTORY("factory", null),
    STUFF("stuff", Stuff.class),
    INTEGER("int", Integer.class),
    STRING("string", String.class),
    TYPE("type", PropertyType.class),
    IRRIAL("IRRial", IRRial.class),
    MILLION_RIAL("MillionRial", IRRial.class),
    MILLION_TOMAN("MillionToman", IRRial.class),
    TOMAN("toman", IRRial.class),
    THOUSAND_RIAL("ThousandRial", IRRial.class),
    THOUSAND_TOMAN("ThousandToman", IRRial.class),
    USDOLLAR("USDollar", USDollar.class),
    ID("id", EntityID.class),
    RELY("rely_rate", RelyRate.class),
    CATEGORY("category", Category.class),
    ICON("icon", Icon.class),

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
