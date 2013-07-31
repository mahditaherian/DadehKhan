package base.classification;

import base.util.EntityID;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public class EntityClassifier {

    private Map<EntityID, Category> idCategoryMap = new HashMap<EntityID, Category>();

    public void register(Category category) {
        if (category != null && category.getId() != null) {
            idCategoryMap.put(category.getId(), category);
        }
    }


}
