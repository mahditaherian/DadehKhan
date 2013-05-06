package base.applicator.object;

import base.util.Word;

/**
 * @author Mahdi
 */
public abstract class Currency {
    private long value;
    protected Word name;

    protected Currency() {
        value = 0;
    }

    protected Currency(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
