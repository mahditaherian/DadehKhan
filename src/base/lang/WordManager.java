package base.lang;

import base.util.EntityID;
import base.util.Word;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi
 */
public class WordManager {
    private Map<EntityID, Word> idWordMap;
    private Language language;

    public WordManager(Language language) {
        idWordMap = new HashMap<>();
        setLanguage(language);
    }

    public void addWord(Word word) {
        idWordMap.put(word.getId(), word);
    }

    public Word getWord(EntityID id) {
        return idWordMap.get(id);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
