package base.panel;

import base.grabber.GrabManager;
import base.util.Pair;
import base.util.Util;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public abstract class AbstractMaker extends JFrame {
    protected JPanel panel;
    protected JButton preview;
    protected JButton save;
    protected JTextArea result;
    protected Map<String, Pair<JComponent, Class<?>>> componentNameMap;
    protected GrabManager grabManager;

    protected AbstractMaker(GrabManager manager) {
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

    protected abstract void initialize();

    public void initialize(Field[] fields) {
        int gridY = 0;
        JSplitPane split;
        JTextField text;

//        Class<RequestRule> requestRuleClass = RequestRule.class;

        for (Field field : fields) {
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

    public Object processComponentValue(JComponent component) {
        if (component instanceof JTextComponent) {
            JTextComponent text = (JTextComponent) component;
            return text.getText();
        }

        return null;
    }

    protected void save(){

    }

    private void initializeListener() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    public static boolean isValidType(Class<?> type) {
        return Util.isInstance(type, Integer.class) || Util.isInstance(type, String.class);
    }
}
