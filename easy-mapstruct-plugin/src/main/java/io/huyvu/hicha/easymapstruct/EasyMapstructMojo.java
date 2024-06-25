package io.huyvu.hicha.easymapstruct;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class EasyMapstructMojo extends AbstractMojo {
    @Component
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Starting bytecode analysis...");
        File classesDir = new File(project.getBuild().getOutputDirectory());

        try {
            analyzeClasses(classesDir);
        } catch (IOException e) {
            throw new MojoExecutionException("Error analyzing classes", e);
        }
    }

    private void analyzeClasses(File dir) throws IOException {
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isDirectory()) {
                    analyzeClasses(file);
                } else if (file.getName().endsWith(".class")) {
                    analyzeClass(file);
                }
            }
        }
    }

    private void analyzeClass(File classFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(classFile)) {
            ClassReader classReader = new ClassReader(fis);
            ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (opcode == Opcodes.INVOKESTATIC &&
                                    owner.equals("io/huyvu/hicha/mapper/MapperUtils") &&
                                    name.equals("map") &&
                                    descriptor.equals("(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;")) {
                                getLog().info("Found MapperUtils#map usage in: " + classFile.getPath());
                            }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
            };
            classReader.accept(classVisitor, 0);
        }
    }
}
