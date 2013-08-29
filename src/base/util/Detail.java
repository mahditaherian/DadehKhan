package base.util;

/**
 * @author Mahdi
 */
public class Detail {

    boolean isNumeric = false;
    boolean isChart = false;
    private Word name, value;


//    public Word name;


    public Detail(Word name, Word value) {
        this(name, value, false, false);
    }

    public Detail(Word name, Word value, boolean numeric, boolean chart) {
        isNumeric = numeric;
        isChart = chart;
        this.name = name;
        this.value = value;
    }

    public boolean isNumeric() {
        return isNumeric;
    }

    public void setNumeric(boolean numeric) {
        isNumeric = numeric;
    }

    public boolean isChart() {
        return isChart;
    }

    public void setChart(boolean chart) {
        isChart = chart;
    }

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        this.name = name;
    }

    public Word getValue() {
        return value;
    }

    public void setValue(Word value) {
        this.value = value;
    }
}
