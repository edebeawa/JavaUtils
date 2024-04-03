package pers.edebe.util.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionUtils {
    @SafeVarargs
    public static <T> List<T> merge(Iterable<? extends T>... iterables) {
        List<T> list = new ArrayList<>();
        Arrays.stream(iterables).forEach((iterable) -> iterable.forEach(list::add));
        return list;
    }
}