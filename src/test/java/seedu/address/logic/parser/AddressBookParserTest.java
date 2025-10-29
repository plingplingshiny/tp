package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ConfirmCommand;
import seedu.address.logic.commands.ConfirmationManager;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddressBookParserTest {

    private AddressBookParser parser;

    @BeforeEach
    public void setUp() {
        parser = new AddressBookParser();
    }

    @Test
    public void parseCommandAddReturnsAddCommand() throws Exception {
        // use a minimal valid add command matching AddCommand.MESSAGE_USAGE
        String addArgs = "add i/sell n/John Doe p/98765432 e/johnd@example.com "
                + "a/SomeAddress pt/hdb pr/450000";
        assertTrue(parser.parseCommand(addArgs) instanceof AddCommand);
    }

    @Test
    public void parseCommandHelpReturnsHelp() throws Exception {
        assertTrue(parser.parseCommand("help") instanceof HelpCommand);
    }

    @Test
    public void parseCommandDeleteParsesToDelete() throws Exception {
        assertTrue(parser.parseCommand("delete 1") instanceof DeleteCommand);
    }

    @Test
    public void parseCommandSingleTokenYesNoConfirmationBehavior() throws Exception {
        // when no pending confirmation, 'yes' and 'no' map to ConfirmCommand yes/no
        assertTrue(parser.parseCommand("yes") instanceof ConfirmCommand);
        assertTrue(parser.parseCommand("no") instanceof ConfirmCommand);
    }

    @Test
    public void parseCommandSingleTokenInvalidConfirmationWhenPending() throws Exception {
        // set a pending confirmation by using delete that triggers pending
        // Simulate by setting ConfirmationManager directly
        seedu.address.testutil.PersonBuilder pb = new seedu.address.testutil.PersonBuilder().withName("Alice");
        seedu.address.model.person.Person p = pb.build();
        ConfirmationManager.setPending(java.util.Collections.singletonList(p),
                java.util.Collections.emptyList());
        // now a single token that is not a known command should return InvalidConfirmationCommand
        assertTrue(parser.parseCommand("foobar") instanceof seedu.address.logic.commands.InvalidConfirmationCommand);
        // clear pending
        ConfirmationManager.clearPending();
    }

    @Test
    public void parseCommand_invalidCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand("unknowncommand"));
    }
}
