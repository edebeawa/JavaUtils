package pers.edebe.util.base;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum MethodAccessFlag implements AccessFlag {
    PUBLIC("public", 0x0001),
    PRIVATE("private", 0x0002),
    PROTECTED("protected", 0x0004),
    STATIC("static", 0x0008),
    FINAL("final", 0x0010),
    SYNCHRONIZED("synchronized", 0x0020),
    NATIVE("native", 0x0100),
    ABSTRACT("abstract", 0x0400),
    STRICT("strict", 0x0800),
    SYNTHETIC("synthetic", 0x1000),
    BRIDGE("bridge", 0x0040),
    VARARGS("varargs", 0x0080);

    private final String name;
    @Getter
    private final int value;

    MethodAccessFlag(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }

    private static final Map<Integer, MethodAccessFlag> MAP = AccessFlag.newMap(MethodAccessFlag.values());

    public static int serialize(List<MethodAccessFlag> list) {
        return AccessFlag.serialize(list);
    }

    public static List<MethodAccessFlag> deserialize(int access) {
        return AccessFlag.deserialize(access, MAP);
    }
}