package base.applicator.object;

import base.util.Page;
import base.util.Property;
import base.util.Reference;
import base.util.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        referencePropertyMap = new HashMap<Page, List<Property>>();
        price = new HashMap<Page, Currency>();
        bazaarPrice = new HashMap<Page, Currency>();
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
