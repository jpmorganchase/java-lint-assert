package org.lint.azzert.strategy.decorator;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.AnnotationMetadata;

import java.util.HashSet;
import java.util.Set;

class Junit4AnnotationDecoratorTest {

    Set<AnnotationMetadata> annotations = new HashSet<>();
    @BeforeEach
    void setUp(){
        AnnotationMetadata annotation = new AnnotationMetadata("Lorg/junit/Test;");
        annotations.add(annotation);
    }
    @Test
    void getExpectedExceptions() {
        annotations.iterator().next().addParameter("expected", "Ljava/lang/NullPointerException;");
        Junit4AnnotationDecorator decorator = new Junit4AnnotationDecorator(annotations);
        String exceptions = decorator.getExpectedException();
        Assertions.assertEquals("java/lang/NullPointerException", exceptions);
    }

    @Test
    void getNoExpectedExceptions() {
        Junit4AnnotationDecorator decorator = new Junit4AnnotationDecorator(annotations);
        String exceptions = decorator.getExpectedException();
        Assertions.assertNull(exceptions);
    }
}