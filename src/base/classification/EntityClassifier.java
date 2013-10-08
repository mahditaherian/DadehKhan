package base.classification;

import base.applicator.object.detail.DetailField;
import base.util.EntityID;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public class EntityClassifier {

    private Map<EntityID, Category> idCategoryMap = new HashMap<>();
    private Map<EntityID, DetailField> idDetailMap = new HashMap<>();
    private Category root;
    private Category car;
    private Category currency;
    private Category metal;

    public void register(Category category) {
        if (category != null && category.getId() != null) {
            if (idCategoryMap.containsKey(category.getId())) {
                System.out.println("Warning:This category id is exist....");
            }
            idCategoryMap.put(category.getId(), category);
        }
    }

    public void register(DetailField field) {
        if(field != null && field.getId() !=null){
//            field.getCategory().addItem(field);
            idDetailMap.put(field.getId(), field);
        }
    }

    public Category getCar() {
        return car;
    }
    
    public DetailField getDetailField(EntityID id){
        return idDetailMap.get(id);
    }

    public Category getCurrency() {
        return currency;
    }

    public Category getMetal() {
        return metal;
    }

    public Category getCategory(EntityID id) {
        return idCategoryMap.get(id);
    }
}
