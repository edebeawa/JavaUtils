package pers.edebe.util.io;

import pers.edebe.util.reflect.ReflectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NoRestrictObjectOutputStream extends ObjectOutputStream {
    private static final Field OBJECT_OUTPUT_STREAM_ENABLE_OVERRIDE_FIELD;
    private static final Method OBJECT_OUTPUT_STREAM_WRITE_OBJECT_0_METHOD;
    private static final Method OBJECT_OUTPUT_STREAM_WRITE_FATAL_EXCEPTION_METHOD;
    private static final Field OBJECT_OUTPUT_STREAM_DEPTH_FIELD;
    private static final Field OBJECT_OUTPUT_STREAM_BOUT_FIELD;
    private final Method blockDataOutputStreamSetBlockDataModeMethod;
    private static final Method OBJECT_STREAM_CLASS_HAS_WRITE_REPLACE_METHOD_METHOD;
    private static final Method OBJECT_STREAM_CLASS_INVOKE_WRITE_REPLACE_METHOD;
    private static final Field OBJECT_OUTPUT_STREAM_ENABLE_REPLACE_FIELD;
    private static final Field OBJECT_OUTPUT_STREAM_SUBS_FIELD;
    private final Method replaceTableAssignMethod;
    private static final Method OBJECT_OUTPUT_STREAM_WRITE_ORDINARY_OBJECT_METHOD;
    private static final Field OBJECT_STREAM_CLASS_SERIALIZABLE_FIELD;

    static {
        try {
            OBJECT_OUTPUT_STREAM_ENABLE_OVERRIDE_FIELD = ReflectionUtils.getAccessibleDeclaredField(ObjectOutputStream.class, "enableOverride");
            OBJECT_OUTPUT_STREAM_WRITE_OBJECT_0_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(ObjectOutputStream.class, "writeObject0", Object.class, boolean.class);
            OBJECT_OUTPUT_STREAM_WRITE_FATAL_EXCEPTION_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(ObjectOutputStream.class, "writeFatalException", IOException.class);
            OBJECT_OUTPUT_STREAM_DEPTH_FIELD = ReflectionUtils.getAccessibleDeclaredField(ObjectOutputStream.class, "depth");
            OBJECT_OUTPUT_STREAM_BOUT_FIELD = ReflectionUtils.getAccessibleDeclaredField(ObjectOutputStream.class, "bout");
            OBJECT_STREAM_CLASS_HAS_WRITE_REPLACE_METHOD_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(ObjectStreamClass.class, "hasWriteReplaceMethod");
            OBJECT_STREAM_CLASS_INVOKE_WRITE_REPLACE_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(ObjectStreamClass.class, "invokeWriteReplace", Object.class);
            OBJECT_OUTPUT_STREAM_ENABLE_REPLACE_FIELD = ReflectionUtils.getAccessibleDeclaredField(ObjectOutputStream.class, "enableReplace");
            OBJECT_OUTPUT_STREAM_SUBS_FIELD = ReflectionUtils.getAccessibleDeclaredField(ObjectOutputStream.class, "subs");
            OBJECT_OUTPUT_STREAM_WRITE_ORDINARY_OBJECT_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(ObjectOutputStream.class, "writeOrdinaryObject", Object.class, ObjectStreamClass.class, boolean.class);
            OBJECT_STREAM_CLASS_SERIALIZABLE_FIELD = ReflectionUtils.getAccessibleDeclaredField(ObjectStreamClass.class, "serializable");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public NoRestrictObjectOutputStream(OutputStream out) throws IOException, ReflectiveOperationException {
        super(out);
        blockDataOutputStreamSetBlockDataModeMethod = ReflectionUtils.getAccessibleDeclaredMethod(OBJECT_OUTPUT_STREAM_BOUT_FIELD.get(this).getClass(), "setBlockDataMode", boolean.class);
        replaceTableAssignMethod = ReflectionUtils.getAccessibleDeclaredMethod(OBJECT_OUTPUT_STREAM_SUBS_FIELD.get(this).getClass(), "assign", Object.class, Object.class);
    }

    private void setDepth(int depth) throws IllegalAccessException {
        OBJECT_OUTPUT_STREAM_DEPTH_FIELD.set(this, depth);
    }

    private int getDepth() throws IllegalAccessException {
        return (int) OBJECT_OUTPUT_STREAM_DEPTH_FIELD.get(this);
    }

    public final void writeObjectNoRestrict(Object obj) throws IOException, ReflectiveOperationException {
        if ((boolean) OBJECT_OUTPUT_STREAM_ENABLE_OVERRIDE_FIELD.get(this)) {
            writeObjectOverride(obj);
            return;
        }
        try {
            OBJECT_OUTPUT_STREAM_WRITE_OBJECT_0_METHOD.invoke(this, obj, false);
        } catch (IllegalAccessException | InvocationTargetException e) {
            try {
                writeObject0(obj);
            } catch (IOException ex) {
                if (getDepth() == 0) {
                    OBJECT_OUTPUT_STREAM_WRITE_FATAL_EXCEPTION_METHOD.invoke(this, ex);
                }
                throw ex;
            }
        }
    }

    private boolean setBlockDataMode(boolean mode) throws ReflectiveOperationException {
        return (boolean) blockDataOutputStreamSetBlockDataModeMethod.invoke(OBJECT_OUTPUT_STREAM_BOUT_FIELD.get(this), mode);
    }

    private void writeObject0(Object obj) throws IOException, ReflectiveOperationException {
        boolean oldMode = setBlockDataMode(false);
        setDepth(getDepth() + 1);
        try {
            Object orig = obj;
            Class<?> cl = obj.getClass();
            ObjectStreamClass desc;
            for (;;) {
                Class<?> repCl;
                ReflectionUtils.setClassType(cl, Serializable.class);
                desc = ObjectStreamClass.lookupAny(cl);
                if (!(boolean) OBJECT_STREAM_CLASS_HAS_WRITE_REPLACE_METHOD_METHOD.invoke(desc) || (obj = OBJECT_STREAM_CLASS_INVOKE_WRITE_REPLACE_METHOD.invoke(desc, obj)) == null || (repCl = obj.getClass()) == cl) {
                    break;
                }
                cl = repCl;
            }
            if ((boolean) OBJECT_OUTPUT_STREAM_ENABLE_REPLACE_FIELD.get(this)) {
                Object rep = replaceObject(obj);
                if (rep != obj && rep != null) {
                    cl = rep.getClass();
                    ReflectionUtils.setClassType(cl, Serializable.class);
                    desc = ObjectStreamClass.lookupAny(cl);
                }
                obj = rep;
            }
            if (obj != orig)
                replaceTableAssignMethod.invoke(OBJECT_OUTPUT_STREAM_SUBS_FIELD.get(this), orig, obj);
            OBJECT_STREAM_CLASS_SERIALIZABLE_FIELD.set(desc, true);
            OBJECT_OUTPUT_STREAM_WRITE_ORDINARY_OBJECT_METHOD.invoke(this, obj, desc, false);
        } finally {
            setDepth(getDepth() - 1);
            setBlockDataMode(oldMode);
        }
    }
}