package base.applicator;

import base.grabber.KindType;
import base.grabber.PropertyType;

/**
 * @author Mahdi
 */
public class Property extends Parameter {

    Object value;
    private KindType kind;

    public Property(String name, Object value, PropertyType type) {
        this(name, value, type, false);
    }
    
    public Property(String name, Object value, PropertyType type,boolean simple) {
        super(name, type);
        this.value = value;
        this.simple = simple;
    }

    public Object getValue() {
        return value;
    }

    public KindType getKind() {
        return kind;
    }

    public void setKind(KindType kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Property{"
                + "name='" + name + '\''
                + ", value=" + value
                + '}';
    }
}
