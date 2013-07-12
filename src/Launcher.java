import base.grabber.GrabManager;
import base.panel.RuleMaker;

import javax.swing.*;

/**
 * Created by: Mahdi Taherian
 */

public class Launcher {

    public static void main(String[] args) {
        GrabManager manager = new GrabManager();
        manager.initializeData();
//        manager.execute();

        RuleMaker ruleMaker = new RuleMaker(manager);
        ruleMaker.setSize(450, 550);
        ruleMaker.setResizable(false);
//        ruleMaker.setLocationRelativeTo(null);
//        ruleMaker.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ruleMaker.setVisible(true);
    }
}
