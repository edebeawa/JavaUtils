package pers.edebe.util.classloader;

import java.util.HashMap;
import java.util.Map;

public class ZipClassLoader extends ClassLoader {
    protected final Map<String, byte[]> classes = new HashMap<>();

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        if (classes.containsKey(name)) {
            byte[] code = classes.get(name);
            return this.defineClass(name, code, 0, code.length);
        } else {
            throw new ClassNotFoundException();
        }
    }
}