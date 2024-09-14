package be.cloudns.edebe.util.wrapper;

import be.cloudns.edebe.util.reflect.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodWrapper<T> extends AccessibleObjectWrapper<Method, MethodWrapper<?>, MethodInfo> {
    private static final Method NATIVE_METHOD_ACCESSOR_IMPL_INVOKE_METHOD;

    static {
        try {
            NATIVE_METHOD_ACCESSOR_IMPL_INVOKE_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.forName("jdk.internal.reflect.NativeMethodAccessorImpl"), "invoke0", Method.class, Object.class, Object[].class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private MethodWrapper(Method method) {
        super(method);
    }

    public static MethodWrapper<?> wrap(Method method) {
        return new MethodWrapper<>(method);
    }

    public static MethodWrapper<?> wrap(MethodInfo info) throws ReflectiveOperationException {
        return wrap(info.getObjectOrNewInstance());
    }

    public static MethodWrapper<?>[] wrapAll(Method[] methods) {
        return Arrays.stream(methods).map(MethodWrapper::wrap).toArray(MethodWrapper[]::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> MethodWrapper<C> setType(Class<C> type) {
        return (MethodWrapper<C>) this;
    }

    @Override
    public MethodInfo getInfo() throws ReflectiveOperationException {
        return new MethodInfo(getObject());
    }

    @SuppressWarnings("unchecked")
    public T invoke(Object object, Object... arguments) throws ReflectiveOperationException {
        return (T) getObject().invoke(object, arguments);
    }

    @SuppressWarnings("unchecked")
    public T invokeStatic(Object... arguments) throws ReflectiveOperationException {
        return (T) getObject().invoke(null, arguments);
    }

    private static Object invokeNoRestrict(Method method, Object object, Object[] arguments) throws ReflectiveOperationException {
        return NATIVE_METHOD_ACCESSOR_IMPL_INVOKE_METHOD.invoke(null, method, object, arguments);
    }

    @SuppressWarnings("unchecked")
    public T invokeNoRestrict(Object object, Object... arguments) throws ReflectiveOperationException {
        return (T) invokeNoRestrict(getObject(), object, arguments);
    }

    @SuppressWarnings("unchecked")
    public T invokeStaticNoRestrict(Object... arguments) throws ReflectiveOperationException {
        return (T) invokeNoRestrict(getObject(), null, arguments);
    }
}