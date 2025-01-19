package top.edebe.util.base;

import top.edebe.util.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

interface AccessFlag {
    int getValue();

    static <T extends AccessFlag> @NotNull Map<Integer, T> newMap(final @NotNull T @NotNull [] values) {
        final Map<Integer, T> map = new LinkedHashMap<>();
        Arrays.stream(values).forEach((flag) -> map.put(flag.getValue(), flag));
        CollectionUtils.reverse(map);
        return new ImmutableMap<>(map);
    }

    static int serialize(final @NotNull List<? extends AccessFlag> list) {
        int access = 0;
        for (final AccessFlag flag : list)
            access += flag.getValue();
        return access;
    }

    static <T extends AccessFlag> @NotNull List<T> deserialize(int access, final @NotNull Map<Integer, T> map) {
        final List<T> list = new ArrayList<>();
        for (final Map.Entry<Integer, T> entry : map.entrySet()) {
            final int flag = entry.getKey();
            if (access >= flag) {
                list.add(entry.getValue());
                access -= flag;
            }
        }
        return list;
    }
}