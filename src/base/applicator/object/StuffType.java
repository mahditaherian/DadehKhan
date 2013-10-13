package base.applicator.object;

import base.Config;
import base.applicator.Parameter;
import base.classification.Category;
import base.classification.EntityType;
import base.grabber.PropertyType;
import base.util.EntityID;
import base.util.Word;
import java.util.ArrayList;
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
    private EntityType entityType;
    private Class<? extends Stuff> clazz;

    public StuffType() {
        stuffIDMap = new HashMap<>();
        stuffs = new ArrayList<>();
        addParameter(new Parameter("name", PropertyType.WORD));
        addParameter(new Parameter("fields", PropertyType.LIST));
        addParameter(new Parameter("clazz", PropertyType.CLAZZ));
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
        setEntityType(EntityType.get(clazz));
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
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
    
    @Override
    public String toString(){
        return name.get(Config.DEFAULT_LANGUAGE);
    }
}
