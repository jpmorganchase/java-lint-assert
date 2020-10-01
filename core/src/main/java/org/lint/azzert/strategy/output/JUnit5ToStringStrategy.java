package org.lint.azzert.strategy.output;

import org.lint.azzert.ToStringStrategy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class JUnit5ToStringStrategy implements ToStringStrategy {

    public Collection<String> getTableHeaders (){
        return Collections.unmodifiableList(Arrays.asList(
                "Package", "Test file name", "Test method name", "Asserts count"));
    }

    @Override
    public String render() {
        return null;
    }
}
