package base.classification;

import base.util.EntityID;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public class EntityClassifier {

    private Map<EntityID, Category> idCategoryMap = new HashMap<EntityID, Category>();
    private Category root;
    private Category car;
    private Category currency;
    private Category metal;
    private Map<EntityType, Category> rootCategoryMap = new HashMap<>();

    public void register(Category category) {
        if (category != null && category.getId() != null) {
            if (idCategoryMap.containsKey(category.getId())) {
                System.out.println("Warning:This category id is exist....");
            }
            idCategoryMap.put(category.getId(), category);
            String name = category.getName().getEnglish();
            if (name.equalsIgnoreCase("car")) {
                car = category;
            } else if (name.equalsIgnoreCase("currency")) {
                currency = category;
            } else if (name.equalsIgnoreCase("metal")) {
                metal = category;
            }
        }
    }

    public Category getCar() {
        return car;
    }

    public Category getCurrency() {
        return currency;
    }

    public Category getMetal() {
        return metal;
    }

    public Category getRootCategory(EntityType type) {
        return rootCategoryMap.get(type);
    }

    public Category getCategory(EntityID id) {
        return idCategoryMap.get(id);
    }
}
