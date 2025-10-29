package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;

/**
 * Unit tests for {@link InvalidConfirmationCommand}.
 */
public class InvalidConfirmationCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        ConfirmationManager.clearPending();
    }

    @Test
    public void execute_noPending_returnsNoPendingMessage() throws Exception {
        InvalidConfirmationCommand cmd = new InvalidConfirmationCommand();
        CommandResult result = cmd.execute(model);
        assertEquals(ConfirmCommand.MESSAGE_NO_PENDING, result.getFeedbackToUser());
    }

    @Test
    public void execute_withPending_returnsInvalidMessage() throws Exception {
        // set a pending confirmation so invalid input is meaningful
        ConfirmationManager.setPending(Collections.singletonList(TypicalPersons.ALICE), Collections.emptyList());
        InvalidConfirmationCommand cmd = new InvalidConfirmationCommand();
        CommandResult result = cmd.execute(model);
        assertEquals(ConfirmCommand.MESSAGE_INVALID, result.getFeedbackToUser());
    }
}

