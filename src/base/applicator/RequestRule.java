package base.applicator;

/**
 * @author Mahdi Taherian
 */

public class RequestRule {
    public String tagName = "table";
    public String containsText = "I30";
    public String containsID = "";
    public String containsClass = "";
    public int requiredParent = 0;
    public int resultIndex = 0;

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
}
