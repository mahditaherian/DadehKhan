package base.applicator;

import base.applicator.object.StandardEntity;
import base.grabber.PropertyType;
import base.util.EntityID;

/**
 * @author Mahdi Taherian
 */

public class RequestRule extends StandardEntity {
    public String tagName = "table";
    public String containsText = "I30";
    public String containsID = "";
    public String containsClass = "";
    public int requiredParent = 0;
    public int resultIndex = 0;
    public EntityID ID;

    {
        addParameter(new Property("tagName", tagName, PropertyType.STRING));
        addParameter(new Property("containsText", containsText, PropertyType.STRING));
        addParameter(new Property("containsID", containsID, PropertyType.STRING));
        addParameter(new Property("containsClass", containsClass, PropertyType.STRING));
        addParameter(new Property("id", ID, PropertyType.ID));
        addParameter(new Property("requiredParent", requiredParent, PropertyType.INTEGER));
        addParameter(new Property("resultIndex", resultIndex, PropertyType.INTEGER));
    }

    public RequestRule() {

    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getContainsText() {
        return containsText;
    }

    public void setContainsText(String containsText) {
        this.containsText = containsText;
    }

    public String getContainsID() {
        return containsID;
    }

    public void setContainsID(String containsID) {
        this.containsID = containsID;
    }

    public String getContainsClass() {
        return containsClass;
    }

    public void setContainsClass(String containsClass) {
        this.containsClass = containsClass;
    }

    public int getRequiredParent() {
        return requiredParent;
    }

    public void setRequiredParent(int requiredParent) {
        this.requiredParent = requiredParent;
    }

    public int getResultIndex() {
        return resultIndex;
    }

    public void setResultIndex(int resultIndex) {
        this.resultIndex = resultIndex;
    }

    public EntityID getID() {
        return ID;
    }

    public void setID(EntityID ID) {
        this.ID = ID;
    }
}
