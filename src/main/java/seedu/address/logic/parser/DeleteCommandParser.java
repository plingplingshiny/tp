package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIRM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/** Parses input arguments and creates a DeleteCommand. */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    public static final String MESSAGE_INVALID_CONFIRMATION =
            "Confirmation value must be 'yes', 'no', 'y', 'n', or empty.";

    @Override
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CONFIRM);

        List<String> nameValues = argMultimap.getAllValues(PREFIX_NAME);
        boolean hasConfirmation = argMultimap.getValue(PREFIX_CONFIRM).isPresent();

        if (!nameValues.isEmpty()) {
            return parseDeleteByNames(argMultimap, nameValues, hasConfirmation);
        } else {
            // CHANGED: pass argMultimap so we can read the preamble (index) safely
            return parseDeleteByIndex(argMultimap, hasConfirmation);
        }
    }

    private DeleteCommand parseDeleteByNames(ArgumentMultimap argMultimap, List<String> nameValues,
                                             boolean hasConfirmation) throws ParseException {
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        List<Name> names = parseNames(nameValues);
        boolean isConfirmed = parseConfirmation(hasConfirmation, argMultimap);

        if (names.size() == 1) {
            return new DeleteCommand(names.get(0), isConfirmed);
        } else {
            return new DeleteCommand(names, isConfirmed);
        }
    }

    private List<Name> parseNames(List<String> nameValues) throws ParseException {
        List<Name> names = new ArrayList<>();
        for (String nameValue : nameValues) {
            try {
                names.add(ParserUtil.parseName(nameValue));
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteCommand.MESSAGE_USAGE), pe);
            }
        }
        return names;
    }

    private boolean parseConfirmation(boolean hasConfirmation, ArgumentMultimap argMultimap) throws ParseException {
        if (!hasConfirmation) {
            return false;
        }

        // NEW: reject multiple confirmation prefixes
        List<String> confirms = argMultimap.getAllValues(PREFIX_CONFIRM);
        if (confirms.size() > 1) {
            throw new ParseException("Only one confirmation (c/) is allowed.");
        }

        String confirmValue = confirms.get(0).toLowerCase().trim();
        // NEW: accept short forms y/n as well
        if ("yes".equals(confirmValue) || "y".equals(confirmValue)) {
            return true;
        } else if ("no".equals(confirmValue) || "n".equals(confirmValue) || confirmValue.isEmpty()) {
            return false;
        } else {
            throw new ParseException(MESSAGE_INVALID_CONFIRMATION);
        }
    }

    // CHANGED: take ArgumentMultimap and read the preamble for the index
    private DeleteCommand parseDeleteByIndex(ArgumentMultimap argMultimap, boolean hasConfirmation)
            throws ParseException {
        if (hasConfirmation) {
            throw new ParseException("Confirmation is not required for deletion by index");
        }

        String preamble = argMultimap.getPreamble();
        try {
            Index index = ParserUtil.parseIndex(preamble);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
