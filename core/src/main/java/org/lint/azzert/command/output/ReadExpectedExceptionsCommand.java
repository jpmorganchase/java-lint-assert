package org.lint.azzert.command.output;

import org.javatuples.Pair;
import org.lint.azzert.OutputFormatterCommand;
import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.context.MethodMetadata;

import java.util.Collection;
import java.util.Set;

public class ReadExpectedExceptionsCommand implements OutputFormatterCommand<Integer> {

    //FIXME:: this should decipher 
    @Override
    public Integer execute(Set<MethodMetadata> testMethods) {
        Integer longestAnnotationLength = 0;

        for (MethodMetadata method : testMethods) {
            Set<AnnotationMetadata> annotations = method.getAnnotations();
            for (AnnotationMetadata annotation : annotations) {
                final Collection<Pair<String, String>> parameters = annotation.getParameters();

//                if (parameters.size() > 0)
//                    longestAnnotationLength = parameters.iterator().next().getValue(1).toString().length();
            }
        }
        return longestAnnotationLength;
    }
}
