package base.panel;

import base.Config;
import base.applicator.object.Car;
import base.unit.currency.CurrencyUnit;
import base.applicator.object.detail.Detail;
import base.util.Page;
import javax.swing.table.DefaultTableModel;

/**
 * @author Mahdi
 */
public class CarViewer extends ItemPanel {

    void show(Car car) {
        DefaultTableModel commonModel = (DefaultTableModel) commonDetailsTable.getModel();
        commonModel.getDataVector().clear();
        for (Detail detail : car.getDetail()) {
            commonModel.addRow(
                    new Object[]{
                        detail.getName().get(Config.DEFAULT_LANGUAGE),
                        detail.getValue().toString()});
        }

        commonModel = (DefaultTableModel) commonDetailsTable1.getModel();
        commonModel.getDataVector().clear();

        for (Page page : car.getReferences()) {
            CurrencyUnit bazaar = car.getBazaarPrice().get(page);
            CurrencyUnit price = car.getPrice().get(page);
            commonModel.addRow(new Object[]{
                price,
                bazaar,
                page.getParent().getName()/*.get(Config.DEFAULT_LANGUAGE)*/,
                page.getRate()
            });
        }
    }
}
