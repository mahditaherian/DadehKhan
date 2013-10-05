package base.applicator.object.detail;

import base.unit.Unit;

/**
 * @author Mahdi
 */
public class NumericValue extends DetailValue<Number> {

    private Unit unit;
    public NumericValue(Number number) {
        super(number);
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
    
}
