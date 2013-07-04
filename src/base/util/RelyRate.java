package base.util;

/**
 * @author Mahdi
 */
public enum RelyRate {
    FULLY(0),
    ALMOST(1),
    HIGH(2),
    ORDINARY(5),
    LOW(8),
    LITTLE(9),
    UNRELIABLE(10);

    int val;
    RelyRate(int i) {
        val = i;
    }

    public static RelyRate valueOf(int rate){
        for (RelyRate relyRate : RelyRate.values()){
            if(relyRate.val == rate){
                return relyRate;
            }
        }
        throw new IllegalArgumentException();
    }
}
