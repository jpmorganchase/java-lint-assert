package org.lint.azzert.context;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassMetadata {

    private final Set<AnnotationMetadata> annotations;
    private boolean visible;

    public ClassMetadata(){
        annotations = new LinkedHashSet<>();
    }

    public ClassMetadata(ClassMetadata classMetadata) {
        annotations = new LinkedHashSet<>(classMetadata.annotations);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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
