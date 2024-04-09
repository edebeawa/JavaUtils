package pers.edebe.util.base;

import pers.edebe.util.collect.ImmutableMap;

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
        for (int flag : map.keySet())
            if (access >= flag) {
                list.add(map.get(flag));
                access-= flag;
            }
        return list;
    }
}