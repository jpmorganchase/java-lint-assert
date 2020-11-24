package org.lint.azzert.strategy.framework;

public class JUnit5Strategy extends JUnit4Strategy{

    @Override
    public String getSupportedFramework(){
        return "Lorg/junit/jupiter/api/Test;";
    }

    @Override
    public String getAssertApi(){
        return "org.junit.jupiter.api";
    }

    @Override
    public String getDisabledAnnotation(){
        return "Lorg/junit/jupiter/api/Disabled;";
    }
}
