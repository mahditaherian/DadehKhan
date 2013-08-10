package base.applicator.object;

import base.applicator.Parameter;
import base.applicator.Property;
import base.grabber.PropertyType;
import base.util.Page;
import base.util.Word;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi Taherian
 */
public class Car extends Stuff {
    private Map<Page, Currency> bazaarPrice;
    private Map<Page, Currency> price;


    public Car() {
        super();
        typeName = new Word("خودرو", "Car", "Khodro");
        referencePropertyMap = new HashMap<>();
        price = new HashMap<>();
        bazaarPrice = new HashMap<>();
    }

    protected void initVariables() {
        variables.add(new Parameter("bazaarPrice", PropertyType.IRRIAL));
        propertyNameMap.put("bazaarPrice", new Word("قیمت بازار", "bazaar price", "gheymate bazar"));

        variables.add(new Parameter("price", PropertyType.IRRIAL));
        propertyNameMap.put("price", new Word("قیمت نمایندگی", "price", "gheymate namayandegi"));
    }

    public Map<Page, Currency> getBazaarPrice() {
        return bazaarPrice;
    }

    public Map<Page, Currency> getPrice() {
        return price;
    }

    @Override
    public void addProperty(Page reference, Property property) {
        if (property.getName().equalsIgnoreCase("bazaarPrice")) {
            bazaarPrice.put(reference, (Currency) property.getValue());
        } else if (property.getName().equalsIgnoreCase("price")) {
            price.put(reference, (Currency) property.getValue());
        }
    }

}
