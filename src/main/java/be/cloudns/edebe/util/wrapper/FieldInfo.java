package be.cloudns.edebe.util.wrapper;

import lombok.Getter;

import java.lang.reflect.Field;

@Getter
public class FieldInfo extends AccessibleObjectInfo<Field> {
    private final Class<?> declaringClass;
    private final int slot;
    private final String name;
    private final Class<?> type;
    private final int modifiers;
    private final boolean trustedFinal;
    private final transient String signature;
    private final byte[] annotations;

    public FieldInfo(Field field) throws ReflectiveOperationException {
        super(field);
        this.declaringClass = field("clazz", Class.class);
        this.name = field("name", String.class);
        this.type = field("type", Class.class);
        this.modifiers = field("modifiers", int.class);
        this.trustedFinal = field("trustedFinal", boolean.class);
        this.slot = field("slot", int.class);
        this.signature = field("signature", String.class);
        this.annotations = field("annotations", byte[].class);
    }

    public FieldInfo(Class<?> declaringClass, String name, Class<?> type, int modifiers, boolean trustedFinal, int slot, String signature, byte[] annotations) {
        super(Field.class);
        this.declaringClass = declaringClass;
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
        this.trustedFinal = trustedFinal;
        this.slot = slot;
        this.signature = signature;
        this.annotations = annotations;
    }

    @Override
    public Class<?>[] getArgTypes() {
        return new Class[]{Class.class, String.class, Class.class, int.class, boolean.class, int.class, String.class, byte[].class};
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{declaringClass, name, type, modifiers, trustedFinal, slot, signature, annotations};
    }
}