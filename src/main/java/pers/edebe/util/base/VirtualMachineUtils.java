package pers.edebe.util.base;

import org.jetbrains.annotations.Nullable;
import pers.edebe.util.wrapper.ClassWrapper;

public class VirtualMachineUtils {
    public static void allowAttachSelf(Class<?> clazz, boolean value) throws ReflectiveOperationException {
        ClassWrapper.wrap(clazz)
                .getDeclaredField("ALLOW_ATTACH_SELF")
                .setAccessibleNoRestrict(true)
                .setType(boolean.class)
                .setStatic(value);
    }

    public static void allowAttachSelf(boolean value) throws ReflectiveOperationException {
        allowAttachSelf(Class.forName("sun.tools.attach.HotSpotVirtualMachine"), value);
    }

    public static Object attach(Class<?> clazz, long pid) throws ReflectiveOperationException {
        return clazz.getMethod("attach", String.class).invoke(null, String.valueOf(pid));
    }

    public static void detach(Class<?> clazz, Object instance) throws ReflectiveOperationException {
        clazz.getMethod("detach").invoke(instance);
    }

    public static void loadAgentLibrary(Class<?> clazz, Object instance, String agent, @Nullable String argument) throws ReflectiveOperationException {
        clazz.getMethod("loadAgentLibrary", String.class, String.class).invoke(instance, agent, argument);
    }

    public static void loadAgentLibrary(Class<?> clazz, Object instance, String agent) throws ReflectiveOperationException {
        loadAgentLibrary(clazz, instance, agent, null);
    }

    public static void loadAgentPath(Class<?> clazz, Object instance, String agent, @Nullable String argument) throws ReflectiveOperationException {
        clazz.getMethod("loadAgentPath", String.class, String.class).invoke(instance, agent, argument);
    }

    public static void loadAgentPath(Class<?> clazz, Object instance, String agent) throws ReflectiveOperationException {
        loadAgentPath(clazz, instance, agent, null);
    }

    public static void loadAgent(Class<?> clazz, Object instance, String agent, @Nullable String argument) throws ReflectiveOperationException {
        clazz.getMethod("loadAgent", String.class, String.class).invoke(instance, agent, argument);
    }

    public static void loadAgent(Class<?> clazz, Object instance, String agent) throws ReflectiveOperationException {
        loadAgent(clazz, instance, agent, null);
    }
}