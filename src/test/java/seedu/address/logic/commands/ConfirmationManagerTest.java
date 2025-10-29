package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@link ConfirmationManager}.
 */
public class ConfirmationManagerTest {

    @BeforeEach
    public void setUp() {
        ConfirmationManager.clearPending();
    }

    @Test
    public void setPendingClearPendingBehaviour() {
        Person p = new PersonBuilder().withName("Alice").build();
        Name notFound = new Name("Bob");

        ConfirmationManager.setPending(Collections.singletonList(p), Collections.singletonList(notFound));
        assertTrue(ConfirmationManager.hasPending());
        assertEquals(1, ConfirmationManager.getPersonsToDelete().size());
        assertEquals(1, ConfirmationManager.getNotFoundNames().size());

        ConfirmationManager.clearPending();
        assertFalse(ConfirmationManager.hasPending());
        assertTrue(ConfirmationManager.getPersonsToDelete().isEmpty());
        assertTrue(ConfirmationManager.getNotFoundNames().isEmpty());
    }
}
