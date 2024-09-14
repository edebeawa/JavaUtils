package be.cloudns.edebe.util.collect;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MapConstructor<M, K, V> {
    private final int constructor;
    private final int initialCapacity;
    private final float loadFactor;
    private final Map<? extends K, ? extends V> map;

    public MapConstructor(int initialCapacity, float loadFactor) {
        constructor = 0;
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        map = null;
    }

    public MapConstructor(int initialCapacity) {
        constructor = 1;
        this.initialCapacity = initialCapacity;
        loadFactor = 0;
        map = null;
    }

    public MapConstructor() {
        constructor = 2;
        initialCapacity = 0;
        loadFactor = 0;
        map = null;
    }

    public MapConstructor(Map<? extends K, ? extends V> m) {
        constructor = 3;
        initialCapacity = 0;
        loadFactor = 0;
        map = m;
    }

    public M newInstance(BiFunction<Integer, Float, M> constructor0, Function<Integer, M> constructor1, Supplier<M> constructor2, Function<Map<? extends K, ? extends V>, M> constructor3) {
        switch (constructor) {
            case 0: return constructor0.apply(initialCapacity, loadFactor);
            case 1: return constructor1.apply(initialCapacity);
            case 2: return constructor2.get();
            case 3: return constructor3.apply(map);
            default: return null;
        }
    }
}