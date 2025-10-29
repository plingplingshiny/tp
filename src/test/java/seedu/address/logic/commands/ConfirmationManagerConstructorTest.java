package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;

/**
 * Tests to ensure the private/default constructor of utility class can be invoked for coverage.
 */
public class ConfirmationManagerConstructorTest {

    @Test
    public void callPrivateConstructor_usingReflection() throws Exception {
        Constructor<ConfirmationManager> ctor = ConfirmationManager.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        ConfirmationManager instance = ctor.newInstance();
        assertNotNull(instance);
    }
}

