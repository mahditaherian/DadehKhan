package base.unit;

/**
 * @author Mahdi
 */
public abstract class Unit {
    private UnitKind kind;
    private Amount amount;

    public UnitKind getKind() {
        return kind;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setKind(UnitKind kind) {
        this.kind = kind;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public abstract Unit convert(UnitKind kind);
}
