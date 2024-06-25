package io.huyvu.hicha.easymapstruct;

import org.apache.maven.plugin.logging.Log;
import org.objectweb.asm.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CustomClassVisitor extends ClassVisitor {
    private final int api;
    private final Log log;
    private final File classFile;

    protected CustomClassVisitor(int api, File file, Log log) {
        super(api);
        this.api = api;
        this.classFile = file;
        this.log = log;
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return new MethodVisitor(api) {
            private final Map<Integer, String> localVariables = new HashMap<>();
            private final Stack<String> stack = new Stack<>();
            private Type[] argumentTypes;

            @Override
            public void visitCode() {
                argumentTypes = Type.getArgumentTypes(descriptor);
                int index = 0;
                if ((access & Opcodes.ACC_STATIC) == 0) {
                    localVariables.put(index++, "this");
                }
                for (Type argType : argumentTypes) {
                    localVariables.put(index, argType.getClassName());
                    index++;
                }
                super.visitCode();
            }

            @Override
            public void visitLocalVariable(String localVarName, String descriptor, String signature, Label start, Label end, int index) {
                Type type = Type.getType(descriptor);
                localVariables.put(index, type.getClassName());
                super.visitLocalVariable(localVarName, descriptor, signature, start, end, index);
            }

            @Override
            public void visitVarInsn(int opcode, int varIndex) {
                if (opcode == Opcodes.ALOAD) {
                    stack.push(localVariables.get(varIndex));
                }
                super.visitVarInsn(opcode, varIndex);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if (opcode == Opcodes.INVOKESTATIC &&
                        owner.equals("io/huyvu/hicha/mapper/MapperUtils") &&
                        name.equals("map") &&
                        descriptor.equals("(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;")) {

                    if (stack.size() >= 2) {
                        String sourceType = stack.get(stack.size() - 2);
                        String targetType = stack.get(stack.size() - 1);
                        log.info("Found MapperUtils#map usage in: " + classFile.getPath());
                        log.info("Source type: " + sourceType);
                        log.info("Target type: " + targetType);
                    } else {
                        log.error("Stack does not contain enough elements for method invocation: " + classFile.getPath());
                    }
                }
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }

        };
    }

}
