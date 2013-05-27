package structure;

import base.applicator.RequestRule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.*;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class EditorPaneStructure extends JPanel {
    JEditorPane sourcePane;
    int maximumLevel = 0;
    int level = 0;
    int markedLevel = 0;
    private Map<String, String> tableAttributes = new HashMap<String, String>();
    private org.jsoup.nodes.Document sourceDocument;
    JLabel lblViewBounds = new JLabel() {
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(new Color(200, 200, 255, 128));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    };
    //    JTree trDocument = new JTree() {
//        public String getToolTipText(MouseEvent event) {
//            return processDocumentTooltip(event);
//        }
//    };
    private final static int MAX_ROW_SIZE = 20;
    JTable table = new JTable(MAX_ROW_SIZE, 2);
    JTree trView = new JTree() {
        public String getToolTipText(MouseEvent event) {
            return processViewTooltip(event);
        }
    };
    JButton btnRefresh = new JButton("Refresh");
    JButton btnMark = new JButton("Mark as key");
    JButton btnMakeRule = new JButton("make rule");
    JTextArea txtRule = new JTextArea();

    public EditorPaneStructure(JEditorPane source) {
        this.sourcePane = source;

        init();
        initListeners();
    }

    protected void init() {
        setLayout(new GridBagLayout());
        btnMakeRule.setEnabled(false);

        add(new JLabel("Field attributes"), new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(200, 100));
        add(scroll, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        add(new JLabel("Views structure"), new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        scroll = new JScrollPane(trView);
        scroll.setPreferredSize(new Dimension(200, 300));
        add(scroll, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        add(new JLabel("XML Rule"), new GridBagConstraints(0, 4, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        scroll = new JScrollPane(txtRule);
        scroll.setPreferredSize(new Dimension(200, 100));
        add(scroll, new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        add(btnRefresh, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(5, 5, 5, 5), 0, 0));
        add(btnMark, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(5, 100, 5, 5), 0, 0));
        add(btnMakeRule, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(5, 220, 5, 5), 0, 0));
        btnRefresh.setToolTipText("Press here to refresh trees");
        btnMark.setRequestFocusEnabled(false);
    }

    protected void initListeners() {
        sourcePane.setFocusable(true);
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        sourcePane.setEditable(false);
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                sourcePane.requestFocusInWindow();
                if (e.getKeyChar() == '+') {
                    level++;
                    if (level > maximumLevel) {
                        level = maximumLevel;
                    } else {
                        getCurrentElement(level);
                    }
                } else if (e.getKeyChar() == '-') {
                    level--;
                    if (level < 0) {
                        level = 0;
                    } else {
                        getCurrentElement(level);
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        this.setFocusable(true);
        this.addKeyListener(keyListener);
//        btnMark.addKeyListener(keyListener);
        sourcePane.addKeyListener(keyListener);
        sourcePane.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getCurrentElement(level = 0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

//        sourcePane.addMouseMotionListener(new MouseMotionListener() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//
//            }
//
//            long currentTime = System.currentTimeMillis();
//
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                if (System.currentTimeMillis() - currentTime >= 100) {
//                    currentTime = System.currentTimeMillis();
//                    try {
//                        final Robot robot = new Robot();
//                        robot.mousePress(InputEvent.BUTTON1_MASK);
//                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
//                    } catch (AWTException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//            }
//        });
        trView.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getNewLeadSelectionPath() != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();
                    org.jsoup.nodes.Element element = (org.jsoup.nodes.Element) node.getUserObject();
                    View v = elementViewMap.get(element);
                    if (v.getParent() == null && node.getParent() != null) {
                        View vParent = elementViewMap.get(((DefaultMutableTreeNode) node.getParent()).getUserObject());
                        v = vParent.getView(vParent.getViewIndex(v.getStartOffset(), Position.Bias.Forward));
                    }
                    fillTable(element);
                    Rectangle r = getAllocation(v, sourcePane).getBounds();
                    lblViewBounds.setBounds(r);
                    sourcePane.add(lblViewBounds);
                    sourcePane.repaint();
                }
            }
        });

        btnMark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnMakeRule.setEnabled(true);
                markedLevel = level;
//                RequestRule rule = new RequestRule();
//                rule.setContainsClass(tableAttributes.get("class"));
//                rule.setContainsID(tableAttributes.get("id"));
//                rule.setTagName(tableAttributes.get("name"));
//                rule.setContainsText(tableAttributes.get("containsText"));
//                rule.setRequiredParent(level);
//                rule.setResultIndex(0);
//                txtRule.setText(ReferenceManager.makeXML(rule));
            }
        });

        btnMakeRule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestRule rule = new RequestRule();
                rule.setContainsClass(tableAttributes.get("class"));
                rule.setContainsID(tableAttributes.get("id"));
                rule.setTagName(tableAttributes.get("tagName"));
                rule.setContainsText(tableAttributes.get("containsText"));
                rule.setRequiredParent(level - markedLevel);
                rule.setResultIndex(0);
                txtRule.setText(ReferenceManager.makeXML(rule));
            }
        });
    }

    public void refresh() {
        createElementViewMap();
        if (sourcePane != null) {
            if (sourceDocument.children().size() > 1) {
                System.out.println("hfdsjlhujklh");
            }
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(sourceDocument.children().get(0));
            rf(node, sourceDocument.children().get(0));
            trView.setModel(new DefaultTreeModel(node));
            int row = 0;
            while (row < trView.getRowCount()) {
                trView.expandRow(row);
                row++;
            }
            trView.setToolTipText(" ");
        }
    }


    public void rf(DefaultMutableTreeNode treeNode, org.jsoup.nodes.Element elementRoot) {
        for (org.jsoup.nodes.Element element : elementRoot.children()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(element);
            treeNode.add(node);
            rf(node, element);
            maximumLevel = Math.max(maximumLevel, node.getLevel());
        }
    }

    public void buildViewTree(DefaultMutableTreeNode root, View v) {
        for (int i = 0; i < v.getViewCount(); i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(v.getView(i));
            root.add(node);
            buildViewTree(node, v.getView(i));
            maximumLevel = Math.max(maximumLevel, node.getLevel());
        }
    }

    protected AttributeSet getAttributes(Element elem) {
        if (elem instanceof AbstractDocument.AbstractElement) {
            try {
                Field f = AbstractDocument.AbstractElement.class.getDeclaredField("attributes");
                f.setAccessible(true);
                return (AttributeSet) f.get(elem);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    protected String getText(View view) {
        return getText(view.getDocument(), view.getStartOffset(), view.getEndOffset());
    }

    protected String getText(Document doc, int startOffset, int endOffset) {
        try {
            String text = doc.getText(startOffset, endOffset - startOffset);
            text = text.replaceAll("\n", "\\\\n");
            text = text.replaceAll("\t", "\\\\t");
            text = text.replaceAll("\r", "\\\\r");

            return text;
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    protected String processViewTooltip(MouseEvent e) {
        int rn = trView.getRowForLocation(e.getX(), e.getY());
        if (trView.getPathForRow(rn) != null) {
            org.jsoup.nodes.Element element = (org.jsoup.nodes.Element) ((DefaultMutableTreeNode) trView.getPathForRow(rn).getLastPathComponent()).getUserObject();
            View tn = elementViewMap.get(element);
            if (tn == null) {
                return "";
            }
            StringBuilder buff = new StringBuilder();
            buff.append("<html>");
            buff.append("<b>Start offset: </b>").append(tn.getStartOffset()).append("<br>");
            buff.append("<b>End offset: </b>").append(tn.getEndOffset()).append("<br>");
            buff.append("<b>Child count: </b>").append(tn.getViewCount()).append("<br>");
            buff.append("<b>Text: </b>\"").append(element.text()/*getText(tn.getDocument(), tn.getStartOffset(), tn.getEndOffset())*/).append("\"<br>");
            if (tn.getAttributes() != null) {
                buff.append("<b>Attributes: </b>").append("<br>");
                Enumeration names = tn.getAttributes().getAttributeNames();
                while (names.hasMoreElements()) {
                    Object name = names.nextElement();
                    Object value = tn.getAttributes().getAttribute(name);
                    buff.append("&nbsp;&nbsp;<b>").append(name).append(":</b>").append(value).append("<br>");
                }
            }
            buff.append("</html>");
            return buff.toString();
        }

        return null;
    }

    public org.jsoup.nodes.Element getCurrentElement(int levelUp) {
        int startOffset = 0, endOffset = Integer.MAX_VALUE;

        int caret = sourcePane.getCaretPosition();
        TreePath path, targetPath = null;
        DefaultMutableTreeNode treeNode = null;
        for (int i = 0; i < trView.getRowCount(); i++) {
            path = trView.getPathForRow(i);
            org.jsoup.nodes.Element element = (org.jsoup.nodes.Element) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
            View tn = elementViewMap.get(element);
            if (tn == null) {
                System.out.println("view == nul");
                continue;
            }
            if (tn.getStartOffset() < caret && caret < tn.getEndOffset()) {
                if (tn.getStartOffset() > startOffset || tn.getEndOffset() < endOffset) {
                    startOffset = tn.getStartOffset();
                    endOffset = tn.getEndOffset();
                    treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                    targetPath = path;
                }
            }
        }
        org.jsoup.nodes.Element element = null;
        if (treeNode != null) {
            element = (org.jsoup.nodes.Element) treeNode.getUserObject();
            for (int i = 0; i < levelUp && i < treeNode.getLevel(); i++) {
                if (element.parent() == null) {
                    break;
                }
                element = element.parent();
                targetPath = targetPath.getParentPath();
            }
            trView.setSelectionRow(trView.getRowForPath(targetPath));
            trView.scrollRowToVisible(trView.getRowForPath(targetPath));
        }
        fillTable(element);
        return element;
    }

    public void fillTable(org.jsoup.nodes.Element element) {
        for (int i = 0; i < MAX_ROW_SIZE; i++) {
            table.setValueAt("", i, 0);
            table.setValueAt("", i, 1);
        }
        int i = 0;
        tableAttributes.clear();
        if (element == null) {
            return;
        }
        tableAttributes.put("tagName", element.tagName());
        table.setValueAt("tagName", i, 0);
        table.setValueAt(element.tagName(), i++, 1);
        for (Attribute attribute : element.attributes()) {
            if (i >= MAX_ROW_SIZE) {
                break;
            }

            Object name = attribute.getKey();
            Object value = attribute.getValue();
            tableAttributes.put(name.toString(), value.toString());
            table.setValueAt(name, i, 0);
            table.setValueAt(value, i, 1);
            i++;
        }

        String text = element.text();
        tableAttributes.put("containsText", text);
        table.setValueAt("containsText", i, 0);
        table.setValueAt(text, i, 1);
    }

    Map<org.jsoup.nodes.Element, View> elementViewMap = new HashMap<org.jsoup.nodes.Element, View>();

    public void createElementViewMap() {
        View view = sourcePane.getUI().getRootView(sourcePane);
        sourceDocument = Jsoup.parse(App.htmlStr);
        elementViewMap.clear();
        elementViewMap.put(sourceDocument.child(0), view.getView(0));
        elementViewSync(sourceDocument.child(0), view.getView(0));
    }

    public void elementViewSync(org.jsoup.nodes.Element element, View view) {
        int count = element.children().size();
        for (int i = 0; i < count; i++) {
            if (view.getViewCount() != count) {
                System.out.println("ghjalhfrlahurljkl");
            }
            org.jsoup.nodes.Element childElement = element.child(i);
            View childView = view.getView(i);
            if (childView == null) {
                elementViewMap.put(childElement, view);
                elementViewSync(childElement, view);
            } else if (isUndefined(childElement)) {
                elementViewMap.put(childElement, view);
                elementViewSync(childElement, view);
                childView.setParent(view);
            } else {
                elementViewMap.put(childElement, childView);
                elementViewSync(childElement, childView);
                childView.setParent(view);
            }
        }
    }

    public boolean isUndefined(org.jsoup.nodes.Element element) {
        if (element.tagName().equalsIgnoreCase("tbody") ||
                element.tagName().equalsIgnoreCase("script") ||
                element.tagName().equalsIgnoreCase("html") ||
                element.tagName().equalsIgnoreCase("head")) {
            return true;
        }
        return false;
    }

    protected static Shape getAllocation(View v, JEditorPane edit) {
        Insets ins = edit.getInsets();
        View vParent = v.getParent();
        int x = ins.left;
        int y = ins.top;
        while (vParent != null) {
            int i = vParent.getViewIndex(v.getStartOffset(), Position.Bias.Forward);
            Shape alloc = vParent.getChildAllocation(i, new Rectangle(0, 0, Short.MAX_VALUE, Short.MAX_VALUE));
            x += alloc.getBounds().x;
            y += alloc.getBounds().y;

            vParent = vParent.getParent();
        }

        if (v instanceof BoxView) {
            int ind = v.getParent().getViewIndex(v.getStartOffset(), Position.Bias.Forward);
            Rectangle r2 = v.getParent().getChildAllocation(ind, new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE)).getBounds();

            return new Rectangle(x, y, r2.width, r2.height);
        }

        return new Rectangle(x, y, (int) v.getPreferredSpan(View.X_AXIS), (int) v.getPreferredSpan(View.Y_AXIS));
    }
}
