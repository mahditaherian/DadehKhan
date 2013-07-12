package base.panel;

import base.applicator.RequestRule;
import base.grabber.GrabManager;
import base.util.Pair;
import base.util.Util;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public class RuleMaker extends JFrame {
    private JPanel panel;
    private JButton preview;
    private JButton save;
    private JTextArea result;
    private Map<String, Pair<JComponent, Class<?>>> componentNameMap;
    private GrabManager grabManager;

    public RuleMaker(GrabManager manager) {
        super();
        this.grabManager = manager;
        componentNameMap = new HashMap<String, Pair<JComponent, Class<?>>>();
        panel = new JPanel();
        preview = new JButton("preview");
        save = new JButton("save");
        result = new JTextArea(12, 36);
        initialize();
        getContentPane().add(panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initializeListener();
    }

    private void initializeListener() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    private void save() {
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

    public Object processComponentValue(JComponent component) {
        if (component instanceof JTextComponent) {
            JTextComponent text = (JTextComponent) component;
            return text.getText();
        }

        return null;
    }


    public void initialize() {
        int gridY = 0;
        JSplitPane split;
        JTextField text;

        Class<RequestRule> requestRuleClass = RequestRule.class;

        for (Field field : requestRuleClass.getFields()) {
            if (!isValidType(field.getType())) {
                continue;
            }
            text = new JTextField();
            text.setName(field.getName());
            componentNameMap.put(field.getName(), new Pair<JComponent, Class<?>>(text, field.getType()));
            split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JLabel(field.getName() + "(" + field.getType().getSimpleName() + ")"), text);
            split.setPreferredSize(new Dimension(400, 30));
            split.setDividerLocation(140);
            panel.add(split,
                    new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.VERTICAL,
                            new Insets(30 * gridY + 5, 5, 5, 5), 10, 10));
            gridY++;
        }

        panel.add(save,
                new GridBagConstraints(0, gridY++, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                        new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));

        panel.add(preview,
                new GridBagConstraints(0, gridY++, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                        new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));

        panel.add(new JScrollPane(result, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                new GridBagConstraints(0, gridY++, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                        new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));

    }

    public static boolean isValidType(Class<?> type) {
        return Util.isInstance(type, Integer.class) || Util.isInstance(type, String.class);
    }


}
