package base.classification;

import base.util.EntityID;

/**
 * @author Mahdi
 */
public class Icon {
    private EntityID id;
    private String url;

    public Icon() {
    }

    public EntityID getId() {
        return id;
    }

    public void setId(EntityID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
