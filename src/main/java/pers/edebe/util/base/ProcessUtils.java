package pers.edebe.util.base;

import java.lang.management.ManagementFactory;

public class ProcessUtils {
    public static long getCurrentProcessId() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return Long.parseLong(name.substring(0, name.indexOf("@")));
    }
}