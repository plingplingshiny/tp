package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTENTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 * Supports prefix-based searches by name, phone, email, address, tag,
 * price, property type, and intention.
 */
public class FindCommandParser implements Parser<FindCommand> {

    @Override
    public FindCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments should not be null.";

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_PRICE, PREFIX_PROPERTY_TYPE, PREFIX_INTENTION);

        assert argMultimap != null : "ArgumentMultimap should not be null after tokenization.";

        List<String> rawNames = argMultimap.getAllValues(PREFIX_NAME);
        List<String> rawPhones = argMultimap.getAllValues(PREFIX_PHONE);
        List<String> rawEmails = argMultimap.getAllValues(PREFIX_EMAIL);
        List<String> rawAddresses = argMultimap.getAllValues(PREFIX_ADDRESS);
        List<String> rawTags = argMultimap.getAllValues(PREFIX_TAG);
        List<String> rawPrices = argMultimap.getAllValues(PREFIX_PRICE);
        List<String> rawPropertyTypes = argMultimap.getAllValues(PREFIX_PROPERTY_TYPE);
        List<String> rawIntentions = argMultimap.getAllValues(PREFIX_INTENTION);

        assert rawNames != null && rawPhones != null && rawEmails != null && rawAddresses != null;
        assert rawTags != null && rawPrices != null && rawPropertyTypes != null && rawIntentions != null;

        List<String> nameKeywords = splitNormalizeDedup(rawNames);
        List<String> phoneKeywords = splitNormalizeDedup(rawPhones);
        List<String> emailKeywords = splitNormalizeDedup(rawEmails);
        List<String> addressKeywords = splitNormalizeDedup(rawAddresses);
        List<String> tagKeywords = splitNormalizeDedup(rawTags);
        List<String> priceKeywords = splitNormalizeDedup(rawPrices);
        List<String> propertyTypeKeywords = splitNormalizeDedup(rawPropertyTypes);
        List<String> intentionKeywords = splitNormalizeDedup(rawIntentions);

        if (nameKeywords.isEmpty() && phoneKeywords.isEmpty() && emailKeywords.isEmpty()
                && addressKeywords.isEmpty() && tagKeywords.isEmpty()
                && priceKeywords.isEmpty() && propertyTypeKeywords.isEmpty() && intentionKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                nameKeywords, phoneKeywords, emailKeywords, addressKeywords,
                tagKeywords, priceKeywords, propertyTypeKeywords, intentionKeywords);

        return new FindCommand(predicate);
    }

    private static List<String> splitNormalizeDedup(List<String> rawValues) {
        Set<String> out = new LinkedHashSet<>();
        for (String v : rawValues) {
            if (v == null) {
                continue;
            }
            for (String token : v.trim().split("\\s+")) {
                String t = token.trim().toLowerCase();
                if (!t.isEmpty()) {
                    out.add(t);
                }
            }
        }
        return new ArrayList<>(out);
    }
}
