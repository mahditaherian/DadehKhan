package base.applicator.object;

import base.util.Word;

/**
 * @author Mahdi
 */
public abstract class Currency {
    private double value;
    protected Word name;

    protected Currency() {
        value = 0;
    }

    protected Currency(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
