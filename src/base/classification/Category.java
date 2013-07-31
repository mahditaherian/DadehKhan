package base.classification;

import base.applicator.object.StandardEntity;
import base.util.EntityID;
import base.util.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mahdi
 */
public class Category {
    private Category parent;
    private List<Category> subCategories;
    private Word name;
    private Icon icon;
    private Word description;
    private EntityID id;
    private Set<StandardEntity> items;

    public Category() {
        this.parent = null;
        this.subCategories = new ArrayList<Category>();
        this.name = new Word("Farsi name");
        this.icon = null;
        this.description = new Word("Farsi description");
        this.id = null;
        items = new HashSet<StandardEntity>();
    }

    public Set<StandardEntity> getItems() {
        return items;
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
        return "Category{" +
                "name=" + name +
                '}';
    }
}
