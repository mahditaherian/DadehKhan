package base.util;

import base.applicator.Property;
import base.applicator.RequestRule;
import base.applicator.object.StandardEntity;
import base.grabber.PropertyType;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Mahdi
 */
public class Page extends StandardEntity {
    public String url;
    public RelyRate rate;
    public Reference parent;
    public HostType host;

    private Document document;
    private Element dataElement;
    private EntityID id;
    private long docUpdateTime;
    private RequestRule requestRule;
    private boolean isDataChanged;

    {
        addParameter(new Property("url", url, PropertyType.STRING));
        addParameter(new Property("rate", rate, PropertyType.RELY));
        addParameter(new Property("parent", parent, PropertyType.REFERENCE));
        addParameter(new Property("host", host, PropertyType.HOST));
    }

    public Page() {
        docUpdateTime = 0;
        isDataChanged = false;
        setProperty(new Property("page", this, PropertyType.PAGE));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RelyRate getRate() {
        return rate;
    }

    public void setRate(RelyRate rate) {
        this.rate = rate;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
        isDataChanged = true;
        setDocUpdateTime(System.currentTimeMillis());
    }

    public void setDocUpdateTime(long docUpdateTime) {
        this.docUpdateTime = docUpdateTime;
    }

    public long getDocUpdateTime() {
        return docUpdateTime;
    }

    public EntityID getId() {
        return id;
    }

    public void setId(EntityID id) {
        this.id = id;
    }

    public Reference getParent() {
        return parent;
    }

    public void setParent(Reference parent) {
        this.parent = parent;
    }

    public void setRequestRule(RequestRule requestRule) {
        this.requestRule = requestRule;
    }

    public RequestRule getRequestRule() {
        return requestRule;
    }

    public void reset() {
        document = null;
        docUpdateTime = 0;
    }

    public HostType getHost() {
        return host;
    }

    public void setHost(HostType host) {
        this.host = host;
    }

    public Element getDataElement() {
        return dataElement;
    }

    public void setDataElement(Element dataElement) {
        this.dataElement = dataElement;
        isDataChanged = false;
    }

    public boolean isDataChanged() {
        return isDataChanged;
    }
}
