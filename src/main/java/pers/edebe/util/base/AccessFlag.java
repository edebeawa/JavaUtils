package pers.edebe.util.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

interface AccessFlag {
    int getValue();

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