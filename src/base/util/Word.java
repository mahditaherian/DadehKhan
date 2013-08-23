package base.util;

import base.applicator.Parameter;
import base.applicator.object.StandardEntity;
import base.grabber.PropertyType;
import base.lang.Language;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public class Word extends StandardEntity {
    private Map<Language, String> valueMap;

    {
        addParameter(new Parameter("value", PropertyType.STRING));
    }

    public Word() {
        super();
        valueMap = new HashMap<>();
    }

    public Word(EntityID id) {
        this();
        setId(id);
    }

    public String get(Language language) {
        return valueMap.get(language);
    }

    public void set(Language language, String value) {
        valueMap.put(language, value);
    }

    public void setValue(String value) {
        set(base.Config.DEFAULT_LANGUAGE, value);
    }

    public Word(String farsi, String english, String farsiInEnglish) {
//        this.farsi = farsi;
//        this.english = english;
//        this.farsiInEnglish = farsiInEnglish;
    }

    @Override
    public void setName(Word name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Word{" +
                "farsi='" + get(Language.FARSI) + '\'' +
//                ", english='" + english + '\'' +
//                ", farsiInEnglish='" + farsiInEnglish + '\'' +
                '}';
    }
}
