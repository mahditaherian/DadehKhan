package base.applicator.object;

import base.util.Word;

/**
 * @author Mahdi
 */
public class USDollar extends Currency {

    {
        name = new Word("دلار آمریکا", "US Dollar" , "Dolar");
    }

    public USDollar() {
        super();
    }

    public USDollar(long value) {
        super(value);
    }
}
