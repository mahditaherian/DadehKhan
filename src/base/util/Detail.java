package base.util;

import base.applicator.Property;
import base.applicator.object.StandardEntity;
import base.grabber.PropertyType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi
 */
public class Detail<T> extends StandardEntity {
    List<Property> properties = new ArrayList<>();
    private DetailKind kind;
//    public Word name;

    {
        addParameter(new Property("kind", kind, PropertyType.LIST));
    }

    public void add(String name, T value) {
        Property property = new Property(name, value.toString(), PropertyType.STRING);
        properties.add(property);
    }

    public List<Property> getProperties() {
        return properties;
    }

    public enum DetailKind {
        TABLE,
        CHART
    }
}
