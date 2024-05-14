package pers.edebe.util.base;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class MathUtils {
    public static int nextInt(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static float nextFloat(Random random, float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static double nextDouble(Random random, double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }
}