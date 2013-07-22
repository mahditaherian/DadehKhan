package base.panel;

//import base.applicator.RequestRule;

import base.applicator.object.Car;
import base.applicator.object.Stuff;
import base.grabber.GrabManager;
import base.util.Pair;
import base.util.Util;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Mahdi
 */
public class StuffMaker extends AbstractMaker {
    protected StuffMaker(GrabManager manager) {
        super(manager);
    }

    protected void save() {
        Class<? extends Stuff> stuffClass = Car.class;

        try {
            Stuff stuff = stuffClass.newInstance();

            stuff.setId(grabManager.getIdManager().getNextRequestRuleID());
            Pair<JComponent, Class<?>> pair;
            Object value;
            Method setter;
            for (String name : componentNameMap.keySet()) {
                if (!componentNameMap.containsKey(name)) {
                    continue;
                }
                pair = componentNameMap.get(name);
                if (Util.isInstance(pair.getValue(), Integer.class)) {
                    value = Util.convertToInt(String.valueOf(processComponentValue(pair.getKey())));
                } else if (Util.isInstance(pair.getValue(), String.class)) {
                    value = String.valueOf(processComponentValue(pair.getKey()));
                } else {
                    continue;
                }
                setter = Util.getSetter(name, pair.getValue(), stuffClass);
                if (setter != null && value != null) {
                    try {
                        setter.invoke(stuff, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            grabManager.append(stuff);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initialize() {

    }
}
