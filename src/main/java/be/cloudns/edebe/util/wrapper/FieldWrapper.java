package be.cloudns.edebe.util.wrapper;

import be.cloudns.edebe.util.misc.UnsafeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class FieldWrapper<T> extends AccessibleObjectWrapper<Field, FieldWrapper<?>, FieldInfo> {
    private final long fieldOffset;

    private FieldWrapper(Field field) {
        super(field);
        if (Modifier.isStatic(field.getModifiers())) {
            fieldOffset = UnsafeUtils.UNSAFE_INSTANCE.staticFieldOffset(field);
        } else {
            fieldOffset = UnsafeUtils.UNSAFE_INSTANCE.objectFieldOffset(field);
        }
    }

    public static FieldWrapper<?> wrap(Field field) {
        return new FieldWrapper<>(field);
    }

    public static FieldWrapper<?> wrap(FieldInfo info) throws ReflectiveOperationException {
        return wrap(info.getObjectOrNewInstance());
    }

    public static FieldWrapper<?>[] wrapAll(Field[] fields) {
        return Arrays.stream(fields).map(FieldWrapper::wrap).toArray(FieldWrapper[]::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> FieldWrapper<C> setType(Class<C> type) {
        return (FieldWrapper<C>) this;
    }

    @Override
    public FieldInfo getInfo() throws ReflectiveOperationException {
        return new FieldInfo(getObject());
    }

    private Object getBase() {
        return UnsafeUtils.UNSAFE_INSTANCE.staticFieldBase(getObject());
    }

    @SuppressWarnings("unchecked")
    public T get(Object object) throws ReflectiveOperationException {
        return (T) getObject().get(object);
    }

    @SuppressWarnings("unchecked")
    public T getStatic() throws ReflectiveOperationException {
        return (T) getObject().get(null);
    }

    @SuppressWarnings("unchecked")
    public T getNoRestrict(Object object) throws ReflectiveOperationException {
        return (T) UnsafeUtils.getReference(object, fieldOffset);
    }

    @SuppressWarnings("unchecked")
    public T getStaticNoRestrict() throws ReflectiveOperationException {
        return (T) UnsafeUtils.getReference(getBase(), fieldOffset);
    }

    public void set(Object object, T value) throws ReflectiveOperationException {
        getObject().set(object, value);
    }

    public void setStatic(T value) throws ReflectiveOperationException {
        getObject().set(null, value);
    }

    public void setNoRestrict(Object object, T value) throws ReflectiveOperationException {
        UnsafeUtils.putReference(object, fieldOffset, value);
    }

    public void setStaticNoRestrict(T value) throws ReflectiveOperationException {
        UnsafeUtils.putReference(getBase(), fieldOffset, value);
    }
}