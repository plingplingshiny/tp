package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an invalid confirmation input while a confirmation is pending.
 */
public class InvalidConfirmationCommand extends Command {

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!ConfirmationManager.hasPending()) {
            return new CommandResult(ConfirmCommand.MESSAGE_NO_PENDING);
        }
        return new CommandResult(ConfirmCommand.MESSAGE_INVALID);
    }
}
