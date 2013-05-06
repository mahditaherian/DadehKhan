package base.applicator;

import base.util.MySqlConnector;

/**
 * @author Mahdi Taherian
 */
public abstract class Provider {
    protected MySqlConnector connector;

    protected Provider(MySqlConnector connector) {
        this.connector = connector;
    }

    public abstract void update();

    public abstract void initialize();

    public abstract boolean needUpdate();
}
