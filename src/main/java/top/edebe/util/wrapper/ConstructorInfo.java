package top.edebe.util.wrapper;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Getter
public class ConstructorInfo extends AccessibleObjectInfo<Constructor<?>> {
    private final @NotNull Class<?> declaringClass;
    private final @NotNull Class<?> @NotNull [] parameterTypes;
    private final @NotNull Class<?> @NotNull [] checkedExceptions;
    private final int modifiers;
    private final int slot;
    private final transient @Nullable String signature;
    private final byte @Nullable [] annotations;
    private final byte @Nullable [] parameterAnnotations;

    public ConstructorInfo(final @NotNull Constructor<?> constructor) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        super(constructor);
        this.declaringClass = super.field("clazz", Class.class);
        this.parameterTypes = super.field("parameterTypes", Class[].class);
        this.checkedExceptions = super.field("exceptionTypes", Class[].class);
        this.modifiers = super.field("modifiers", int.class);
        this.slot = super.field("slot", int.class);
        this.signature = super.field("signature", String.class);
        this.annotations = super.field("annotations", byte[].class);
        this.parameterAnnotations = super.field("parameterAnnotations", byte[].class);
    }

    public ConstructorInfo(final @NotNull Class<?> declaringClass, final @NotNull Class<?> @NotNull [] parameterTypes, final @NotNull Class<?> @NotNull [] checkedExceptions, final int modifiers, final int slot, final @Nullable String signature, final byte @Nullable [] annotations, final byte @Nullable [] parameterAnnotations) {
        super(Constructor.class);
        this.declaringClass = declaringClass;
        this.parameterTypes = parameterTypes.clone();
        this.checkedExceptions = checkedExceptions.clone();
        this.modifiers = modifiers;
        this.slot = slot;
        this.signature = signature;
        this.annotations = annotations == null ? null : annotations.clone();
        this.parameterAnnotations = parameterAnnotations == null ? null : parameterAnnotations.clone();
    }

    @Override
    public @NotNull Class<?> @NotNull [] getArgTypes() {
        return new Class[]{Class.class, Class[].class, Class[].class, int.class, int.class, String.class, byte[].class, byte[].class};
    }

    @Override
    public @Nullable Object @NotNull [] getArgs() {
        return new Object[]{this.declaringClass, this.parameterTypes, this.checkedExceptions, this.modifiers, this.slot, this.signature, this.annotations, this.parameterAnnotations};
    }
}