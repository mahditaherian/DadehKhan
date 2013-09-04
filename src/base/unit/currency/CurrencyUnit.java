package base.unit.currency;

import base.unit.Unit;
import base.unit.UnitKind;
import base.util.Util;
import base.util.Word;
import java.text.DecimalFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public abstract class CurrencyUnit extends Unit {
    protected double value;
    protected Word name;

    public static Map<UnitKind, Double> exchangeRateMap = new HashMap<>();
    private static UnitKind DEFAULT_CURRENCY_UNIT = UnitKind.IR_RIAL;

    static {
        setExchangeRate(UnitKind.IR_RIAL, 1);
        setExchangeRate(UnitKind.IR_TOMAN, 10);
        setExchangeRate(UnitKind.US_DOLLAR, 32000);//todo
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setName(Word name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public Word getName() {
        return name;
    }

    @Override
    public CurrencyUnit convert(UnitKind kind) {
        if (!(Util.isInstance(kind.clazz, this.getClass()))) {
            throw new UnsupportedOperationException(kind + " cannot be converted to " + getKind());
        }
        CurrencyUnit result;
        try {
            result = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        double value;
        double rate = getExchangeRate(getKind(), kind);
        value = getValue() * rate;

        result.setValue(value);
        result.setAmount(getAmount());

        return result;
    }

    public static double getExchangeRate(UnitKind from, UnitKind to) {
        double fromRate = exchangeRateMap.get(from);
        double toRate = exchangeRateMap.get(to);
        return fromRate / toRate;
    }

    public static void setExchangeRate(UnitKind kind, double rate) {
        exchangeRateMap.put(kind, rate);
    }

    public static void changeDefaultUnit(UnitKind newUnit) {
        Map<UnitKind, Double> tempMap = new HashMap<>();
        double value;
        for (Map.Entry<UnitKind, Double> entry : exchangeRateMap.entrySet()) {
            value = getExchangeRate(DEFAULT_CURRENCY_UNIT, newUnit);
            tempMap.put(entry.getKey(), value);
        }
        exchangeRateMap = tempMap;
        DEFAULT_CURRENCY_UNIT = newUnit;

    }

    @Override
    public String toString() {
        return new DecimalFormat("#.###").format(value);
    }
    
    
}
