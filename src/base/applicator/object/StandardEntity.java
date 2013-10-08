package base.applicator.object;

import base.applicator.Parameter;
import base.applicator.Property;
import base.classification.Category;
import base.grabber.PropertyType;
import base.util.EntityID;
import base.util.Page;
import base.util.Word;

import java.util.*;

/**
 * @author Mahdi
 */
public abstract class StandardEntity extends Entity {

    private Property property;
    private Category category;
    protected Word name;
    protected Map<Page, List<Property>> referencePropertyMap;
    protected Map<String, Word> propertyNameMap = new HashMap<>();
    protected Set<Parameter> variables = new HashSet<>();
    protected EntityID id;

    public StandardEntity() {
        setId(id);
        setName(name);
        setCategory(category);
    }

    public Property getProperty() {
        return property;
    }

    protected void setProperty(Property property) {
        this.property = property;
    }

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        addParameter(new Property("name", name, PropertyType.WORD));
        this.name = name;
    }

    public EntityID getId() {
        return id;
    }

    public void setId(EntityID id) {
        addParameter(new Property("id", id, PropertyType.ID));
        this.id = id;
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
        if (category != null) {
            category.addItem(this);
        }
        this.category = category;
    }
}
