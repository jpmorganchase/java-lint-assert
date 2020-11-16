package org.lint.azzert;

import org.lint.azzert.context.MethodCallMetadata;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.strategy.output.ToStringStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public abstract class LintAssertTest {

    //find a method in the result set
    protected final BiFunction<Set<MethodMetadata>, String, List<MethodMetadata>> findMethod = (mtds, name) -> mtds.stream().filter(
            f -> name.equalsIgnoreCase(f.getMethodName())).collect(Collectors.toList());

    //count asserts in a method
    protected final BiFunction<Set<MethodMetadata>,String, Collection<MethodCallMetadata>> assertsInMethod = (mtds, name) ->
            findMethod.apply(mtds, name).get(0).getMethodCalls();


    protected String render(Set<MethodMetadata> methods) {
        ToStringStrategy strategy = new ToStringStrategy(methods);
        return strategy.render();
    }

    int countMethodOccurrencesInFile(final Set<MethodMetadata> methods, String file, String method){
        final BiFunction<String, String, Long> countOccurences = (fileName, methodName)
                -> methods.stream().filter(m -> m.getFileName().equals(fileName) && m.getMethodName().equals(methodName)).count();

        return countOccurences.apply(file, method).intValue();
    }

}
