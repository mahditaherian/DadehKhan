package base.applicator.object;

import base.applicator.Parameter;
import base.applicator.Property;
import base.classification.Category;
import base.grabber.PropertyType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi
 */
public abstract class StandardEntity {
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private Property property;
    private Category category;

    public List<Parameter> getParameters() {
        return parameters;
    }


    public void addParameter(Parameter parameter) {
        if (parameters.contains(parameter)) {
            parameters.remove(parameter);
        }
        parameters.add(parameter);
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {

        if (this.category != null && !this.category.equals(category)) {
            this.category.removeItem(this);
        }
        addParameter(new Property("category", getCategory(), PropertyType.CATEGORY));
        category.addItem(this);
        this.category = category;
    }
}
