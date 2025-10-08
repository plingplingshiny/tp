package seedu.address.logic.parser;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            // Delete by name
            String name = argMultimap.getValue(PREFIX_NAME).get();
            if (!argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            return new DeleteCommand(new Name(name));
        } else {
            // Delete by index
            try {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
            }
        }
    }

}
