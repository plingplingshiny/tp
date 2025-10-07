package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Tests for {@code FindCommandParser}.
 * Updated for n/ and t/ prefixes, supporting OR semantics and multi-word values.
 */
public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("alice", "bob"), Collections.emptyList());
        FindCommand expectedFindCommand = new FindCommand(predicate);

        // single prefix, multiple words
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple prefixes with single keywords
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // extra whitespace
        assertParseSuccess(parser, " \n n/Alice \n \t n/Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("friends", "colleagues"));
        FindCommand expectedFindCommand = new FindCommand(predicate);

        assertParseSuccess(parser, " t/friends t/colleagues", expectedFindCommand);
    }

    @Test
    public void parse_bothNameAndTagArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("alice"), Arrays.asList("friend"));
        FindCommand expectedFindCommand = new FindCommand(predicate);

        assertParseSuccess(parser, " n/Alice t/friend", expectedFindCommand);
    }
}
