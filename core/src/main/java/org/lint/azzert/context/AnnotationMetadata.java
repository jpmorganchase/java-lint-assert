package org.lint.azzert.context;

import org.javatuples.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class AnnotationMetadata {

    private final String annotationName;

    private final Collection<Pair<String, String>> parameters;

    public AnnotationMetadata(String name){
        this.annotationName = name;
        this.parameters = new HashSet<>();
    }

    public String getAnnotationName() {
        return annotationName;
    }
    public Collection<Pair<String, String>> getParameters() {
        return parameters;
    }

    public void addParameter(String paramName, Object paramValue) {
        parameters.add(new Pair(paramName, paramValue));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnotationMetadata that = (AnnotationMetadata) o;
        return Objects.equals(annotationName, that.annotationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotationName);
    }

    @Override
    public String toString() {
        return "AnnotationMetadata{" +
                "annotationName='" + annotationName + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
