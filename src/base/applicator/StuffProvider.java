package base.applicator;

import base.applicator.object.Stuff;
import base.util.MySqlConnector;

import java.util.ArrayList;
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

    public StuffProvider(MySqlConnector connector) {
        super(connector);
        this.stuffs = new ArrayList<Stuff>();
        stuffKins = new ArrayList<Class<? extends Stuff>>();
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


    public List<Stuff> getStuffs() {
        return stuffs;
    }

    public List<Stuff> getStuffs(Class<? extends Stuff> kind){
        return stuffsMap.get(kind);
    }

    public List<Class<? extends Stuff>> getStuffKins() {
        return stuffKins;
    }

    public void setStuffKins(List<Class<? extends Stuff>> stuffKins) {
        this.stuffKins = stuffKins;
    }
}