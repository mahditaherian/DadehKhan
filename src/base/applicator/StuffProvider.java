package base.applicator;

import base.applicator.object.Stuff;
import base.applicator.object.StuffType;
import base.classification.EntityType;
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
    private Map<EntityType,StuffType> entityStuffTypeMap;

    public StuffProvider(MySqlConnector connector) {
        super(connector);
        this.stuffs = new ArrayList<>();
        stuffTypes = new ArrayList<>();
        entityStuffTypeMap = new HashMap<>();
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

    public StuffType getStuffType(EntityType entityType) {
        return entityStuffTypeMap.get(entityType);
    }
}
