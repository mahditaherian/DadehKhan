package base.applicator.object.detail;

/**
 *
 * @author Mahdi
 */
public enum FieldType {

    //numerical
    INT("int"),
    DOUBLE("double"),
    FLOAT("float"),
    LONG("long"),
    SHORT("short"),
    //non-numerical
    STRING("string");
    String val;

    private FieldType(String str) {
        val = str;
    }

    public static FieldType getValue(String string) {
        for (FieldType type : FieldType.values()) {
            if (type.val.equalsIgnoreCase(string)) {
                return type;
            }
        }
        return null;
    }
}