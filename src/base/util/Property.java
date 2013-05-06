package base.util;

/**
 * @author Mahdi
 */
public class Property {
    String name;
    Object value;
    Class type;

    public Property(String name, Object value, Class type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public Class getType() {
        return type;
    }
}
