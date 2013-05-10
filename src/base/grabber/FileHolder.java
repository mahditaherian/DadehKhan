package base.grabber;

import base.Config;
import base.applicator.object.Stuff;
import base.util.Reference;
import base.util.Util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi Taherian
 */
public class FileHolder {
    private Map<Class, File> fileMap;

    public FileHolder() {
        fileMap = new HashMap<Class, File>();
    }

    public void hold(Class name, File file) {
        fileMap.put(name, file);
    }

    public void hold(Class className, String postfix) {
        StringBuilder path = new StringBuilder("");
        if (Util.isInstance(Stuff.class, className)) {
            path.append(Config.DEFAULT_STUFF_PATH);
        } else if (Util.isInstance(Reference.class, className)) {
            path.append(Config.DEFAULT_REFERENCE_PATH);
        }
        path.append(className.getSimpleName());
        path.append(".");
        path.append(postfix);

        hold(className, new File(path.toString()));
    }

    public void hold(String name, File file) {
        Class clazz = null;
        for (Class c : fileMap.keySet()) {
            if (c.getName().toLowerCase().equals(name.toLowerCase())) {
                clazz = c;
                break;
            }
        }
        if (clazz != null) {
            hold(clazz, file);
        }
    }

    public File getFile(Class name) {
        return fileMap.get(name);
    }

    public boolean containsFile(Class name) {
        return fileMap.containsKey(name);
    }
}
