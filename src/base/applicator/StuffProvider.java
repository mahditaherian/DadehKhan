package base.applicator;

import base.applicator.object.Car;
import base.applicator.object.Stuff;
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
    private MySqlConnector connector;
    private List<Stuff> stuffs;
    private Map<Class<? extends Stuff>, List<Stuff>> stuffsMap;
    private ReferenceProvider referenceProvider;
    private List<Class<? extends Stuff>> stuffKins;
    private Map<EntityID , Car> carIDMap;

    public StuffProvider(MySqlConnector connector) {
        super(connector);
        this.stuffs = new ArrayList<>();
        stuffKins = new ArrayList<>();
        stuffsMap = new HashMap<>();
        carIDMap = new HashMap<>();
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
        if (!stuffsMap.containsKey(stuff.getClass())) {
            stuffsMap.put(stuff.getClass(), new ArrayList<Stuff>());
            
        }
        stuffsMap.get(stuff.getClass()).add(stuff);
        
        if(stuff instanceof Car){
            carIDMap.put(stuff.getId(), (Car)stuff);
        }
    }

    public List<Stuff> getStuffs() {
        return stuffs;
    }

    public List<Stuff> getStuffs(Class<? extends Stuff> kind) {
        return stuffsMap.get(kind);
    }

    public List<Class<? extends Stuff>> getStuffKinds() {
        return stuffKins;
    }

    public void setStuffKinds(List<Class<? extends Stuff>> stuffKins) {
        this.stuffKins = stuffKins;
    }

    public Car getCarByID(EntityID id) {
        return carIDMap.get(id);
    }
}
