package org.lint.azzert.command;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DisabledTestMethod {

    @Test
    @Disabled
    void iAmDisabled(){}

    @Test
    void testMe(){}

    @Disabled
    void youWouldThinkIAmATestButIAmNot(){}
}
