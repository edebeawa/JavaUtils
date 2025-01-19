package top.edebe.util.base;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@UtilityClass
public class MathUtils {
    public static int nextInt(final @NotNull Random random, final int min, final int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static float nextFloat(final @NotNull Random random, final float min, final float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static double nextDouble(final @NotNull Random random, final double min, final double max) {
        return random.nextDouble() * (max - min) + min;
    }
}