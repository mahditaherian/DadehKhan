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
    private Map<Page, UpdateRule> pageUpdateRuleMap;

    public UpdateManager() {
        pageUpdateRuleMap = new HashMap<Page, UpdateRule>();
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
        if (page != null) {
            return true;
        }
        UpdateRule rule = pageUpdateRuleMap.get(page);
        return rule != null && rule.isOnTime();

    }
}
