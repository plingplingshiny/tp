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

        args = args.replaceAll(
                "(pr/\\s*)(\\d+(?:,\\d+)*(?:\\.\\d+)?)\\s*-\\s*(\\d+(?:,\\d+)*(?:\\.\\d+)?)", "pr/$2-$3");

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_PRICE, PREFIX_PROPERTY_TYPE, PREFIX_INTENTION);

        List<String> rawNames = argMultimap.getAllValues(PREFIX_NAME);
        List<String> rawPhones = argMultimap.getAllValues(PREFIX_PHONE);
        List<String> rawEmails = argMultimap.getAllValues(PREFIX_EMAIL);
        List<String> rawAddresses = argMultimap.getAllValues(PREFIX_ADDRESS);
        List<String> rawTags = argMultimap.getAllValues(PREFIX_TAG);
        List<String> rawPrices = argMultimap.getAllValues(PREFIX_PRICE);
        List<String> rawPropertyTypes = argMultimap.getAllValues(PREFIX_PROPERTY_TYPE);
        List<String> rawIntentions = argMultimap.getAllValues(PREFIX_INTENTION);

        List<String> nameKeywords = splitNormalizeDedup(rawNames);
        List<String> phoneKeywords = splitNormalizeDedup(rawPhones);
        List<String> emailKeywords = splitNormalizeDedup(rawEmails);
        List<String> addressKeywords = splitNormalizeDedup(rawAddresses);
        List<String> tagKeywords = splitNormalizeDedup(rawTags);
        List<String> priceKeywords = splitNormalizeDedup(rawPrices);
        List<String> propertyTypeKeywords = splitNormalizeDedup(rawPropertyTypes);
        List<String> intentionKeywords = splitNormalizeDedup(rawIntentions);

        for (String keyword : priceKeywords) {
            String errorMessage = validatePriceKeyword(keyword);
            if (errorMessage != null) {
                throw new ParseException(errorMessage);
            }
        }

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

    /**
     * Validates a price keyword and returns an error message if invalid, or {@code null} if valid.
     * Handles both single numeric prices (e.g. "2000") and ranges (e.g. "2000-3000").
     */
    static String validatePriceKeyword(String keyword) {
        String cleaned = keyword.replaceAll("\\s+", "");

        // --- Handle range inputs ---
        if (cleaned.contains("-")) {
            // too many dashes (e.g. 2000--3000)
            if (cleaned.indexOf('-') != cleaned.lastIndexOf('-')) {
                return "Invalid price range: too many '-' characters (" + keyword + ").";
            }

            // preserve empty trailing parts
            String[] parts = cleaned.split("-", -1);
            if (parts.length != 2) {
                return "Invalid price range format (" + keyword + ").";
            }

            String lowerPart = parts[0].replaceAll(",", "");
            String upperPart = parts[1].replaceAll(",", "");

            if (lowerPart.isEmpty()) {
                return "Invalid price range: missing lower bound (" + keyword + ").";
            }
            if (upperPart.isEmpty()) {
                return "Invalid price range: missing upper bound (" + keyword + ").";
            }

            try {
                double lower = Double.parseDouble(lowerPart);
                double upper = Double.parseDouble(upperPart);
                if (lower > upper) {
                    return "Invalid price range: lower bound cannot exceed upper bound (" + keyword + ").";
                }
            } catch (NumberFormatException e) {
                return "Invalid price range: non-numeric value detected (" + keyword + ").";
            }

            return null; // valid range
        }

        // --- Handle single price inputs ---
        String numeric = cleaned.replaceAll(",", "");
        try {
            Double.parseDouble(numeric);
        } catch (NumberFormatException e) {
            return "Invalid price: must be a numeric value (" + keyword + ").";
        }

        return null; // valid single price
    }



}
