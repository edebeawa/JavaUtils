package pers.edebe.util.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import pers.edebe.util.base.EnumUtils;

import java.lang.reflect.Array;
import java.util.*;

public class EnumTransformer {
    public static byte[] apply(byte[] classfileBuffer) {
        ClassReader reader = new ClassReader(classfileBuffer);
        if (reader.getSuperName().equals("java/lang/Enum")) {
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
            reader.accept(new EnumClassVisitor(reader.getClassName(), writer), 0);
            return writer.toByteArray();
        } else {
            return classfileBuffer;
        }
    }

    public static <T extends Enum<T>> Enum<?>[] onGetValues(Class<T> type, Enum<?>[] values) {
        List<Enum<?>> list = new ArrayList<>();
        list.addAll(Arrays.stream(values).toList());
        list.addAll(EnumUtils.getGlobalEnumMap(type).values());
        Enum<?>[] enums = (Enum<?>[]) Array.newInstance(type, list.size());
        for (int i = 0; i < list.size(); i++) {
            Array.set(enums, i, list.get(i));
        }
        return enums;
    }

    public static <T extends Enum<T>> Enum<?> onGetValueOf(Class<T> type, String name, Enum<?>[] values) {
        Map<String, Enum<?>> map = new HashMap<>(EnumUtils.getGlobalEnumMap(type));
        Arrays.stream(values).forEach((value) -> map.put(value.name(), value));
        return map.get(name);
    }
}