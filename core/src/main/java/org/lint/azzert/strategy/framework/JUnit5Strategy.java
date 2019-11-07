package org.lint.azzert.strategy.framework;

import java.util.Arrays;
import java.util.List;

public class JUnit5Strategy extends JUnit4Strategy{

    @Override
    public String getSupportedFramework(){return "Lorg/junit/jupiter/api/Test;";}

    @Override
    public List<String> getAssertApis(){return Arrays.asList("org.junit.jupiter.api");}

    @Override
    public String getDisabledAnnotation(){return "Lorg/junit/jupiter/api/Disabled;";}
}
