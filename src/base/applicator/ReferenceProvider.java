package base.applicator;

import base.applicator.object.Stuff;
import base.util.MySqlConnector;
import base.util.Reference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mahdi
 */
public class ReferenceProvider extends Provider {
    private List<Reference> references;
    private List<Stuff> stuffs;

    public ReferenceProvider(MySqlConnector connector, List<Stuff> stuffs) {
        super(connector);
        this.stuffs = new ArrayList<Stuff>(stuffs);
    }

    public void reset() {
        references.clear();
    }

    @Override
    public void update() {
        reset();


        updateReferences();
    }

    @Override
    public void initialize() {
        reset();
//        for (Stuff stuff : stuffs){
//            stuff.addReference();
//        }

    }

    @Override
    public boolean needUpdate() {
        return true;
    }


    public void update(Reference reference) {
        try {
            Document doc = Jsoup.connect(reference.getUrl()).get();
            reference.setDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateReferences() {
        for (Reference reference : references) {
            update(reference);
        }
    }

}
