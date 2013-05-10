package base.util;

/**
 * @author Mahdi Taherian
 */
public class EntityID {
    private final int id;

    /**
     Construct a new EntityID object.
     @param id The numeric ID to use.
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
     Get the numeric ID for this object.
     @return The numeric ID.
     */
    public int getValue() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
