/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.panel.component;

import base.util.Word;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Mahdi
 */
public class WordComponent extends javax.swing.JPanel {

    private boolean multiline = false;

    /**
     * Creates new form WordComponent
     */
    public WordComponent() {
        initComponents();
        jText = multiline ? new JTextArea() : new JTextField();
        initText();
    }

    public Word getWord() {
        Word word = new Word();
        word.set(base.Config.DEFAULT_LANGUAGE, jText.getText());
        return word;
    }
    
    public void setWord(Word word){
        jText.setText(word.get(base.Config.DEFAULT_LANGUAGE));
    }
    
    public void setText(Object obj) {
        jText.setText(String.valueOf(obj));
    }

    public void setMultiline(boolean b) {
        if (b != multiline) {
            String txt = jText.getText();
            jText = b ? new JTextArea(txt) : new JTextField(txt);
            initText();
        }
    }
    private javax.swing.text.JTextComponent jText;
    
    private void initText(){
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jText, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jText, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 191, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
