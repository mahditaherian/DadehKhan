package base.applicator;

/**
 * @author Mahdi Taherian
 */

public class RequestRule {
    private String tagName = "table";
    private String containsText = "I30";
    private String containsID = "";
    private String containsClass = "";
    private int requiredParent = 0;
    private int resultIndex = 0;

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
