package pers.edebe.util.wrapper;

import pers.edebe.util.reflect.ReflectionUtils;

import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWrapper<T extends AccessibleObject, W extends AbstractWrapper<T>, I extends AccessibleObjectInfo<T>> extends AbstractWrapper<T> {
    protected AccessibleObjectWrapper(T object) {
        super(object);
    }

    public static <T extends AccessibleObject, W extends AccessibleObjectWrapper<T, W, I>, I extends AccessibleObjectInfo<T>> AccessibleObjectWrapper<T, W, I> wrap(T object) {
        return new AccessibleObjectWrapper<>(object);
    }

    @SuppressWarnings("unchecked")
    public W setAccessible(boolean flag) {
        getObject().setAccessible(flag);
        return (W) this;
    }

    @SuppressWarnings("unchecked")
    public W setAccessibleNoRestrict(boolean flag) {
        ReflectionUtils.setAccessibleNoRestrict(getObject(), flag);
        return (W) this;
    }

    @Override
    public T getObject() {
        return super.getObject();
    }

    protected <C> W setType(Class<C> type) {
        throw new IllegalAccessError();
    }

    protected I getInfo() throws ReflectiveOperationException {
        throw new IllegalAccessError();
    }
}