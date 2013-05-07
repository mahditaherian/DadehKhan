package base.applicator;

/**
 * @author Mahdi Taherian
 */
public class ConvertRule {
    private String explosion = "TD";
    private int index = 0;
    private Class propertyClass;
    private String propertyName;

    public ConvertRule(String explosionTag, int index, Class propertyClass, String propertyName) {
        this.explosion = explosionTag;
        this.index = index;
        this.propertyClass = propertyClass;
        this.propertyName = propertyName;
    }

    public String getExplosion() {
        return explosion;
    }

    public void setExplosion(String explosion) {
        this.explosion = explosion;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Class getPropertyClass() {
        return propertyClass;
    }

    public void setPropertyClass(Class propertyClass) {
        this.propertyClass = propertyClass;
    }

    /**
     * <b>Note</b> propertyName Must Start with uppercase character
     */
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    //    private List<Pair<RequestRule, Property>> requestRules;
//    private HtmlRequestHandler requestHandler;

//    public ConvertRule(List<Pair<RequestRule, Property>> requestRules) {
//        this.requestRules = requestRules;
//        this.requestHandler = new HtmlRequestHandler();
//    }

//    public List<Property> convert(Element element) {
//        List<Property> properties = new ArrayList<Property>();
//        for (Pair<RequestRule, Property> rule : requestRules) {
//            Element elm = requestHandler.getElementByRule(element.ownerDocument(), rule.getKey());
//            String propertyName = rule.getValue().setName();
//            Class type = rule.getValue().getType();
//            if (type.equals(Integer.class)) {
//                properties.add(new Property(propertyName, Integer.parseInt(elm.text()), type));
//            } else {
//                properties.add(new Property(propertyName, elm.text(), type));
//            }
//        }
//
//
//        return properties;
//    }
}
