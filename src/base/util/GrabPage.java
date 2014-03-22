package base.util;

import base.applicator.ConvertRule;
import java.util.List;

/**
 *
 * @author Mahdi
 */
public class GrabPage {
    private Page page;
    private List<ConvertRule> convertRuleIDs;

    public GrabPage(Page page, List<ConvertRule> convertRules) {
        this.page = page;
        this.convertRuleIDs = convertRules;
    }

    public Page getPage() {
        return page;
    }

    public List<ConvertRule> getConvertRules() {
        return convertRuleIDs;
    }
}
