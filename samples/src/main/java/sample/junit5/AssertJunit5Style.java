package sample.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertJunit5Style {

    @Test
    void withoutAsserts() {
        Function<String, String> toLowerCase = input -> input.toLowerCase();
        toLowerCase.apply("ZZZ");
    }

    @Test
    void withAsserts() {
        assertTrue(true);
        Assertions.assertArrayEquals(new int[]{1,2}, new int[]{1,2} );
    }

    @Test
    @Disabled
    void iAmDisabled(){
        throw new UnsupportedOperationException();
    }

    void iAmNotATest1(){
        throw new UnsupportedOperationException();
    }

    @Deprecated
    void iAmNotATest2(){
        throw new UnsupportedOperationException();
    }

    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("1a");
        });

        String expectedMessage = "For input string";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
