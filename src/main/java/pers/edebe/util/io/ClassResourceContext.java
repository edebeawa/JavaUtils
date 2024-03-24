package pers.edebe.util.io;

import pers.edebe.util.exception.ResourceNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.function.Function;

public class ClassResourceContext {
    private final Function<String, URL> getResourceFunction;
    private final Function<String, InputStream> getResourceAsStreamFunction;

    public ClassResourceContext(Class<?> clazz) {
        getResourceFunction = clazz::getResource;
        getResourceAsStreamFunction = clazz::getResourceAsStream;
    }

    public ClassResourceContext(ClassLoader loader) {
        getResourceFunction = loader::getResource;
        getResourceAsStreamFunction = loader::getResourceAsStream;
    }

    public URL getResource(String name) throws ResourceNotFoundException {
        URL url = getResourceFunction.apply(name);
        if (url != null)
            return url;
        else
            throw new ResourceNotFoundException(name);
    }

    public InputStream getResourceAsStream(String name) throws ResourceNotFoundException {
        InputStream stream = getResourceAsStreamFunction.apply(name);
        if (stream != null)
            return stream;
        else
            throw new ResourceNotFoundException(name);
    }

    public String getResourceAsString(String name) throws IOException {
        return StreamUtils.toString(getResourceAsStream(name));
    }

    public String getResourceAsString(String name, String charsetName) throws IOException {
        return StreamUtils.toString(getResourceAsStream(name), charsetName);
    }

    public String getResourceAsString(String name, Charset charset) throws IOException {
        return StreamUtils.toString(getResourceAsStream(name), charset);
    }
}