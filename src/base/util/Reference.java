package base.util;

import org.jsoup.nodes.Document;

/**
 * @author Mahdi
 */
public class Reference {
    private Word name;
    private String url;
    private RelyRate rate;
    private Document document;
    private long docUpdateTime;

    public Reference(Word name, String url, RelyRate rate) {
        this.name = name;
        this.url = url;
        this.rate = rate;
        docUpdateTime = 0;
    }

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        this.name = name;
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

    public long getDocUpdateTime() {
        return docUpdateTime;
    }

    public void setDocUpdateTime(long docUpdateTime) {
        this.docUpdateTime = docUpdateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Reference)) {
            return false;
        }
        Reference reference = (Reference) obj;
        if (reference.getUrl().equalsIgnoreCase(url) && reference.getDocUpdateTime() == docUpdateTime) {
            if (reference.getDocument().text().equalsIgnoreCase(document.text())) {
                return true;
            }
        }
        return false;
    }
}
