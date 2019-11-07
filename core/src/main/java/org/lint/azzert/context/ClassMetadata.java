package org.lint.azzert.context;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassMetadata {

    private final Set<AnnotationMetadata> annotations;

    public ClassMetadata(){
        annotations = new LinkedHashSet<>();
    }

    public ClassMetadata(ClassMetadata that) {
        this.annotations = new LinkedHashSet<>(that.annotations);
    }

    public Set<AnnotationMetadata> getAnnotations() {
        return annotations;
    }

    public void updateLastAddedAnnotation(String name, Object value) {
        if (annotations.isEmpty()){
            annotations.add(new AnnotationMetadata(null));
        }
        ((AnnotationMetadata) annotations.toArray()[annotations.size() - 1]).addParameter(name, value);
    }
}
