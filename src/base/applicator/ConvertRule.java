package base.applicator;

import base.applicator.object.StandardEntity;
import base.grabber.PropertyType;
import base.unit.Unit;

/**
 * @author Mahdi Taherian
 */
public class ConvertRule extends StandardEntity {
    public String explosion = "";
    public String keyword;
    public int index = -1;
    public Unit unit;
    public String varName;
//    public EntityID id;
    public String parent;

    {
        addParameter(new Property("explosion", explosion, PropertyType.STRING));
        addParameter(new Property("keyword", keyword, PropertyType.STRING));
        addParameter(new Property("varName", varName, PropertyType.STRING));
//        addParameter(new Property("id", id, PropertyType.ID));
        addParameter(new Property("index", index, PropertyType.INTEGER));
        addParameter(new Property("unit", unit, PropertyType.UNIT));
        addParameter(new Property("parent", parent, PropertyType.STRING));
    }

    public ConvertRule(String explosionTag, int index, Unit unit, String propertyName) {
        super();
        this.explosion = explosionTag;
        this.index = index;
        this.unit = unit;
        setVarName(propertyName);
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * <b>Note</b> name Must Start with uppercase character
     */
    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

//    public EntityID getId() {
//        return id;
//    }
//
//    public void setId(EntityID id) {
//        this.id = id;
//    }

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
