package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        List<String> rawNames = argMultimap.getAllValues(PREFIX_NAME);
        List<String> rawTags  = argMultimap.getAllValues(PREFIX_TAG);

        // Split each value by whitespace and dedupe
        List<String> nameKeywords = splitNormalizeDedup(rawNames);
        List<String> tagKeywords  = splitNormalizeDedup(rawTags);

        if (nameKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(nameKeywords, tagKeywords));
    }

    private static List<String> splitNormalizeDedup(List<String> rawValues) {
        Set<String> out = new LinkedHashSet<>();
        for (String v : rawValues) {
            if (v == null) continue;
            for (String token : v.trim().split("\\s+")) {  // <-- split multi-word
                String t = token.trim().toLowerCase();
                if (!t.isEmpty()) {
                    out.add(t);
                }
            }
        }
        return new ArrayList<>(out);
    }

}
