package org.lint.azzert.context;

import org.junit.jupiter.api.Test;

class MethodCallMetadataTest {

    @Test
    void getOwnerPackage() {
        new MethodCallMetadata("org/mockito/Mockito", "verify", 0).getFullyQualifiedPackageName();
    }
}