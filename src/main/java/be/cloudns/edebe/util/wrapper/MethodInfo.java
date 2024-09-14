package be.cloudns.edebe.util.wrapper;

import lombok.Getter;

import java.lang.reflect.Method;

@Getter
public class MethodInfo extends AccessibleObjectInfo<Method> {
    private final Class<?> declaringClass;
    private final int slot;
    private final String name;
    private final Class<?> returnType;
    private final Class<?>[] parameterTypes;
    private final Class<?>[] checkedExceptions;
    private final int modifiers;
    private final transient String signature;
    private final byte[] annotations;
    private final byte[] parameterAnnotations;
    private final byte[] annotationDefault;

    public MethodInfo(Method method) throws ReflectiveOperationException {
        super(method);
        this.declaringClass = field("clazz", Class.class);
        this.name = field("name", String.class);
        this.parameterTypes = field("parameterTypes", Class[].class);
        this.returnType = field("returnType", Class.class);
        this.checkedExceptions = field("exceptionTypes", Class[].class);
        this.modifiers = field("modifiers", int.class);
        this.slot = field("slot", int.class);
        this.signature = field("signature", String.class);
        this.annotations = field("annotations", byte[].class);
        this.parameterAnnotations = field("parameterAnnotations", byte[].class);
        this.annotationDefault = field("annotationDefault", byte[].class);
    }

    public MethodInfo(Class<?> declaringClass, String name, Class<?>[] parameterTypes, Class<?> returnType, Class<?>[] checkedExceptions, int modifiers, int slot, String signature, byte[] annotations, byte[] parameterAnnotations, byte[] annotationDefault) {
        super(Method.class);
        this.declaringClass = declaringClass;
        this.name = name;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.checkedExceptions = checkedExceptions;
        this.modifiers = modifiers;
        this.slot = slot;
        this.signature = signature;
        this.annotations = annotations;
        this.parameterAnnotations = parameterAnnotations;
        this.annotationDefault = annotationDefault;
    }

    @Override
    public Class<?>[] getArgTypes() {
        return new Class[]{Class.class, String.class, Class[].class, Class.class, Class[].class, int.class, int.class, String.class, byte[].class, byte[].class, byte[].class};
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{declaringClass, name, parameterTypes, returnType, checkedExceptions, modifiers, slot, signature, annotations, parameterAnnotations, annotationDefault};
    }
}