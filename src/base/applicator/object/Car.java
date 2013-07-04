package base.applicator.object;

import base.util.Word;

/**
 * @author Mahdi Taherian
 */
public class Car extends Stuff {
    private Currency bazaarPrice;
    private Currency price;


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

    public <T extends Currency> void setPrice(T price) {
        this.price = price;
    }
}
