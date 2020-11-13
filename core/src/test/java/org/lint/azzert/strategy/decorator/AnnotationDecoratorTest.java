package org.lint.azzert.strategy.decorator;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.strategy.output.AnnotationDecorator;

import java.util.HashSet;
import java.util.Set;

class AnnotationDecoratorTest {

    Set<AnnotationMetadata> annotations = new HashSet<>();
    @BeforeEach
    void setUp(){
        AnnotationMetadata annotation = new AnnotationMetadata("Lorg/junit/Test;");
        annotations.add(annotation);
    }
    @Test
    void getExpectedExceptions() {
        annotations.iterator().next().addParameter("expected", "Ljava/lang/NullPointerException;");
        AnnotationDecorator decorator = new AnnotationDecorator(annotations);
        String exceptions = decorator.getExpectedException();
        Assertions.assertEquals("java/lang/NullPointerException", exceptions);
    }

    @Test
    void getNoExpectedExceptions() {
        AnnotationDecorator decorator = new AnnotationDecorator(annotations);
        String exceptions = decorator.getExpectedException();
        Assertions.assertNull(exceptions);
    }
}