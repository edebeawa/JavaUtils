package pers.edebe.util.base;

import lombok.experimental.UtilityClass;

import java.lang.management.ManagementFactory;

@UtilityClass
public class ProcessUtils {
    public static long getCurrentProcessId() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return Long.parseLong(name.substring(0, name.indexOf("@")));
    }
}