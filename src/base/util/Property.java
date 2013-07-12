package base.util;

import base.grabber.PropertyType;

/**
 * @author Mahdi
 */
public class Property {
    String name;
    Object value;
    PropertyType type;

    public Property(String name, Object value, PropertyType type) {
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

    public PropertyType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
