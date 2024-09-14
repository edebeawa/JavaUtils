package be.cloudns.edebe.util.wrapper;

import lombok.Getter;
import be.cloudns.edebe.util.base.ArrayUtils;
import org.jetbrains.annotations.Nullable;
import be.cloudns.edebe.util.reflect.ReflectionUtils;

import java.lang.reflect.AccessibleObject;

public abstract class AccessibleObjectInfo<T extends AccessibleObject> extends AbstractWrapper<T> {
    @Getter private final Class<?> objectType;

    protected AccessibleObjectInfo(T object) {
        super(object);
        this.objectType = object.getClass();
    }

    protected AccessibleObjectInfo(Class<?> clazz) {
        super(null);
        this.objectType = clazz;
    }

    @Nullable
    @Override
    public T getObject() {
        return super.getObject();
    }

    public T getObjectOrNewInstance() throws ReflectiveOperationException {
        return getObject() != null ? getObject() : newInstance();
    }

    public abstract Class<?>[] getArgTypes();

    public abstract Object[] getArgs();

    @Override
    public String toString() {
        return ArrayUtils.toString(getArgs());
    }

    protected <F> F field(String name, Class<F> type) throws ReflectiveOperationException {
        return ClassWrapper.wrap(objectType)
                .getDeclaredFieldNoRestrict(name)
                .setAccessibleNoRestrict(true)
                .setType(type)
                .get(getObject());
    }

    @SuppressWarnings("unchecked")
    public T newInstance() throws ReflectiveOperationException {
        return (T) ConstructorWrapper.wrap(ReflectionUtils.getAccessibleDeclaredConstructor(objectType, getArgTypes())).newInstanceNoRestrict(getArgs());
    }
}