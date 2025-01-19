package top.edebe.util.wrapper;

import top.edebe.util.base.ArrayUtils;
import top.edebe.util.misc.UnsafeUtils;
import top.edebe.util.reflect.ReflectionUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClassWrapper<T> extends AbstractWrapper<Class<?>> {
    private static final Object REFLECTION_FACTORY_INSTANCE;
    private static final Method CLASS_GET_DECLARED_FIELDS_METHOD;
    private static final Method CLASS_GET_DECLARED_METHODS_METHOD;
    private static final Method CLASS_GET_DECLARED_CONSTRUCTORS_METHOD;
    private static final Method CLASS_GET_DECLARED_CLASSES_METHOD;

    static {
        try {
            REFLECTION_FACTORY_INSTANCE = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "getReflectionFactory").invoke(null);
            CLASS_GET_DECLARED_FIELDS_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "getDeclaredFields0", boolean.class);
            CLASS_GET_DECLARED_METHODS_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "getDeclaredMethods0", boolean.class);
            CLASS_GET_DECLARED_CONSTRUCTORS_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "getDeclaredConstructors0", boolean.class);
            CLASS_GET_DECLARED_CLASSES_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "getDeclaredClasses0");
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private ClassWrapper(final @NotNull Class<T> clazz) {
        super(clazz);
    }

    public static @NotNull <C> ClassWrapper<C> wrap(final @NotNull Class<C> clazz) {
        return new ClassWrapper<>(clazz);
    }

    public static @NotNull ClassWrapper<?> wrap(final @NotNull Object object) {
        return ClassWrapper.wrap(object.getClass());
    }

    public static @NotNull ClassWrapper<?>[] wrapAll(final @NotNull Class<?> @NotNull [] classes) {
        return Arrays.stream(classes).map(ClassWrapper::wrap).toArray(ClassWrapper[]::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Class<T> getObject() {
        return (Class<T>) super.getObject();
    }

    @SuppressWarnings("unchecked")
    public @NotNull <C> ClassWrapper<C> setType(final @NotNull Class<C> type) {
        final Class<T> clazz = this.getObject();
        if (type.isAssignableFrom(clazz))
            return (ClassWrapper<C>) this;
        else
            throw new ClassCastException(clazz.toString());
    }

    private @NotNull String methodToString(final @NotNull String name, final @NotNull Class<?> @NotNull [] argTypes) {
        return this.getObject().getName() + '.' + name +
                (argTypes.length == 0 ? "()" : Arrays.stream(argTypes)
                        .map(Class::getName)
                        .collect(Collectors.joining(",", "(", ")"))
                );
    }

    private @NotNull Field @NotNull [] getFields(final boolean restrict, final boolean publicOnly) throws InvocationTargetException, IllegalAccessException {
        final Class<T> object = this.getObject();
        if (restrict)
            return publicOnly ? object.getFields() : object.getDeclaredFields();
        else
            return (Field[]) CLASS_GET_DECLARED_FIELDS_METHOD.invoke(object, publicOnly);
    }

    public @NotNull FieldWrapper<?> @NotNull [] getDeclaredFields() throws InvocationTargetException, IllegalAccessException {
        return FieldWrapper.wrapAll(this.getFields(true, false));
    }

    public @NotNull FieldWrapper<?> @NotNull [] getFields() throws InvocationTargetException, IllegalAccessException {
        return FieldWrapper.wrapAll(this.getFields(true, true));
    }

    public @NotNull FieldWrapper<?> @NotNull [] getDeclaredFieldsNoRestrict() throws InvocationTargetException, IllegalAccessException {
        return FieldWrapper.wrapAll(this.getFields(false, false));
    }

    public @NotNull FieldWrapper<?> @NotNull [] getFieldsNoRestrict() throws InvocationTargetException, IllegalAccessException {
        return FieldWrapper.wrapAll(this.getFields(false, true));
    }

    private @NotNull Field searchField(final @NotNull String name, final boolean restrict, final boolean publicOnly) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        for (final Field field : this.getFields(restrict, publicOnly)) {
            if (field.getName().equals(name))
                return field;
        }
        throw new NoSuchFieldException(name);
    }

    public @NotNull FieldWrapper<?> getDeclaredField(final @NotNull String name) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        return FieldWrapper.wrap(this.searchField(name, true, false));
    }

    public @NotNull FieldWrapper<?> getField(final @NotNull String name) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        return FieldWrapper.wrap(this.searchField(name, true, true));
    }

    public @NotNull FieldWrapper<?> getDeclaredFieldNoRestrict(final @NotNull String name) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        return FieldWrapper.wrap(this.searchField(name, false, false));
    }

    public @NotNull FieldWrapper<?> getFieldNoRestrict(final @NotNull String name) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        return FieldWrapper.wrap(this.searchField(name, false, true));
    }

    private @NotNull Method @NotNull [] getMethods(final boolean restrict, final boolean publicOnly) throws InvocationTargetException, IllegalAccessException {
        final Class<T> object = this.getObject();
        if (restrict)
            return publicOnly ? object.getMethods() : object.getDeclaredMethods();
        else
            return (Method[]) CLASS_GET_DECLARED_METHODS_METHOD.invoke(object, publicOnly);
    }

    public @NotNull MethodWrapper<?> @NotNull [] getDeclaredMethods() throws InvocationTargetException, IllegalAccessException {
        return MethodWrapper.wrapAll(this.getMethods(true, false));
    }

    public @NotNull MethodWrapper<?> @NotNull [] getMethods() throws InvocationTargetException, IllegalAccessException {
        return MethodWrapper.wrapAll(this.getMethods(true, true));
    }

    public @NotNull MethodWrapper<?> @NotNull [] getDeclaredMethodsNoRestrict() throws InvocationTargetException, IllegalAccessException {
        return MethodWrapper.wrapAll(this.getMethods(false, false));
    }

    public @NotNull MethodWrapper<?> @NotNull [] getMethodsNoRestrict() throws InvocationTargetException, IllegalAccessException {
        return MethodWrapper.wrapAll(this.getMethods(false, true));
    }

    private @NotNull Method searchMethod(final @NotNull String name, final @NotNull Class<?> @NotNull [] parameterTypes, final boolean restrict, final boolean publicOnly) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method res = null;
        for (final Method method : this.getMethods(restrict, publicOnly)) {
            if (method.getName().equals(name) &&
                    ArrayUtils.equals(parameterTypes, method.getParameterTypes()) &&
                    (res == null || (res.getReturnType() != method.getReturnType() && res.getReturnType().isAssignableFrom(method.getReturnType())))
            )
                res = method;
        }
        if (res != null)
            return res;
        else
            throw new NoSuchMethodException(this.methodToString(name, parameterTypes));
    }

    public @NotNull MethodWrapper<?> getDeclaredMethod(final @NotNull String name, final @NotNull Class<?> @NotNull ... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return MethodWrapper.wrap(this.searchMethod(name, parameterTypes, true, false));
    }

    public @NotNull MethodWrapper<?> getMethod(final @NotNull String name, final @NotNull Class<?> @NotNull ... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return MethodWrapper.wrap(this.searchMethod(name, parameterTypes, true, true));
    }

    public @NotNull MethodWrapper<?> getDeclaredMethodNoRestrict(final @NotNull String name, final @NotNull Class<?> @NotNull ... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return MethodWrapper.wrap(this.searchMethod(name, parameterTypes, false, false));
    }

    public @NotNull MethodWrapper<?> getMethodNoRestrict(final @NotNull String name, final @NotNull Class<?> @NotNull ... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return MethodWrapper.wrap(this.searchMethod(name, parameterTypes, false, true));
    }

    private @NotNull Constructor<?> @NotNull [] getConstructors(final boolean restrict, final boolean publicOnly) throws InvocationTargetException, IllegalAccessException {
        final Class<T> object = this.getObject();
        if (restrict)
            return publicOnly ? object.getConstructors() : object.getDeclaredConstructors();
        else
            return (Constructor<?>[]) CLASS_GET_DECLARED_CONSTRUCTORS_METHOD.invoke(object, publicOnly);
    }

    @SuppressWarnings("unchecked")
    public @NotNull ConstructorWrapper<T> @NotNull [] getDeclaredConstructors() throws InvocationTargetException, IllegalAccessException {
        return (ConstructorWrapper<T>[]) ConstructorWrapper.wrapAll(this.getConstructors(true, false));
    }

    @SuppressWarnings("unchecked")
    public @NotNull ConstructorWrapper<T> @NotNull [] getConstructors() throws InvocationTargetException, IllegalAccessException {
        return (ConstructorWrapper<T>[]) ConstructorWrapper.wrapAll(this.getConstructors(true, true));
    }

    @SuppressWarnings("unchecked")
    public @NotNull ConstructorWrapper<T> @NotNull [] getDeclaredConstructorsNoRestrict() throws InvocationTargetException, IllegalAccessException {
        return (ConstructorWrapper<T>[]) ConstructorWrapper.wrapAll(this.getConstructors(false, false));
    }

    @SuppressWarnings("unchecked")
    public @NotNull ConstructorWrapper<T> @NotNull [] getConstructorsNoRestrict() throws InvocationTargetException, IllegalAccessException {
        return (ConstructorWrapper<T>[]) ConstructorWrapper.wrapAll(this.getConstructors(false, true));
    }

    private @NotNull Constructor<?> searchConstructor(final @NotNull Class<?> @NotNull [] parameterTypes, final boolean restrict, final boolean publicOnly) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (final Constructor<?> constructor : this.getConstructors(restrict, publicOnly)) {
            if (ArrayUtils.equals(parameterTypes, constructor.getParameterTypes()))
                return constructor;
        }
        throw new NoSuchMethodException(this.methodToString("<init>", parameterTypes));
    }

    @SuppressWarnings("unchecked")
    public @NotNull ConstructorWrapper<T> getDeclaredConstructor(final @NotNull Class<?> @NotNull ... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(this.searchConstructor(parameterTypes, true, false));
    }

    @SuppressWarnings("unchecked")
    public @NotNull ConstructorWrapper<T> getConstructor(final @NotNull Class<?> @NotNull ... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(this.searchConstructor(parameterTypes, true, true));
    }

    @SuppressWarnings("unchecked")
    public @NotNull ConstructorWrapper<T> getDeclaredConstructorNoRestrict(final @NotNull Class<?> @NotNull ... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(this.searchConstructor(parameterTypes, false, false));
    }

    @SuppressWarnings("unchecked")
    public @NotNull ConstructorWrapper<T> getConstructorNoRestrict(final @NotNull Class<?> @NotNull ... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(this.searchConstructor(parameterTypes, false, true));
    }

    private @NotNull Class<?> @NotNull [] getClasses(final boolean restrict, final boolean publicOnly) throws InvocationTargetException, IllegalAccessException {
        final Class<T> object = this.getObject();
        if (restrict) {
            return publicOnly ? object.getClasses() : object.getDeclaredClasses();
        }
        else {
            final Class<?>[] classes = (Class<?>[]) CLASS_GET_DECLARED_CLASSES_METHOD.invoke(object);
            if (publicOnly) {
                final List<Class<?>> list = new ArrayList<>();
                for (final Class<?> clazz : classes) {
                    if (Modifier.isPublic(clazz.getModifiers()))
                        list.add(clazz);
                }
                return list.toArray(new Class[0]);
            }
            else {
                return classes;
            }
        }
    }

    public @NotNull ClassWrapper<?> @NotNull [] getDeclaredClasses() throws InvocationTargetException, IllegalAccessException {
        return ClassWrapper.wrapAll(this.getClasses(true, false));
    }

    public @NotNull ClassWrapper<?> @NotNull [] getClasses() throws InvocationTargetException, IllegalAccessException {
        return ClassWrapper.wrapAll(this.getClasses(true, true));
    }

    public @NotNull ClassWrapper<?> @NotNull [] getDeclaredClassesNoRestrict() throws InvocationTargetException, IllegalAccessException {
        return ClassWrapper.wrapAll(this.getClasses(false, false));
    }

    public @NotNull ClassWrapper<?> @NotNull [] getClassesNoRestrict() throws InvocationTargetException, IllegalAccessException {
        return ClassWrapper.wrapAll(this.getClasses(false, true));
    }

    private @NotNull Class<?> searchClass(final @NotNull String name, final boolean restrict, final boolean publicOnly) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        for (final Class<?> clazz : this.getClasses(restrict, publicOnly)) {
            if (clazz.getName().equals(name))
                return clazz;
        }
        throw new ClassNotFoundException(name);
    }

    public @NotNull ClassWrapper<?> getDeclaredClass(final @NotNull String name) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return ClassWrapper.wrap(this.searchClass(name, true, false));
    }

    public @NotNull ClassWrapper<?> getClass(final @NotNull String name) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return ClassWrapper.wrap(this.searchClass(name, true, true));
    }

    public @NotNull ClassWrapper<?> getDeclaredClassNoRestrict(final @NotNull String name) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return ClassWrapper.wrap(this.searchClass(name, false, false));
    }

    public @NotNull ClassWrapper<?> getClassNoRestrict(final @NotNull String name) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return ClassWrapper.wrap(this.searchClass(name, false, true));
    }

    public @NotNull T cast(Object object) {
        return this.getObject().cast(object);
    }

    public @NotNull T castNoRestrict(Object object) {
        return ReflectionUtils.castNoRestrict(object, this.getObject());
    }

    @SuppressWarnings("unchecked")
    public @NotNull T allocateInstance() throws InstantiationException {
        return (T) UnsafeUtils.UNSAFE_INSTANCE.allocateInstance(this.getObject());
    }
}