import base.grabber.GrabManager;

/**
 * Created by: Mahdi Taherian
 */

public class Launcher {

    public static void main(String[] args) {
        GrabManager manager = new GrabManager();
        manager.initializeData();
        manager.execute();
    }
}
