package top.edebe.util.wrapper;

import top.edebe.util.misc.UnsafeUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class FieldWrapper<T> extends AccessibleObjectWrapper<Field, FieldWrapper<?>, FieldInfo> {
    private final long fieldOffset;

    private FieldWrapper(final @NotNull Field field) {
        super(field);
        if (Modifier.isStatic(field.getModifiers()))
            this.fieldOffset = UnsafeUtils.UNSAFE_INSTANCE.staticFieldOffset(field);
        else
            this.fieldOffset = UnsafeUtils.UNSAFE_INSTANCE.objectFieldOffset(field);
    }

    public static @NotNull FieldWrapper<?> wrap(final @NotNull Field field) {
        return new FieldWrapper<>(field);
    }

    public static @NotNull FieldWrapper<?> wrap(final @NotNull FieldInfo info) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return wrap(info.getObjectOrNewInstance());
    }

    public static @NotNull FieldWrapper<?> @NotNull [] wrapAll(final @NotNull Field @NotNull [] fields) {
        return Arrays.stream(fields).map(FieldWrapper::wrap).toArray(FieldWrapper[]::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <C> FieldWrapper<C> setType(final @NotNull Class<C> type) {
        return (FieldWrapper<C>) this;
    }

    @Override
    public @NotNull FieldInfo getInfo() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        return new FieldInfo(this.getObject());
    }

    private @NotNull Object getBase() {
        return UnsafeUtils.UNSAFE_INSTANCE.staticFieldBase(this.getObject());
    }

    @SuppressWarnings("unchecked")
    public T get(final @NotNull Object object) throws IllegalAccessException {
        return (T) this.getObject().get(object);
    }

    @SuppressWarnings("unchecked")
    public T getStatic() throws IllegalAccessException {
        return (T) this.getObject().get(null);
    }

    @SuppressWarnings("unchecked")
    public T getNoRestrict(final @NotNull Object object) throws InvocationTargetException, IllegalAccessException {
        return (T) UnsafeUtils.getReference(object, this.fieldOffset);
    }

    @SuppressWarnings("unchecked")
    public T getStaticNoRestrict() throws InvocationTargetException, IllegalAccessException {
        return (T) UnsafeUtils.getReference(this.getBase(), this.fieldOffset);
    }

    public void set(final @NotNull Object object, final T value) throws IllegalAccessException {
        this.getObject().set(object, value);
    }

    public void setStatic(final T value) throws IllegalAccessException {
        this.getObject().set(null, value);
    }

    public void setNoRestrict(final @NotNull Object object, final T value) throws InvocationTargetException, IllegalAccessException {
        UnsafeUtils.putReference(object, this.fieldOffset, value);
    }

    public void setStaticNoRestrict(final T value) throws InvocationTargetException, IllegalAccessException {
        UnsafeUtils.putReference(this.getBase(), this.fieldOffset, value);
    }
}