import base.grabber.GrabManager;
import base.panel.AbstractMaker;
import base.panel.StuffMaker;

/**
 * Created by: Mahdi Taherian
 */

public class Launcher {

    public static void main(String[] args) {
        GrabManager manager = new GrabManager();
        manager.initializeData();
//        manager.execute();

        StuffMaker stuffMaker = new StuffMaker(manager);
//        RuleMaker ruleMaker = new RuleMaker(manager);

        runMaker(stuffMaker);
    }

    private static void runMaker(AbstractMaker maker){
        maker.setSize(450, 550);
        maker.setResizable(false);
//        ruleMaker.setLocationRelativeTo(null);
//        ruleMaker.setExtendedState(JFrame.MAXIMIZED_BOTH);
        maker.setVisible(true);
    }
}
