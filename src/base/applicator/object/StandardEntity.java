package base.applicator.object;

import base.applicator.Parameter;
import base.applicator.Property;
import base.classification.Category;
import base.grabber.PropertyType;
import base.util.Page;
import base.util.Word;

import java.util.*;

/**
 * @author Mahdi
 */
public abstract class StandardEntity {
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private Property property;
    private Category category;
    protected Word name;
    protected Map<Page, List<Property>> referencePropertyMap;
    protected Map<String, Word> propertyNameMap = new HashMap<>();
    protected Set<Parameter> variables = new HashSet<>();

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

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        addParameter(new Property("name", name, PropertyType.WORD));
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public Word getPropertyName(String name) {
        return propertyNameMap.get(name);
    }

    public Set<Parameter> getVariables() {
        return variables;
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
