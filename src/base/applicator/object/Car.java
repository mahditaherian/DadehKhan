package base.applicator.object;

import base.util.Word;

/**
 * @author Mahdi Taherian
 */
public class Car extends Stuff {
    protected Currency bazaarPrice;
    protected Currency price;


    public Car() {
        super();
        typeName = new Word("خودرو", "Car", "Khodro");
    }

    public Currency getBazaarPrice() {
        return bazaarPrice;
    }

    public void setBazaarPrice(Currency bazaarPrice) {
        this.bazaarPrice = bazaarPrice;
    }

    public Currency getPrice() {
        return price;
    }

    public void setPrice(Currency price) {
        this.price = price;
    }
}
