package base;

/**
 * @author Mahdi
 */
public final class Config {
    public static final String MYSQL_USERNAME = "root";
    public static final String MYSQL_PASSWORD = "254136";
    public static final String MYSQL_HOST = "localhost";
    public static final String MYSQL_DB_NAME = "dadehkhan";

    public static base.lang.Language DEFAULT_LANGUAGE = base.lang.Language.FARSI;

    public static final String DATA_PATH = "data/";
    public static String DEFAULT_STUFF_PATH = DATA_PATH + "stuff/";
    public static String DEFAULT_LANGUAGE_PATH = DATA_PATH + "lang/";
    public static String DEFAULT_DETAIL_PATH = DATA_PATH + "stuff/";
    public final static String DEFAULT_REFERENCE_PATH = DATA_PATH + "reference/";
    public static String DEFAULT_REQUEST_RULE_PATH = DEFAULT_REFERENCE_PATH + "rule/";
    public static String DEFAULT_CONVERT_RULE_PATH = DEFAULT_REFERENCE_PATH + "rule/";
    public static String DEFAULT_UPDATE_RULE_PATH = DEFAULT_REFERENCE_PATH + "rule/";
    public static String DEFAULT_CATEGORY_PATH = DATA_PATH;
    public static Object DEFAULT_STUFF_TYPE_PATH = DATA_PATH;
}
