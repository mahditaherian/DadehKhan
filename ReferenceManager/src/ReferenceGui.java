import org.joox.Match;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.joox.JOOX.$;

/**
 * @author Mahdi Taherian
 */
public class ReferenceGui extends JFrame {
    JButton runBtn = new JButton("Run");
    JTable table;
    private JPanel panel1;

    public void start() {
        append();
        table = new JTable(/*createModel()*/);
        setSize(750, 550);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, runBtn, new JScrollPane());
        split.setDividerLocation(900);
        getContentPane().add(split);
    }

//    public TableModel createModel() {
//        Class<? extends Stuff> kind;
//        TableModel tableModel = new DefaultTableModel();
//        Document doc = getDocument(kind);
//        if (doc == null) {
//            System.err.println("xmlDocument is null");
//            return null;
//        }
//        Stuff stuff = null;
//// Wrap the document with the jOOX API
//        org.joox.Match stuffs = $(doc).find(kind.getSimpleName().toLowerCase());
//        Field[] fields;
//        for (Element stuffElement : stuffs) {
//            try {
//                stuff = kind.newInstance();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            if (stuff == null) {
//                continue;
//            }
//
//            fields = kind.getFields();
//            for (Field field : fields) {
////                if (field.isAccessible()) {
//                Node node = stuffElement.getElementsByTagName(field.getName()).item(0);
//                if (node == null) {
//                    continue;
//                }
//                try {
//                    field.set(stuff, processProperty(node));
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }

    private void append() {
        File file = new File("data/reference/reference.xml");
        try {
            Match doc = $(file);
            Match references = $(doc).find("reference");
            List<Element> elements = references.get();
            for (Element element : elements) {
                Match att = $(element);
                for (Element subRef : att.get()) {

                }
            }
            Match reference = $(references);
            for (int i = 0; i < references.size(); i++) {
                Match refElement = $(reference.get(i));
                for (int j = 0; j < reference.size(); j++) {
                    String attr = refElement.get(0).getAttribute("type");
                    refElement = reference.next();
                }
                reference = $(references);
            }
            for (Element element : references.child()) {

            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private

    private TableModel recursiveFunction(Document doc) {
        DefaultTableModel model = new DefaultTableModel();
        NodeList props = doc.getElementsByTagName("reference");


        Element element = doc.getDocumentElement();
        NodeList nodeList = doc.getChildNodes();
        Node node;
        String[] rows = new String[nodeList.getLength()];
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            rows[i] = node.getNodeName();
        }
        return null;
    }

}
