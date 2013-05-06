import javax.swing.*;

/**
 * @author Mahdi
 */
public class ReferenceManagerForm {

    private JButton button1;
    private JEditorPane editorPane1;
    private JTextPane textPane1;
    private JButton button2;
    private JTable table1;
    private JPanel mainPanel;


    private void createUIComponents() {
        table1 = new JTable(new Object[10][2], new String[]{"Property", "Value"});
        int i = 0;
        table1.setValueAt("tag name", i, 0);
        table1.setValueAt("", i, 1);
        i++;
        table1.setValueAt("contains text", i, 0);
        table1.setValueAt("", i, 1);
        i++;
        table1.setValueAt("contains ID", i, 0);
        table1.setValueAt("", i, 1);
        i++;
        table1.setValueAt("contains class", i, 0);
        table1.setValueAt("", i, 1);
        i++;
        table1.setValueAt("required parent", i, 0);
        table1.setValueAt(0, i, 1);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ReferenceManagerForm");
        frame.setContentPane(new ReferenceManagerForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
