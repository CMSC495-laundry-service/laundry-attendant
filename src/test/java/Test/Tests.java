package Test;

import org.junit.jupiter.api.Test;

import cmsc495.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class Tests {

    @Test
    public void testHelloWorld() {
        assertEquals("Hello World",Main.hello());
    }

}