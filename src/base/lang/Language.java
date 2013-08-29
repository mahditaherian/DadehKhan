package base.lang;

/**
 * @author Mahdi
 */
public enum Language {
    FARSI("fa_ir"),
    ENGLISH("en_us"),
    FINGLISH("fi_ir"),
    DEFAULT(null),
    NUMBER("");

    public String fileName;

    Language(String name) {
        this.fileName = name;
    }

    public Language getValue(String name){
        for (Language lang : Language.values()){
            if (name.equalsIgnoreCase(lang.fileName)){
                return lang;
            }
        }
        return null;
    }
}
