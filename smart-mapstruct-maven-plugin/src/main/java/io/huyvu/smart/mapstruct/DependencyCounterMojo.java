package io.huyvu.smart.mapstruct;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mojo(name = "generate-mapper", defaultPhase = LifecyclePhase.COMPILE)
public class DependencyCounterMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            // Setup TypeSolver
            String absolutePath = project.getBasedir().getAbsolutePath();
            Path srcDir = Paths.get(absolutePath, "src/main/java");
            Path testDir = Paths.get(absolutePath, "src/test/java");

            CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
            combinedTypeSolver.add(new ReflectionTypeSolver());
            combinedTypeSolver.add(new JavaParserTypeSolver(srcDir));
            combinedTypeSolver.add(new JavaParserTypeSolver(testDir));

            JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
            StaticJavaParser.getParserConfiguration().setSymbolResolver(symbolSolver);

            List<Path> sourceFiles = findJavaFiles(srcDir);
            for (Path path : sourceFiles) {
                try (FileInputStream in = new FileInputStream(path.toFile())) {
                    CompilationUnit cu = StaticJavaParser.parse(in);
                    cu.accept(new MethodCallVisitor(), null);
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
        @Override
        public void visit(MethodCallExpr methodCall, Void arg) {
            super.visit(methodCall, arg);
            if (methodCall.getNameAsString().equals("mapTo")) {
                /*methodCall.getScope().ifPresent(scope -> {
                    if (scope.toString().equals("MapperUtils")) {
                        System.out.println("Found usage of io.huyvu.smart.mapstruct.MapperUtils#mapTo at: " +
                                methodCall.getBegin().orElse(null));
                    }
                });*/

                try {
                    ResolvedMethodDeclaration resolvedMethod = methodCall.resolve();
                    // Get argument types
                    List<ResolvedType> argumentTypes = methodCall.getArguments().stream()
                            .map(argExpr -> argExpr.calculateResolvedType())
                            .collect(Collectors.toList());

                    // Get return type
                    ResolvedType returnType = resolvedMethod.getReturnType();

                    System.out.println("Argument types: " + argumentTypes);
                    System.out.println("Return type: " + returnType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}