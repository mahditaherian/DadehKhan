package base.applicator.object;

import base.applicator.ConvertRule;
import base.applicator.Property;
import base.grabber.PropertyType;
import base.applicator.object.detail.Detail;
import base.grabber.KindType;
import base.util.Page;
import base.util.Word;

import java.util.*;

/**
 * @author Mahdi
 */
public abstract class Stuff extends StandardEntity {
//    public EntityID id;
    protected Word typeName;
    protected Map<Page, List<ConvertRule>> pageRulesMap;
    public List<Page> references;
    protected static List<Page> KIND_REFERENCES = new ArrayList<>();
    protected List<Detail> detail = new ArrayList<>();
    protected StuffType stuffType;

    protected Stuff() {
        super();
        pageRulesMap = new HashMap<>();
        references = new ArrayList<>();
        
        setProperty(new Property("stuff", this, PropertyType.STUFF));
        initVariables();
        setParameters();
    }
    
    @Override
    protected void setParameters(){
        super.setParameters();
        Property refProp = new Property("references", references, PropertyType.LIST);
        refProp.setKind(KindType.PAGE);
        addParameter(refProp);
        Property detProp = new Property("detail", detail, PropertyType.LIST);
        detProp.setKind(KindType.DETAIL);
        addParameter(detProp);
    }

    public Collection<Page> getKindReferences() {
        return Collections.unmodifiableCollection(KIND_REFERENCES);
    }

    protected void initVariables() {
    }
    
    public void addReference(Page page , List<ConvertRule> convertRules) {
        references.add(page);
        addRule(page, convertRules);
    }

    public void setReferences(List<Page> references) {
        this.references.clear();
        this.references.addAll(references);
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

//    public EntityID getId() {
//        return id;
//    }
//
//    public void setId(EntityID id) {
//        addParameter(new Property("id", id, PropertyType.ID));
//        this.id = id;
//    }

    public List<Page> getReferences() {
        return references;
    }

    public List<Property> getProperties(Page reference) {
        return referencePropertyMap.get(reference);
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail.clear();
        this.detail.addAll(detail);
    }

    public StuffType getStuffType() {
        return stuffType;
    }

    public void setStuffType(StuffType stuffType) {
        this.stuffType = stuffType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name=" + name +
                '}';
    }
}
