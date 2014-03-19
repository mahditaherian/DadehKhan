package base.util;

import base.applicator.ConvertRule;

/**
 *
 * @author Mahdi
 */
public class GrabPage {
    private Page page;
    private EntityID convertRuleID;

    public GrabPage(Page page, EntityID convertRule) {
        this.page = page;
        this.convertRuleID = convertRule;
    }

    public Page getPage() {
        return page;
    }

    public EntityID getConvertRuleID() {
        return convertRuleID;
    }
}
