package base.applicator.object;

import base.applicator.Parameter;
import base.classification.Category;
import base.grabber.PropertyType;
import base.util.EntityID;
import base.util.Word;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mahdi
 */
public class StuffType extends Entity {

    private Word name;
    private List<Category> fields;
    private List<Stuff> stuffs;
    private Map<EntityID,Stuff> stuffIDMap;
    private Class<? extends Stuff> clazz;

    public StuffType() {
        stuffIDMap = new HashMap<>();
        addParameter(new Parameter("name", PropertyType.WORD));
        addParameter(new Parameter("fields", PropertyType.LIST));
    }

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        this.name = name;
    }

    public List<Category> getFiealds() {
        return fields;
    }

    public List<Stuff> getStuffs() {
        return stuffs;
    }
    
    public void addStuff(Stuff stuff){
        stuffs.add(stuff);
        stuffIDMap.put(stuff.getId(), stuff);
    }
    
    public Stuff getStuffByID(EntityID id){
        return stuffIDMap.get(id);
    }

    public void setClazz(Class<? extends Stuff> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Stuff> getClazz() {
        return clazz;
    }
    
    public void addField(Category detailCategory){
        fields.add(detailCategory);
    }

    public void setFields(List<Category> fields) {
        this.fields = fields;
    }
}
