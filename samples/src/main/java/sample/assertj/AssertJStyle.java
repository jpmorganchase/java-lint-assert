package sample.assertj;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class AssertJStyle implements WithAssertions {

    @Test
    public void withAssertions_examples() {
        assertThat("The Lord of the Rings").isNotNull()
                .startsWith("The")
                .contains("Lord")
                .endsWith("Rings");
    }

}
