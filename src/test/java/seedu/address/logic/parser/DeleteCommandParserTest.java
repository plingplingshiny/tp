package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Integration-style tests for delete parsing via AddressBookParser to exercise real tokenization.
 */
public class DeleteCommandParserTest {

    private final AddressBookParser addressParser = new AddressBookParser();

    @Test
    public void parse_validIndex_returnsDeleteCommand() throws Exception {
        DeleteCommand cmd = (DeleteCommand) addressParser.parseCommand("delete 1");
        assertEquals(new DeleteCommand(Index.fromOneBased(1)), cmd);
    }

    @Test
    public void parse_indexWithConfirmation_throwsParseException() {
        assertThrows(ParseException.class, () -> addressParser.parseCommand("delete 1 confirm/yes"));
    }

    @Test
    public void parse_singleName_returnsDeleteByName() throws Exception {
        DeleteCommand cmd = (DeleteCommand) addressParser.parseCommand("delete n/Alice");
        assertEquals(new DeleteCommand(new Name("Alice")), cmd);
    }

    @Test
    public void parse_singleName_withConfirmationVariants() throws Exception {
        assertEquals(new DeleteCommand(new Name("Alice"), true),
                addressParser.parseCommand("delete n/Alice confirm/yes"));
        assertEquals(new DeleteCommand(new Name("Alice"), true),
                addressParser.parseCommand("delete n/Alice confirm/y"));
        assertEquals(new DeleteCommand(new Name("Alice"), false),
                addressParser.parseCommand("delete n/Alice confirm/no"));
        assertEquals(new DeleteCommand(new Name("Alice"), false),
                addressParser.parseCommand("delete n/Alice confirm/n"));
        // empty confirm value treated as false
        assertEquals(new DeleteCommand(new Name("Alice"), false),
                addressParser.parseCommand("delete n/Alice confirm/"));
    }

    @Test
    public void parse_multipleNames_returnsDeleteMultiple() throws Exception {
        DeleteCommand cmd = (DeleteCommand) addressParser.parseCommand("delete n/Alice n/Benson");
        List<Name> expectedNames = List.of(new Name("Alice"), new Name("Benson"));
        assertEquals(new DeleteCommand(expectedNames, false), cmd);
    }

    @Test
    public void parse_multipleConfirmationPrefixes_throwsParseException() {
        assertThrows(ParseException.class, () -> addressParser.parseCommand("delete n/Alice confirm/yes confirm/no"));
    }

    @Test
    public void parse_invalidConfirmation_throwsParseException() {
        assertThrows(ParseException.class, () -> addressParser.parseCommand("delete n/Alice confirm/maybe"));
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        // invalid name will be caught and wrapped as invalid command format
        assertThrows(ParseException.class, () -> addressParser.parseCommand("delete n/12345"));
    }
}
