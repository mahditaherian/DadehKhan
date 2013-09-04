package base.unit;

/**
 * @author Mahdi
 */
public enum Amount {
    MILLI(0.001),
    CENTI(0.01),
    DECI(0.01),

    ONE(1),

    THOUSAND(1000),
    KILO(THOUSAND.value),

    MILLION(1000000),
    MEGA(MILLION.value),

    BILLION(1000000000),
    GIGA(BILLION.value),
    ;

    double value;

    Amount(double value) {
        this.value = value;
    }
}
