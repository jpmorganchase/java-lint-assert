package com.jpmorgan.java.lint;

import com.jpmorgan.java.lint.azzert.context.TestMethodContext;
import com.jpmorgan.java.lint.azzert.strategy.ConsoleOutputStrategy;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

class LintPluginTest {

    static Logger log = LoggerFactory.getLogger(LintPluginTest.class);

    @Test
    void lint() throws IOException, ParseException {
        LintTests lt = new LintTests();
        lt.setPackageName("com.jpmorgan.java.lint");
        Set<TestMethodContext> result = lt.lintAssert();

        System.out.println(new ConsoleOutputStrategy(result).render());

        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(0, result.size());
    }

    //TODO::handle methods in nested classes
    class InnerClass {
        @Test
        void dummy() {

        }
    }

    //TODO::handle @Ignore and @Disabled methods
    @org.junit.Ignore
    @org.junit.jupiter.api.Disabled
    void ignoredOrdisabled(){

    }

    //TODO::handle @Ignore and @Disabled at the class level
    @org.junit.Ignore
    @org.junit.jupiter.api.Disabled
    class Disabled {
        @Test
        void dummy() {

        }
    }
}