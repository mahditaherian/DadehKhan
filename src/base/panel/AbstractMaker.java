package base.panel;

import base.applicator.Parameter;
import base.applicator.object.StandardEntity;
import base.grabber.GrabManager;
import base.util.Pair;
import base.util.Util;
import base.util.Word;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

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
    protected int gridY = 0;

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

    public void initialize(Collection<Parameter> parameters) {
        JSplitPane split;
//        JTextField text;
        JComponent field = null;

//        Class<RequestRule> requestRuleClass = RequestRule.class;

        for (Parameter parameter : parameters) {
//            if (!isValidType(parameter.getType().clazz)) {
//                continue;
//            }
//            text = new JTextField();
//            text.setName(parameter.getName());
            field = getParameterComponent(parameter);
            if (field == null) {
                continue;
            }
            componentNameMap.put(parameter.getName(), new Pair<JComponent, Class<?>>(field, parameter.getType().clazz));
            split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JLabel(parameter.getName() + "(" + parameter.getType().clazz.getSimpleName() + ")"), field);
            split.setPreferredSize(new Dimension(400, field.getHeight()));
            split.setDividerLocation(140);
            panel.add(split,
                    new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.VERTICAL,
                            new Insets(30 * gridY + 5, 5, 5, 5), 10, 10));
            gridY++;
        }

        panel.add(save,
                new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                        new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));
        gridY++;
        panel.add(preview,
                new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                        new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));
        gridY++;
        panel.add(new JScrollPane(result, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                        new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));
        gridY++;

    }

    protected Object processValue(JComponent component, Class<?> clazz) {
        Object value = null;
        if (Util.isInstance(clazz, Integer.class)) {
            value = Util.convertToInt(String.valueOf(processComponentValue(component)));
        } else if (Util.isInstance(clazz, String.class)) {
            value = String.valueOf(processComponentValue(component));
        } else if (Util.isInstance(clazz, List.class)) {
            value = processComponentValue(component);
        } else if (Util.isInstance(clazz, Word.class)) {
            String pe = processComponentValue((JTextField) component.getComponents()[0]).toString();
            String en = processComponentValue((JTextField) component.getComponents()[1]).toString();
            String fi = processComponentValue((JTextField) component.getComponents()[2]).toString();
            value = new Word(pe, en, fi);
        }
        return value;
    }

    public JComponent getParameterComponent(Parameter parameter) {
        JComponent field = null;
        switch (parameter.getType()) {
            case INTEGER:
            case STRING:
                field = new JTextField();

                field.setName(parameter.getName());
                field.setSize(200, 30);
                break;
            case WORD:
                field = new JPanel();
                Dimension dim = new Dimension(50, 22);
                JTextField textField = new JTextField();
                textField.setPreferredSize(dim);
                field.add(textField);
                textField = new JTextField();
                textField.setPreferredSize(dim);
                field.add(textField);
                textField = new JTextField();
                textField.setPreferredSize(dim);
                field.add(textField);
                field.setSize(200, 30);
                break;
            case LIST:
                JList<StandardEntity> list = new JList<StandardEntity>(new DefaultListModel<StandardEntity>());
                list.setSize(200, 100);
                field = list;
                break;
        }
        return field;
    }

    public Object processComponentValue(JComponent component) {
        if (component instanceof JTextComponent) {
            JTextComponent text = (JTextComponent) component;
            return text.getText();
        } else if (component instanceof JList) {
            JList list = (JList) component;
            List<StandardEntity> entities = new ArrayList<StandardEntity>();
            for (int i = 0; i < list.getModel().getSize(); i++) {
                StandardEntity entity = (StandardEntity) list.getModel().getElementAt(i);
                entities.add(entity);
            }
            return entities;
        }

        return null;
    }

    protected void save() {

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
        return Util.isInstance(type, Integer.class) ||
                Util.isInstance(type, String.class) ||
                Util.isInstance(type, List.class) ||
                Util.isInstance(type, Word.class);
    }
}
