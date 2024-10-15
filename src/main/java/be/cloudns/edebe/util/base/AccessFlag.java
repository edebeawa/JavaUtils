package be.cloudns.edebe.util.base;

import be.cloudns.edebe.util.collect.ImmutableMap;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

interface AccessFlag {
    int getValue();

    static <T extends AccessFlag> Map<Integer, T> newMap(T[] values) {
        Map<Integer, T> map = new LinkedHashMap<>();
        Arrays.stream(values).forEach((flag) -> map.put(flag.getValue(), flag));
        CollectionUtils.reverse(map);
        return new ImmutableMap<>(map);
    }

    static int serialize(List<? extends AccessFlag> list) {
        AtomicInteger access = new AtomicInteger();
        list.forEach((flag) -> access.addAndGet(flag.getValue()));
        return access.get();
    }

    static <T extends AccessFlag> List<T> deserialize(int access, Map<Integer, T> map) {
        List<T> list = new ArrayList<>();
        for (Map.Entry<Integer, T> entry : map.entrySet()) {
            int flag = entry.getKey();
            if (access >= flag) {
                list.add(entry.getValue());
                access -= flag;
            }
        }
        return list;
    }
}