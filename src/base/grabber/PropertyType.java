package base.grabber;

/**
 * @author Mahdi Taherian
 */
public enum PropertyType {
    LIST("list"),
    WORD("word"),
    REFER("refer"),
    STUFF("stuff"),
    INTEGER("int"),
    STRING("string");


    PropertyType(String str) {

    }

    public static PropertyType valueOf(String str,PropertyType defaultType){
        try {
            return valueOf(str);
        }catch (IllegalArgumentException ignored){
            return defaultType;
        }
    }
}
