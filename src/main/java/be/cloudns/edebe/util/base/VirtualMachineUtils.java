package be.cloudns.edebe.util.base;

import be.cloudns.edebe.util.wrapper.ClassWrapper;
import lombok.experimental.UtilityClass;
import be.cloudns.edebe.util.wrapper.FieldWrapper;

@UtilityClass
public class VirtualMachineUtils {
    private static final FieldWrapper<Boolean> HOT_SPOT_VIRTUAL_MACHINE_ALLOW_ATTACH_SELF_FIELD;

    static {
        try {
            HOT_SPOT_VIRTUAL_MACHINE_ALLOW_ATTACH_SELF_FIELD = ClassWrapper.wrap(Class.forName("sun.tools.attach.HotSpotVirtualMachine"))
                    .getDeclaredField("ALLOW_ATTACH_SELF")
                    .setAccessibleNoRestrict(true)
                    .setType(boolean.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void allowAttachSelf(boolean value) throws ReflectiveOperationException {
        HOT_SPOT_VIRTUAL_MACHINE_ALLOW_ATTACH_SELF_FIELD.setStatic(value);
    }
}