package io.huyvu.smart.mapstruct;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mojo(name = "generate-mapper", defaultPhase = LifecyclePhase.COMPILE)
public class DependencyCounterMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            // Setup TypeSolver
            TypeSolver typeSolver = new CombinedTypeSolver(
                    new ReflectionTypeSolver(),
                    new JavaParserTypeSolver(Paths.get(project.getBasedir().getAbsolutePath()))
            );
            JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
            StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

            List<Path> sourceFiles = findJavaFiles(Paths.get(project.getBasedir().getAbsolutePath()));
            for (Path path : sourceFiles) {
                try (FileInputStream in = new FileInputStream(path.toFile())) {
                    CompilationUnit cu = StaticJavaParser.parse(in);
                    MethodCallVisitor methodCallVisitor = new MethodCallVisitor();
                    cu.accept(methodCallVisitor, null);
                    StaticImportVisitor staticImportVisitor = new StaticImportVisitor(methodCallVisitor);
                    cu.accept(staticImportVisitor, null);
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Error scanning source files", e);
        }
    }


    private List<Path> findJavaFiles(Path start) throws IOException {
        try (Stream<Path> stream = Files.walk(start)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(".java"))
                    .collect(Collectors.toList());
        }
    }

    private static class MethodCallVisitor extends VoidVisitorAdapter<Void> {
        private final Set<String> staticImports = new HashSet<>();
        @Override
        public void visit(MethodCallExpr methodCall, Void arg) {
            super.visit(methodCall, arg);
            if (methodCall.getNameAsString().equals("mapTo")) {
                methodCall.getScope().ifPresent(scope -> {
                    try {
                        String fullyQualifiedName = scope.calculateResolvedType().describe();
                        if (fullyQualifiedName.equals("io.huyvu.smart.mapstruct.MapperUtils")) {
                            System.out.println("Found usage of io.huyvu.smart.mapstruct.MapperUtils#mapTo at: " +
                                    methodCall.getBegin().orElse(null));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                // Handle static import case
                if (methodCall.getScope().isEmpty() && staticImports.contains("io.huyvu.smart.mapstruct.MapperUtils.mapTo")) {
                    System.out.println("Found usage of static import io.huyvu.smart.mapstruct.MapperUtils#mapTo at: " +
                            methodCall.getBegin().orElse(null));
                }
            }
        }

        public void addStaticImport(String staticImport) {
            staticImports.add(staticImport);
        }
    }
    private static class StaticImportVisitor extends VoidVisitorAdapter<Void> {
        private final MethodCallVisitor methodCallVisitor;

        public StaticImportVisitor(MethodCallVisitor methodCallVisitor) {this.methodCallVisitor = methodCallVisitor;
        }

        @Override
        public void visit(ImportDeclaration importDeclaration, Void arg) {
            super.visit(importDeclaration, arg);
            if (importDeclaration.isStatic() && !importDeclaration.isAsterisk()) {
                String staticImport = importDeclaration.getNameAsString();
                methodCallVisitor.addStaticImport(staticImport);
            }
        }
    }
}