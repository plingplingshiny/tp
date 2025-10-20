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
 * Updated for all prefixes: n/, p/, e/, a/, t/, pr/, pt/, and i/.
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
                new PersonContainsKeywordsPredicate(
                        Arrays.asList("alice", "bob"),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        FindCommand expectedFindCommand = new FindCommand(predicate);

        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);
        assertParseSuccess(parser, " \n n/Alice \n \t n/Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Arrays.asList("1234", "9876"),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " p/1234 p/9876", expectedFindCommand);
    }

    @Test
    public void parse_validEmailArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Arrays.asList("gmail", "example"),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " e/gmail e/example", expectedFindCommand);
    }

    @Test
    public void parse_validAddressArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Arrays.asList("street", "avenue"),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " a/street a/avenue", expectedFindCommand);
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Arrays.asList("friends", "colleagues"),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " t/friends t/colleagues", expectedFindCommand);
    }

    @Test
    public void parse_validPriceArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Arrays.asList("500000", "600000"),
                        Collections.emptyList(),
                        Collections.emptyList());
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " pr/500000 pr/600000", expectedFindCommand);
    }

    @Test
    public void parse_validPropertyTypeArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Arrays.asList("hdb", "condo"),
                        Collections.emptyList());
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " pt/HDB pt/Condo", expectedFindCommand);
    }

    @Test
    public void parse_validIntentionArgs_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Arrays.asList("buy", "sell"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " i/Buy i/Sell", expectedFindCommand);
    }

    @Test
    public void parse_multiplePrefixes_returnsFindCommand() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Arrays.asList("alice"),
                        Arrays.asList("9123"),
                        Arrays.asList("gmail"),
                        Arrays.asList("street"),
                        Arrays.asList("friend"),
                        Arrays.asList("500000"),
                        Arrays.asList("hdb"),
                        Arrays.asList("buy"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " n/Alice p/9123 e/gmail a/street t/friend pr/500000 pt/HDB i/Buy",
                expectedFindCommand);
    }
}
