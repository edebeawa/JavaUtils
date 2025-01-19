package top.edebe.util.wrapper;

import top.edebe.util.base.ArrayUtils;
import top.edebe.util.reflect.ReflectionUtils;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;

public abstract class AccessibleObjectInfo<T extends AccessibleObject> extends AbstractWrapper<T> {
    @Getter private final @NotNull Class<?> objectType;

    protected AccessibleObjectInfo(final @NotNull T object) {
        super(object);
        this.objectType = object.getClass();
    }

    protected AccessibleObjectInfo(final @NotNull Class<?> clazz) {
        super(null);
        this.objectType = clazz;
    }

    @Override
    public @Nullable T getObject() {
        return super.getObject();
    }

    public @NotNull T getObjectOrNewInstance() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        final T object = this.getObject();
        return object != null ? object : this.newInstance();
    }

    public abstract @NotNull Class<?> @NotNull [] getArgTypes();

    public abstract @Nullable Object @NotNull [] getArgs();

    @Override
    public @NotNull String toString() {
        return ArrayUtils.toString(this.getArgs());
    }

    protected final <F> F field(@NotNull String name, @NotNull Class<F> type) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        return ClassWrapper.wrap(this.objectType)
                .getDeclaredFieldNoRestrict(name)
                .setAccessibleNoRestrict(true)
                .setType(type)
                .get(this.getObject());
    }

    @SuppressWarnings("unchecked")
    public @NotNull T newInstance() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (T) ConstructorWrapper.wrap(ReflectionUtils.getAccessibleDeclaredConstructor(this.objectType, this.getArgTypes())).newInstanceNoRestrict(this.getArgs());
    }
}