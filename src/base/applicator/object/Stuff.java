package base.applicator.object;

import base.applicator.ConvertRule;
import base.applicator.RequestRule;
import base.util.Pair;
import base.util.Property;
import base.util.Reference;
import base.util.Word;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Mahdi
 */
public abstract class Stuff {
    //    protected List<Reference> references;
    protected Word name;
    protected Word typeName;
    protected List<Pair<Reference, RequestRule>> references;
    protected Map<Reference, List<ConvertRule>> referenceConvertRuleMap;
    protected static List<Reference> KIND_REFERENCES = new ArrayList<Reference>();


    protected Stuff() {
        references = new ArrayList<Pair<Reference, RequestRule>>();
        referenceConvertRuleMap = new HashMap<Reference, List<ConvertRule>>();
    }

    public void addReference(Reference reference, RequestRule rule) {
        references.add(new Pair<Reference, RequestRule>(reference, rule));
        KIND_REFERENCES.add(reference);
    }

    public Collection<Reference> getKindReferences() {
        return Collections.unmodifiableCollection(KIND_REFERENCES);
    }

    public List<Pair<Reference, RequestRule>> getReferences() {
        return references;
    }

    public void setProperties(List<Property> properties){
        Method method;
        for (Property property : properties) {
            try {
                method = getClass().getMethod("set" + property.getName(), property.getType());
                method.invoke(this, property.getValue());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        this.name = name;
    }

    public Word getTypeName() {
        return typeName;
    }

    public List<ConvertRule> getConvertRules(Reference reference){
        return referenceConvertRuleMap.get(reference);
    }

    public void setTypeName(Word typeName) {
        this.typeName = typeName;
    }
}
