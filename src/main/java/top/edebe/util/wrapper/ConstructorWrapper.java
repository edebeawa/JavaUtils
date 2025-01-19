package top.edebe.util.wrapper;

import top.edebe.util.reflect.ReflectionUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ConstructorWrapper<T> extends AccessibleObjectWrapper<Constructor<?>, ConstructorWrapper<?>, ConstructorInfo> {
    private static final Method NATIVE_CONSTRUCTOR_ACCESSOR_IMPL_NEW_INSTANCE_METHOD;

    static {
        try {
            NATIVE_CONSTRUCTOR_ACCESSOR_IMPL_NEW_INSTANCE_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.forName("jdk.internal.reflect.NativeConstructorAccessorImpl"), "newInstance0", Constructor.class, Object[].class);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private ConstructorWrapper(final @NotNull Constructor<?> constructor) {
        super(constructor);
    }

    public static @NotNull ConstructorWrapper<?> wrap(final @NotNull Constructor<?> constructor) {
        return new ConstructorWrapper<>(constructor);
    }

    public static @NotNull ConstructorWrapper<?> wrap(final @NotNull ConstructorInfo info) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return ConstructorWrapper.wrap(info.getObjectOrNewInstance());
    }

    public static @NotNull ConstructorWrapper<?> @NotNull [] wrapAll(final @NotNull Constructor<?> @NotNull [] constructors) {
        return Arrays.stream(constructors).map(ConstructorWrapper::wrap).toArray(ConstructorWrapper[]::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <C> ConstructorWrapper<C> setType(final @NotNull Class<C> type) {
        return (ConstructorWrapper<C>) this;
    }

    @Override
    public @NotNull ConstructorInfo getInfo() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        return new ConstructorInfo(this.getObject());
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Constructor<T> getObject() {
        return (Constructor<T>) super.getObject();
    }

    public @NotNull T newInstance(final Object @NotNull ... initargs) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return this.getObject().newInstance(initargs);
    }

    @SuppressWarnings("unchecked")
    public @NotNull T newInstanceNoRestrict(final Object @NotNull ... initargs) throws InvocationTargetException, IllegalAccessException {
        return (T) NATIVE_CONSTRUCTOR_ACCESSOR_IMPL_NEW_INSTANCE_METHOD.invoke(null, this.getObject(), initargs);
    }
}