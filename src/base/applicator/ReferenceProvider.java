package base.applicator;

import base.applicator.object.Stuff;
import base.grabber.XmlGrabber;
import base.util.*;

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
    private UpdateManager updateManager;

    public ReferenceProvider(XmlGrabber xmlGrabber, MySqlConnector connector, List<Stuff> stuffs) {
        super(connector);
        this.xmlGrabber = xmlGrabber;
        this.stuffs = new ArrayList<Stuff>(stuffs);
        initialize();
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
        updateManager.updateReferences(references);
    }

    @Override
    public void initialize() {
        referenceIDMap = new HashMap<EntityID, Reference>();
        referencePageIDMap = new HashMap<EntityID, Page>();
        convertRuleIDMap = new HashMap<EntityID, ConvertRule>();
        requestRuleIDMap = new HashMap<EntityID, RequestRule>();
        references = new ArrayList<Reference>();
        updateManager = new UpdateManager();
//        reset();
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

//    public void update(Reference reference) {
//        Document doc;
//        for (Page page : reference.getPages()) {
//            try {
//                if (page.getHost().equals(HostType.REMOTE)) {
//                    doc = Jsoup.connect(page.getUrl()).get();
//                } else if (page.getHost().equals(HostType.LOCAL)) {
//                    File file = new File(page.getUrl());
//                    doc = Jsoup.parse(file, "utf-8");
//                } else {
//                    doc = null;
//                }
//                page.setDocument(doc);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    private void updateReferences() {
//        for (Reference reference : references) {
//            if (reference.needUpdate())
//                reference.update();
//        }
//    }

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

    public Collection<ConvertRule> getConvertRules(){
        return convertRuleIDMap.values();
    }

    public Page getPageByID(EntityID id) {
        Page page = referencePageIDMap.get(id);
        if (page == null) {
            System.out.println("page with id '" + id.getValue() + "' does not exist");
        }
        return page;
    }

    public Collection<Page> getPages() {
        return referencePageIDMap.values();
    }
}
