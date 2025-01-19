package top.edebe.util.wrapper;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Getter
public class FieldInfo extends AccessibleObjectInfo<Field> {
    private final @NotNull Class<?> declaringClass;
    private final @NotNull String name;
    private final @NotNull Class<?> type;
    private final int modifiers;
    private final boolean trustedFinal;
    private final int slot;
    private final transient @Nullable String signature;
    private final byte @Nullable [] annotations;

    public FieldInfo(final @NotNull Field field) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        super(field);
        this.declaringClass = super.field("clazz", Class.class);
        this.name = super.field("name", String.class);
        this.type = super.field("type", Class.class);
        this.modifiers = super.field("modifiers", int.class);
        this.trustedFinal = super.field("trustedFinal", boolean.class);
        this.slot = super.field("slot", int.class);
        this.signature = super.field("signature", String.class);
        this.annotations = super.field("annotations", byte[].class);
    }

    public FieldInfo(final @NotNull Class<?> declaringClass, final @NotNull String name, final @NotNull Class<?> type, final int modifiers, final boolean trustedFinal, final int slot, final @Nullable String signature, final byte @Nullable [] annotations) {
        super(Field.class);
        this.declaringClass = declaringClass;
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
        this.trustedFinal = trustedFinal;
        this.slot = slot;
        this.signature = signature;
        this.annotations = annotations == null ? null : annotations.clone();
    }

    @Override
    public @NotNull Class<?> @NotNull [] getArgTypes() {
        return new Class[]{Class.class, String.class, Class.class, int.class, boolean.class, int.class, String.class, byte[].class};
    }

    @Override
    public @Nullable Object @NotNull [] getArgs() {
        return new Object[]{this.declaringClass, this.name, this.type, this.modifiers, this.trustedFinal, this.slot, this.signature, this.annotations};
    }
}