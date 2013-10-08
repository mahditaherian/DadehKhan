package base.grabber;

import base.Config;
import base.applicator.ConvertRule;
import base.applicator.RequestRule;
import base.applicator.object.Stuff;
import base.applicator.object.StuffType;
import base.classification.Category;
import base.applicator.object.detail.DetailField;
import base.util.Reference;
import base.util.UpdateRule;
import base.util.Util;
import base.util.Word;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi Taherian
 */
public class FileHolder {
    private Map<String, File> fileMap;

    public FileHolder() {
        fileMap = new HashMap<>();
    }

    public void hold(String name, File file) {
        fileMap.put(name, file);
    }

    public void hold(Class className,String fileName, String postfix) {
        StringBuilder path = new StringBuilder("");
        if (Util.isInstance(Stuff.class, className)) {
            path.append(Config.DEFAULT_STUFF_PATH);
        } else if (Util.isInstance(Reference.class, className)) {
            path.append(Config.DEFAULT_REFERENCE_PATH);
        } else if (Util.isInstance(RequestRule.class, className)) {
            path.append(Config.DEFAULT_REQUEST_RULE_PATH);
        } else if (Util.isInstance(ConvertRule.class, className)) {
            path.append(Config.DEFAULT_CONVERT_RULE_PATH);
        } else if (Util.isInstance(className, Category.class)) {
            path.append(Config.DEFAULT_CATEGORY_PATH);
        } else if (Util.isInstance(className, DetailField.class)) {
            path.append(Config.DEFAULT_DETAIL_PATH);
        } else if (Util.isInstance(className, Word.class)) {
            path.append(Config.DEFAULT_LANGUAGE_PATH);
        } else if (Util.isInstance(className, UpdateRule.class)) {
            path.append(Config.DEFAULT_UPDATE_RULE_PATH);
        } else if (Util.isInstance(className, StuffType.class)) {
            path.append(Config.DEFAULT_STUFF_TYPE_PATH);
        }
        path.append(fileName);
        path.append(".");
        path.append(postfix);

        hold(fileName, new File(path.toString()));
    }

//    public void hold(String name, File file) {
//        Class clazz = null;
//        for (String c : fileMap.keySet()) {
//            if (c.equalsIgnoreCase(name)) {
//                clazz = c;
//                break;
//            }
//        }
//        if (clazz != null) {
//            hold(clazz, file);
//        }
//    }

    public File getFile(String name) {
        return fileMap.get(name);
    }

    public boolean containsFile(String name) {
        return fileMap.containsKey(name);
    }
}
