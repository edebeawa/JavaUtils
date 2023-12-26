package pers.edebe.util.base;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import pers.edebe.util.wrapper.ClassWrapper;

import java.io.IOException;

public class VirtualMachineUtils {
    public static VirtualMachine attachSelf() throws AttachNotSupportedException {
        try {
            ClassWrapper.wrap(Class.forName("sun.tools.attach.HotSpotVirtualMachine"))
                    .getDeclaredField("ALLOW_ATTACH_SELF")
                    .setAccessibleNoRestrict(true)
                    .setType(boolean.class)
                    .setStatic(true);
            return VirtualMachine.attach(String.valueOf(ProcessHandle.current().pid()));
        } catch (ReflectiveOperationException | IOException | AttachNotSupportedException e) {
            throw new AttachNotSupportedException();
        }
    }
}