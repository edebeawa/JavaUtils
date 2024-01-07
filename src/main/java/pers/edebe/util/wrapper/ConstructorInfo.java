package pers.edebe.util.wrapper;

import lombok.Getter;

import java.lang.reflect.Constructor;

@Getter
public class ConstructorInfo extends AccessibleObjectInfo<Constructor<?>> {
    private final Class<?> declaringClass;
    private final int slot;
    private final Class<?>[] parameterTypes;
    private final Class<?>[] checkedExceptions;
    private final int modifiers;
    private final transient String signature;
    private final byte[] annotations;
    private final byte[] parameterAnnotations;

    public ConstructorInfo(Constructor<?> constructor) throws ReflectiveOperationException {
        super(constructor);
        this.declaringClass = field("clazz", Class.class);
        this.parameterTypes = field("parameterTypes", Class[].class);
        this.checkedExceptions = field("exceptionTypes", Class[].class);
        this.modifiers = field("modifiers", int.class);
        this.slot = field("slot", int.class);
        this.signature = field("signature", String.class);
        this.annotations = field("annotations", byte[].class);
        this.parameterAnnotations = field("parameterAnnotations", byte[].class);
    }

    public ConstructorInfo(Class<?> declaringClass, Class<?>[] parameterTypes, Class<?>[] checkedExceptions, int modifiers, int slot, String signature, byte[] annotations, byte[] parameterAnnotations) {
        super(Constructor.class);
        this.declaringClass = declaringClass;
        this.parameterTypes = parameterTypes;
        this.checkedExceptions = checkedExceptions;
        this.modifiers = modifiers;
        this.slot = slot;
        this.signature = signature;
        this.annotations = annotations;
        this.parameterAnnotations = parameterAnnotations;
    }

    @Override
    public Class<?>[] getArgTypes() {
        return new Class[]{Class.class, Class[].class, Class[].class, int.class, int.class, String.class, byte[].class, byte[].class};
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{declaringClass, parameterTypes, checkedExceptions, modifiers, slot, signature, annotations, parameterAnnotations};
    }
}