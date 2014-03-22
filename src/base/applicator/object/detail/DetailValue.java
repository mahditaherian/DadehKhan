package base.applicator.object.detail;

import base.lang.Language;
import base.util.Util;
import base.util.Word;

/**
 * @author Mahdi
 */
public abstract class DetailValue<T> {

    protected T value;
    protected boolean refer;

    public DetailValue(T val) {
        setValue(val);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    public static DetailValue newInstance(String value, FieldType type) {
        switch (type) {
            case INT:
            case DOUBLE:
            case FLOAT:
            case SHORT:
            case LONG: {
                return new NumericValue((Number) Util.convert(value, type));
            }
            case STRING: {
                String str = (String) Util.convert(value, type);
                Word word = new Word();
                word.set(Language.DEFAULT, str);
                return new StringValue(word);
            }
        }
        return null;

    }

    public boolean isRefer() {
        return refer;
    }

    public void setRefer(boolean refer) {
        this.refer = refer;
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
