package io.huyvu.hicha.easymapstruct;

import org.apache.maven.plugin.AbstractMojo;
import org.objectweb.asm.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CustomClassVisitor extends ClassVisitor {
    private final AbstractMojo mojo;
    private final File classFile;

    protected CustomClassVisitor(AbstractMojo mojo, File file) {
        super(Opcodes.ASM9);
        this.mojo = mojo;
        this.classFile = file;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return new MethodVisitor(api) {
            private static final String METHOD_MAP = "map";
            private static final String GROUP_ID = "io/huyvu/hicha/mapper/MapperUtils";
            private static final String BUILDER_GROUP_ID = "io/huyvu/hicha/mapper/MapperUtils$MapperBuilder";
            private static final String METHOD_FROM = "from";
            private static final String METHOD_BUILD = "build";
            private static final String FROM_ARGUMENT_DESCRIPTION = "(Ljava/lang/Object;[Ljava/lang/Object;)Lio/huyvu/hicha/mapper/MapperUtils$MapperBuilder;";
            private static final String MAP_ARGUMENT_DESCRIPTION = "(Ljava/lang/String;Ljava/lang/String;)Lio/huyvu/hicha/mapper/MapperUtils$MapperBuilder;";
            private static final String BUILD_ARGUMENT_DESCRIPTION = "()Ljava/lang/Object;";

            private final Map<Integer, String> localVariables = new HashMap<>();
            private final LinkedList<Object> stack = new LinkedList<>();


            @Override
            public void visitCode() {
                Type[] argumentTypes = Type.getArgumentTypes(descriptor);
                int index = 0;
                if ((access & Opcodes.ACC_STATIC) == 0) {
                    localVariables.put(index++, "this");
                }
                for (Type argType : argumentTypes) {
                    localVariables.put(index, argType.getInternalName() + ":" + argType.getClassName());
                    index++;
                }
                super.visitCode();
            }

            @Override
            public void visitLocalVariable(String localVarName, String descriptor, String signature, Label start, Label end, int index) {
                Type type = Type.getType(descriptor);
                localVariables.put(index, type.getInternalName() + ":" + type.getClassName());
                super.visitLocalVariable(localVarName, descriptor, signature, start, end, index);
            }

            @Override
            public void visitVarInsn(int opcode, int varIndex) {
                if (opcode == Opcodes.ALOAD) {
                    stack.add(localVariables.get(varIndex));
                } else if (opcode == Opcodes.ASTORE || opcode == Opcodes.ISTORE) {
                    stack.pollLast();
                }
                super.visitVarInsn(opcode, varIndex);
            }

            @Override
            public void visitTypeInsn(int opcode, String type) {
                if (opcode == Opcodes.CHECKCAST) {
                    Object o = stack.pollLast();
                    if (o instanceof String s && "target_type".equals(s)) {
                        mojo.getLog().info(classFile.getPath() + ": target_type -> " + type);
                    }
                }
                super.visitTypeInsn(opcode, type);
            }

            @Override
            public void visitLdcInsn(Object cst) {
                stack.add(cst);
                super.visitLdcInsn(cst);
            }


            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {


                if (opcode == Opcodes.INVOKESTATIC &&
                        owner.equals(GROUP_ID) &&
                        name.equals(METHOD_FROM) &&
                        descriptor.equals(FROM_ARGUMENT_DESCRIPTION)) {

                    if (!stack.isEmpty()) {
                        mojo.getLog().info(" -> Stack: " + stack);
                        mojo.getLog().info(classFile.getPath() + ": static from(" + stack.pollLast() + ")");
                        stack.add("MapperBuilderInstance");

                    }
                } else if (opcode == Opcodes.INVOKEVIRTUAL &&
                        owner.equals(BUILDER_GROUP_ID) &&
                        name.equals(METHOD_MAP) &&
                        descriptor.equals(MAP_ARGUMENT_DESCRIPTION)) {

                    mojo.getLog().info(" -> Stack: " + stack);
                    Object target = stack.pollLast();
                    Object source = stack.pollLast();
                    Object instance = stack.pollLast();
                    mojo.getLog().info(classFile.getPath() + ": virtual " + instance + ".map(" + source + ", " + target + ")");
                    stack.add("MapperBuilderInstance");
                } else if (opcode == Opcodes.INVOKEVIRTUAL &&
                        owner.equals(BUILDER_GROUP_ID) &&
                        name.equals(METHOD_BUILD) &&
                        descriptor.equals(BUILD_ARGUMENT_DESCRIPTION)) {

                    mojo.getLog().info(" -> Stack: " + stack);
                    Object instance = stack.pollLast();
                    mojo.getLog().info(classFile.getPath() + ": virtual " + instance + ".build()");
                    stack.add("target_type");

                }
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }

        };
    }

}
