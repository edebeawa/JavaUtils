package top.edebe.util.wrapper;

import top.edebe.util.reflect.ReflectionUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AccessibleObject;

public class AccessibleObjectWrapper<T extends AccessibleObject, W extends AbstractWrapper<T>, I extends AccessibleObjectInfo<T>> extends AbstractWrapper<T> {
    protected AccessibleObjectWrapper(final @NotNull T object) {
        super(object);
    }

    public static @NotNull <T extends AccessibleObject, W extends AccessibleObjectWrapper<T, W, I>, I extends AccessibleObjectInfo<T>> AccessibleObjectWrapper<T, W, I> wrap(final @NotNull T object) {
        return new AccessibleObjectWrapper<>(object);
    }

    @SuppressWarnings("unchecked")
    public @NotNull W setAccessible(final boolean flag) {
        this.getObject().setAccessible(flag);
        return (W) this;
    }

    @SuppressWarnings("unchecked")
    public @NotNull W setAccessibleNoRestrict(final boolean flag) {
        ReflectionUtils.setAccessibleNoRestrict(this.getObject(), flag);
        return (W) this;
    }

    @Override
    public @NotNull T getObject() {
        return super.getObject();
    }

    protected @NotNull <C> W setType(final @NotNull Class<C> type) {
        throw new IllegalAccessError();
    }

    protected @NotNull I getInfo() throws ReflectiveOperationException {
        throw new IllegalAccessError();
    }
}