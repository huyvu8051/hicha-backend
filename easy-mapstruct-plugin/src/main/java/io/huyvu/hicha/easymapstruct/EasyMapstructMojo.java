package io.huyvu.hicha.easymapstruct;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

import java.io.File;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class EasyMapstructMojo extends AbstractMojo {
    @Component
    private MavenProject project;
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Hello, this is my custom Mojo!");

        // Get the compiled classes directory
        File compiledClassesDir = new File(project.getBuild().getOutputDirectory());

        // Verify the directory exists
        if (compiledClassesDir.exists() && compiledClassesDir.isDirectory()) {
            getLog().info("Compiled classes directory: " + compiledClassesDir.getAbsolutePath());

            // Perform actions on the compiled classes
            processCompiledClasses(compiledClassesDir);
        } else {
            getLog().warn("Compiled classes directory does not exist or is not a directory.");
        }
    }

    private void processCompiledClasses(File compiledClassesDir) {
        // Custom logic to process compiled classes
        // For example, you could iterate through the classes and perform actions on them
        File[] files = compiledClassesDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    getLog().info("Processing compiled class: " + file.getName());
                    // Add your custom processing logic here
                }
            }
        }
    }
}
