/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.applicator.object;

import base.classification.Category;
import base.util.Word;
import java.util.List;

/**
 *
 * @author mrl
 */
public class StuffType {

    private Word name;
    private List<Category> details;
    private List<Stuff> stuffs;
    private Class<? extends Stuff> clazz;

    public StuffType() {
    }

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        this.name = name;
    }

    public List<Category> getDetails() {
        return details;
    }

    public List<Stuff> getStuffs() {
        return stuffs;
    }

    public void setClazz(Class<? extends Stuff> clazz) {
        this.clazz = clazz;
    }
    
    public void addDetail(){
        
    }
    
}
