package top.edebe.util.wrapper;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Getter
public class MethodInfo extends AccessibleObjectInfo<Method> {
    private final @NotNull Class<?> declaringClass;
    private final @NotNull String name;
    private final @NotNull Class<?> @NotNull [] parameterTypes;
    private final @NotNull Class<?> returnType;
    private final @NotNull Class<?> @NotNull [] checkedExceptions;
    private final int modifiers;
    private final int slot;
    private final transient @Nullable String signature;
    private final byte @Nullable [] annotations;
    private final byte @Nullable [] parameterAnnotations;
    private final byte @Nullable [] annotationDefault;

    public MethodInfo(final @NotNull Method method) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        super(method);
        this.declaringClass = super.field("clazz", Class.class);
        this.name = super.field("name", String.class);
        this.parameterTypes = super.field("parameterTypes", Class[].class);
        this.returnType = super.field("returnType", Class.class);
        this.checkedExceptions = super.field("exceptionTypes", Class[].class);
        this.modifiers = super.field("modifiers", int.class);
        this.slot = super.field("slot", int.class);
        this.signature = super.field("signature", String.class);
        this.annotations = super.field("annotations", byte[].class);
        this.parameterAnnotations = super.field("parameterAnnotations", byte[].class);
        this.annotationDefault = super.field("annotationDefault", byte[].class);
    }

    public MethodInfo(final @NotNull Class<?> declaringClass, final @NotNull String name, final @NotNull Class<?> @NotNull [] parameterTypes, final @NotNull Class<?> returnType, final @NotNull Class<?> @NotNull [] checkedExceptions, final int modifiers, final int slot, final @Nullable String signature, final byte @Nullable [] annotations, final byte @Nullable [] parameterAnnotations, final byte @Nullable [] annotationDefault) {
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
    public @NotNull Class<?> @NotNull [] getArgTypes() {
        return new Class[]{Class.class, String.class, Class[].class, Class.class, Class[].class, int.class, int.class, String.class, byte[].class, byte[].class, byte[].class};
    }

    @Override
    public @Nullable Object @NotNull [] getArgs() {
        return new Object[]{this.declaringClass, this.name, this.parameterTypes, this.returnType, this.checkedExceptions, this.modifiers, this.slot, this.signature, this.annotations, this.parameterAnnotations, this.annotationDefault};
    }
}