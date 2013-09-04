package base.applicator.object;

import base.applicator.Parameter;
import base.applicator.Property;
import base.grabber.PropertyType;
import base.unit.currency.CurrencyUnit;
import base.util.Page;
import base.util.Word;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi Taherian
 */
public class Car extends Stuff {
    private Map<Page, CurrencyUnit> bazaarPrice;
    private Map<Page, CurrencyUnit> price;


    public Car() {
        super();
        typeName = new Word("خودرو", "Car", "Khodro");
        referencePropertyMap = new HashMap<>();
        price = new HashMap<>();
        bazaarPrice = new HashMap<>();
    }

    protected void initVariables() {
        variables.add(new Parameter("bazaarPrice", PropertyType.UNIT));
        propertyNameMap.put("bazaarPrice", new Word("قیمت بازار", "bazaar price", "gheymate bazar"));

        variables.add(new Parameter("price", PropertyType.UNIT));
        propertyNameMap.put("price", new Word("قیمت نمایندگی", "price", "gheymate namayandegi"));
    }

    public Map<Page, CurrencyUnit> getBazaarPrice() {
        return bazaarPrice;
    }

    public Map<Page, CurrencyUnit> getPrice() {
        return price;
    }

    @Override
    public void addProperty(Page reference, Property property) {
        if (property.getName().equalsIgnoreCase("bazaarPrice")) {
            bazaarPrice.put(reference, (CurrencyUnit) property.getValue());
        } else if (property.getName().equalsIgnoreCase("price")) {
            price.put(reference, (CurrencyUnit) property.getValue());
        }
    }

}
