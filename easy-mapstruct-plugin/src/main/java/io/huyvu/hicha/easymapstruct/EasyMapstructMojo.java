package io.huyvu.hicha.easymapstruct;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class EasyMapstructMojo extends AbstractMojo {
    @Parameter( defaultValue = "${project}", readonly = true )
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

    public void analyzeClass(File classFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(classFile)) {
            ClassReader classReader = new ClassReader(fis);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            CustomClassVisitor classVisitor = new CustomClassVisitor(this, classWriter, project);
            classReader.accept(classVisitor, 0);

            // Write the modified class file
            try (FileOutputStream fos = new FileOutputStream(classFile)) {
                fos.write(classWriter.toByteArray());
            }
        }
    }



}
