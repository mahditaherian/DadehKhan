/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.panel;

import base.applicator.object.Car;
import base.classification.Category;
import base.classification.EntityType;
import base.grabber.GrabManager;
import base.util.EntityID;

/**
 *
 * @author Mahdi
 */
public class ContentPanel extends javax.swing.JPanel {

    private GrabManager grabManager;

    /**
     * Creates new form ContentPanel
     */
    public ContentPanel(GrabManager grabManager) {
        this.grabManager = grabManager;
        initComponents();
        categoryPanel1.setContentPanel(this);
        navigationPanel2.setContentPanel(this);
    }

    public ContentPanel() {
        this.grabManager = null;
        initComponents();
        categoryPanel1.setContentPanel(this);
        navigationPanel2.setContentPanel(this);
    }

    public void setCategory(Category category) {
        navigationPanel2.show(category);
        categoryPanel1.show(category);
        revalidate();
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navigationPanel2 = new base.panel.NavigationPanel();
        categoryPanel1 = new base.panel.CategoryPanel(grabManager);

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navigationPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
            .addComponent(categoryPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(navigationPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoryPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private base.panel.CategoryPanel categoryPanel1;
    private base.panel.NavigationPanel navigationPanel2;
    // End of variables declaration//GEN-END:variables

    void showItem(EntityID id, EntityType entityType) {
        
        switch(entityType){
            case CAR:{
                Car car = grabManager.getStuffProvider().getCarByID(id);
                CarViewer carViewer = new CarViewer();
                carViewer.show(car);
                carViewer.setVisible(true);
            }
        }
    }
}
