package base.applicator;

import base.util.EntityID;

/**
 * @author Mahdi
 */
public class IDManager {
    int requestRuleIDCounter = 0;
    int convertRuleIDCounter = 0;
    int pageIDCounter = 0;
    int referenceIDCounter = 0;
    int stuffIDCounter = 0;
    int categoryIDCounter = 0;
    int detailIDCounter = 0;
    private int wordIDCounter = 0;
    private int updateRuleIDCounter = 0;

    public EntityID getNextRequestRuleID() {
        return new EntityID(++requestRuleIDCounter);
    }

    public EntityID getNextConvertRuleID() {
        return new EntityID(++convertRuleIDCounter);
    }

    public EntityID getNextPageID() {
        return new EntityID(++pageIDCounter);
    }

    public EntityID getNextReferenceID() {
        return new EntityID(++referenceIDCounter);
    }

    public EntityID getNextStuffID() {
        return new EntityID(++stuffIDCounter);
    }

    public void addRequestRuleID(int requestRuleIDCounter) {
        this.requestRuleIDCounter = Math.max(requestRuleIDCounter, this.requestRuleIDCounter);
    }

    public void addConvertRuleID(int convertRuleIDCounter) {
        this.convertRuleIDCounter = Math.max(convertRuleIDCounter, this.convertRuleIDCounter);
    }

    public void addPageID(int pageIDCounter) {
        this.pageIDCounter = Math.max(pageIDCounter, this.pageIDCounter);
    }

    public void addReferenceID(int referenceIDCounter) {
        this.referenceIDCounter = Math.max(referenceIDCounter, this.referenceIDCounter);
    }

    public void addStuffID(int stuffIDCounter) {
        this.stuffIDCounter = Math.max(stuffIDCounter, this.stuffIDCounter);
    }

    public void addCategoryID(int id) {
        this.categoryIDCounter = Math.max(id, categoryIDCounter);
    }

    public EntityID getNextCategoryID() {
        return new EntityID(++categoryIDCounter);
    }

    public void addDetailID(int id) {
        this.detailIDCounter = Math.max(id, detailIDCounter);
    }

    public EntityID getNextDetailID() {
        return new EntityID(++detailIDCounter);
    }

    public void addWordID(int id) {
        this.wordIDCounter = Math.max(id, wordIDCounter);
    }

    public EntityID getNextWordID() {
        return new EntityID(++wordIDCounter);
    }

    public void addUpdateRuleID(int id) {
        this.updateRuleIDCounter = Math.max(id, updateRuleIDCounter);
    }
    
    public EntityID getNextUpdateRuleID(){
        return new EntityID(++updateRuleIDCounter);
    }
}
