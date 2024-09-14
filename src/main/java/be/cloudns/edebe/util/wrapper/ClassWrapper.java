package be.cloudns.edebe.util.wrapper;

import be.cloudns.edebe.util.misc.UnsafeUtils;
import be.cloudns.edebe.util.base.ArrayUtils;
import be.cloudns.edebe.util.base.StringUtils;
import be.cloudns.edebe.util.reflect.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClassWrapper<T> extends AbstractWrapper<Class<?>> {
    private static final Method CLASS_GET_DECLARED_FIELDS_METHOD;
    private static final Method CLASS_GET_DECLARED_METHODS_METHOD;
    private static final Method CLASS_GET_DECLARED_CONSTRUCTORS_METHOD;
    private static final Method CLASS_GET_DECLARED_CLASSES_METHOD;

    static {
        try {
            CLASS_GET_DECLARED_FIELDS_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "getDeclaredFields0", boolean.class);
            CLASS_GET_DECLARED_METHODS_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "getDeclaredMethods0", boolean.class);
            CLASS_GET_DECLARED_CONSTRUCTORS_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "getDeclaredConstructors0", boolean.class);
            CLASS_GET_DECLARED_CLASSES_METHOD = ReflectionUtils.getAccessibleDeclaredMethod(Class.class, "getDeclaredClasses0");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private ClassWrapper(Class<T> clazz) {
        super(clazz);
    }

    public static <C> ClassWrapper<C> wrap(Class<C> clazz) {
        return new ClassWrapper<>(clazz);
    }

    public static ClassWrapper<?> wrap(Object object) {
        return wrap(object.getClass());
    }

    public static ClassWrapper<?>[] wrapAll(Class<?>[] classes) {
        return Arrays.stream(classes).map(ClassWrapper::wrap).toArray(ClassWrapper[]::new);
    }

    @SuppressWarnings("unchecked")
    public <C> ClassWrapper<C> setType(Class<C> type) {
        return (ClassWrapper<C>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<T> getObject() {
        return (Class<T>) super.getObject();
    }

    private String methodToString(String name, Class<?>[] argTypes) {
        return getObject().getName() + '.' + name +
                ((argTypes == null || argTypes.length == 0) ? "()" :
                        Arrays.stream(argTypes)
                                .map(c -> c == null ? StringUtils.NULL : c.getName())
                                .collect(Collectors.joining(",", "(", ")"))
                );
    }

    private Field[] getFields(boolean restrict, boolean publicOnly) throws ReflectiveOperationException {
        if (restrict) {
            return publicOnly ? getObject().getFields() : getObject().getDeclaredFields();
        } else {
            return (Field[]) CLASS_GET_DECLARED_FIELDS_METHOD.invoke(getObject(), publicOnly);
        }
    }

    public FieldWrapper<?>[] getDeclaredFields() throws ReflectiveOperationException {
        return FieldWrapper.wrapAll(getFields(true, false));
    }

    public FieldWrapper<?>[] getFields() throws ReflectiveOperationException {
        return FieldWrapper.wrapAll(getFields(true, true));
    }

    public FieldWrapper<?>[] getDeclaredFieldsNoRestrict() throws ReflectiveOperationException {
        return FieldWrapper.wrapAll(getFields(false, false));
    }

    public FieldWrapper<?>[] getFieldsNoRestrict() throws ReflectiveOperationException {
        return FieldWrapper.wrapAll(getFields(false, true));
    }

    private static Field searchField(Field[] fields, String name) {
        for (Field field : fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }

    private Field searchField(String name, boolean restrict, boolean publicOnly) throws ReflectiveOperationException {
        Field field = searchField(getFields(restrict, publicOnly), name);
        if (field != null) {
            return field;
        } else {
            throw new NoSuchFieldException(name);
        }
    }

    public FieldWrapper<?> getDeclaredField(String name) throws ReflectiveOperationException {
        return FieldWrapper.wrap(searchField(name, true, false));
    }

    public FieldWrapper<?> getField(String name) throws ReflectiveOperationException {
        return FieldWrapper.wrap(searchField(name, true, true));
    }

    public FieldWrapper<?> getDeclaredFieldNoRestrict(String name) throws ReflectiveOperationException {
        return FieldWrapper.wrap(searchField(name, false, false));
    }

    public FieldWrapper<?> getFieldNoRestrict(String name) throws ReflectiveOperationException {
        return FieldWrapper.wrap(searchField(name, false, true));
    }

    private Method[] getMethods(boolean restrict, boolean publicOnly) throws ReflectiveOperationException {
        if (restrict) {
            return publicOnly ? getObject().getMethods() : getObject().getDeclaredMethods();
        } else {
            return (Method[]) CLASS_GET_DECLARED_METHODS_METHOD.invoke(getObject(), publicOnly);
        }
    }

    public MethodWrapper<?>[] getDeclaredMethods() throws ReflectiveOperationException {
        return MethodWrapper.wrapAll(getMethods(true, false));
    }

    public MethodWrapper<?>[] getMethods() throws ReflectiveOperationException {
        return MethodWrapper.wrapAll(getMethods(true, true));
    }

    public MethodWrapper<?>[] getDeclaredMethodsNoRestrict() throws ReflectiveOperationException {
        return MethodWrapper.wrapAll(getMethods(false, false));
    }

    public MethodWrapper<?>[] getMethodsNoRestrict() throws ReflectiveOperationException {
        return MethodWrapper.wrapAll(getMethods(false, true));
    }

    private static Method searchMethod(Method[] methods, String name, Class<?>[] parameterTypes, boolean fuzzy) {
        Method res = null;
        for (Method method : methods) {
            if (method.getName().equals(name) &&
                    ArrayUtils.equals(parameterTypes, method.getParameterTypes(), fuzzy) &&
                    (res == null || (res.getReturnType() != method.getReturnType() && res.getReturnType().isAssignableFrom(method.getReturnType())))
            ) res = method;
        }
        return res;
    }

    private Method searchMethod(String name, Class<?>[] parameterTypes, boolean restrict, boolean publicOnly, boolean fuzzy) throws ReflectiveOperationException {
        Method method = searchMethod(getMethods(restrict, publicOnly), name, parameterTypes, fuzzy);
        if (method != null) {
            return method;
        } else {
            throw new NoSuchMethodException(methodToString(name, parameterTypes));
        }
    }

    public MethodWrapper<?> getDeclaredMethodExactMatch(String name, Class<?>... parameterTypes) throws ReflectiveOperationException {
        return MethodWrapper.wrap(searchMethod(name, parameterTypes, true, false, false));
    }

    public MethodWrapper<?> getMethodExactMatch(String name, Class<?>... parameterTypes) throws ReflectiveOperationException {
        return MethodWrapper.wrap(searchMethod(name, parameterTypes, true, true, false));
    }

    public MethodWrapper<?> getDeclaredMethodNoRestrictExactMatch(String name, Class<?>... parameterTypes) throws ReflectiveOperationException {
        return MethodWrapper.wrap(searchMethod(name, parameterTypes, false, false, false));
    }

    public MethodWrapper<?> getMethodNoRestrictExactMatch(String name, Class<?>... parameterTypes) throws ReflectiveOperationException {
        return MethodWrapper.wrap(searchMethod(name, parameterTypes, false, true, false));
    }

    public MethodWrapper<?> getDeclaredMethodFuzzyMatch(String name, Class<?>... parameterTypes) throws ReflectiveOperationException {
        return MethodWrapper.wrap(searchMethod(name, parameterTypes, true, false, true));
    }

    public MethodWrapper<?> getMethodFuzzyMatch(String name, Class<?>... parameterTypes) throws ReflectiveOperationException {
        return MethodWrapper.wrap(searchMethod(name, parameterTypes, true, true, true));
    }

    public MethodWrapper<?> getDeclaredMethodNoRestrictFuzzyMatch(String name, Class<?>... parameterTypes) throws ReflectiveOperationException {
        return MethodWrapper.wrap(searchMethod(name, parameterTypes, false, false, true));
    }

    public MethodWrapper<?> getMethodNoRestrictFuzzyMatch(String name, Class<?>... parameterTypes) throws ReflectiveOperationException {
        return MethodWrapper.wrap(searchMethod(name, parameterTypes, false, true, true));
    }

    private Constructor<?>[] getConstructors(boolean restrict, boolean publicOnly) throws ReflectiveOperationException {
        if (restrict) {
            return publicOnly ? getObject().getConstructors() : getObject().getDeclaredConstructors();
        } else {
            return (Constructor<?>[]) CLASS_GET_DECLARED_CONSTRUCTORS_METHOD.invoke(getObject(), publicOnly);
        }
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T>[] getDeclaredConstructors() throws ReflectiveOperationException {
        return (ConstructorWrapper<T>[]) ConstructorWrapper.wrapAll(getConstructors(true, false));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T>[] getConstructors() throws ReflectiveOperationException {
        return (ConstructorWrapper<T>[]) ConstructorWrapper.wrapAll(getConstructors(true, true));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T>[] getDeclaredConstructorsNoRestrict() throws ReflectiveOperationException {
        return (ConstructorWrapper<T>[]) ConstructorWrapper.wrapAll(getConstructors(false, false));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T>[] getConstructorsNoRestrict() throws ReflectiveOperationException {
        return (ConstructorWrapper<T>[]) ConstructorWrapper.wrapAll(getConstructors(false, true));
    }

    private static Constructor<?> searchConstructor(Constructor<?>[] constructors, Class<?>[] parameterTypes, boolean fuzzy) {
        for (Constructor<?> constructor : constructors) {
            if (ArrayUtils.equals(parameterTypes, constructor.getParameterTypes(), fuzzy)) {
                return constructor;
            }
        }
        return null;
    }

    private Constructor<?> searchConstructor(Class<?>[] parameterTypes, boolean restrict, boolean publicOnly, boolean fuzzy) throws ReflectiveOperationException {
        Constructor<?> constructor = searchConstructor(getConstructors(restrict, publicOnly), parameterTypes, fuzzy);
        if (constructor != null) {
            return constructor;
        } else {
            throw new NoSuchMethodException(methodToString("<init>", parameterTypes));
        }
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T> getDeclaredConstructorExactMatch(Class<?>... parameterTypes) throws ReflectiveOperationException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(searchConstructor(parameterTypes, true, false, false));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T> getConstructorExactMatch(Class<?>... parameterTypes) throws ReflectiveOperationException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(searchConstructor(parameterTypes, true, true, false));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T> getDeclaredConstructorNoRestrictExactMatch(Class<?>... parameterTypes) throws ReflectiveOperationException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(searchConstructor(parameterTypes, false, false, false));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T> getConstructorNoRestrictExactMatch(Class<?>... parameterTypes) throws ReflectiveOperationException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(searchConstructor(parameterTypes, false, true, false));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T> getDeclaredConstructorFuzzyMatch(Class<?>... parameterTypes) throws ReflectiveOperationException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(searchConstructor(parameterTypes, true, false, true));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T> getConstructorFuzzyMatch(Class<?>... parameterTypes) throws ReflectiveOperationException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(searchConstructor(parameterTypes, true, true, true));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T> getDeclaredConstructorNoRestrictFuzzyMatch(Class<?>... parameterTypes) throws ReflectiveOperationException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(searchConstructor(parameterTypes, false, false, true));
    }

    @SuppressWarnings("unchecked")
    public ConstructorWrapper<T> getConstructorNoRestrictFuzzyMatch(Class<?>... parameterTypes) throws ReflectiveOperationException {
        return (ConstructorWrapper<T>) ConstructorWrapper.wrap(searchConstructor(parameterTypes, false, true, true));
    }

    private Class<?>[] getClasses(boolean restrict, boolean publicOnly) throws ReflectiveOperationException {
        if (restrict) {
            return publicOnly ? getObject().getClasses() : getObject().getDeclaredClasses();
        } else {
            Class<?>[] classes = (Class<?>[]) CLASS_GET_DECLARED_CLASSES_METHOD.invoke(getObject());
            if (publicOnly) {
                List<Class<?>> list = new ArrayList<>();
                for (Class<?> clazz : classes) {
                    if (Modifier.isPublic(clazz.getModifiers())) {
                        list.add(clazz);
                    }
                }
                return list.toArray(new Class[0]);
            } else {
                return classes;
            }
        }
    }

    public ClassWrapper<?>[] getDeclaredClasses() throws ReflectiveOperationException {
        return wrapAll(getClasses(true, false));
    }

    public ClassWrapper<?>[] getClasses() throws ReflectiveOperationException {
        return wrapAll(getClasses(true, true));
    }

    public ClassWrapper<?>[] getDeclaredClassesNoRestrict() throws ReflectiveOperationException {
        return wrapAll(getClasses(false, false));
    }

    public ClassWrapper<?>[] getClassesNoRestrict() throws ReflectiveOperationException {
        return wrapAll(getClasses(false, true));
    }

    private static Class<?> searchClass(Class<?>[] classes, String name) {
        for (Class<?> clazz : classes) {
            if (clazz.getName().equals(name)) {
                return clazz;
            }
        }
        return null;
    }

    private Class<?> searchClass(String name, boolean restrict, boolean publicOnly) throws ReflectiveOperationException {
        Class<?> clazz = searchClass(getClasses(restrict, publicOnly), name);
        if (clazz != null) {
            return clazz;
        } else {
            throw new ClassNotFoundException(name);
        }
    }

    public ClassWrapper<?> getDeclaredClass(String name) throws ReflectiveOperationException {
        return wrap(searchClass(name, true, false));
    }

    public ClassWrapper<?> getClass(String name) throws ReflectiveOperationException {
        return wrap(searchClass(name, true, true));
    }

    public ClassWrapper<?> getDeclaredClassNoRestrict(String name) throws ReflectiveOperationException {
        return wrap(searchClass(name, false, false));
    }

    public ClassWrapper<?> getClassNoRestrict(String name) throws ReflectiveOperationException {
        return wrap(searchClass(name, false, true));
    }

    public T cast(Object object) {
        return getObject().cast(object);
    }

    public T castNoRestrict(Object object) throws InstantiationException {
        return ReflectionUtils.castNoRestrict(object, getObject());
    }

    @SuppressWarnings("unchecked")
    public T allocateInstance() throws InstantiationException {
        return (T) UnsafeUtils.UNSAFE_INSTANCE.allocateInstance(getObject());
    }
}