package structure;

//import articles.struct.EditorPaneStructure;

import javax.swing.*;

public class StructureDialog extends JDialog {

    EditorPaneStructure pnlStructure;
    public StructureDialog(JFrame parent, JEditorPane source) {
        super(parent, "Structure");

        pnlStructure=new EditorPaneStructure(source);
        pnlStructure.refresh();
        getContentPane().add(pnlStructure);
        setSize(700,500);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
