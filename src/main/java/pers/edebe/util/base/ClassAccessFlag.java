package pers.edebe.util.base;

import lombok.Getter;

import java.util.List;
import java.util.Map;

public enum ClassAccessFlag implements AccessFlag {
    PUBLIC("public", 0x0001),
    FINAL("final", 0x0010),
    SUPER("super", 0x0020),
    INTERFACE("interface", 0x0200),
    ABSTRACT("abstract", 0x0400),
    SYNTHETIC("synthetic", 0x1000),
    ANNOTATION("annotation", 0x2000),
    ENUM("enum", 0x4000),
    MODULE("module", 0x8000);

    private final String name;
    @Getter
    private final int value;

    ClassAccessFlag(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }

    private static final Map<Integer, ClassAccessFlag> MAP = AccessFlag.newMap(ClassAccessFlag.values());

    public static int serialize(List<ClassAccessFlag> list) {
        return AccessFlag.serialize(list);
    }

    public static List<ClassAccessFlag> deserialize(int access) {
        return AccessFlag.deserialize(access, MAP);
    }
}