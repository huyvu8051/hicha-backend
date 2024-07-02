package io.huyvu.hicha.easymapstruct;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.objectweb.asm.*;

import java.io.File;
import java.util.*;

public class CustomClassVisitor extends ClassVisitor {
    private final AbstractMojo mojo;
    private String className;
    MavenProject project;
    protected CustomClassVisitor(AbstractMojo mojo, ClassWriter classWriter, MavenProject project) {
        super(Opcodes.ASM9, classWriter);
        this.mojo = mojo;
        this.project = project;
    }
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name; // Capture the class name
        super.visit(version, access, name, signature, superName, interfaces);
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        return new CustomMethodVisitor(this.api, mv, mojo, className, name, project);

    }

}
