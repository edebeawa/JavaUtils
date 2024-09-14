package be.cloudns.edebe.util.base;

import lombok.Getter;

import java.util.List;
import java.util.Map;

public enum FieldAccessFlag implements AccessFlag {
    PUBLIC("public", 0x0001),
    PRIVATE("private", 0x0002),
    PROTECTED("protected", 0x0004),
    STATIC("static", 0x0008),
    FINAL("final", 0x0010),
    VOLATILE("volatile", 0x0040),
    TRANSIENT("transient", 0x0080),
    SYNTHETIC("synthetic", 0x1000),
    ENUM("enum", 0x4000);

    private final String name;
    @Getter
    private final int value;

    FieldAccessFlag(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }

    private static final Map<Integer, FieldAccessFlag> MAP = AccessFlag.newMap(FieldAccessFlag.values());

    public static int serialize(List<FieldAccessFlag> list) {
        return AccessFlag.serialize(list);
    }

    public static List<FieldAccessFlag> deserialize(int access) {
        return AccessFlag.deserialize(access, MAP);
    }
}