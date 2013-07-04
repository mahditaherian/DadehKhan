package base.applicator;

import base.applicator.object.Stuff;
import base.grabber.XmlGrabber;
import base.util.EntityID;
import base.util.MySqlConnector;
import base.util.Page;
import base.util.Reference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Mahdi
 */
public class ReferenceProvider extends Provider {
    private List<Reference> references;
    private List<Stuff> stuffs;
    private Map<EntityID, Reference> referenceIDMap;
    private Map<EntityID, Page> referencePageIDMap;
    private Map<EntityID, ConvertRule> convertRuleIDMap;
    private Map<EntityID, RequestRule> requestRuleIDMap;
    private XmlGrabber xmlGrabber;

    public ReferenceProvider(XmlGrabber xmlGrabber, MySqlConnector connector, List<Stuff> stuffs) {
        super(connector);
        this.xmlGrabber = xmlGrabber;
        this.stuffs = new ArrayList<Stuff>(stuffs);
        referenceIDMap = new HashMap<EntityID, Reference>();
        referencePageIDMap = new HashMap<EntityID, Page>();
        convertRuleIDMap = new HashMap<EntityID, ConvertRule>();
        requestRuleIDMap = new HashMap<EntityID, RequestRule>();
        references = new ArrayList<Reference>();
    }

    public void reset() {
        for (Reference reference : references) {
            for (Page page : reference.getPages()) {
                page.reset();
            }
        }
    }

    @Override
    public void update() {
        reset();


        updateReferences();
    }

    @Override
    public void initialize() {
        reset();
//        for (Stuff stuff : stuffs){
//            stuff.addReference();
//        }

    }

    @Override
    public boolean needUpdate() {
        return true;
    }

    public void addReference(Reference reference) {
        referenceIDMap.put(reference.getId(), reference);
        for (Page page : reference.getPages()) {
            referencePageIDMap.put(page.getId(), page);
        }
        references.add(reference);
    }

    public void update(Reference reference) {
        for (Page page : reference.getPages()) {
            try {
//                Document doc = Jsoup.connect(page.getUrl()).get();
                File file = new File(page.getUrl());
                Document doc = Jsoup.parse(file, "utf-8");
                page.setDocument(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateReferences() {
        for (Reference reference : references) {
            update(reference);
        }
    }

    public ConvertRule getConvertRuleByID(EntityID id) {
        return convertRuleIDMap.get(id);
    }

    public void addConvertRule(EntityID id, ConvertRule convertRule) {
        this.convertRuleIDMap.put(id, convertRule);
    }

    public RequestRule getRequestRuleByID(EntityID id) {
        return requestRuleIDMap.get(id);
    }

    public void addRequestRule(EntityID id, RequestRule requestRule) {
        this.requestRuleIDMap.put(id, requestRule);
    }

    public Reference getReferenceByID(EntityID id) {
        Reference reference = null;
        if (referenceIDMap.containsKey(id)) {
            reference = referenceIDMap.get(id);
        } else if (referencePageIDMap.containsKey(id)) {
            reference = referencePageIDMap.get(id).getParent();
        }
        if (reference == null) {
            throw new NoSuchElementException();
        }
        return reference;
    }

    public Page getPageByID(EntityID id) {
        Page page = referencePageIDMap.get(id);
        if(page==null){
            System.out.println("page with id '"+id.getValue()+"' does not exist");
        }
        return page;
    }
}
