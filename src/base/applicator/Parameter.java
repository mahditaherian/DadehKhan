package base.applicator;

import base.grabber.PropertyType;

/**
 * @author Mahdi
 */
public class Parameter {
    protected String name;
    protected PropertyType type;

    public Parameter(String name,  PropertyType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }


    public PropertyType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (!name.equals(parameter.name)) return false;
        if (type != parameter.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
