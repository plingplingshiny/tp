package seedu.address.logic.parser;

<<<<<<< HEAD
=======
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIRM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;

>>>>>>> 3c4158d5f330460e632ffec71dcce374ea6e30b4
import seedu.address.commons.core.index.Index;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.DeleteCommand;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected
     * format
     */
    @Override
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CONFIRM);

<<<<<<< HEAD
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return parseDeleteByName(argMultimap);
        } else {
            return parseDeleteByIndex(args);
        }
    }

    private DeleteCommand parseDeleteByName(ArgumentMultimap argMultimap) throws ParseException {
        String name = argMultimap.getValue(PREFIX_NAME).get();
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return new DeleteCommand(new Name(name));
    }

    private DeleteCommand parseDeleteByIndex(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
=======
        List<String> nameValues = argMultimap.getAllValues(PREFIX_NAME);
        boolean hasConfirmation = argMultimap.getValue(PREFIX_CONFIRM).isPresent();

        if (!nameValues.isEmpty()) {
            return parseDeleteByNames(argMultimap, nameValues, hasConfirmation);
        } else {
            return parseDeleteByIndex(args, hasConfirmation);
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
            return new DeleteCommand(names.get(0));
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
>>>>>>> 3c4158d5f330460e632ffec71dcce374ea6e30b4
        }
        return names;
    }

    private boolean parseConfirmation(boolean hasConfirmation, ArgumentMultimap argMultimap) throws ParseException {
        if (!hasConfirmation) {
            return false;
        }

        String confirmValue = argMultimap.getValue(PREFIX_CONFIRM).get().toLowerCase().trim();
        if ("yes".equals(confirmValue)) {
            return true;
        } else if (!confirmValue.isEmpty()) {
            throw new ParseException("Confirmation value must be 'yes' or leave it empty");
        }
        return false;
    }

    private DeleteCommand parseDeleteByIndex(String args, boolean hasConfirmation) throws ParseException {
        if (hasConfirmation) {
            throw new ParseException("Confirmation is not required for deletion by index");
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
