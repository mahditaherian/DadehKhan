package base.panel;

//import base.applicator.RequestRule;

import base.applicator.ConvertRule;
import base.applicator.object.DefaultStuff;
import base.applicator.object.Stuff;
import base.grabber.GrabManager;
import base.util.Page;
import base.util.Pair;
import base.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mahdi
 */
public class StuffMaker<T extends Stuff> extends AbstractMaker {
    private JComboBox<Class<? extends Stuff>> stuffTypes;
    private JList<ConvertRule> rules;
    private JList<Page> pages;
    private ConvertRule selectedRule;
    private Page selectedPage;
    private Map<Page, Set<ConvertRule>> pageRuleMap;

    public StuffMaker(GrabManager manager) {
        super(manager);
    }

    protected void save() {
        if (stuffTypes == null || stuffTypes.getSelectedItem() == null || !(stuffTypes.getSelectedItem() instanceof Class)) {
            System.out.println("Warning: no valid types were selected!");
            return;
        }
        Class stuffClass = (Class) stuffTypes.getSelectedItem();
//        for (Class<? extends Stuff> stuffClass : grabManager.getStuffProvider().getStuffKinds()) {
        try {
            Object instance = stuffClass.newInstance();
            if (!(instance instanceof Stuff)) {
                return;
            }
            Stuff stuff = (Stuff) stuffClass.newInstance();

            stuff.setId(grabManager.getIdManager().getNextStuffID());
            Pair<JComponent, Class<?>> pair;
            Object value;
            Method setter;
            for (String name : componentNameMap.keySet()) {
                pair = componentNameMap.get(name);
                value = processValue(pair.getKey(), pair.getValue());
                if (value != null) {
                    setter = Util.getSetter(name, pair.getValue(), stuffClass);
                    if (setter != null) {
                        Util.invoke(setter, stuff, value);
                    }
                }
            }
            for (Map.Entry<Page, Set<ConvertRule>> pageRule : pageRuleMap.entrySet()) {
                if (!pageRule.getValue().isEmpty()) {
                    stuff.addRule(pageRule.getKey(), pageRule.getValue());
                }
            }
            grabManager.append(stuff);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        }
    }

    @Override
    protected void initialize() {
        pageRuleMap = new HashMap<Page, Set<ConvertRule>>();
        Stuff temp = new DefaultStuff();
        {
            DefaultComboBoxModel<Class<? extends Stuff>> model = new DefaultComboBoxModel<Class<? extends Stuff>>();
            for (Class<? extends Stuff> clazz : grabManager.getStuffProvider().getStuffKinds()) {
                model.addElement(clazz);
            }
            stuffTypes = new JComboBox<Class<? extends Stuff>>(model);
            stuffTypes.setPreferredSize(new Dimension(400, 30));
            panel.add(stuffTypes,
                    new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                            new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));

            gridY++;
        }
        {
            DefaultComboBoxModel<ConvertRule> ruleModel = new DefaultComboBoxModel<ConvertRule>();
            for (ConvertRule rule : grabManager.getReferenceProvider().getConvertRules()) {
                ruleModel.addElement(rule);
            }
            DefaultComboBoxModel<Page> pageModel = new DefaultComboBoxModel<Page>();
            for (Page page : grabManager.getReferenceProvider().getPages()) {
                pageRuleMap.put(page, new HashSet<ConvertRule>());
                pageModel.addElement(page);
            }


            JComboBox<Page> pageJComboBox = new JComboBox<Page>(pageModel);
            pageJComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    selectedPage = (Page) e.getItem();
                    Set<ConvertRule> convertRules = pageRuleMap.get(selectedPage);
                    DefaultListModel<ConvertRule> listModel = new DefaultListModel<ConvertRule>();
                    for (ConvertRule rule : convertRules) {
                        listModel.addElement(rule);
                    }
                    rules.setModel(listModel);
                }
            });
            pageJComboBox.setPreferredSize(new Dimension(400, 50));
            panel.add(pageJComboBox,
                    new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                            new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));

            gridY++;

            JComboBox<ConvertRule> convertRuleJComboBox = new JComboBox<ConvertRule>(ruleModel);
            convertRuleJComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    selectedRule = (ConvertRule) e.getItem();
                }
            });
            convertRuleJComboBox.setPreferredSize(new Dimension(300, 50));
            panel.add(convertRuleJComboBox,
                    new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                            new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));

            gridY++;

            JButton addBtn = new JButton("add");
            addBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedRule == null || selectedPage == null) {
                        System.out.println("Error: No rule was selected");
                        return;
                    }
                    boolean contains = ((DefaultListModel<ConvertRule>) rules.getModel()).contains(selectedRule);
//                    for (int i = 0; i < rules.getModel().getSize(); i++) {
//                        ConvertRule rule = rules.getModel().getElementAt(i);
//                        if (selectedRule.equals(rule)) {
//                            contains = true;
//                            break;
//                        }
//                    }
                    if (!contains) {
                        ((DefaultListModel<ConvertRule>) rules.getModel()).addElement(selectedRule);
                        pageRuleMap.get(selectedPage).add(selectedRule);
                    }

                    JList<Page> pages = (JList<Page>) componentNameMap.get("references").getKey();
                    contains = ((DefaultListModel<Page>) pages.getModel()).contains(selectedPage);
                    if (!contains) {
                        ((DefaultListModel<Page>) pages.getModel()).addElement(selectedPage);
                    }

                }
            });
            addBtn.setPreferredSize(new Dimension(70, 50));
            panel.add(addBtn,
                    new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                            new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));

            gridY++;


            rules = new JList<ConvertRule>(new DefaultListModel<ConvertRule>());
            rules.setPreferredSize(new Dimension(400, 100));
            panel.add(rules,
                    new GridBagConstraints(0, gridY, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.HORIZONTAL,
                            new Insets(30 * gridY + 5, 5, 5, 5), 20, 20));

            gridY++;

            rules.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (rules.getModel().getSize() == 0) {
                        return;
                    }
                    if (e.getKeyChar() == '\u007F') {
                        int index = rules.getSelectedIndex();
                        if (index >= 0) {
                            ((DefaultListModel) rules.getModel()).remove(index);
                            pageRuleMap.get(selectedPage).remove(selectedRule);
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }
        super.initialize(temp.getParameters());
    }
}
