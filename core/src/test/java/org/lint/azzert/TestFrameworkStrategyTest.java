package org.lint.azzert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.strategy.framework.JUnit5Strategy;

import java.util.Set;

public class TestFrameworkStrategyTest extends AbstractTest{


    @Test
    void assertGetTestFramework() throws Exception {
        final Context context = TestUtil.buildContext("sample.mockito");

        TestFrameworkStrategy strategy = context.getMethods().iterator().next().getTestFramework();
        Assertions.assertEquals(JUnit5Strategy.class, strategy.getClass());
    }

    @Test
    void removeCallsThatAreNotAsserts_forJUnit4() throws Exception{
        final Context context = TestUtil.buildContext("sample.junit4");

        final Set<MethodMetadata> methods = context.getMethods();
        TestFrameworkStrategy strategy = methods.iterator().next().getTestFramework();
        methods.forEach(m-> strategy.removeCallsThatAreNotAsserts(m, context));

//        String content = super.render(methods);
//        System.out.println(content);

        Assertions.assertEquals(7, methods.size());
        MethodMetadata method = methods.stream().filter(
                        m -> m.getFileName().equals("AssertJunit4Style.java") &&
                                m.getMethodName().equals("whenExceptionThrown_thenExpectationSatisfied")).findAny().get();
        Assertions.assertEquals(1, method.getVerificationsCount());
        Assertions.assertTrue(assertsInMethod.apply(methods, "withoutAsserts").isEmpty());
        Assertions.assertEquals(1, assertsInMethod.apply(methods, "withAsserts").size());

    }

    @Test
    void removeCallsThatAreNotAsserts_forMockito() throws Exception{
        final Context context = TestUtil.buildContext("sample.mockito");

        TestFrameworkStrategy strategy = context.getMethods().iterator().next().getTestFramework();
        strategy.removeCallsThatAreNotAsserts(context.getMethods().iterator().next(), context);
//        context.getMethods().forEach( m-> System.out.println(m));
        //1 test method
        Assertions.assertEquals(1, context.getMethods().size());
        //3 calls to 'verify'
        Assertions.assertEquals(2, context.getMethods().iterator().next().getMethodCalls().size());
    }

}
