package base.applicator;

import base.grabber.PropertyType;

/**
 * @author Mahdi
 */
public class Property extends Parameter {
    Object value;

    public Property(String name, Object value, PropertyType type) {
        super(name, type);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
