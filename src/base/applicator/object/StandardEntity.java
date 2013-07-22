package base.applicator.object;

import base.applicator.Parameter;
import base.applicator.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi
 */
public abstract class StandardEntity {
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private Property property;

    public List<Parameter> getParameters() {
        return parameters;
    }


    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }


}
