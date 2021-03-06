package base.classification;

import base.applicator.Parameter;
import base.applicator.object.StandardEntity;
import base.grabber.PropertyType;
import base.util.EntityID;
import base.util.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mahdi
 */
public class Category extends StandardEntity {
    private Category parent;
    private List<Category> subCategories;
    private Word name;
    private Icon icon;
    private Word description;
    private EntityID id;
    private Set<StandardEntity> items;

    public Category() {
        super();
        this.parent = null;
        this.subCategories = new ArrayList<>();
//        this.name = new Word("Farsi name");
        this.icon = null;
//        this.description = new Word("Farsi description");
        this.id = null;
        this.items = new HashSet<>();

        addParameter(new Parameter("parent", PropertyType.CATEGORY));
        addParameter(new Parameter("subCategories", PropertyType.LIST));
        addParameter(new Parameter("name", PropertyType.WORD));
        addParameter(new Parameter("description", PropertyType.WORD));
        addParameter(new Parameter("icon", PropertyType.ICON));
        addParameter(new Parameter("id", PropertyType.ID));
    }

    public Set<StandardEntity> getItems() {
        return items;
    }

    @Override
    public void setCategory(Category category) {
        
    }
    
    

    /**
     * this method add an entity item to this category and parents if available.<br/>
     * if category contains this item existing one will be replaced with new one.
     *
     * @param entity entity which should add into category items list.
     */
    public void addItem(StandardEntity entity) {
        if (parent != null) {
            parent.addItem(entity);
        }
        if (items.contains(entity)) {
            System.out.println(this.toString() + " contains this item... now going to remove it");
            items.remove(entity);
        }
//        variables.addAll(entity.getVariables());
        for (Parameter var : entity.getVariables()){
            variables.add(var);
            propertyNameMap.put(var.getName(),entity.getPropertyName(var.getName()));
        }
        items.add(entity);
    }

    /**
     * remove the specific entity item from this category items list if exists and parent of it if available.
     *
     * @param entity entity which should add into category items list.
     */
    public void removeItem(StandardEntity entity) {
        if (!items.contains(entity)) {
            return;
        }
        if (parent != null) {
            parent.removeItem(entity);
        }

        items.remove(entity);
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Word getDescription() {
        return description;
    }

    public void setDescription(Word description) {
        this.description = description;
    }

    public EntityID getId() {
        return id;
    }

    public void setId(EntityID id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
