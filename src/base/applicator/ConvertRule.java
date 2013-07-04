package base.applicator;

import base.grabber.PropertyType;
import base.util.EntityID;
import base.util.Util;

/**
 * @author Mahdi Taherian
 */
public class ConvertRule {
    public String explosion = "";
    public String keyword;
    public int index = -1;
    public PropertyType type;
    public String name;
    public EntityID ID;
    public String parent;

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

    public void setIndex(int index) {
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

    public EntityID getID() {
        return ID;
    }

    public void setID(EntityID ID) {
        this.ID = ID;
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
