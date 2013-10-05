package base.applicator.object.detail;

import base.util.Word;

/**
 * @author Mahdi
 */
public class Detail {

    private DetailValue value;
    private DetailField field;

    public Detail(DetailField field, DetailValue value) {
        this.value = value;
        this.field = field;
    }

    public DetailValue getValue() {
        return value;
    }

    public void setValue(DetailValue value) {
        this.value = value;
    }

    public DetailField getField() {
        return field;
    }

    public void setField(DetailField field) {
        this.field = field;
    }
    
    public Word getName(){
        return field.getName();
    }
    
    @Override
    public String toString(){
        return getName().toString();
    }
}
