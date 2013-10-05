package base.applicator.object.detail;

import base.applicator.Property;
import base.applicator.object.StandardEntity;
import base.grabber.PropertyType;

/**
 *
 * @author Mahdi
 */
public class DetailField extends StandardEntity {

    private FieldType fieldType;
    
    {
        addParameter(new Property("fieldType", fieldType, PropertyType.FIELD_TYPE));
    }

    public DetailField() {
        super();
        this.fieldType = FieldType.STRING;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
}
