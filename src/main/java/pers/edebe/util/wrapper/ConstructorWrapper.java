package pers.edebe.util.wrapper;

import pers.edebe.util.reflect.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ConstructorWrapper<T> extends AccessibleObjectWrapper<Constructor<?>, ConstructorWrapper<?>, ConstructorInfo> {
    private static final Method NATIVE_CONSTRUCTOR_ACCESSOR_IMPL_NEW_INSTANCE_METHOD;

    static {
        try {
            NATIVE_CONSTRUCTOR_ACCESSOR_IMPL_NEW_INSTANCE_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.forName("jdk.internal.reflect.NativeConstructorAccessorImpl"), "newInstance0", Constructor.class, Object[].class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private ConstructorWrapper(Constructor<?> constructor) {
        super(constructor);
    }

    public static ConstructorWrapper<?> wrap(Constructor<?> constructor) {
        return new ConstructorWrapper<>(constructor);
    }

    public static ConstructorWrapper<?> wrap(ConstructorInfo info) throws ReflectiveOperationException {
        return wrap(info.getObjectOrNewInstance());
    }

    public static ConstructorWrapper<?>[] wrapAll(Constructor<?>[] constructors) {
        return Arrays.stream(constructors).map(ConstructorWrapper::wrap).toArray(ConstructorWrapper[]::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> ConstructorWrapper<C> setType(Class<C> type) {
        return (ConstructorWrapper<C>) this;
    }

    @Override
    public ConstructorInfo getInfo() throws ReflectiveOperationException {
        return new ConstructorInfo(getObject());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Constructor<T> getObject() {
        return (Constructor<T>) super.getObject();
    }

    public T newInstance(Object... initargs) throws ReflectiveOperationException {
        return getObject().newInstance(initargs);
    }

    @SuppressWarnings("unchecked")
    public T newInstanceNoRestrict(Object... initargs) throws ReflectiveOperationException {
        return (T) NATIVE_CONSTRUCTOR_ACCESSOR_IMPL_NEW_INSTANCE_METHOD.invoke(null, getObject(), initargs);
    }
}