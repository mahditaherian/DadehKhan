package base.applicator.object;

import base.util.Word;

/**
 * @author Mahdi
 */
public class IRRial extends Currency {

    {
        name = new Word("ریال", "Rial" , "Rial");
    }
    public IRRial() {
        super();
    }

    public IRRial(long value) {
        super(value);
    }
}
