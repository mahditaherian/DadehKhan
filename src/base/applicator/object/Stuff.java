package base.applicator.object;

import base.applicator.ConvertRule;
import base.applicator.Property;
import base.grabber.PropertyType;
import base.util.EntityID;
import base.util.Page;
import base.util.Word;

import java.util.*;

/**
 * @author Mahdi
 */
public abstract class Stuff extends StandardEntity {
    public EntityID id;
    protected Word typeName;
    protected Map<Page, List<ConvertRule>> pageRulesMap;
    public List<Page> references;
    protected static List<Page> KIND_REFERENCES = new ArrayList<Page>();


    {
        addParameter(new Property("name", name, PropertyType.WORD));
        addParameter(new Property("id", id, PropertyType.ID));
        addParameter(new Property("references", references, PropertyType.LIST));
        addParameter(new Property("category", getCategory(), PropertyType.CATEGORY));
    }

    protected Stuff() {
        pageRulesMap = new HashMap<Page, List<ConvertRule>>();
        references = new ArrayList<Page>();
        setProperty(new Property("stuff", this, PropertyType.STUFF));
        initVariables();
    }

    public Collection<Page> getKindReferences() {
        return Collections.unmodifiableCollection(KIND_REFERENCES);
    }

    protected void initVariables() {
    }

    public void setReferences(List<Page> references) {
        addParameter(new Property("references", references, PropertyType.LIST));
        this.references = references;
    }

    public void setProperties(Page reference, List<Property> properties) {
        referencePropertyMap.put(reference, properties);
        for (Property property : properties) {
            addProperty(reference, property);
        }
    }

    public abstract void addProperty(Page reference, Property property);

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

    public void addRule(Page page, Collection<ConvertRule> rules) {
        if (page == null || rules == null) {
            System.out.println("page = " + page + " rules = null");
            return;
        }
        for (ConvertRule rule : rules) {
            addRule(page, rule);
        }
    }

    public void setTypeName(Word typeName) {
        this.typeName = typeName;
    }

    public EntityID getId() {
        return id;
    }

    public void setId(EntityID id) {
        addParameter(new Property("id", id, PropertyType.ID));
        this.id = id;
    }

    public List<Page> getReferences() {
        return references;
    }

    public List<Property> getProperties(Page reference) {
        return referencePropertyMap.get(reference);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name=" + name +
                '}';
    }
}
