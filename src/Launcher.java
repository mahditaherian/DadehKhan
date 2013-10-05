import base.grabber.GrabManager;
import base.panel.AbstractMaker;
import base.panel.ContainerPanel;
import base.panel.component.ReferenceComponent;
import javax.swing.JFrame;

/**
 * Created by: Mahdi Taherian
 */

public class Launcher {

    public static void main(String[] args) {
        final GrabManager manager = new GrabManager();
        manager.initializeData();
//        manager.execute();

//        StuffMaker stuffMaker = new StuffMaker(manager);
//        RuleMaker ruleMaker = new RuleMaker(manager);
        
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ContainerPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContainerPanel(manager).setVisible(true);
            }
        });
//        runMaker(stuffMaker);
    }

    private static void runMaker(AbstractMaker maker){
        maker.setSize(450, 550);
        maker.setResizable(false);
//        ruleMaker.setLocationRelativeTo(null);
//        ruleMaker.setExtendedState(JFrame.MAXIMIZED_BOTH);
        maker.setVisible(true);
    }
}
