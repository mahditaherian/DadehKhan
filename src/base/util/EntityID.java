package base.util;

/**
 * @author Mahdi Taherian
 */
public class EntityID {
    private final int id;

    /**
     Construct a new EntityID object.
     @param id The numeric id to use.
     */
    public EntityID(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EntityID) {
            return this.id == ((EntityID)o).id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }

    /**
     Get the numeric id for this object.
     @return The numeric id.
     */
    public int getValue() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
