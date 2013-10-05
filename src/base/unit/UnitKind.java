package base.unit;

import base.unit.currency.IRRial;
import base.unit.currency.IRToman;
import base.unit.currency.USDollar;

/**
 * @author Mahdi
 */
public enum UnitKind {
    EMPTY(null),
    IR_RIAL(IRRial.class),
    IR_TOMAN(IRToman.class),
    US_DOLLAR(USDollar.class);

    public Class<? extends Unit> clazz;

    UnitKind(Class<? extends Unit> clazz) {
        this.clazz = clazz;
    }

}
