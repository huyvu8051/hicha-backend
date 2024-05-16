package io.huyvu.smart.mapstruct;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("io.huyvu.smart.mapstruct.SmartMapStruct")
public class AnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Chung ta cua hien tai.");
        return false;
    }
}
