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
            if(idCategoryMap.containsKey(category.getId())){
                System.out.println("Warning:This category id is exist....");
            }
            idCategoryMap.put(category.getId(), category);
        }
    }

    public Category getCategory(EntityID id){
        return idCategoryMap.get(id);
    }


}
