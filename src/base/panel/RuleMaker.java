package base.panel;

import base.applicator.RequestRule;
import base.grabber.GrabManager;
import base.util.Pair;
import base.util.Util;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Mahdi
 */
public class RuleMaker extends AbstractMaker {

    public RuleMaker(GrabManager manager) {
        super(manager);
    }

    protected void save() {
        RequestRule requestRule = new RequestRule();
        requestRule.setID(grabManager.getIdManager().getNextRequestRuleID());
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
            setter = Util.getSetter(name, pair.getValue(), RequestRule.class);
            if (setter != null && value != null) {
                try {
                    setter.invoke(requestRule, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        grabManager.append(requestRule);
    }

    public void initialize() {
        super.initialize(RequestRule.class.getFields());
    }

}
