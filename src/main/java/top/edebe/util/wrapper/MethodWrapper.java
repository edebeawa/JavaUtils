package top.edebe.util.wrapper;

import top.edebe.util.reflect.ReflectionUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodWrapper<T> extends AccessibleObjectWrapper<Method, MethodWrapper<?>, MethodInfo> {
    private static final Method NATIVE_METHOD_ACCESSOR_IMPL_INVOKE_METHOD;

    static {
        try {
            NATIVE_METHOD_ACCESSOR_IMPL_INVOKE_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.forName("jdk.internal.reflect.NativeMethodAccessorImpl"), "invoke0", Method.class, Object.class, Object[].class);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private MethodWrapper(final @NotNull Method method) {
        super(method);
    }

    public static @NotNull MethodWrapper<?> wrap(final @NotNull Method method) {
        return new MethodWrapper<>(method);
    }

    public static @NotNull MethodWrapper<?> wrap(final @NotNull MethodInfo info) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return wrap(info.getObjectOrNewInstance());
    }

    public static @NotNull MethodWrapper<?>[] wrapAll(final @NotNull Method @NotNull [] methods) {
        return Arrays.stream(methods).map(MethodWrapper::wrap).toArray(MethodWrapper[]::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <C> MethodWrapper<C> setType(final @NotNull Class<C> type) {
        return (MethodWrapper<C>) this;
    }

    @Override
    public @NotNull MethodInfo getInfo() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        return new MethodInfo(this.getObject());
    }

    @SuppressWarnings("unchecked")
    public T invoke(final @NotNull Object object, final Object @NotNull ... arguments) throws InvocationTargetException, IllegalAccessException {
        return (T) this.getObject().invoke(object, arguments);
    }

    @SuppressWarnings("unchecked")
    public T invokeStatic(final Object @NotNull ... arguments) throws InvocationTargetException, IllegalAccessException {
        return (T) this.getObject().invoke(null, arguments);
    }

    @SuppressWarnings("unchecked")
    public T invokeNoRestrict(final @NotNull Object object, final Object @NotNull ... arguments) throws InvocationTargetException, IllegalAccessException {
        return (T) NATIVE_METHOD_ACCESSOR_IMPL_INVOKE_METHOD.invoke(null, this.getObject(), object, arguments);
    }

    @SuppressWarnings("unchecked")
    public T invokeStaticNoRestrict(final Object @NotNull ... arguments) throws InvocationTargetException, IllegalAccessException {
        return (T) NATIVE_METHOD_ACCESSOR_IMPL_INVOKE_METHOD.invoke(null, this.getObject(), null, arguments);
    }
}