package base.util;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi
 */
public class Reference {
    public Word name;
    public List<Page> pages;
    public Page main;
    private Document document;
    private long docUpdateTime;
    public EntityID id;

    public Reference(Word name, String url, RelyRate rate, EntityID id) {
        this.name = name;
        this.id = id;
        docUpdateTime = 0;
        pages = new ArrayList<Page>();
    }

    public Reference() {
        docUpdateTime = 0;
        pages = new ArrayList<Page>();
        main = null;
        id = new EntityID(0);
    }

    public Word getName() {
        return name;
    }

    public void setName(Word name) {
        this.name = name;
    }

    public Page getMain() {
        return main;
    }

    public void setMain(Page main) {
        this.main = main;
        addPage(main);
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

    public EntityID getId() {
        return id;
    }

    public void setId(EntityID id) {
        this.id = id;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void addPage(Page page) {
        if (pages.contains(page)) {
            pages.remove(page);
        }
        pages.add(page);
    }

    public void setPages(List<Page> pages) {
        for (Page page : pages) {
            if (page instanceof Page) {
                addPage(page);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        if (docUpdateTime != reference.docUpdateTime) return false;
        if (!id.equals(reference.id)) return false;
        if (main != null ? !main.equals(reference.main) : reference.main != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = main != null ? main.hashCode() : 0;
        result = 31 * result + (int) (docUpdateTime ^ (docUpdateTime >>> 32));
        result = 31 * result + id.hashCode();
        return result;
    }
}
