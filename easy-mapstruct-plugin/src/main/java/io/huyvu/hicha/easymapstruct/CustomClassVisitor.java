package io.huyvu.hicha.easymapstruct;

import org.apache.maven.plugin.AbstractMojo;
import org.objectweb.asm.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

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

            private final Map<Integer, LocalVar> localVariables = new HashMap<>();
            private final LinkedList<LocalVar> stack = new LinkedList<>();
            private final LinkedList<MapBuilder> mapBuilders = new LinkedList<>();


            @Override
            public void visitLocalVariable(String localVarName, String descriptor, String signature, Label start, Label end, int index) {
                Type type = Type.getType(descriptor);
                localVariables.put(index, new LocalVar(index, type.getClassName(), localVarName));
                super.visitLocalVariable(localVarName, descriptor, signature, start, end, index);
            }

            //Everytime push variable onto stack
            @Override
            public void visitVarInsn(int opcode, int var) {
                switch (opcode) {
                    case Opcodes.ILOAD:
                    case Opcodes.LLOAD:
                    case Opcodes.FLOAD:
                    case Opcodes.DLOAD:
                    case Opcodes.ALOAD:
                        stack.addLast(new LocalVar(var, "unknown", "unknown"));
                        break;
                }

                super.visitVarInsn(opcode, var);
            }

            @Override
            public void visitIntInsn(int opcode, int operand) {
                switch (opcode) {
                    case Opcodes.BIPUSH:
                    case Opcodes.SIPUSH:
                        stack.addLast(new LocalVar(-1, "BiSiPush_Number", String.valueOf(operand)));
                        break;
                }
                super.visitIntInsn(opcode, operand);
            }

            @Override
            public void visitLdcInsn(Object value) {
                stack.addLast(new LocalVar(-1, "String", "'" + value.toString() + "'"));
                super.visitLdcInsn(value);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                if (opcode == Opcodes.GETSTATIC || opcode == Opcodes.GETFIELD) {
                    Type type = Type.getType(descriptor);
                    stack.addLast(new LocalVar(-1, type.getClassName(), name));
                }

                super.visitFieldInsn(opcode, owner, name, descriptor);
            }

            @Override
            public void visitInsn(int opcode) {
                switch (opcode) {
                    case Opcodes.IALOAD:
                    case Opcodes.LALOAD:
                    case Opcodes.FALOAD:
                    case Opcodes.DALOAD:
                    case Opcodes.AALOAD:
                    case Opcodes.BALOAD:
                    case Opcodes.CALOAD:
                    case Opcodes.SALOAD:
                        stack.addLast(localVariables.get(opcode));
                        break;
                }


                super.visitInsn(opcode);
            }
            //Everytime push variable onto stack


            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {


                if (opcode == Opcodes.INVOKESTATIC &&
                        owner.equals(GROUP_ID) &&
                        name.equals(METHOD_FROM) &&
                        descriptor.equals(FROM_ARGUMENT_DESCRIPTION)) {


                    LocalVar localVar = stack.getLast();
                    MapBuilder mapBuilder = new MapBuilder();
                    mapBuilder.sourceIndex = localVar.index;
                    mapBuilders.push(mapBuilder);
                    mojo.getLog().info(" -> from(" + localVar.index + "): " + stack);

                    /*      mojo.getLog().info(classFile.getPath() + ": static from(" + stack.pollLast() + ")");*/
                    stack.addLast(new LocalVar(-2, "MapperBuilderInstance", "mbi_" + UUID.randomUUID().toString().substring(0,5)));

                } else if (opcode == Opcodes.INVOKEVIRTUAL &&
                        owner.equals(BUILDER_GROUP_ID) &&
                        name.equals(METHOD_MAP) &&
                        descriptor.equals(MAP_ARGUMENT_DESCRIPTION)) {
                    LocalVar localVar = stack.get(stack.size() - 3);
                    if(localVar.varName.startsWith("mbi_")){
                        mojo.getLog().info(localVar.varName + ".map("+ stack.get(stack.size() - 2) +  ","+ stack.get(stack.size() - 1) +"): " + stack);
                        stack.addLast(new LocalVar(-2, "MapperBuilderInstance", localVar.varName));
                    }

                } else if (opcode == Opcodes.INVOKEVIRTUAL &&
                        owner.equals(BUILDER_GROUP_ID) &&
                        name.equals(METHOD_BUILD) &&
                        descriptor.equals(BUILD_ARGUMENT_DESCRIPTION)) {

                    mojo.getLog().info(" -> build(): " + stack);
                    /*Object instance = stack.pollLast();
                    mojo.getLog().info(classFile.getPath() + ": virtual " + instance + ".build()");*/
                    stack.addLast(new LocalVar(-2, "target_type", "currStack"));

                }
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
            }
        };
    }

}
