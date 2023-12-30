package pers.edebe.util.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class EnumClassVisitor extends ClassVisitor {
    private static final int ASM_VERSION = Opcodes.ASM9;
    private final String classname;

    public EnumClassVisitor(String name, ClassVisitor visitor) {
        super(ASM_VERSION, visitor);
        classname = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return new MethodVisitor(ASM_VERSION, super.visitMethod(access, name, descriptor, signature, exceptions)) {
            @Override
            public void visitCode() {
                String method = name + descriptor;
                if (method.equals("values()[L" + classname + ";")) {
                    super.visitLdcInsn(Type.getType("L" + classname + ";"));
                    super.visitFieldInsn(Opcodes.GETSTATIC, classname, "$VALUES", "[L" + classname + ";");
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, "pers/edebe/util/asm/EnumTransformer", "onGetValues", "(Ljava/lang/Class;[Ljava/lang/Enum;)[Ljava/lang/Enum;", false);
                    super.visitTypeInsn(Opcodes.CHECKCAST, "[L" + classname + ";");
                    super.visitInsn(Opcodes.ARETURN);
                } else if (method.equals("valueOf(Ljava/lang/String;)L" + classname + ";")) {
                    super.visitLdcInsn(Type.getType("L" + classname + ";"));
                    super.visitVarInsn(Opcodes.ALOAD, 0);
                    super.visitFieldInsn(Opcodes.GETSTATIC, classname, "$VALUES", "[L" + classname + ";");
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, "pers/edebe/util/asm/EnumTransformer", "onGetValueOf", "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Enum;)Ljava/lang/Enum;", false);
                    super.visitTypeInsn(Opcodes.CHECKCAST, classname);
                    super.visitInsn(Opcodes.ARETURN);
                } else {
                    super.visitCode();
                }
            }
        };
    }
}