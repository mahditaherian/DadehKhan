/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.classification;

import base.applicator.ConvertRule;
import base.applicator.RequestRule;
import base.applicator.object.Car;
import base.applicator.object.Entity;
import base.applicator.object.StandardEntity;

/**
 *
 * @author Mahdi
 */
public enum EntityType {

    /**
     *
     */
    CAR("car", Car.class),
    CONVERT_RULE("convertRule", ConvertRule.class),
    REQUEST_RULE("requestRule", RequestRule.class);
    public String name;
    public Class<? extends StandardEntity> clazz;

    /**
     *
     * @param type
     */
    EntityType(String name, Class<? extends StandardEntity> type) {
        this.clazz = type;
        this.name = name;
    }
    
    public static EntityType get(String name){
        for(EntityType type : EntityType.values()){
            if(type.name.equalsIgnoreCase(name)){
                return type;
            }
        }
        return null;
    }
    
    public static EntityType get(Class<? extends StandardEntity> clazz ){
        return get(clazz.getSimpleName());
    }
}
