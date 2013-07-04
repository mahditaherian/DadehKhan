package base.util;

import base.applicator.RequestRule;
import org.jsoup.nodes.Document;

/**
 * @author Mahdi
 */
public class Page {
    public String url;
    public RelyRate rate;
    private Document document;
    public Reference parent;
    private EntityID id;
    private long docUpdateTime;
    private RequestRule requestRule;


    public Page() {
        docUpdateTime = 0;
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
}
