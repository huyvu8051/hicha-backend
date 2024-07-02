package io.huyvu.hicha.easymapstruct;

import com.squareup.javapoet.*;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;


public class JavaFileGenerator {
    private final MavenProject mavenProject;
    private final Mojo mojo;
    JavaFileGenerator(MavenProject mavenProject, Mojo mojo){
        this.mavenProject = mavenProject;
        this.mojo = mojo;
    }

    public void generate(MapBuilder mapBuilder) {
        // Define the package and class names
        String packageName = "io.huyvu.hicha.mapper";
        String className = "Mapper11";
        String interfaceName = "MapStructMapper";

        // Define the imports
        ClassName messageController = ClassName.get("io.huyvu.hicha.controller", "MessageController");
        ClassName message = ClassName.get("io.huyvu.hicha.repository.model", "Message");
        ClassName mappers = ClassName.get("org.mapstruct.factory", "Mappers");

        // Define the map method in the interface
        MethodSpec mapInterfaceMethod = MethodSpec.methodBuilder("map")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .returns(message)
                .addParameter(messageController.nestedClass("MessageDTO"), "source")
                .build();

        // Define the MapStructMapper interface
        TypeSpec mapStructMapper = TypeSpec.interfaceBuilder(interfaceName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addField(FieldSpec.builder(ClassName.get(packageName, interfaceName), "INSTANCE")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$T.getMapper($T.class)", mappers, ClassName.get(packageName, interfaceName))
                        .build())
                .addMethod(mapInterfaceMethod)
                .build();

        // Define the map method in the class
        MethodSpec mapMethod = MethodSpec.methodBuilder("map")
                .addModifiers(Modifier.STATIC)
                .returns(Object.class)
                .addParameter(Object.class, "source")
                .addStatement("return $L.INSTANCE.map(($T.$L) source)", interfaceName, messageController, "MessageDTO")
                .build();

        // Define the Mapper11 class
        TypeSpec mapperClass = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addType(mapStructMapper)
                .addMethod(mapMethod)
                .build();

        // Generate the Java file
        JavaFile javaFile = JavaFile.builder(packageName, mapperClass)
                .addFileComment("Generated by JavaPoet")
                .build();

        try {
            File execute = execute(mavenProject, mojo);
            javaFile.writeTo(execute);
            System.out.println("Java file generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MojoExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public static File execute(MavenProject project, Mojo mojo) throws MojoExecutionException {
        File generatedSourcesDir = new File(project.getBuild().getDirectory(), "generated-sources/annotations");

        // Ensure the directory exists
        if (!generatedSourcesDir.exists()) {
            boolean created = generatedSourcesDir.mkdirs();
            if (!created) {
                throw new MojoExecutionException("Could not create generated-sources directory");
            }
        }

        mojo.getLog().info("Generated Sources Directory: " + generatedSourcesDir.getAbsolutePath());
        return generatedSourcesDir;
        // You can now use generatedSourcesDir for your plugin's generated sources
    }
}