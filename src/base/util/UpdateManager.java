package base.util;

import base.applicator.object.Stuff;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public class UpdateManager {
    private Map<EntityID, UpdateRule> updateRuleIDMap;

    public UpdateManager() {
        updateRuleIDMap = new HashMap<>();
    }

    public void updateReferences(Collection<Reference> references) {
        for (Reference reference : references) {
            if (reference.needUpdate()) {
                updateSpecificReference(reference);
            }
        }
    }

    public void updateSpecificReference(Reference reference) {
        for (Page page : reference.getPages()) {
            if (isTimeToUpdate(page)) {
                updateSpecificPage(page);
            }
        }
    }

    public void updateSpecificPage(Page page) {
        Document doc;
        try {
            if (page.getHost().equals(HostType.REMOTE)) {
                doc = Jsoup.connect(page.getUrl()).get();
            } else if (page.getHost().equals(HostType.LOCAL)) {
                File file = new File(page.getUrl());
                doc = Jsoup.parse(file, "utf-8");
            } else {
                doc = null;
            }
            page.setDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStuffReferences(Stuff stuff) {
        for (Page page : stuff.getReferences()) {
            if (isTimeToUpdate(page)) {
                updateSpecificPage(page);
            }
        }
    }

    public boolean isTimeToUpdate(Page page) {
        //todo this always returned true for deeper test
        if (page == null) {
            return false;
        }
        UpdateRule rule = page.getUpdateRule();
        if (rule == null) {
            System.out.println("there is no rule found for page:" + page.toString());
        }
        return rule == null || rule.allowUpdate();

    }

    public void addUpdateRule(EntityID id, UpdateRule rule) {
        updateRuleIDMap.put(id, rule);
    }

    public UpdateRule getUpdateRule(EntityID updateRuleID) {
        return updateRuleIDMap.get(updateRuleID);
    }
}
