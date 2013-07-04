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

    public void setProperties(List<Property> properties) {
        Method method;
        for (Property property : properties) {
            try {
                method = Util.getSetter(property.getName(), property.getType().clazz, this.getClass());
                method.invoke(this, property.getValue());
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
}
