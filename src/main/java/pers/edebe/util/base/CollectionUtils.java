package pers.edebe.util.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CollectionUtils {
    @SafeVarargs
    public static <T> List<T> merge(Iterable<? extends T>... iterables) {
        List<T> list = new ArrayList<>();
        Arrays.stream(iterables).forEach((iterable) -> iterable.forEach(list::add));
        return list;
    }

    @SafeVarargs
    public static <T> List<T> merge(Stream<? extends T>... streams) {
        List<T> list = new ArrayList<>();
        Arrays.stream(streams).forEach((stream) -> stream.forEach(list::add));
        return list;
    }
}