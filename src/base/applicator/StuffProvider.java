package base.applicator;

import base.applicator.object.Stuff;
import base.applicator.object.StuffType;
import base.classification.EntityType;
import base.util.EntityID;
import base.util.MySqlConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mahdi Taherian
 */
public class StuffProvider extends Provider {

    private List<Stuff> stuffs;
    private List<StuffType> stuffTypes;
    //private Map<EntityType,StuffType> entityStuffTypeMap;
    private Map<EntityID, Stuff> stuffIDMap;

    public StuffProvider(MySqlConnector connector) {
        super(connector);
        this.stuffs = new ArrayList<>();
        stuffTypes = new ArrayList<>();
        stuffIDMap = new HashMap<>();
    }

    @Override
    public void update() {
    }

    @Override
    public void initialize() {
    }

    @Override
    public boolean needUpdate() {
        return true;
    }

    public void addStuff(Stuff stuff) {
        stuffs.add(stuff);
        stuff.getStuffType().addStuff(stuff);
        stuffIDMap.put(stuff.getId(), stuff);
    }

    public void addStuffType(StuffType type) {
        stuffTypes.add(type);
    }

    public List<StuffType> getStuffTypes() {
        return stuffTypes;
    }

    public List<Stuff> getStuffs() {
        return stuffs;
    }

    public void setStuffKinds(List<StuffType> stuffTypes) {
        this.stuffTypes = stuffTypes;
    }
    
    public Stuff getStuffByID(EntityID id){
        return stuffIDMap.get(id);
    }
//
//    public StuffType getStuffType(EntityType entityType) {
//        return entityStuffTypeMap.get(entityType);
//    }
}
