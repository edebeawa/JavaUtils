package pers.edebe.util.bytecode;

import java.util.ArrayList;
import java.util.List;

public class AccessUtils {
    public static List<Integer> resolve(int access, int max) {
        List<Integer> list = new ArrayList<>();
        while (max > 0) {
            if (access >= max) {
                list.add(max);
                access -= max;
            }
            max /= 2;
        }
        return list;
    }
}