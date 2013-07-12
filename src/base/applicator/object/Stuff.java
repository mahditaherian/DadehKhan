package base.applicator.object;

import base.applicator.ConvertRule;
import base.util.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Mahdi
 */
public abstract class Stuff {
    public Word name;
    public EntityID id;
    protected Word typeName;
    protected Map<Page, List<ConvertRule>> pageRulesMap;
    public List<Page> references;
    protected static List<Page> KIND_REFERENCES = new ArrayList<Page>();
    Map<Page, List<Property>> referencePropertyMap;


    protected Stuff() {
        pageRulesMap = new HashMap<Page, List<ConvertRule>>();
        references = new ArrayList<Page>();
    }

    public Collection<Page> getKindReferences() {
        return Collections.unmodifiableCollection(KIND_REFERENCES);
    }

    public void setReferences(List<Page> references) {
        this.references = references;
    }

    public void setProperties(Page reference, List<Property> properties) {
        referencePropertyMap.put(reference, properties);
        for (Property property : properties) {
            addProperty(reference, property);
        }
    }

    public abstract void addProperty(Page reference, Property property);

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        this.name = name;
    }

    public Word getTypeName() {
        return typeName;
    }

    public List<ConvertRule> getConvertRules(Page page) {
        return pageRulesMap.get(page);
    }


    public void addRule(Page page, ConvertRule rule) {
        if (page == null || rule == null) {
            System.out.println("page = " + page + " rule = " + rule);
            return;
        }
        if (!pageRulesMap.containsKey(page)) {
            pageRulesMap.put(page, new ArrayList<ConvertRule>());
        }
        pageRulesMap.get(page).add(rule);
        KIND_REFERENCES.add(page);
    }

    public void setTypeName(Word typeName) {
        this.typeName = typeName;
    }

    public EntityID getId() {
        return id;
    }

    public void setId(EntityID id) {
        this.id = id;
    }

    public List<Page> getReferences() {
        return references;
    }

    public List<Property> getProperties(Reference reference) {
        return referencePropertyMap.get(reference);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name=" + name +
                '}';
    }
}
