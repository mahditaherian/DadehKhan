package base.applicator;

import base.applicator.object.StandardEntity;
import base.grabber.PropertyType;
import base.util.EntityID;
import base.util.Util;

/**
 * @author Mahdi Taherian
 */
public class ConvertRule extends StandardEntity {
    public String explosion = "";
    public String keyword;
    public int index = -1;
    public PropertyType type;
    public String name;
    public EntityID id;
    public String parent;

    {
        addParameter(new Property("explosion", explosion, PropertyType.STRING));
        addParameter(new Property("keyword", keyword, PropertyType.STRING));
        addParameter(new Property("name", name, PropertyType.STRING));
        addParameter(new Property("id", id, PropertyType.ID));
        addParameter(new Property("index", index, PropertyType.INTEGER));
        addParameter(new Property("type", type, PropertyType.TYPE));
        addParameter(new Property("parent", parent, PropertyType.STRING));
    }

    public ConvertRule(String explosionTag, int index, PropertyType propertyType, String propertyName) {
        this.explosion = explosionTag;
        this.index = index;
        this.type = propertyType;
        setName(propertyName);
    }

    public ConvertRule() {
        this.explosion = "";
        index = -1;
    }

    public String getExplosion() {
        return explosion;
    }

    public void setExplosion(String explosion) {
        this.explosion = explosion;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    /**
     * <b>Note</b> name Must Start with uppercase character
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Util.capitalize(name);
    }

    public EntityID getId() {
        return id;
    }

    public void setId(EntityID id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
