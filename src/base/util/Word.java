package base.util;

/**
 * @author Mahdi
 */
public class Word {
    private String farsi;
    private String english;
    private String farsiInEnglish;

    public Word(String farsi) {
        this.farsi = farsi;
    }

    public Word(String farsi, String english) {
        this.farsi = farsi;
        this.english = english;
    }

    public Word(String farsi, String english, String farsiInEnglish) {
        this.farsi = farsi;
        this.english = english;
        this.farsiInEnglish = farsiInEnglish;
    }

    public String getFarsi() {
        return farsi;
    }

    public void setFarsi(String farsi) {
        this.farsi = farsi;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getFarsiInEnglish() {
        return farsiInEnglish;
    }

    public void setFarsiInEnglish(String farsiInEnglish) {
        this.farsiInEnglish = farsiInEnglish;
    }
}
